package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.dao.ContactDaoImpl;
import JMartin_886079_SW2.dao.DBUtility;
import JMartin_886079_SW2.model.Contact;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * JavaFX Screen Controller to run/display reports.
 */
public class reportsScreen implements Initializable {
    /** JavaFX Tab for Report #1 */
    @FXML private Tab report1Tab;
    /** JavaFX TextArea for Report #1 */
    @FXML private TextArea rep1Textarea;
    /** JavaFX Tab for Report #2 */
    @FXML private Tab report2Tab;
    /** JavaFX ChoiceBox for Report #2 */
    @FXML private ChoiceBox<String> rep2ContactDropdown;
    /** JavaFX TextArea for Report #2 */
    @FXML private TextArea rep2Textarea;
    /** JavaFX Tab for Report #3 */
    @FXML private Tab report3Tab;
    /** JavaFX TextArea for Report #3 */
    @FXML private TextArea rep3Textarea;

    /** Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populate the Report #2 Contact Dropdown with all our contacts.
        populateContactDropdown();
        //Create Listener for Report #2's ChoiceBox Parameter.
        //Upon selecting an item from the ChoiceBox, it should run the report.
        rep2ContactDropdown.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue value, String oldValue, String newValue) {
                runReport2(newValue);
            }
        });
    }

    /**
     * Populates the Contact dropdown with all contacts from the database
     */
    public void populateContactDropdown() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ContactDaoImpl.getContacts();
        for (Contact contact : Contact.getAllContacts()) {
            contactNames.add(contact.getContactName());
        }
        rep2ContactDropdown.setItems(contactNames);
    }
    /**
     * Loads the previous screen (Main Menu)
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("mainScreen", "Main Menu", actionEvent);
    }

    /**
     * When a tab is switched, the corresponding report is run.
     * @param event Represents a tab switch fire event.
     */
    public void handleTabSwitch(Event event) {
        // Report Tab #1
        if (report1Tab.isSelected()) { runReport1(); }
        // Report Tab #2
        else if (report2Tab.isSelected()) { /* Do Nothing, the Dropdown will trigger the report */ }
        //Report Tab #3
        else if (report3Tab.isSelected()) { runReport3(); }
    }

    /**
     * Runs report #1 (Appointments by Month by Type)
     */
    private void runReport1() {
        //Clear any existing report text
        rep1Textarea.clear();

        String query = "SELECT MONTHNAME(`Start`) AS 'Month', type AS 'Type', COUNT(*) as 'Count'\n" +
                "FROM appointments\n" +
                "GROUP BY MONTHNAME(`Start`), type;";
        ResultSet rs;
        StringBuilder reportLine = new StringBuilder();
        String stringFormat = "%1$-12s %2$-22s %3$-7s\n";
        reportLine.append(String.format(stringFormat,"Month", "Type", "Count"));
        reportLine.append("-".repeat(42) + "\n");

        //Output the Query Results
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                reportLine.append(String.format(stringFormat,rs.getString("Month"),rs.getString("Type"),rs.getInt("Count")));
            }
            //Display the report
            rep1Textarea.setText(reportLine.toString());
        } catch (Exception e) {
            AlertBox.display(2, "Error in runReport1()", e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
    }

    /**
     * Runs Report #2 (Reports by Contact)
     * @param contact the contact to get appointments for.
     */
    private void runReport2(String contact) {
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm aa");
        int contactID = Contact.getContactID(contact);
        //Clear any existing report text
        rep2Textarea.clear();

        String query = "SELECT Appointment_ID, Title, Type, Description, Start, End, customers.Customer_Name\n" +
                "FROM appointments\n" +
                "INNER JOIN customers ON customers.Customer_ID = appointments.Customer_ID\n" +
                "WHERE Contact_ID = " + contactID + ";";
        ResultSet rs;
        StringBuilder reportLine = new StringBuilder();
        String stringFormat = "%1$-17s%2$-25s%3$-26s%4$-28s%5$-23s%6$-23s%7$-33s\n";
        reportLine.append(String.format(stringFormat,"Appointment ID","Title","Type","Description","Start","End","Customer"));
        reportLine.append("-".repeat(176) + "\n");

        //Output the Query Results
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                reportLine.append(String.format(stringFormat,
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Type"),
                        rs.getString("Description"),
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getString("Customer_Name")));
            }
            //Display the report
            rep2Textarea.setText(reportLine.toString());
        } catch (Exception e) {
            AlertBox.display(2, "Error in runReport2()", e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
    }

    /**
     * Runs report #3 (Customer Counts by Country)
     */
    private void runReport3() {
        //Clear any existing report text
        rep3Textarea.clear();

        String query = "SELECT countries.Country, COUNT(customers.Customer_Name) AS 'Count'\n" +
                "FROM customers\n" +
                "LEFT JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID\n" +
                "LEFT JOIN countries ON countries.Country_ID = first_level_divisions.COUNTRY_ID\n" +
                "GROUP BY countries.Country;";
        ResultSet rs;
        StringBuilder report3Line = new StringBuilder();
        String stringFormat = "%1$-15s%2$-5s\n";
        report3Line.append(String.format(stringFormat,"Country","Count"));
        report3Line.append("-".repeat(21) + "\n");

        //Output the Query Results
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                report3Line.append(String.format(stringFormat,rs.getString("Country"),rs.getInt("Count")));
            }
            //Display the report
            rep3Textarea.setText(report3Line.toString());
        } catch (Exception e) {
            AlertBox.display(2, "Error in runReport3()", e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
    }
}
