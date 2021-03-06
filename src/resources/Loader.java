package resources;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Loads Scenes in a generic way.
 */
public class Loader {
    /**
     * @param actionEvent Button event that triggered loading the new Scene.
     * @return The Stage or base window.
     */
    public Stage getStage(ActionEvent actionEvent){
        return (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
    }

    /**
     * @param actionEvent Button event that triggered loading the new Scene.
     * @param fxml The name of the view to load.
     */
    public void loadScene(ActionEvent actionEvent, String fxml) {
        Stage stage = getStage(actionEvent);
        try {
            Parent scene = FXMLLoader.load(getClass().getResource(fxml));
            stage.setScene(new javafx.scene.Scene(scene));
        } catch (IOException e) {
            System.out.println("Unable to Load: " + fxml);
        }
    }

    /**
     * @param actionEvent Button event that triggered loading the new Scene.
     * @param fxml The name of the view to load.
     * @param object the Data object to be passed to the new scense.
     * @param <T> any Class that extends the interface LoadObject.
     */
    public <T extends LoadObject> void loadScene(ActionEvent actionEvent, String fxml, Object object){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        try {
            loader.load();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error Could not load selected screen!");
            alert.setContentText(fxml);

            alert.showAndWait();
        }

        T controller = loader.getController();
        controller.loadObject(object);

        Stage stage = getStage(actionEvent);
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
