package JMartin_886079_SW2.dao;

import JMartin_886079_SW2.utils.AlertBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalTime;

/**
 * Utility class to interact with the MySQL Database.
 */
public class DBUtility {
    /** the jdbc url for the database connection */
    private static final String jdbcURL = "jdbc:mysql://3.227.166.251/WJ05iQl";
    /** the jdbc driver for the database connection */
    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    /** the username for the database connection */
    private static final String username = "U05iQl"; //Username
    /** the password for the database connection */
    private static final String password = "53688515233"; //Password
    /** the connection object for connecting, querying, and updating the database */
    private static Connection conn = null;
    /** the statement object for querying and updating the database */
    private static Statement stmt = null;
    /** the result set object for storing results of querying and updating the database */
    private static ResultSet results = null;

    /**
     * Opens a connection to the database.
     * @return the active connection object.
     */
    public static Connection openConnection() {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("["+LocalTime.now()+"] Connection OPEN");
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Closes a connection to the database.
     */
    public static void closeConnection() {
        try { results.close(); System.out.println("["+LocalTime.now()+"] ResultSet CLOSED"); } catch (SQLException e) { /* Ignore */ }
        try { stmt.close(); System.out.println("["+LocalTime.now()+"] Statement CLOSED"); } catch (SQLException e) { /* Ignore */ }
        try { conn.close(); System.out.println("["+LocalTime.now()+"] Connection CLOSED"); } catch (SQLException e) { /* Ignore */ }
    }

    /**
     * Returns the current active connection to the database
     * @return the current active connection to the database
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Executes a Query to the database and returns the results
     * @param sqlQuery A SQL Query formatted in MySQL Syntax
     * @return The results of the query
     * @throws SQLException
     */
    public static ResultSet ExecuteQuery(String sqlQuery) throws SQLException {
        try {
            DBUtility.openConnection();
            stmt = DBUtility.getConnection().createStatement();
            results = stmt.executeQuery(sqlQuery);
            return stmt.getResultSet();
        } catch(SQLException e) {
            System.out.println("SQL Exception in ExecuteQuery: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Executes an update to the database.
     * @param sqlQuery A SQL Statement formatted in MySQL Syntax
     * @return The results of the query
     * @throws SQLException
     */
    public static PreparedStatement ExecuteUpdate(String sqlQuery) throws SQLException {
        PreparedStatement ps = null;
        try {
            DBUtility.openConnection();
            stmt = DBUtility.getConnection().createStatement();
            ps = DBUtility.getConnection().prepareStatement(sqlQuery);
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in ExecuteUpdate method", e.getMessage());
        }
        return ps;
    }
}
