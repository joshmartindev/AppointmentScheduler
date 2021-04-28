package JMartin_886079_SW2.dao;

import JMartin_886079_SW2.model.Appointment;
import JMartin_886079_SW2.model.User;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides utilities to interact with the Appointment table.
 */
public class AppointmentsDaoImpl {
    /**
     * Queries the database to retrieve all appointments and returns them
     * @return all appointments retrieved from the database.
     */
    public static ObservableList<Appointment> getAppointments() {
        //Clear any appointments that may have been previously loaded.
        if (Appointment.getAllAppointments().size() > 0) { Appointment.getAllAppointments().clear(); }

        //Query the database
        String query =  "SELECT a.*, c.Customer_Name, con.Contact_Name, u.User_Name\n" +
                "FROM appointments AS a\n" +
                "INNER JOIN customers AS c\n" +
                "ON a.Customer_ID = c.Customer_ID\n" +
                "LEFT JOIN contacts AS con\n" +
                "ON a.Contact_ID = con.Contact_ID\n" +
                "LEFT JOIN users AS u\n" +
                "ON a.User_ID = u.User_ID;";
        ResultSet rs;

        //Store the Query Results
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
                String createdBy = rs.getString("Created_By");
                LocalDate updateDate = rs.getDate("Last_Update").toLocalDate();
                String updatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment returnedAppointment = new Appointment(id, title, description, location, type, start, end, createDate, createdBy, updateDate, updatedBy, customerID, customerName, userID, userName, contactID, contactName);
                Appointment.addAppointment(returnedAppointment);
            }
        } catch(Exception e) {
            AlertBox.display(2,"Error in getAppointments Method",e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
        //Finally return the appointments
        return Appointment.getAllAppointments();
    }

    /**
     * Inserts a new appointment into the database
     * @param title the Title of the appointment
     * @param description the Description of the appointment
     * @param location the Location of the appointment
     * @param type the Type of appointment
     * @param start the Start Date and Time of the appointment
     * @param end the End Date and Time of the appointment
     * @param customerID the ID of the Customer for the appointment
     * @param userID the User ID for the appointment
     * @param contactID the Contact ID for the appointment.
     * @throws SQLException if the SQL Server returns an error.
     */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {
        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setInt(1,0);                                             //Appointment_ID
            ps.setString(2,title);                                         //Title
            ps.setString(3,description);                                   //Description
            ps.setString(4,location);                                      //Location
            ps.setString(5, type);                                         //Type
            ps.setTimestamp(6, Timestamp.valueOf(start));                  //Start
            ps.setTimestamp(7, Timestamp.valueOf(end));                    //End
            ps.setTimestamp(8,Timestamp.valueOf(LocalDateTime.now()));     //Create_Date
            ps.setString(9,User.getUsername());                            //Created_By
            ps.setTimestamp(10,Timestamp.valueOf(LocalDateTime.now()));    //Last_Update
            ps.setString(11,User.getUsername());                           //Last_Updated_By
            ps.setInt(12,customerID);                                      //Customer_ID
            ps.setInt(13,userID);                                          //User_ID
            ps.setInt(14,contactID);                                       //Contact_ID

            int result = ps.executeUpdate();

            if (result > 0) {
                AlertBox.display(0,"Appointment Successfully Added","");
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in insertAppointment method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
    }

    /**
     * Updates an appointment in the database
     * @param appointmentID the ID of the appointment to update
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of the appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param customerID the customer ID for the appointment
     * @param userID the User ID for the appointment
     * @param contactID the Contact ID for the appointment
     * @throws SQLException if the SQL Server returns an error
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {

        String query = "UPDATE appointments\n" +
                        "SET Title = ?, " +
                        "Description = ?, " +
                        "Location = ?, " +
                        "Type = ?, " +
                        "Start = ?, " +
                        "End = ?, " +
                        "Last_Update = ?, " +
                        "Last_Updated_By = ?, " +
                        "Customer_ID = ?, " +
                        "User_ID = ?, " +
                        "Contact_ID= ?\n" +
                        "WHERE Appointment_ID = ?;";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setString(1,title);                                         //Title
            ps.setString(2,description);                                   //Description
            ps.setString(3,location);                                      //Location
            ps.setString(4, type);                                         //Type
            ps.setTimestamp(5, Timestamp.valueOf(start));                  //Start
            ps.setTimestamp(6, Timestamp.valueOf(end));                    //End
            ps.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));    //Last_Update
            ps.setString(8,User.getUsername());                           //Last_Updated_By
            ps.setInt(9,customerID);                                      //Customer_ID
            ps.setInt(10,userID);                                          //User_ID
            ps.setInt(11,contactID);                                       //Contact_ID
            ps.setInt(12,appointmentID);                                    //Appointment_ID

            int result = ps.executeUpdate();

            if (result > 0) {
                AlertBox.display(0,"Appointment Successfully Updated","");
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in updateAppointment method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
    }

    /**
     * Deletes an existing appointment from the database based on an appointment instance object
     * @param appointment the appointment to delete from the database
     * @return true/false if the deletion was successful.
     */
    public static boolean deleteAppointment(Appointment appointment) {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setInt(1,appointment.getId());

            int result = ps.executeUpdate();
            if (result > 0) {
                /* Fix from Attempt 1: Forgot to add in functionality to indicate what appointment ID and Type was
                   deleted.  Added this functionality by adding the 'message' parameter on the AlertBox.display line.
                 */
                AlertBox.display(0,"Appointment Successfully Deleted/Cancelled","The following appointment was deleted:\n" +
                                                                                                        "ID: "+appointment.getId()+"\n" +
                                                                                                        "Type: "+appointment.getType()+"\n");
                Appointment.deleteAppointment(appointment);
                return true;
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in deleteAppointment method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
        return false;
    }

    /**
     * Deletes all existing appointments for a customer based on their customer ID.
     * @param id the ID of the customer you want to delete all appointments for.
     */
    public static void deleteAppointment(int id) {
        String query = "DELETE FROM appointments WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setInt(1,id);

            int result = ps.executeUpdate();
            if (result > 0) {
                //Appointment.deleteAppointment(appointment);
                AlertBox.display(0,"All Customer's Appointments Successfully Deleted","");
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in deleteAppointment method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
    }

    /**
     * Returns true/false if an appointment start/end time overlaps with an existing appointment in the database.
     * @param start the Start Date and Time of the desired appointment
     * @param end the End Date and Time of the desired appointment
     * @return true/false if an appointment start/end time overlaps with an existing appointment in the database.
     */
    public static boolean checkOverlappingAppointments(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime startUTC = start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        //System.out.println("Formatted Date: " + startUTC.format(dtf));
        String query = "SELECT * FROM appointments\n" +
                "WHERE ((appointments.Start BETWEEN '"+startUTC.format(dtf)+"' AND '"+endUTC.format(dtf)+"' OR\n" +
                "appointments.End BETWEEN '"+startUTC.format(dtf)+"' AND '"+endUTC.format(dtf)+"') OR\n" +
                "('"+startUTC.format(dtf)+"' BETWEEN appointments.Start AND appointments.End OR" +
                "'"+endUTC.format(dtf)+"' BETWEEN appointments.Start AND appointments.End));";
        System.out.println(query);
        ResultSet rs;

        //Execute the Query
        try {
            rs = DBUtility.ExecuteQuery(query);
            if (rs.next()) {
                AlertBox.display(1,"Overlapping Appointment!","There is an overlapping appointment at " + rs.getTime("Start") + " until " + rs.getTime("End"));
                return true;
            }
            else return false;
        } catch (SQLException e) { AlertBox.display(2,"Error in checkOverlappingAppointments method",e.getMessage()); return false;}
    }
}
