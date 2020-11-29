package customer;

import dao.implementations.CountryDao;
import dao.implementations.CustomerDao;
import dao.implementations.First_Level_DivisionDao;
import dao.models.Country;
import dao.models.Customer;
import dao.models.First_Level_Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import resources.LoadObject;
import resources.Loader;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

public class CustomerController implements LoadObject {

    @FXML
    private TextField custIDTxt;
    @FXML
    private TextField custNameTxt;
    @FXML
    private TextField custAddressTxt;
    @FXML
    private TextField custPostCodeTxt;
    @FXML
    private TextField custPhoneTxt;
    @FXML
    private ComboBox<Country> custCountryCombo;
    @FXML
    private ComboBox<First_Level_Division> custFLDCombo;


    Loader loader = new Loader();

    /**
     * Loads the data into a view for the controller.
     *
     * @param obj A data structure class.
     */
    @Override
    public void loadObject(Object obj) {
        Customer customer = (Customer) obj;

        First_Level_DivisionDao fldDao = new First_Level_DivisionDao();
        First_Level_Division fld = fldDao.get(customer.getDivisionID()).get();
        CountryDao countryDao = new CountryDao();
        Country country = countryDao.get(fld.getCountryID()).get();

        ObservableList<Country> countries = custCountryCombo.getItems();

        for (Country c : countries){
            if (c.getId() == country.getId()){
                custCountryCombo.getSelectionModel().select(c);
                onActionCustCountryCombo(null);
                break;
            }
        }

        ObservableList<First_Level_Division> flds = custFLDCombo.getItems();

        for (First_Level_Division f : flds) {
            if (f.getId() == fld.getId()){
                custFLDCombo.getSelectionModel().select(f);
                break;
            }
        }

        custIDTxt.setText(String.valueOf(customer.getId()));
        custNameTxt.setText(customer.getName());
        custAddressTxt.setText(customer.getAddress());
        custPostCodeTxt.setText(customer.getPostalCode());
        custPhoneTxt.setText(customer.getPhone());
    }

    /** Loads the mainmenu and does not change any Customer information.
     * @param actionEvent cancel button click
     */
    public void onActionCancel(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
    }

    /** Saves the Customer to the db and loads the mainmenu.
     * @param actionEvent Save button click
     */
    public void onActionSave(ActionEvent actionEvent) {
        // Default to a new customer.
        boolean isNew = true;

        // Collects error messages to display if error.
        StringBuilder errors = new StringBuilder();

        // Create all vars needed for a customer
        int id = 0;
        int fld = 0;
        int country = 0;
        String name, address, postCode, phone, createBy, lastUpdatedBY;

        // Starting getting the values for the above vars.

        try {
            id = Integer.parseInt(custIDTxt.getText().trim());
            isNew = false;
        } catch (NumberFormatException e) {

        }

        name = custNameTxt.getText().trim();
        if (name.length() < 1) {
            errors.append("Name must have a value.\n");
        }

        address = custAddressTxt.getText().trim();
        if (address.length() < 1) {
            errors.append("Address must have a value.\n");
        }

        postCode = custPostCodeTxt.getText().trim();
        if (postCode.length() < 1) {
            errors.append("Postal Code must have a value\n");
        }

        phone = custPhoneTxt.getText().trim();
        if (phone.length() < 1) {
            errors.append("Phone must have a value\n");
        }

        try {
            country = custCountryCombo.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            errors.append("You must select a Country\n");
        }


        try {
            fld = custFLDCombo.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            errors.append("You must select a First Level Division\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText(errors.toString());

            alert.showAndWait();
        }else {
            CustomerDao customerDao = new CustomerDao();

            if (isNew) {
                Customer customer = new Customer(
                        0,
                        fld,
                        name,
                        address,
                        postCode,
                        phone,
                        "test",
                        "test",
                        Calendar.getInstance(),
                        Calendar.getInstance()
                );
                customerDao.save(customer);
            } else {
                Customer customer = new Customer(
                        id,
                        fld,
                        name,
                        address,
                        postCode,
                        phone,
                        "test",
                        "test",
                        Calendar.getInstance(),
                        Calendar.getInstance()
                );
                customerDao.update(customer);
            }

            loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
        }
    }

    /** loads the First Level Divisions related to the country selected.
     * I created a Lambda here to simplify the CellFactory on the First Level Division Combo
     * @param actionEvent A Country is selected from the countryCombo Combo Box
     */
    public void onActionCustCountryCombo(ActionEvent actionEvent) {
        First_Level_DivisionDao fldDao = new First_Level_DivisionDao();

        Country country = custCountryCombo.getSelectionModel().getSelectedItem();

        Callback<ListView<First_Level_Division>, ListCell<First_Level_Division>> fldFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(First_Level_Division first_level_division, boolean b) {
                super.updateItem(first_level_division, b);
                setText(b ? "" : first_level_division.getDivision());
            }
        };

        custFLDCombo.setItems(fldDao.getAllByCountryID(country.getId()));
        custFLDCombo.setCellFactory(fldFactory);
        custFLDCombo.setButtonCell(fldFactory.call(null));
        custFLDCombo.setPromptText(null);
    }

    /**
     * Sets up the view for Appointment adds and edits.
     * I created the lambda here to simplify the creation of the Country Combo Block Cell Factory.
     */
    public void initialize(){
        CountryDao countryDao = new CountryDao();

        // Cell Factory WIth Lambda
        Callback<ListView<Country>, ListCell<Country>> countryFactory = lv -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country country, boolean b) {
                super.updateItem(country, b);
                setText(b ? "" : country.getCountry());
            }
        };

        custCountryCombo.setItems(countryDao.getAll());
        custCountryCombo.setCellFactory(countryFactory);
        custCountryCombo.setButtonCell(countryFactory.call(null));
        custFLDCombo.setPromptText("You Must Choose a Country");
    }
}
