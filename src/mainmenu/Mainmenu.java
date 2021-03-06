package mainmenu;

import dao.implementations.AppointmentDao;
import dao.implementations.CustomerDao;
import dao.implementations.First_Level_DivisionDao;
import dao.models.Appointment;
import dao.models.Customer;
import dao.models.First_Level_Division;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import resources.Loader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author CBlack
 */
public class Mainmenu {

    @FXML
    public TableView<Appointment> appTableView;
    @FXML
    public RadioButton appDisplayToggleMonthly;
    @FXML
    public ToggleGroup appDisplayToggle;
    @FXML
    public RadioButton appDisplayToggleWeekly;
    @FXML
    public TableColumn<Appointment, Integer> appIDCol;
    @FXML
    public TableColumn<Appointment, String> appTitleCol;
    @FXML
    public TableColumn<Appointment, String> appDesCol;
    @FXML
    public TableColumn<Appointment, String> appLocCol;
    @FXML
    public TableColumn<Appointment, String> appContactCol;
    @FXML
    public TableColumn<Appointment, String> appTypeCol;
    @FXML
    public TableColumn<Appointment, String> appStartcol;
    @FXML
    public TableColumn<Appointment, String> appEndCol;
    @FXML
    public TableColumn<Appointment, Integer> appCustCol;
    @FXML
    public Button appPreviousBtn;
    @FXML
    public Button appNextBtn;
    @FXML
    public Button appAddButton;
    @FXML
    public Button appEditButton;
    @FXML
    public Button appDeleteButton;
    @FXML
    public Label appDateDisplaylbl;
    @FXML
    public Button custClearButton;
    @FXML
    private Label custRecLbl;
    @FXML
    private TableView<Customer> custTableView;
    @FXML
    private TableColumn<Customer, Integer> custIDCol;
    @FXML
    private TableColumn<Customer, String> custNameCol;
    @FXML
    private TableColumn<Customer, String> custFLDCol;
    @FXML
    private Button custAddButton;
    @FXML
    private Button custEditButton;
    @FXML
    private Button custDeleteButton;

    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    ObservableList<Appointment> filteredByCustAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> filteredByDateAppointments = FXCollections.observableArrayList();
    Calendar appdisplayDate;

    Loader loader = new Loader();

    /** Updates the Appointment tableview when a customer is selected.
     * @param mouseEvent Select customer in TableView
     */
    public void onMouseClickCust(MouseEvent mouseEvent) {
        updateAppointmentsFromCustomers();
    }

    /** Deselects all Customers from the tableview.
     * @param actionEvent Clear button push
     */
    public void onActionCustClear(ActionEvent actionEvent) {
        custTableView.getSelectionModel().select(null);
        updateAppointmentsFromCustomers();
    }

    /**
     * Updates the Appointment tableview based on Customer selection.
     */
    private void updateAppointmentsFromCustomers(){
        this.filteredByCustAppointments = this.appointments.filtered(appointment -> {
            // If no Customer selected do not filter.
            try {
                custTableView.getSelectionModel().getSelectedItem().getId();
            } catch (NullPointerException e) {
                return true;
            }

            boolean display = true;

            if (!(appointment.getCustomerID() == custTableView.getSelectionModel().getSelectedItem().getId()))
                display = false;

            return display;
        });

        updateAppointmentsFromDate();
    }

    /**
     * Creates list of appointments within the current date range.
     */
    private void updateAppointmentsFromDate(){
        this.filteredByDateAppointments = this.filteredByCustAppointments.filtered(appointment -> {
            boolean display = true;

            // confirm same year.
            if (!(appointment.getStart().get(Calendar.YEAR) == appdisplayDate.get(Calendar.YEAR)))
                display = false;

            if (appDisplayToggleMonthly.isSelected()) {
                if (!(appointment.getStart().get(Calendar.MONTH) == appdisplayDate.get(Calendar.MONTH)))
                    display = false;

            } else {
                if (!(appointment.getStart().get(Calendar.WEEK_OF_YEAR) == appdisplayDate.get(Calendar.WEEK_OF_YEAR)))
                    display = false;
            }


            return display;
        });

        appTableView.setItems(this.filteredByDateAppointments);
    }

