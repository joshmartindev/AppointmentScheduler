package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.dao.AppointmentsDaoImpl;
import JMartin_886079_SW2.dao.ContactDaoImpl;
import JMartin_886079_SW2.dao.CustomerDaoImpl;
import JMartin_886079_SW2.model.Contact;
import JMartin_886079_SW2.model.Customer;
import JMartin_886079_SW2.model.User;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * JavaFX screen to add a new appointment.
 */
public class addAppointmentScreen implements Initializable {
    /** Stores the Business Hours Start time in Local Time */
    LocalTime businessHoursStartLT = null;
    /** Stores the Business Hours End time in Local Time */
    LocalTime businessHoursEndLT = null;
    /** Stores true/false if an appointment being added is outside business hours start time */
    Boolean outsideBusinessHoursStart = null;
    /** Stores true/false if the an appointment being added is outside business hours end time */
    Boolean outsideBusinessHoursEnd = null;
    /** JavaFX TextField for Appointment description. */
    @FXML private TextField descriptionTextfield;
    /** JavaFX TextField for Appointment location */
    @FXML private TextField locationTextfield;
    /** JavaFX ChoiceBox for Appointment customer */
    @FXML private ChoiceBox<String> customerDropdown;
    /** JavaFX TextField for Appointment type */
    @FXML private TextField typeTextfield;
    /** JavaFX TextField for Appointment user */
    @FXML private TextField userTextfield;
    /** JavaFX TextField for Appointment title */
    @FXML private TextField titleTextfield;
    /** JavaFX ChoiceBox for Appointment contact */
    @FXML private ChoiceBox<String> contactDropdown;
    /** JavaFX DatePicker for Appointment start date */
    @FXML private DatePicker startDatepicker;
    /** JavaFX ChoiceBox for Appointment Start Time Hour */
    @FXML private ChoiceBox<String> startHourDropdown;
    /** JavaFX ChoiceBox for Appointment Start Time Minute */
    @FXML private ChoiceBox<String> startMinuteDropdown;
    /** JavaFX ChoiceBox for Appointment End Time Hour */
    @FXML private ChoiceBox<String> endHourDropdown;
    /** JavaFX ChoiceBox for Appointment End Time Minute */
    @FXML private ChoiceBox<String> endMinuteDropdown;

    /**
     * Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Time Dropdowns
        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        startHourDropdown.setItems(hours);
        startMinuteDropdown.setItems(minutes);
        endHourDropdown.setItems(hours);
        endMinuteDropdown.setItems(minutes);

        userTextfield.setText(User.getUsername());

        populateContactDropdown();
        populateCustomerDropdown();
    }

    /**
     * Populates the Contact dropdown with all contacts from the database.
     */
    public void populateContactDropdown() {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ContactDaoImpl.getContacts();
        for (Contact contact : Contact.getAllContacts()) {
            contactNames.add(contact.getContactName());
        }
        contactDropdown.setItems(contactNames);
    }

    /**
     * Populates the Customer dropdown with all customers from the database.
     */
    public void populateCustomerDropdown() {
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        CustomerDaoImpl.getCustomers();
        for (Customer customer : Customer.getAllCustomers()) {
            customerNames.add(customer.getName());
        }
        customerDropdown.setItems(customerNames);
    }

