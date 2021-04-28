package JMartin_886079_SW2.dao;

import JMartin_886079_SW2.model.Contact;
import JMartin_886079_SW2.utils.AlertBox;

import java.sql.ResultSet;

/**
 * Provides utilities to interact with the Contact table.
 */
public class ContactDaoImpl {
    /**
     * Queries the database for all contacts and stores them in the Contact class.
     */
    public static void getContacts() {
        //Clear any customers that may have been previously loaded.
        Contact.getAllContacts().clear();

        //Query the database
        String query = "SELECT * FROM contacts;";
        ResultSet rs;

        //Store the Query Results
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                Contact returnedContact = new Contact(id, name);
            }
        } catch (Exception e) {
            AlertBox.display(2,"Error in getContacts Method",e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
    }
}
