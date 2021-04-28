package JMartin_886079_SW2.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Customer.
 */
public class Customer {
    /** The ID of the Customer. */
    private int id;
    /** The name of the Customer. */
    private SimpleStringProperty name;
    /** the address of the Customer */
    private SimpleStringProperty address;
    /** the postal code of the Customer */
    private SimpleStringProperty postalCode;
    /** the phone number of the Customer */
    private SimpleStringProperty phone;
    /** the creation date of the Customer */
    private LocalDate createdDate;
    /** the name of the person who created the Customer */
    private SimpleStringProperty createdBy;
    /** the last time the Customer was updated */
    private LocalDateTime updatedDate;
    /** the name of the person who last updated the Customer */
    private SimpleStringProperty updatedBy;
    /** the division of the Customer */
    private SimpleStringProperty division;
    /** the country of the Customer */
    private SimpleStringProperty country;
    /** contains all customer objects created */
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * Constructor to create the customer instance object.
     * @param id The id of the customer.
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param createdDate The date the customer was created in the database.
     * @param createdBy The person who created the customer in the database.
     * @param updatedDate The date the customer was last updated in the database.
     * @param updatedBy The person who last updated the customer in the database.
     * @param division The division of the customer. (varies depending upon the country param)
     * @param country The country of the customer.
     */
    public Customer(int id,
                    String name,
                    String address,
                    String postalCode,
                    String phone,
                    LocalDate createdDate,
                    String createdBy,
                    LocalDateTime updatedDate,
                    String updatedBy,
                    String division,
                    String country) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.createdDate = createdDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.updatedDate = updatedDate;
        this.updatedBy = new SimpleStringProperty(updatedBy);
        this.division = new SimpleStringProperty(division);
        this.country = new SimpleStringProperty(country);
    }

    /**
     * Adds a customer to the JavaFX Observable Array List
     * @param customer The customer object instance to add to the array list.
     */
    public static void addCustomer(Customer customer) { allCustomers.add(customer); }

    /**
     * Removes a customer from the JavaFX Observable Array List
     * @param customer The customer object instance to remove from the array list.
     */
    public static void deleteCustomer(Customer customer) { allCustomers.remove(customer); }

    /**
     * Returns the customer id
     * @return the customer id.
     */
    public int getID() { return this.id; }

    /**
     * Returns the customer name.
     * @return the customer name.
     */
    public String getName() { return this.name.get(); }

    /**
     * Returns the customer address.
     * @return the customer address.
     */
    public String getAddress() { return this.address.get(); }

    /**
     * Returns the customer postal code.
     * @return the customer postal code.
     */
    public String getPostalCode() { return this.postalCode.get(); }

    /**
     * Returns the customer phone number.
     * @return the customer phone number.
     */
    public String getPhone() { return this.phone.get(); }

    /**
     * Returns the customer division.
     * @return the customer division.
     */
    public String getDivision() { return this.division.get(); }

    /**
     * Returns the customer country.
     * @return the customer country.
     */
    public String getCountry() { return this.country.get(); }
    /**
     * Returns all the customers as an ObservableList
     * @return all the customers as an ObservableList
     */
    public static ObservableList<Customer> getAllCustomers() { return allCustomers; }
}