    /** Load view to add a Customer
     * @param actionEvent Add button push
     */
    public void onActionCustAdd(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/customer/customer.fxml");
    }

    /** Load view to edit a customer
     * @param actionEvent edit button push.
     */
    public void onActionCustEdit(ActionEvent actionEvent) {
        try{
            ObservableList<Customer> customers = custTableView.getSelectionModel().getSelectedItems();

            if (customers.size() > 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("You Can only edit one customer at a time.");
                alert.setContentText("There can be only one!");

                alert.showAndWait();

                return;
            }

            Customer customer = customers.get(0);

            loader.loadScene(actionEvent, "/customer/customer.fxml", customer);

        } catch (IndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Choose a Customer to modify");
            alert.setContentText("You must select a customer to modify before pressing the edit button");
            alert.showAndWait();
        }
    }

    /** Called when the delete customer button is pushed on the mainmenu. Deletes the customer.
     * @param actionEvent Delete button push.
     */
    public void onActionCustDelete(ActionEvent actionEvent) {
        CustomerDao customerDao = new CustomerDao();
        try{
            ObservableList<Customer> customers = custTableView.getSelectionModel().getSelectedItems();

            if (customers.size() > 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("You Can only Delete one customer at a time.");
                alert.setContentText("There can be only one!");

                alert.showAndWait();

                return;
            }

            Customer customer = customers.get(0);

            if (customAlert(customer.getName())) {
                AppointmentDao appointmentDao = new AppointmentDao();

                for (Appointment a : appointmentDao.getAllByCustomer(customer.getId())){
                    appointmentDao.delete(a);
                }

                customerDao.delete(customer);
                initialize();
            }
        } catch (IndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Choose a Customer to delete");
            alert.setContentText("You must select a customer to delete before pressing the delete button");
            alert.showAndWait();
        }
    }

    /** Warn that all Appointments for this Customer will also be deleted.
     * @param customer Customer to be deleted
     * @return did the user press ok.
     */
    private boolean customAlert(String customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete all appointments for this customer as well!");
        alert.setHeaderText("Are you sure you want to delete customer: " + customer + "?");

        Optional<ButtonType> confirmed = alert.showAndWait();
        return confirmed.get() == ButtonType.OK;
    }

    /**
     * Setup all data for the view.
     */
    public void initialize(){
        this.appdisplayDate = Calendar.getInstance();
//        this.appdisplayDate.set(Calendar.DAY_OF_MONTH, 1);

        CustomerDao customerDao = new CustomerDao();
        First_Level_DivisionDao fldDao = new First_Level_DivisionDao();
        String[] divisions = getFLDArray(fldDao);

        this.customers = customerDao.getAll();

        custTableView.setItems(this.customers);
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custFLDCol.setCellValueFactory((cust -> new SimpleStringProperty(divisions[cust.getValue().getDivisionID()])));

        AppointmentDao appointmentDao = new AppointmentDao();
        this.appointments = appointmentDao.getAll();

        updateAppDateDisplay();
        updateAppointmentsFromCustomers();
        updateAppointmentsFromDate();

        appIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartcol.setCellValueFactory((app) -> new SimpleStringProperty(app.getValue().getStart().getTime().toString()));
        appEndCol.setCellValueFactory((app) -> new SimpleStringProperty(app.getValue().getEnd().getTime().toString()));
        appCustCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));



