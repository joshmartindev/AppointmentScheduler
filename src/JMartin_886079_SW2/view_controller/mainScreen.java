package JMartin_886079_SW2.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * JavaFX Screen Controller for the main menu of the application.
 */
public class mainScreen implements Initializable {

    /** Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         * There is nothing to initialize on this screen. FXML file takes care of the display.
         */
    }

    /**
     * Opens the Customers screen.
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).

     */
    public void handleCustomerRecordsButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("customerSearchScreen","Customer Search",actionEvent);
    }

    /**
     * Opens the Appointments screen
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).

     */
    public void handleSchedulingButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("viewAppointmentsScreen","Appointment View",actionEvent);
    }

    /**
     * Opens the Reports screen
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).

     */
    public void handleReportsButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("reportsScreen", "Reports", actionEvent);
    }
}
