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

    /** Create a user from a Result Set
     * @param rs Result Set from a query.
     * @return User from the Result Set.
     * @throws SQLException
     */
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

    /** Search the DB for a user with the Username and Password provided.
     * @param user Username of user
     * @param pass Password of user
     * @return optional User if one is found else empty.
     */
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

    /** Get a single User from the DB by id.
     * @param id The ID of the object to be retrieved from the db.
     * @return
     */
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

    /** Not Implemented
     * @return null
     */
    @Override
    public ObservableList getAll() {
        return null;
    }

    /** Not Implemented.
     * @param o generic object
     */
    @Override
    public void save(Object o) {

    }

    /** Not Implemented.
     * @param o generic object
     */
    @Override
    public void update(Object o) {

    }

    /** Not Implemented.
     * @param o generic object
     */
    @Override
    public void delete(Object o) {

    }
}
