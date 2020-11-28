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

    private Contact createContactFromResultSet(ResultSet rs) throws SQLException {
        Contact contact = new Contact(
                rs.getInt("Contact_ID"),
                rs.getString("Contact_Name"),
                rs.getString("Email")
        );

        return contact;
    }

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

    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }
}
