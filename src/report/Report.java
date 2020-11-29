package report;

import dao.implementations.AppointmentDao;
import dao.implementations.ContactDao;
import dao.models.Appointment;
import dao.models.Contact;
import dao.models.ReportOne;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import resources.Loader;

public class Report {
    @FXML
    public TableView<ReportOne> r1TableView;
    @FXML
    public TableColumn<ReportOne, String> r1Type;
    @FXML
    public TableColumn<ReportOne, String> r1Month;
    @FXML
    public TableColumn<ReportOne, Integer> r1Count;
    @FXML
    public ComboBox<Contact> r2ContactCombo;
    @FXML
    public TableView<Appointment> r2TableView;
    @FXML
    public TableColumn<Appointment, Integer> r2AppID;
    @FXML
    public TableColumn<Appointment, String> r2Title;
    @FXML
    public TableColumn<Appointment, String> r2Type;
    @FXML
    public TableColumn<Appointment, String> r2Desc;
    @FXML
    public TableColumn<Appointment, String> r2Start;
    @FXML
    public TableColumn<Appointment, String> r2End;
    @FXML
    public TableColumn<Appointment, Integer> r2CustID;
    @FXML
    public ComboBox<Contact> r3ContactCombo;
    @FXML
    public TableView<ReportOne> r3TableView;
    @FXML
    public TableColumn<ReportOne, String> r3Type;
    @FXML
    public TableColumn<ReportOne, Integer> r3Count;

    Loader loader = new Loader();

    /** Loads the mainmenu.
     * @param actionEvent Back Button click
     */
    public void onActionBackBtn(ActionEvent actionEvent) {
        loader.loadScene(actionEvent,"/mainmenu/mainmenu.fxml");
    }

    /** Loads the schedule for the contact selected.
     * @param actionEvent event for selecting a new contact
     */
    public void onActionR2SelectContact(ActionEvent actionEvent) {
        AppointmentDao appointmentDao = new AppointmentDao();
        try {
            r2TableView.setItems(appointmentDao.getAllByContact(r2ContactCombo.getSelectionModel().getSelectedItem().getId()));
        } catch (NullPointerException ignore){}
    }

    public void onActionR3SelectContact(ActionEvent actionEvent) {
        AppointmentDao appointmentDao = new AppointmentDao();
        try {
            r3TableView.setItems(appointmentDao.ReportTwo(r3ContactCombo.getSelectionModel().getSelectedItem().getId()));
        } catch (NullPointerException ignore){}
    }

    /** Load the view for the first tab.
     * @param event When selecting the first tab/report
     */
    public void onActionSelectR1(Event event){
        AppointmentDao appointmentDao = new AppointmentDao();

        r1TableView.setItems(appointmentDao.reportOne());
        r1Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        r1Month.setCellValueFactory(new PropertyValueFactory<>("month"));
        r1Count.setCellValueFactory(new PropertyValueFactory<>("count"));


    }

    /** Load the view for the second tab.
     * @param event When selecting the second tab/report
     */
    public void onActionSelectR2(Event event){
        // Add Contact Selection
        Callback<ListView<Contact>, ListCell<Contact>> contactFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean b) {
                super.updateItem(contact, b);
                setText(b ? "" : contact.getName());
            }
        };

        ContactDao contactDao = new ContactDao();
        r2ContactCombo.setPromptText("Choose Contact");
        r2ContactCombo.setItems(contactDao.getAll());
        r2ContactCombo.setCellFactory(contactFactory);
        r2ContactCombo.setButtonCell(contactFactory.call(null));
        r2ContactCombo.getSelectionModel().select(0);

        // Setup Table View
        onActionR2SelectContact(null);
        r2AppID.setCellValueFactory(new PropertyValueFactory<>("id"));
        r2Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        r2Desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        r2Start.setCellValueFactory((app) -> new SimpleStringProperty(app.getValue().getStart().getTime().toString()));
        r2End.setCellValueFactory((app) -> new SimpleStringProperty(app.getValue().getEnd().getTime().toString()));
        r2CustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }

    /** Load the view for the third tab.
     * @param event When selecting the third tab/report
     */
    public void onActionSelectR3(Event event) {
        // Add Contact Selection
        Callback<ListView<Contact>, ListCell<Contact>> contactFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Contact contact, boolean b) {
                super.updateItem(contact, b);
                setText(b ? "" : contact.getName());
            }
        };

        ContactDao contactDao = new ContactDao();
        r3ContactCombo.setPromptText("Choose Contact");
        r3ContactCombo.setItems(contactDao.getAll());
        r3ContactCombo.setCellFactory(contactFactory);
        r3ContactCombo.setButtonCell(contactFactory.call(null));
        r3ContactCombo.getSelectionModel().select(0);

        onActionR3SelectContact(null);
        r3Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        r3Count.setCellValueFactory(new PropertyValueFactory<>("count"));

    }

    /**
     * Load the view for the initial tab.
     */
    public void initialize(){
        onActionSelectR1(null);
    }


}
