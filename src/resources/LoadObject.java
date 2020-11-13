package resources;

/**
 * Implemented by FXML Controllers to load data from a data structure class.
 */
public interface LoadObject {
    /** Loads the data into a view for the controller.
     * @param obj A data structure class.
     */
    void loadObject(Object obj);
}
