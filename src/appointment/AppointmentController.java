package appointment;

import dao.implementations.AppointmentDao;
import dao.implementations.ContactDao;
import dao.implementations.CustomerDao;
import dao.models.Appointment;
import dao.models.Contact;
import dao.models.Country;
import dao.models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.util.Callback;
import resources.Loader;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AppointmentController {
    @FXML
    public Label appIDLbl;
    @FXML
    public TextField appIDTxt;
    @FXML
    public Label appCustLbl;
    @FXML
    public ComboBox<Customer> appCustCombo;
    @FXML
    public Label appContactLbl;
    @FXML
    public ComboBox<Contact> appContactCombo;
    @FXML
    public Label appTitleLbl;
    @FXML
    public TextField appTitleTxt;
    @FXML
    public Label appDescLbl;
    @FXML
    public TextField appDescTxt;
    @FXML
    public Label appLocationLbl;
    @FXML
    public TextField appLocationTxt;
    @FXML
    public Label appTypeLbl;
    @FXML
    public TextField appTypeTxt;
    @FXML
    public Label appStartLbl;
    @FXML
    public DatePicker appStartDate;
    @FXML
    public Label appStopLbl;
    @FXML
    public ComboBox<Integer> appStartHourCombo;
    @FXML
    public ComboBox<Integer> appStartMinCombo;
    @FXML
    public Label appStartTZLbl;
    @FXML
    public ComboBox<Integer> appEndHourCombo;
    @FXML
    public ComboBox<Integer> appEndMinCombo;
    @FXML
    public Label appEndTZLbl;
    @FXML
    public DatePicker appStopDate;

    private Loader loader = new Loader();

    public void onActionSave(ActionEvent actionEvent) {
        // Default to a new customer.
        boolean isNew = true;

        // Collects error messages to display if error.
        StringBuilder errors = new StringBuilder();

        // Create all vars needed for a
        int appID = 0, custID = 0, contactID = 0, startHour = 0, startMin = 0, stopHour = 0, stopMin = 0;
        String title, desc, location, type;
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        // Starting getting the values for the above vars.
        try {
            appID = Integer.parseInt(appIDTxt.getText().trim());
            isNew = false;
        } catch (NumberFormatException ignored) {}

        try {
            custID = appCustCombo.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            errors.append("You must select a Customer\n");
        }

        try {
            contactID = appContactCombo.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            errors.append("You must select a Contact\n");
        }

        title = appTitleTxt.getText().trim();
        if (title.length() < 1) {
            errors.append("Title must have a value.\n");
        }

        desc = appDescTxt.getText().trim();
        if (desc.length() < 1) {
            errors.append("Description must have a value.\n");
        }

        location = appTitleTxt.getText().trim();
        if (location.length() < 1) {
            errors.append("Location must have a value.\n");
        }

        type = appDescTxt.getText().trim();
        if (type.length() < 1) {
            errors.append("Type must have a value.\n");
        }

        try {
            startDate.setTimeInMillis(java.sql.Date.valueOf(appStartDate.getValue()).getTime());
            startDate.set(Calendar.HOUR, appStartHourCombo.getValue());
            startDate.set(Calendar.MINUTE, appStartMinCombo.getValue());

            System.out.println("Start Date: " + startDate.getTime());
        } catch (NullPointerException e) {
            errors.append("You must set a Start Date and Time.\n");
        }

        try {
            endDate.setTimeInMillis(java.sql.Date.valueOf(appStopDate.getValue()).getTime());
            endDate.set(Calendar.HOUR, appEndHourCombo.getValue());
            endDate.set(Calendar.MINUTE, appEndMinCombo.getValue());

            System.out.println("End Date: " + endDate.getTime());
        } catch (NullPointerException e) {
            errors.append("You must set a End Date and Time.\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText(errors.toString());

            alert.showAndWait();
        }else {
            AppointmentDao appointmentDao = new AppointmentDao();

            if (isNew) {
                Appointment appointment = new Appointment(
                        appID,
                        custID,
                        1,
                        contactID,
                        title,
                        desc,
                        location,
                        type,
                        "test",
                        "test",
                        startDate,
                        endDate,
                        Calendar.getInstance(),
                        Calendar.getInstance()
                );

                appointmentDao.save(appointment);
            } else {
                Appointment appointment = new Appointment(
                        appID,
                        custID,
                        1,
                        contactID,
                        title,
                        desc,
                        location,
                        type,
                        "test",
                        "test",
                        startDate,
                        endDate,
                        Calendar.getInstance(),
                        Calendar.getInstance()
                );

                appointmentDao.update(appointment);

            }
            loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
        }
    }

    public void onActionCancel(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
    }

    public void initialize(){
        // Add Customer Selection
        Callback<ListView<Customer>, ListCell<Customer>> customerFactory = lv -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer customer, boolean b) {
                super.updateItem(customer, b);
                setText(b ? "" : customer.getName());
            }
        };

        CustomerDao customerDao = new CustomerDao();
        appCustCombo.setPromptText("Choose Customer");
        appCustCombo.setItems(customerDao.getAll());
        appCustCombo.setCellFactory(customerFactory);
        appCustCombo.setButtonCell(customerFactory.call(null));

        // Add Contact Selection
        Callback<ListView<Contact>, ListCell<Contact>> contactFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean b) {
                super.updateItem(contact, b);
                setText(b ? "" : contact.getName());
            }
        };

        ContactDao contactDao = new ContactDao();
        appContactCombo.setPromptText("Choose Contact");
        appContactCombo.setItems(contactDao.getAll());
        appContactCombo.setCellFactory(contactFactory);
        appContactCombo.setButtonCell(contactFactory.call(null));

        // Deal With DateTime for Form
        Calendar localTimeZone = Calendar.getInstance();

        appStartTZLbl.setText(localTimeZone.getTimeZone().getDisplayName(false,TimeZone.SHORT));
        appEndTZLbl.setText(localTimeZone.getTimeZone().getDisplayName(false,TimeZone.SHORT));

        appStartHourCombo.setPromptText("Hour");
        appStartMinCombo.setPromptText("Min");
        appEndHourCombo.setPromptText("Hour");
        appEndMinCombo.setPromptText("Min");

        for (int i = 0; i <= 23; i++) {
            appStartHourCombo.getItems().add(i);
            appEndHourCombo.getItems().add(i);
        }
        for (int i = 0; i <= 59; i++) {
            appStartMinCombo.getItems().add(i);
            appEndMinCombo.getItems().add(i);
        }

    }
}
