package JMartin_886079_SW2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Contact.
 */
public class Contact {
    /** the ID of the contact */
    private int contactID;
    /** the name of the contact */
    private String contactName;
    /** stores all contact objects created */
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Creates a contact with the specified Contact ID, Name, and Email.
     * @param contactID The Contact's ID
     * @param contactName The Contact's Name
     */
    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
        allContacts.add(this);
    }
    /**
     * Returns the Contact ID
     * @return the Contact ID
     */
    public int getContactID() { return this.contactID; }
    /**
     * Returns the Contact ID based on the Contact Name
     * @param contactName The Contact's Name to search for
     * @return the Contact ID based on the Contact Name param.
     */
    public static int getContactID(String contactName) {
        int contactID = 0;
        for (Contact contact : getAllContacts()) {
            if (contact.contactName == contactName) {
                contactID = contact.contactID;
            }
        }
        return contactID;
    }

    /**
     * Returns the Contact Name
     * @return the Contact Name
     */
    public String getContactName() { return this.contactName; }

    /**
     * Returns all Contacts created as an ObservableList
     * @return all Contacts created as an ObservableList
     */
    public static ObservableList<Contact> getAllContacts() { return allContacts; }

}
