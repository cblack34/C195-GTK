package login;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import dao.implementations.AppointmentDao;
import dao.implementations.UserDao;
import dao.models.Appointment;
import dao.models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import resources.Loader;
import resources.Make_Logger;

/**
 * Controller for login.fxml
 */
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
    private PasswordField loginPassTextField;

    // Buttons
    @FXML
    private Button loginBtn;

    Loader loader = new Loader();


    /**
     * validates username and password by searching the DB for users with matching username and password as provided by the User. Logs login attempts.
     * @param actionEvent Button click for login
     */
    public void onActionLogin(ActionEvent actionEvent){
        String username = loginUserTextField.getText();
        String password = loginPassTextField.getText();

        UserDao userDao = new UserDao();

        Optional<User> optionalUser = userDao.getByUserAndPass(username, password);

        StringBuilder sb = new StringBuilder("Username: ");
        sb.append(username);
        sb.append(" Login Attempt: ");

        boolean loginSuccess = optionalUser.isPresent();

        if (loginSuccess) {
            sb.append("Successful");
        } else {
            sb.append("Unsuccessful");
        }

        log.info(sb.toString());

        if(!loginSuccess){
          Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("loginError"), ButtonType.OK);
          alert.showAndWait();
        } else {
            AppointmentDao appointmentDao = new AppointmentDao();
            ObservableList<Appointment> appointments = appointmentDao.getUpcoming(optionalUser.get().getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Upcoming Appointments");
            if (appointments.size() > 0) {
                Appointment appointment = appointments.get(0);
                alert.setContentText("AppointmentController ID: " + appointment.getId() + "\nDate and time: " + appointment.getStart().getTime());
            } else
                alert.setContentText("You have no upcoming Appointments!\nWhat are you even doing with your life?");

            alert.showAndWait();
            loader.loadScene(actionEvent, "/mainmenu/mainmenu.fxml");
        }
    }

    /**
     * Uses Resource Bundle to set i18n for text on the login screen.
     */
    public void initialize(){
        loginMainLabel.setText(rb.getString("loginMainLabel"));
        loginZoneIDLabel.setText(rb.getString("loginZone") + ": " + Locale.getDefault());
        loginUserLabel.setText(rb.getString("loginUserLabel") + ": ");
        loginPassLabel.setText(rb.getString("loginPassLabel") + ": ");
        loginBtn.setText(rb.getString("loginBtn"));

        // for testing
        loginUserTextField.setText("test");
        loginPassTextField.setText("test");

    }

}
