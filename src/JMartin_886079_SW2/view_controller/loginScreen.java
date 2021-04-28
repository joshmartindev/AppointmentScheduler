package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.dao.DBUtility;
import JMartin_886079_SW2.dao.UserDaoImpl;

import JMartin_886079_SW2.utils.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * JavaFX Screen Controller to log into the system.
 */
public class loginScreen implements Initializable {
    /** Resource Bundle for language translations. Bundle = JMartin_886079_SW2.utils.Nat */
    private ResourceBundle rb = ResourceBundle.getBundle("JMartin_886079_SW2.utils.Nat", Locale.getDefault());
    /** JavaFX TextField for Username input */
    @FXML private TextField loginUsername;
    /** JavaFX TextField for Password input */
    @FXML private PasswordField loginPassword;
    /** JavaFX Label for language translations */
    @FXML private Label locationLabel;
    /** JavaFX Label for language translations */
    @FXML private Label usernameLabel;
    /** JavaFX Label for language translations */
    @FXML private Label passwordLabel;
    /** JavaFX Label for language translations */
    @FXML private Label locationDescriptionLabel;
    /** JavaFX Button for language translations */
    @FXML private Button loginButton;

    /** Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationLabel.setText(Locale.getDefault().getDisplayCountry());
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        locationDescriptionLabel.setText(rb.getString("location"));
        loginButton.setText(rb.getString("login"));
    }

    /**
     * Checks the credentials provided against the database and logs it as successful or unsuccessful.
     * @param actionEvent Represents a button fire event.
     */
    public void handleLogInButton(ActionEvent actionEvent) {
        try {
            String username = loginUsername.getText();
            String password = loginPassword.getText();
            boolean upcomingAppt = false;
            boolean isValidUser = UserDaoImpl.verifyCredentials(username, password);

            if (isValidUser) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
                String startTimeToCheck = LocalDateTime.now(Clock.systemUTC()).format(dtf).toString();
                String endTimeToCheck = LocalDateTime.now(Clock.systemUTC()).plusMinutes(15).format(dtf).toString();
                String query = "SELECT * FROM appointments WHERE appointments.Start >= '" + startTimeToCheck + "' AND appointments.Start <= '" + endTimeToCheck + "';";
                ResultSet rs;
                try {
                    rs = DBUtility.ExecuteQuery(query);
                    while (rs.next()) {
                        upcomingAppt = true;
                        AlertBox.display(0,rb.getString("UpcomingAppointmentTitle"),rb.getString("UpcomingAppointmentMessage") + "\n" +
                                                                                     rb.getString("UpcomingAppointmentMessageID") +" "+ rs.getInt("Appointment_ID") + "\n" +
                                                                                     rb.getString("UpcomingAppointmentMessageStart") +" "+ rs.getTime("Start") + "\n" +
                                                                                     rb.getString("UpcomingAppointmentMessageEnd") +" "+ rs.getTime("End"));
                        break;
                    }
                    if (!upcomingAppt) { AlertBox.display(0,rb.getString("NoUpcomingAppointmentTitle"),rb.getString("NoUpcomingAppointmentMessage")); }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                ScreenLoader.display("mainScreen","Main Menu",actionEvent);
            } else {
                AlertBox.display(1,rb.getString("loginerrTitle"),rb.getString("loginerrMessage"));
            }

        } catch (IOException e) {
            AlertBox.display(2,"IOException",e.getMessage());
        }
    }
}
