package login;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import resources.Make_Logger;

public class Login {

    private Logger log = Make_Logger.getLogger("login_activity.txt");
    private ResourceBundle rb = ResourceBundle.getBundle("login/language_files/GTK");

    // Labels
    @FXML
    private Label loginMainLabel;
    @FXML
    private Label loginUserLabel;
    @FXML
    private Label loginPassLabel;
    @FXML
    private Label loginZoneIDLabel;

    // Text Fields
    @FXML
    private TextField loginUserTextField;
    @FXML
    private TextField loginPassTextField;

    // Buttons
    @FXML
    private Button loginBtn;



    public void onActionLogin(){
        String username = loginUserTextField.getText();
        String password = loginPassTextField.getText();

        StringBuilder sb = new StringBuilder("Username: ");
        sb.append(username);
        sb.append(" Login Attempt: ");

        boolean loginSuccess = true;

        if (!username.equals("test")) {
            loginSuccess = false;
            sb.append("Unsuccessful");
        } else if (!password.equals("test")){
            loginSuccess = false;
            sb.append("Unsuccessful");
        } else {
            sb.append("Successful");
        }

        log.info(sb.toString());

        if(!loginSuccess){
          Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("loginError"), ButtonType.OK);
          alert.showAndWait();
        }
    }

    public void initialize(){
        loginMainLabel.setText(rb.getString("loginMainLabel"));
        loginZoneIDLabel.setText(rb.getString("loginZone") + ": " + Locale.getDefault());
        loginUserLabel.setText(rb.getString("loginUserLabel") + ": ");
        loginPassLabel.setText(rb.getString("loginPassLabel") + ": ");
        loginBtn.setText(rb.getString("loginBtn"));
    }

}
