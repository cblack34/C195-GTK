package dao.implementations;

import dao.interfaces.Dao;
import dao.models.Customer;
import dao.models.User;
import javafx.collections.ObservableList;
import resources.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;

public class UserDao implements Dao {

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        Calendar created = Calendar.getInstance();
        created.setTimeInMillis(rs.getTimestamp("Create_Date").getTime());

        Calendar updated = Calendar.getInstance();
        updated.setTimeInMillis(rs.getTimestamp("Last_Update").getTime());

        User user = new User(
                rs.getInt("User_ID"),
                rs.getString("User_name"),
                rs.getString("Password"),
                rs.getString("Created_By"),
                rs.getString("Last_Updated_By"),
                created,
                updated
        );

        return user;
    }

    public Optional<User> getByUserAndPass(String user, String pass){
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE User_name = ? and Password = ?");
            statement.setString(1, user);
            statement.setString(2, pass);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(createUserFromResultSet(rs));
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
    public Optional get(long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE User_ID = ?");
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(createUserFromResultSet(rs));
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
    public ObservableList getAll() {
        return null;
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
