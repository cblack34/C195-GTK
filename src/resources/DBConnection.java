package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author CBlack
 *
 * Class to provide connection to the database. It's not very secure, but I guess that doesn't matter.
 */
public class DBConnection {
    private static final String dbConnectionString ="jdbc:mysql://wgudb.ucertify.com/WJ07syZ";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "U07syZ";
    private static final String password = "53689122556";

    /**
     * Creates a connection to the data base.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        return DriverManager.getConnection(dbConnectionString, username, password);
    }
}
