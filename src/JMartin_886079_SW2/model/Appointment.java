package JMartin_886079_SW2.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a single appointment.
 */
public class Appointment {
    /** the id of the appointment */
    private int id;
    /** the title of the appointment */
    private SimpleStringProperty title;
    /** the description of the appointment */
    private SimpleStringProperty description;
    /** the location of the appointment */
    private SimpleStringProperty location;
    /** the type of the appointment */
    private SimpleStringProperty type;
    /** the date of the appointment */
    private LocalDate appointmentDate;
    /** the start time of the appointment */
    private LocalTime start;
    /** the end time of the appointment */
    private LocalTime end;
    /** the creation date of the appointment */
    private LocalDate createdDate;
    /** the user who created the appointment */
    private SimpleStringProperty createdBy;
    /** the date the appointment was last updated */
    private LocalDate updatedDate;
    /** the user who last updated the appointment */
    private SimpleStringProperty updatedBy;
    /** the customer id of the appointment */
    private int customerID;
    /** the customer name of the appointment */
    private String customerName;
    /** the user id of the appointment */
    private int userID;
    /** the user name of the appointment */
    private String userName;
    /** the contact id of the appointment */
    private int contactID;
    /** the contact name of the appointment */
    private String contactName;
    /** stores all appointment objects created */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Creates an appointment with the specified parameters
     * @param id The Appointment's ID
     * @param title The Appointment's title
     * @param description The Appointment's description
     * @param location The Appointment's location
     * @param type The Appointment's type
     * @param start The Appointment's start date and time
     * @param end The Appointment's end date and time
     * @param createdDate  The Appointment's creation date and time
     * @param createdBy  The Appointment's creation author
     * @param updatedDate  The Appointment's last updated date and time
     * @param updatedBy  The Appointment's last update author
     * @param customerID  The Appointment's customer's id
     * @param customerName The Appointment's customer's name
     * @param userID The Appointment's User's id
     * @param userName The Appointment's User's Name
     * @param contactID The Appointment's Contact's id
     * @param contactName The Appointment's Contact's name
     */
    public Appointment(int id,
                       String title,
                       String description,
                       String location,
                       String type,
                       LocalDateTime start,
                       LocalDateTime end,
                       LocalDate createdDate,
                       String createdBy,
                       LocalDate updatedDate,
                       String updatedBy,
                       int customerID,
                       String customerName,
                       int userID,
                       String userName,
                       int contactID,
                       String contactName) {
        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.appointmentDate = LocalDate.of(start.getYear(), start.getMonth(), start.getDayOfMonth());
        this.start = LocalTime.of(start.getHour(), start.getMinute());
        this.end = LocalTime.of(end.getHour(), end.getMinute());
        this.createdDate = createdDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.updatedDate = updatedDate;
        this.updatedBy = new SimpleStringProperty(updatedBy);
        this.customerID = customerID;
        this.customerName = customerName;
        this.userID = userID;
        this.userName = userName;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * Adds an appointment to the JavaFX Observable Array List
     * @param appointment the appointment to add to the JavaFX Observable Array List
     */
    public static void addAppointment(Appointment appointment) { allAppointments.add(appointment); }
    /**
     * Removes an appointment from the JavaFX Observable Array List
     * @param appointment the appointment to remove from the JavaFX Observable Array List.
     */
    public static void deleteAppointment(Appointment appointment) { allAppointments.remove(appointment); }

    /**
     * Returns the Appointment ID
     * @return the Appointment ID
     */
    public int getId() { return this.id; }

    /**
     * Returns the Appointment Title
     * @return the Appointment Title
     */
    public String getTitle() { return this.title.get(); }

    /**
     * Returns the Appointment Description
     * @return the Appointment Description
     */
    public String getDescription() { return this.description.get(); }

    /**
     * Returns the Appointment Location
     * @return the Appointment Location
     */
    public String getLocation() { return this.location.get(); }

    /**
     * Returns the Appointment Type
     * @return the Appointment Type
     */
    public String getType() { return this.type.get(); }

    /**
     * Returns the Appointment Date
     * @return the Appointment Date
     */
    public LocalDate getAppointmentDate() { return this.appointmentDate; }
    /**
     * Returns the Appointment Start Date and Time
     * @return the Appointment Start Date and Time
     */
    public LocalDateTime getStart() { return LocalDateTime.of(this.appointmentDate, this.start); }

    /**
     * Returns the Appointment Start Time
     * @return the Appointment Start Time
     */
    public LocalTime getStartTime() { return this.start; }

    /**
     * Returns the Appointment End Date and Time
     * @return the Appointment End Date and Time
     */
    public LocalDateTime getEnd() { return LocalDateTime.of(this.appointmentDate, this.end); }

    /**
     * Returns the Appointment End Time
     * @return the Appointment End Time
     */
    public LocalTime getEndTime() { return this.end; }

    /**
     * Returns the Appointment's Customer Name
     * @return the Appointment's Customer Name
     */
    public String getCustomerName() { return this.customerName; }

    /**
     * Returns the Appointment's User Name
     * @return the Appointment's User Name
     */
    public String getUserName() { return this.userName; }

    /**
     * Returns the Appointment's Contact Name
     * @return the Appointment's contact name
     */
    public String getContactName() { return this.contactName; }

    /**
     * Returns the Appointment's Contact ID
     * @return the Appointment's Contact ID
     */
    public int getContactID() { return this.contactID; }
    /**
     * Returns all appointments created
     * @return all appointments created
     */
    public static ObservableList<Appointment> getAllAppointments() { return allAppointments; }

}
