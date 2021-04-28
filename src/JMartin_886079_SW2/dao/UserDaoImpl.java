package JMartin_886079_SW2.dao;

import static JMartin_886079_SW2.dao.DBUtility.ExecuteQuery;
import JMartin_886079_SW2.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides utilities to interact with the User table.
 */
public class UserDaoImpl {
    /**
     * Returns true/false if valid credentials were provided.
     * @param username The username of the login credentials.
     * @param password The password of the login credentials.
     * @return true/false if valid credentials were provided.
     */
    public static Boolean verifyCredentials(String username, String password) {
        try {
            //Set the Query
            String query = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "';";
            //Send the Query, save it to 'rs'
            ResultSet rs = ExecuteQuery(query);
            //Read the results from the Query
            if (rs.next()) {
                //Save the user to an object
                User credentialedUser = new User(rs.getInt("User_ID"), rs.getString("User_Name"));
                //log the successful login attempt
                UserDaoImpl.log(username, true);
                //Return true to say they're properly credentialed
                return true;
            } else {
                UserDaoImpl.log(username, false);
                return false;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            //Close the DB Connection
            DBUtility.closeConnection();
        }
    }

    /**
     * Logs the login attempt to login_activity.txt
     * @param username The username of the user attempting to log in.
     * @param loginSuccess The password of the user attempting to log in.
     * @throws IOException if there is an issue with the logfile create/read/write/access
     */
    public static void log(String username, boolean loginSuccess) throws IOException {
        FileWriter logFile = new FileWriter("login_activity.txt", true);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy kk:mm:ss");
        String timestamp = LocalDateTime.now().format(dtf).toString() + " ";
        if (loginSuccess) { logFile.write(timestamp + username + " - Successful Login Attempt\n"); }
        else { logFile.write(timestamp + username + " - Unsuccessful Login Attempt\n"); }
        logFile.close();
    }
}
