package dao.implementations;

import dao.interfaces.Dao;
import dao.models.First_Level_Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import resources.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;

public class First_Level_DivisionDao implements Dao {

    private static final String DELETE = "DELETE FROM first_level_divisions WHERE Division_ID=?";
    private static final String GET_ALL = "SELECT * FROM first_level_divisions ORDER BY Division_ID";
    private static final String GET_BY_ID = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
    private static final String INSERT = "INSERT INTO first_level_divisions(Division_ID, Division, Created_By, Last_Updated_By, Country_ID) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE first_level_divisions SET Division_ID=?, Division=?, Created_By=?, Last_Updated_By=?, Country_ID=? WHERE Division_ID=?";
    private static final String GET_ALL_BY_COUNTRY_ID = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID=? ORDER BY Division";

    private First_Level_Division createFLDFromResultSet(ResultSet rs) throws SQLException {
        Calendar created = Calendar.getInstance();
        created.setTimeInMillis(rs.getTimestamp("Create_Date").getTime());

        Calendar updated = Calendar.getInstance();
        updated.setTimeInMillis(rs.getTimestamp("Last_Update").getTime());

        return (First_Level_Division) new First_Level_Division(
                rs.getInt("Division_ID"),
                rs.getInt("COUNTRY_ID"),
                rs.getString("Division"),
                rs.getString("Created_By"),
                rs.getString("Last_Updated_By"),
                created,
                updated
        );
    }

    /** Get a single FLD.
     * @param id The ID of the object to be retrieved from the db.
     * @return
     */
    @Override
    public Optional<First_Level_Division> get(long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(createFLDFromResultSet(rs));
            }
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

        return Optional.empty();
    }

    /** Get all First_Level_Division in the DB.
     * @return a List of all FLD in the DB.
     */
    @Override
    public ObservableList<First_Level_Division> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<First_Level_Division> first_level_divisions = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                first_level_divisions.add(createFLDFromResultSet(rs));
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

        return first_level_divisions;
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

    /** Get a list of FLD based on Country ID
     * @param countryID ID of the Country to search for.
     * @return List of FLD with the same countryID
     */
    public ObservableList<First_Level_Division> getAllByCountryID(int countryID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<First_Level_Division> first_level_divisions = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_ALL_BY_COUNTRY_ID);
            statement.setInt(1, countryID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                first_level_divisions.add(createFLDFromResultSet(rs));
            }

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

        return first_level_divisions;
    }
}
