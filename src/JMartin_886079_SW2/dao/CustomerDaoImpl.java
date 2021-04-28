package JMartin_886079_SW2.dao;

import JMartin_886079_SW2.model.Customer;
import JMartin_886079_SW2.model.User;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static JMartin_886079_SW2.dao.DBUtility.ExecuteQuery;

/**
 * Provides utilities to interact with the Customer table.
 */
public class CustomerDaoImpl {
    /**
     * Returns all the customers from the database stored as a Customer object
     * @return all the customers from the database stored as a Customer object
     */
    public static ObservableList<Customer> getCustomers() {
        //Clear any customers that may have been previously loaded.
        Customer.getAllCustomers().clear();

        //Query the database
        String query = "SELECT cust.Customer_ID, cust.Customer_Name, cust.Address, cust.Postal_Code, cust.Phone, cust.Create_Date," +
                "cust.Created_By, cust.Last_Update, cust.Last_Updated_By, division.Division, country.Country " +
                "FROM customers AS cust " +
                "INNER JOIN first_level_divisions AS division ON cust.Division_ID = division.Division_ID " +
                "INNER JOIN countries AS country ON country.Country_ID = division.COUNTRY_ID;";
        ResultSet rs;

        //Store the Query Results
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDate createdDate = null;
                String createdBy = rs.getString("Created_By");
                LocalDateTime updatedDate = null;
                String updatedBy = rs.getString("Last_Updated_By");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                Customer returnedCustomer = new Customer(id, name, address, postalCode, phone, createdDate, createdBy, updatedDate, updatedBy, division, country);
                Customer.addCustomer(returnedCustomer);
            }
        } catch (Exception e) {
            AlertBox.display(2,"Error in getCustomers Method",e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
        //Finally return the customers
        return Customer.getAllCustomers();
    }

    /**
     * Returns all of the countries that Customers can be located in.
     * @return all of the countries that Customers can be located in.
     */
    public static ObservableList<String> getCountries() {

        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.clear();

        String query = "SELECT * FROM countries";
        ResultSet rs;
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                String country = rs.getString("Country");
                countries.add(country);
            }
        } catch (SQLException e) {
            AlertBox.display(2,"Error in getCountries Method",e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
        return countries;
    }

    /**
     * Returns all of the divisions based on a Country Name
     * @param countryName The name of the country you want to get all the divisions for.
     * @return all of the divisions based on a Country name.
     */
    public static ObservableList<String> getDivisions(String countryName) {
        ObservableList<String> divisions = FXCollections.observableArrayList();
        divisions.clear();
        String query = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + returnCountryID(countryName);
        ResultSet rs;
        try {
            rs = DBUtility.ExecuteQuery(query);
            while (rs.next()) {
                String division = rs.getString("Division");
                divisions.add(division);
            }
        } catch (SQLException e) {
            AlertBox.display(2,"Error in getDivisions Method",e.getMessage());
        } finally {
            try { DBUtility.closeConnection(); } catch (Exception e) { /* Ignore */ }
        }
        return divisions;
    }

    /**
     * Inserts a customer into the database
     * @param customerName The name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param divisionID the division of the customer
     * @throws SQLException If the SQL Server returns an error.
     */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setInt(1,0); //Customer_ID
            ps.setString(2,customerName);
            ps.setString(3,address);
            ps.setString(4,postalCode);
            ps.setString(5,phone);
            ps.setString(6,LocalDateTime.now().toString());
            ps.setString(7,User.getUsername());
            ps.setString(8,LocalDateTime.now().toString());
            ps.setString(9,User.getUsername());
            ps.setInt(10,divisionID);

            int result = ps.executeUpdate();

            if (result > 0) {
                AlertBox.display(0,"Customer Successfully Added","");
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in insertCustomer method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
    }

    /**
     * Updates an existing customer in the database.
     * @param customerID the ID of the customer
     * @param customerName the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param divisionID the division id of the customer
     * @throws SQLException if the SQL Server returns an error
     */
    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String query =  "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                        "WHERE Customer_ID = ?;";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setInt(1,0); //Customer_ID
            ps.setString(1,customerName);
            ps.setString(2,address);
            ps.setString(3,postalCode);
            ps.setString(4,phone);
            ps.setString(5,LocalDateTime.now().toString());
            ps.setString(6,User.getUsername());
            ps.setInt(7,divisionID);
            ps.setInt(8,customerID);

            int result = ps.executeUpdate();

            if (result > 0) {
                AlertBox.display(0,"Customer Successfully Updated","");
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in ExecuteUpdate method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
    }

    /**
     * Deletes a customer out of the database
     * @param customer the customer object
     * @return true/false if the delete was successful.
     */
    public static boolean deleteCustomer(Customer customer) {
        AppointmentsDaoImpl.deleteAppointment(customer.getID());
        String query = "DELETE FROM customers WHERE Customer_ID = ?;";
        try {
            PreparedStatement ps = DBUtility.ExecuteUpdate(query);
            ps.setInt(1,customer.getID());

            int result = ps.executeUpdate();
            if (result > 0) {
                Customer.deleteCustomer(customer);
                AlertBox.display(0,"Customer Successfully Deleted","");
                return true;
            }
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in deleteCustomer method", e.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
        return false;
    }

    /**
     * Returns the Division ID based on the Division Name
     * @param divisionName The name of the division to get the ID of
     * @return the Division ID based on the Division Name
     */
    public static Integer returnDivisionID(String divisionName) {
        int divisionID = 0;
        try {
            //Open the Connection
            DBUtility.openConnection();
            //Set the Query
            String query = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionName + "';";
            //Send the Query, save it to 'rs'
            ResultSet rs = ExecuteQuery(query);
            //Read the results from the Query
            if (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        finally {
            //Close the DB Connection
            DBUtility.closeConnection();
        }
        return divisionID;
    }

    /**
     * Returns the Country ID based on the Country Name
     * @param countryName The name of the Country to get the ID of
     * @return the Country ID based on the Country Name
     */
    public static Integer returnCountryID(String countryName) {
        int countryID = 0;
        try {
            DBUtility.openConnection();
            String query = "SELECT * FROM countries WHERE Country = '" + countryName + "';";
            ResultSet rs = ExecuteQuery(query);
            if (rs.next()) {
                countryID = rs.getInt("Country_ID");
            }
        } catch (SQLException sqlE) {
            AlertBox.display(2,"Error in returnCountryID Method",sqlE.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
        return countryID;
    }

    /**
     * Returns the ID of the Customer based on a Customer Name
     * @param customerName The name of the Customer to get the ID of
     * @return the ID of the Customer based on a Customer NAme.
     */
    public static int returnCustomerID(String customerName) {
        int customerID = 0;
        try {
            DBUtility.openConnection();
            String query = "SELECT Customer_ID FROM customers WHERE Customer_Name = '"+ customerName +"';";
            ResultSet rs = ExecuteQuery(query);
            if (rs.next()) {
                customerID = rs.getInt("Customer_ID");
            }
        } catch (SQLException sqlE) {
            AlertBox.display(2,"Error in returnCustomerID Method",sqlE.getMessage());
        } finally {
            DBUtility.closeConnection();
        }
        return customerID;

    }
}
