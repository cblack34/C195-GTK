import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Starts the application
 */
public class Main extends Application {

    /** Starts up the GUI.
     * @param primaryStage The main stage of the GUI
     * @throws Exception Generic Exception that may be thrown
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        primaryStage.setTitle("Global Time Keeper");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    /**
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
//        Locale.setDefault(new Locale("fr"));
        launch(args);
    }
}