    /**
     * Exits our of the Add Appointment Screen and goes back to the Main Menu Screen
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("viewAppointmentsScreen","Appointments",actionEvent);
    }

    /**
     * Checks for required information and executes a database insertion for the appointment.
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     * @throws SQLException if the SQL Server has an error with query syntax.
     */
    public void handleAddButton(ActionEvent actionEvent) throws SQLException, IOException {

        //get the input information
        String title = titleTextfield.getText();
        String description = descriptionTextfield.getText();
        String location = locationTextfield.getText();
        String type = typeTextfield.getText();
        LocalDateTime start = LocalDateTime.of(startDatepicker.getValue(), LocalTime.of(Integer.parseInt(startHourDropdown.getValue()), Integer.parseInt(startMinuteDropdown.getValue())));
        LocalDateTime end = LocalDateTime.of(startDatepicker.getValue(), LocalTime.of(Integer.parseInt(endHourDropdown.getValue()), Integer.parseInt(endMinuteDropdown.getValue())));
        int customerID = CustomerDaoImpl.returnCustomerID(customerDropdown.getValue());
        int userID = User.getUserID();
        int contactID = Contact.getContactID(contactDropdown.getValue());

        if (!title.isBlank() || !description.isBlank() || !location.isBlank() || !type.isBlank() || startDatepicker.getValue() != null || startHourDropdown.getValue() != null || startMinuteDropdown.getValue() != null || endHourDropdown.getValue() != null || endMinuteDropdown.getValue() != null || customerDropdown.getValue() != null || contactDropdown.getValue() != null) {
            //check within business hours
            if (!outsideBusinessHours(start, end)) {
                //check for overlapping appointments.
                if (!overlappingAppointments(start, end)) {
                    //Insert the new appointment
                    AppointmentsDaoImpl.insertAppointment(title,
                            description,
                            location,
                            type,
                            start,
                            end,
                            customerID,
                            userID,
                            contactID);

                    //Take the user back to the view appointments screen.
                    ScreenLoader.display("viewAppointmentsScreen","Appointments",actionEvent);
                }
            } else {
                AlertBox.display(2, "Outside Business Hours", "Please select an appointment " +
                        "time between the business hours of " + businessHoursStartLT + " to " + businessHoursEndLT);
            }
        }
    }

    /**
     * Returns true/false if the desired appointment overlaps with another in the database.
     * @param start Desired Start Date and Time
     * @param end Desired End Date and Time
     * @return true/false if the desired appointment overlaps with another in the database.
     */
    private Boolean overlappingAppointments(LocalDateTime start, LocalDateTime end) {
        if (!AppointmentsDaoImpl.checkOverlappingAppointments(start, end)) { return false; }
        else { return true; }
    }

    /**
     * Returns true/false if the desired appointment is outside of business hours.
     * @param start Desired Start Date and Time
     * @param end Desired End Date and Time
     * @return true/false if the desired appointment is outside of business hours.
     */
    private Boolean outsideBusinessHours(LocalDateTime start, LocalDateTime end) {

        businessHoursStartLT = LocalTime.of(8, 0); //Time in EST
        businessHoursStartLT = LocalDateTime.of(LocalDate.now(),businessHoursStartLT).atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalTime(); //Convert EST to LDT
                businessHoursEndLT = LocalTime.of(22, 0); //Time in EST
        businessHoursEndLT = LocalDateTime.of(LocalDate.now(),businessHoursEndLT).atZone(ZoneId.of("America/New_York")).withZoneSameInstant(ZoneId.systemDefault()).toLocalTime(); //Convert EST to LDT
        System.out.println("Business Hours: " + businessHoursStartLT + " - " + businessHoursEndLT);
        //Check to see if the selected time is outside business hours
        System.out.println("Selected Appointment: " + start + " - " + end);
        System.out.println("Is the Selected Appointment Before Business Hours? -> " + LocalTime.of(start.getHour(),start.getMinute()).isBefore(businessHoursStartLT));
        System.out.println("Is the Selected Appointment After Business Hours? -> " + LocalTime.of(start.getHour(),start.getMinute()).isAfter(businessHoursEndLT));
        outsideBusinessHoursStart = LocalTime.of(start.getHour(), start.getMinute()).isBefore(businessHoursStartLT) ||
                LocalTime.of(start.getHour(), start.getMinute()).isAfter(businessHoursEndLT);
        outsideBusinessHoursEnd = LocalTime.of(end.getHour(), end.getMinute()).isBefore(businessHoursStartLT) ||
                LocalTime.of(end.getHour(), end.getMinute()).isAfter(businessHoursEndLT);
        if (!outsideBusinessHoursStart && !outsideBusinessHoursEnd) { return false; }
        else { return true; }
    }
}
