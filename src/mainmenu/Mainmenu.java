package mainmenu;

import dao.implementations.AppointmentDao;
import dao.implementations.CustomerDao;
import dao.implementations.First_Level_DivisionDao;
import dao.models.Appointment;
import dao.models.Customer;
import dao.models.First_Level_Division;
import dao.models.User;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import resources.LoadObject;
import resources.Loader;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Optional;

public class Mainmenu {

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

    Loader loader = new Loader();

    public void onMouseClickCust(MouseEvent mouseEvent) {
    }

    public void onActionCustAdd(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/customer/customer.fxml");
    }

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
     * @todo Add delete confirmation before deleting.
     *
     * @param actionEvent
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

    private boolean customAlert(String customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete all appointments for this customer as well!");
        alert.setHeaderText("Are you sure you want to delete customer: " + customer + "?");

        Optional<ButtonType> confirmed = alert.showAndWait();
        return confirmed.get() == ButtonType.OK;
    }

    public void initialize(){
        CustomerDao customerDao = new CustomerDao();
        First_Level_DivisionDao fldDao = new First_Level_DivisionDao();
        String[] divisions = getFLDArray(fldDao);


        custTableView.setItems(customerDao.getAll());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custFLDCol.setCellValueFactory((cust -> new SimpleStringProperty(divisions[cust.getValue().getDivisionID()])));

        AppointmentDao appointmentDao = new AppointmentDao();
        ObservableList<Appointment> appointments = appointmentDao.getAll();

        appointments.forEach((appointment) -> System.out.println("Appointment ID: " + appointment.getId() + " Title: " + appointment.getTitle()));
    }

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
}
