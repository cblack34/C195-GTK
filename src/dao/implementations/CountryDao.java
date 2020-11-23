package dao.implementations;

import dao.interfaces.Dao;
import dao.models.Country;
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

public class CountryDao implements Dao {

    private static final String DELETE = "DELETE FROM countries WHERE Country_ID=?";
    private static final String GET_ALL = "SELECT * FROM countries ORDER BY Country_ID";
    private static final String GET_BY_ID = "SELECT * FROM countries WHERE Country_ID=?";
    private static final String INSERT = "INSERT INTO countries(Country, Created_By, Last_Updated_By) VALUES(?, ?, ?)";
    private static final String UPDATE = "UPDATE countries SET Country=?, Created_By=?, Last_Updated_By=? WHERE Country_ID=?";

    private Country createCountryFromResultSet(ResultSet rs) throws SQLException {
        Calendar created = Calendar.getInstance();
        created.setTimeInMillis(rs.getTimestamp("Create_Date").getTime());

        Calendar updated = Calendar.getInstance();
        updated.setTimeInMillis(rs.getTimestamp("Last_Update").getTime());

        Country country = new Country(
                rs.getInt("Country_ID"),
                rs.getString("Country"),
                rs.getString("Created_By"),
                rs.getString("Last_Updated_By"),
                created,
                updated
        );

        return country;
    }


    @Override
    public Optional<Country> get(long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(createCountryFromResultSet(rs));
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

    @Override
    public ObservableList<Country> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(GET_ALL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                countries.add(createCountryFromResultSet(rs));
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

        return countries;
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