//        appointments.forEach((appointment) -> System.out.println("AppointmentController ID: " + appointment.getId() + " Title: " + appointment.getTitle()));
    }

    /** Get an array of all FLD with their ID as the index.
     * @param fldDao The DAO implementation to use.
     * @return List of all FLD.
     */
    private String[] getFLDArray(First_Level_DivisionDao fldDao){
        ObservableList<First_Level_Division> fldFromDB = fldDao.getAll();
        String[] divisions;

        Optional<First_Level_Division> maxID = fldFromDB.stream().max(Comparator.comparing(First_Level_Division::getId));
        if (maxID.isPresent())
            divisions = new String[maxID.get().getId()+1];
        else
            divisions = new String[fldFromDB.size()];

        fldFromDB.forEach((f) -> divisions[f.getId()] = f.getDivision());

        return divisions;
    }

    /** Change the display over Appointments when monthly or weekly is chosen.
     * @param actionEvent toggle event.
     */
    public void onActionDisplayToggle(ActionEvent actionEvent) {
        if (appDisplayToggleMonthly.isSelected())
            System.out.println("Display: Monthly");
        else
            System.out.println("Display: Weekly");

        updateAppointmentsFromDate();
        updateAppDateDisplay();
    }

    /** Go to previous time frame
     * @param actionEvent previous button push
     */
    public void onActionAppPrevious(ActionEvent actionEvent) {
        if (appDisplayToggleMonthly.isSelected())
            this.appdisplayDate.set(Calendar.MONTH, appdisplayDate.get(Calendar.MONTH) - 1);
        else
            this.appdisplayDate.set(Calendar.WEEK_OF_YEAR, appdisplayDate.get(Calendar.WEEK_OF_YEAR) - 1);

        updateAppointmentsFromDate();
        updateAppDateDisplay();
    }

    /** Go to next time frame
     * @param actionEvent next button push
     */
    public void onActionAppNext(ActionEvent actionEvent) {
        if (appDisplayToggleMonthly.isSelected())
            this.appdisplayDate.set(Calendar.MONTH, appdisplayDate.get(Calendar.MONTH) + 1);
        else
            this.appdisplayDate.set(Calendar.WEEK_OF_YEAR, appdisplayDate.get(Calendar.WEEK_OF_YEAR) + 1);

        updateAppointmentsFromDate();
        updateAppDateDisplay();
    }

    /**
     * Update the Current time frame displayed on the Appointments table view.
     */
    private void updateAppDateDisplay(){
        String dateFormat;
        if (appDisplayToggleMonthly.isSelected())
            dateFormat = "MMMMM yyyy";
        else
            dateFormat = "'Week' w 'of' yyyy";

        String formattedDate = new SimpleDateFormat(dateFormat).format(appdisplayDate.getTime());
        try {
            appDateDisplaylbl.setText(formattedDate);
        } catch (Exception e){
            System.out.println("There was an exception in updateAppDateDisplay");
            e.printStackTrace();
        }
    }

    /** load add Appointment view.
     * @param actionEvent Add button push
     */
    public void onActionAppAdd(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/appointment/appointment.fxml");
    }

    /** load edit Appointment view.
     * @param actionEvent edit button push
     */
    public void onActionAppEdit(ActionEvent actionEvent) {
        try {
            Appointment appointment = appTableView.getSelectionModel().getSelectedItem();

            loader.loadScene(actionEvent, "/appointment/appointment.fxml", appointment);
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Choose an Appointment to modify");
            alert.setContentText("You must select an Appointment to modify before pressing the edit button");
            alert.showAndWait();
        }
    }

    /** Delete selected Appointment and reload current view.
     * @param actionEvent Delete button push
     */
    public void onActionAppDelete(ActionEvent actionEvent) {
        AppointmentDao appointmentDao = new AppointmentDao();

        try{
            Appointment appointment = appTableView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Canel Appointment?");
            alert.setHeaderText("Are you sure you want to delete\nappointment ID: " + appointment.getId() + "\nTitle: " + appointment.getType());
            Optional<ButtonType> confirmed = alert.showAndWait();

            if (confirmed.get() == ButtonType.OK) {
                appointmentDao.delete(appointment);
                initialize();
            }
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Choose an Appointment to delete");
            alert.setContentText("You must select an Appointment to delete before pressing the delete button");
            alert.showAndWait();
        }
    }

    public void onActionOpenReports(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/report/report.fxml");
    }
}
