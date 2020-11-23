package mainmenu;

import dao.implementations.CustomerDao;
import dao.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import resources.Loader;

public class Mainmenu{

    @FXML
    private Label custRecLbl;
    @FXML
    private TableView<Customer> custTableView;
    @FXML
    private TableColumn<Customer, Integer> custIDCol;
    @FXML
    private TableColumn<Customer, String> custNameCol;
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

            customerDao.delete(customer);
            initialize();

        } catch (IndexOutOfBoundsException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Choose a Customer to delete");
            alert.setContentText("You must select a customer to delete before pressing the delete button");
            alert.showAndWait();
        }
    }

    public void initialize(){
        custTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        CustomerDao customerDao = new CustomerDao();

        custTableView.setItems(customerDao.getAll());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
