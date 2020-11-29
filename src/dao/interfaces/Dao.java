package dao.interfaces;

import javafx.collections.ObservableList;

import java.util.Optional;

/**
 * @author CBlack
 */
public interface Dao <T> {
    /** Retrieves one item from the db based on the ID.
     * @param id The ID of the object to be retrieved from the db.
     * @return
     */
    Optional<T> get(long id);

    /**Returns all objects in the table on the database.
     * @return Returns all objects in the table on the database.
     */
    ObservableList<T> getAll();

    /** Creates object in the database
     * @param t generic object
     */
    void save(T t);

    /** Updates object in the database
     * @param t generic object
     */
    void update(T t);

    /** Deletes object from the database
     * @param t generic object
     */
    void delete(T t);
}
