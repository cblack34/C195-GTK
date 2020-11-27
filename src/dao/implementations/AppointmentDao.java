package dao.implementations;

import dao.interfaces.Dao;
import dao.models.Appointment;
import dao.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import resources.DBConnection;

import java.sql.*;
import java.util.Calendar;
import java.util.Optional;

public class AppointmentDao implements Dao {

    public ObservableList<Appointment> getUpcoming(int userID){
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("Select * from appointments where User_ID = ? AND Start BETWEEN NOW() AND NOW() + INTERVAL 15 MINUTE");
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                appointments.add(createAppointmentFromResultSet(rs));
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

        return appointments;
    }

    private Appointment createAppointmentFromResultSet(ResultSet rs) throws SQLException {
        Calendar created = Calendar.getInstance();
        created.setTimeInMillis(rs.getTimestamp("Create_Date").getTime());

        Calendar updated = Calendar.getInstance();
        updated.setTimeInMillis(rs.getTimestamp("Last_Update").getTime());

        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(rs.getTimestamp("Start").getTime());

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(rs.getTimestamp("End").getTime());

        Appointment appointment = new Appointment(
                rs.getInt("Appointment_ID"),
                rs.getInt("Customer_ID"),
                rs.getInt("User_ID"),
                rs.getInt("Contact_ID"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Location"),
                rs.getString("Type"),
                rs.getString("Created_By"),
                rs.getString("Last_Updated_By"),
                start,
                end,
                created,
                updated
        );

        return appointment;
    }

    public ObservableList<Appointment> getAllByCustomer(int custID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ?");
            statement.setInt(1, custID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                appointments.add(createAppointmentFromResultSet(rs));
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

        return appointments;
    }

    @Override
    public Optional<Appointment> get(long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("Select * from appointments where User_ID = ?");
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(createAppointmentFromResultSet(rs));
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
    public ObservableList<Appointment> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM appointments");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                appointments.add(createAppointmentFromResultSet(rs));
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

        return appointments;
    }

    @Override
    public void save(Object o) {
        Appointment appointment = (Appointment) o;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO appointments " +
                            "(Title, Description, Location, Type, Start, End, Created_BY, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(appointment.getStart().getTime().toString()));
            statement.setTimestamp(6, java.sql.Timestamp.valueOf(appointment.getEnd().getTime().toString()));
            statement.setString(7, appointment.getCreatedBy());
            statement.setString(8, appointment.getUpdatedBy());
            statement.setInt(9, appointment.getCustomerID());
            statement.setInt(10, appointment.getUserID());
            statement.setInt(11, appointment.getContactID());

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
    }

    @Override
    public void update(Object o) {
        Appointment appointment = (Appointment) o;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(
                    "UPDATE appointments SET " +
                            "Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=?" +
                            "WHERE Appointment_ID = ?"
            );

            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(appointment.getStart().getTime().toString()));
            statement.setTimestamp(6, java.sql.Timestamp.valueOf(appointment.getEnd().getTime().toString()));
            statement.setString(7, appointment.getUpdatedBy());
            statement.setInt(8, appointment.getCustomerID());
            statement.setInt(9, appointment.getUserID());
            statement.setInt(10, appointment.getContactID());

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
    }

    @Override
    public void delete(Object o) {
        Appointment appointment = (Appointment) o;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
            statement.setInt(1, appointment.getId());

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
    }
}
