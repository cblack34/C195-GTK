package appointment;

import dao.implementations.AppointmentDao;
import dao.implementations.ContactDao;
import dao.implementations.CustomerDao;
import dao.implementations.UserDao;
import dao.models.Appointment;
import dao.models.Contact;
import dao.models.Customer;
import dao.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.util.Callback;
import resources.LoadObject;
import resources.Loader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class AppointmentController implements LoadObject {
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
    @FXML
    public Label appUserLbl;
    @FXML
    public ComboBox<User> appUserCombo;

    private Loader loader = new Loader();

    /** Saves to appointment to the Database.
     * @param actionEvent Save button click event.
     */
    public void onActionSave(ActionEvent actionEvent) {
        // Default to a new customer.
        boolean isNew = true;

        // Collects error messages to display if error.
        StringBuilder errors = new StringBuilder();

        // Create all vars needed for a
        int appID = 0, custID = 0, contactID = 0, startHour = 0, startMin = 0, stopHour = 0, stopMin = 0, userID = 0;
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

        try {
            userID = appUserCombo.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            errors.append("You must select a User\n");
        }


        title = appTitleTxt.getText().trim();
        if (title.length() < 1) {
            errors.append("Title must have a value.\n");
        }

        desc = appDescTxt.getText().trim();
        if (desc.length() < 1) {
            errors.append("Description must have a value.\n");
        }

        location = appLocationTxt.getText().trim();
        if (location.length() < 1) {
            errors.append("Location must have a value.\n");
        }

        type = appTypeTxt.getText().trim();
        if (type.length() < 1) {
            errors.append("Type must have a value.\n");
        }

        try {
            startDate.setTimeInMillis(java.sql.Date.valueOf(appStartDate.getValue()).getTime());
            startDate.set(Calendar.HOUR, appStartHourCombo.getValue());
            startDate.set(Calendar.MINUTE, appStartMinCombo.getValue());
        } catch (NullPointerException e) {
            errors.append("You must set a Start Date and Time.\n");
        }

        try {
            endDate.setTimeInMillis(java.sql.Date.valueOf(appStopDate.getValue()).getTime());
            endDate.set(Calendar.HOUR, appEndHourCombo.getValue());
            endDate.set(Calendar.MINUTE, appEndMinCombo.getValue());
        } catch (NullPointerException e) {
            errors.append("You must set a End Date and Time.\n");
        }

        try {
            if (endDate.compareTo(startDate) != 1){
                errors.append("Start Date and Time must be before the end Date and Time.");
            }
        } catch (NullPointerException ignore) {}

        Appointment appointment = new Appointment(
                appID,
                custID,
                userID,
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

        if (isConflicting(appointment)) {
            errors.append("Appointment time conflicts with another appointment.\n");
        }

        if (!inBusinessHours(appointment)){
            errors.append("Appointment must be between 8:00 and 22:00 EST\n");
        }


        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText(errors.toString());

            alert.showAndWait();
        }else {
            AppointmentDao appointmentDao = new AppointmentDao();

            if (isNew) {
                appointmentDao.save(appointment);
            } else {
                appointmentDao.update(appointment);
            }
            loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
        }
    }

    /** Loads the mainmenu without making any changes to appointments.
     * @param actionEvent button press event
     */
    public void onActionCancel(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
    }

    /**
     * Sets up the view for Appointment adds and edits.
     * I created two lambda expressions in this method.
     * Both Lambdas are callbacks for creating CellFactories.
     * The lambdas relieve me of having to create custom classes for Cell Factories.
     * This makes the code much simpler to read and debug.
     */
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

        // Add User Selection
        Callback<ListView<User>, ListCell<User>> userFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean b) {
                super.updateItem(user, b);
                setText(b ? "" : user.getName());
            }
        };

        UserDao userDao = new UserDao();
        appUserCombo.setPromptText("Choose User");
        appUserCombo.setItems(userDao.getAll());
        appUserCombo.setCellFactory(userFactory);
        appUserCombo.setButtonCell(userFactory.call(null));

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

        for (int i : new int[]{0, 15, 30, 45}) {
            appStartMinCombo.getItems().add(i);
            appEndMinCombo.getItems().add(i);
        }

    }

    /**
     * Loads the data into a view for the controller.
     *
     * @param obj A data structure class.
     */
    @Override
    public void loadObject(Object obj) {
        Appointment appointment = (Appointment) obj;

        appIDTxt.setText(String.valueOf(appointment.getId()));

        for (Customer cust : appCustCombo.getItems()){
            if (cust.getId() == appointment.getCustomerID()){
                appCustCombo.getSelectionModel().select(cust);
                break;
            }
        }

        for (Contact con : appContactCombo.getItems()){
            if (con.getId() == appointment.getContactID()){
                appContactCombo.getSelectionModel().select(con);
                break;
            }
        }

        for (User user : appUserCombo.getItems()){
            if (user.getId() == appointment.getUserID()){
                appUserCombo.getSelectionModel().select(user);
            }
        }

        appTitleTxt.setText(appointment.getTitle());
        appDescTxt.setText(appointment.getDescription());
        appLocationTxt.setText(appointment.getLocation());
        appTypeTxt.setText(appointment.getType());

        LocalDateTime localStartTime = LocalDateTime.ofInstant(appointment.getStart().toInstant(),appointment.getStart().getTimeZone().toZoneId());
        LocalDateTime localStopTime = LocalDateTime.ofInstant(appointment.getEnd().toInstant(),appointment.getEnd().getTimeZone().toZoneId());

        appStartDate.setValue(LocalDate.from(localStartTime));
        appStartHourCombo.getSelectionModel().select((Integer) localStartTime.getHour());
        appStartMinCombo.getSelectionModel().select((Integer) localStartTime.getMinute());

        appStopDate.setValue(LocalDate.from(localStopTime));
        appEndHourCombo.getSelectionModel().select((Integer) localStopTime.getHour());
        appEndMinCombo.getSelectionModel().select((Integer) localStopTime.getMinute());
    }

    /** Checks if appointment conflicts with other appointments with the same customerID.
     * @param appointment The appointment to be check.
     * @return
     */
    private boolean isConflicting(Appointment appointment){
        AppointmentDao appointmentDao = new AppointmentDao();
        int comparator;

        for (Appointment a : appointmentDao.getAllByCustomer(appCustCombo.getSelectionModel().getSelectedItem().getId())){
            if (a.getId() == appointment.getId())
                continue;
            else {
                comparator = a.getStart().compareTo(appointment.getStart());
                comparator += a.getStart().compareTo(appointment.getEnd());
                comparator += a.getEnd().compareTo(appointment.getStart());
                comparator += a.getEnd().compareTo(appointment.getEnd());

                if (comparator < 3 && comparator > -3){
                    return true;
                }

            }
        }
        return false;
    }

    /** Checks to see if an appointment is within business hours.
     * @param appointment The appointment to be checked.
     * @return True if the appointment is within business hours else false.
     */
    private boolean inBusinessHours(Appointment appointment){
        // Clone cal for start date and End date
        Calendar comparatorCalendar = (Calendar) appointment.getStart().clone();

        // Set TimeZone to eastern and set to 8am for comparatorCalendar
        comparatorCalendar.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        comparatorCalendar.set(Calendar.HOUR, 8);
        comparatorCalendar.set(Calendar.AM_PM, Calendar.AM);

        // Validate start time is after 8am Easter
        if (appointment.getStart().compareTo(comparatorCalendar) < 0) {
            System.out.println("Too Early");
            return false;
        }

        // Set comparatorCalendar to 10pm Eastern
        comparatorCalendar.set(Calendar.HOUR, 10);
        comparatorCalendar.set(Calendar.AM_PM, Calendar.PM);

        // Validate End time is before 10pm Eastern
        if (appointment.getEnd().compareTo(comparatorCalendar) > 0) {
            System.out.println("Too Late");
            return false;
        }

        // Within business hours return true.
        return true;
    }
}
