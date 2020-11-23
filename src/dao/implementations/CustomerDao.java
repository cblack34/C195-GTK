package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import dao.interfaces.Dao;
import dao.models.Customer;
import resources.DBConnection;



public class CustomerDao implements Dao {

    private static final String DELETE = "DELETE FROM customers WHERE Customer_ID=?";
    private static final String GET_ALL = "SELECT * FROM customers ORDER BY Customer_ID";
    private static final String GET_BY_ID = "SELECT * FROM customers WHERE Customer_ID=?";
    private static final String INSERT = "INSERT INTO customers(Division_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE customers SET Division_ID=?, Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Updated_By=? WHERE Customer_ID=?";

    @Override
    public Optional<Customer> get(long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(createCustomerFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return Optional.empty();
    }

    @Override
    public ObservableList<Customer> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                customers.add(createCustomerFromResultSet(rs));
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

        return customers;
    }

    private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        Calendar created = Calendar.getInstance();
        created.setTimeInMillis(rs.getTimestamp("Create_Date").getTime());

        Calendar updated = Calendar.getInstance();
        updated.setTimeInMillis(rs.getTimestamp("Last_Update").getTime());

        Customer customer = new Customer(
                rs.getInt("Customer_ID"),
                rs.getInt("Division_ID"),
                rs.getString("Customer_Name"),
                rs.getString("Address"),
                rs.getString("Postal_Code"),
                rs.getString("Phone"),
                rs.getString("Created_By"),
                rs.getString("Last_Updated_By"),
                created,
                updated
        );

        return customer;
    }

    @Override
    public void save(Object object) {
        Customer customer = (Customer) object;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(INSERT);

            statement.setInt(1, customer.getDivisionID());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhone());
            statement.setString(6, customer.getCreatedBy());
            statement.setString(7, customer.getUpdatedBy());

            statement.executeUpdate();

        } catch (Exception e) {
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
        System.out.println("Save");
    }

    @Override
    public void update(Object object) {
        Customer customer = (Customer) object;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(UPDATE);

            statement.setInt(1, customer.getDivisionID());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhone());
            statement.setString(6, customer.getUpdatedBy());
            statement.setInt(7, customer.getId());

            statement.executeUpdate();

        } catch (Exception e) {
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
        System.out.println("Update");
    }

    @Override
    public void delete(Object o) {
        Customer customer = (Customer) o;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, customer.getId());

            statement.executeUpdate();

        } catch (Exception e) {
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
        System.out.println("delete");
    }
}
