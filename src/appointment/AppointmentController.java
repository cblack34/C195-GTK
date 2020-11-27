package appointment;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import resources.Loader;

public class AppointmentController {
    public Label appIDLbl;
    public TextField appIDTxt;
    public Label appCustLbl;
    public ComboBox appCustCombo;
    public Label appContactLbl;
    public ComboBox appContactCombo;
    public Label appTitleLbl;
    public TextField appTitleTxt;
    public Label appDescLbl;
    public TextField appDescTxt;
    public Label appLocationLbl;
    public TextField appLocationTxt;
    public Label appTypeLbl;
    public TextField appTypeTxt;
    public Label appStartLbl;
    public DatePicker appStartDate;
    public Label appStopLbl;

    private Loader loader = new Loader();

    public void onActionSave(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
    }

    public void onActionCancel(ActionEvent actionEvent) {
        loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
    }
}
