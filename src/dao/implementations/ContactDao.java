package dao.implementations;

import dao.interfaces.Dao;
import dao.models.Contact;
import dao.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import resources.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;

public class ContactDao implements Dao {

    /** create a Contact from a Result Set.
     * @param rs Results from a query
     * @return the contact model in the rs.
     * @throws SQLException
     */
    private Contact createContactFromResultSet(ResultSet rs) throws SQLException {
        Contact contact = new Contact(
                rs.getInt("Contact_ID"),
                rs.getString("Contact_Name"),
                rs.getString("Email")
        );

        return contact;
    }

    /** Get a single contact from the db.
     * @param id The ID of the object to be retrieved from the db.
     * @return optional Contact Model
     */
    @Override
    public Optional<Contact> get(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM contacts where Contact_ID = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                return Optional.of(createContactFromResultSet(rs));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert statement != null;
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return Optional.empty();
    }

    /** Get all contacts in the db.
     * @return List of all Contacts in the table
     */
    @Override
    public ObservableList<Contact> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM contacts");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                contacts.add(createContactFromResultSet(rs));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert statement != null;
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return contacts;
    }

    /** Not implemented.
     * @param o Object to be saved.
     */
    @Override
    public void save(Object o) {

    }

    /** Not implemented.
     * @param o Object to be saved.
     */
    @Override
    public void update(Object o) {

    }

    /** Not implemented.
     * @param o Object to be saved.
     */
    @Override
    public void delete(Object o) {

    }
}
