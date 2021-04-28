package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.dao.CustomerDaoImpl;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * JavaFX screen to add a new customer.
 */
public class addCustomerScreen implements Initializable {
    /** JavaFX TextField for customer name */
    @FXML private TextField nameTextbox;
    /** JavaFX TextField for customer address */
    @FXML private TextField addressTextbox;
    /** JavaFX TextField for customer postal code */
    @FXML private TextField postalCodeTextbox;
    /** JavaFX TextField for customer phone number */
    @FXML private TextField phoneTextbox;
    /** JavaFX ComboBox for customer division */
    @FXML private ComboBox<String> divisionDropdown;
    /** JavaFX ComboBox for customer country */
    @FXML private ComboBox<String> countryDropdown;

    /**
     * Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryDropdown.setItems(CustomerDaoImpl.getCountries());
    }

    /**
     * Exits our of the Add Appointment Screen and goes back to the Main Menu Screen
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("customerSearchScreen","Customer Search",actionEvent);
    }

    /**
     * Checks for required information and executes a database insertion for the customer.
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     * @throws SQLException if there is an error from the SQL Server
     */
    public void handleAddButton(ActionEvent actionEvent) throws SQLException, IOException {
        String name = nameTextbox.getText();
        String address = addressTextbox.getText();
        String postalCode = postalCodeTextbox.getText();
        String phone = phoneTextbox.getText();
        String division = divisionDropdown.getValue();
        String country = countryDropdown.getValue();
        if (!name.isBlank() && !address.isBlank() && !postalCode.isBlank() && !phone.isBlank() &&
            !(division == null) && !(country == null))
        {
            try {
                CustomerDaoImpl.insertCustomer(nameTextbox.getText(),
                        addressTextbox.getText(),
                        postalCodeTextbox.getText(),
                        phoneTextbox.getText(),
                        CustomerDaoImpl.returnDivisionID(divisionDropdown.getValue()));
            } catch (SQLException e) {
                AlertBox.display(2, "SQLException in handleAddButton",e.getMessage());
            } finally {
                ScreenLoader.display("customerSearchScreen","Customer Search",actionEvent);
            }
        } else {
            AlertBox.display(2,"Missing Information","Please fill out all fields.");
        }
    }

    /**
     * Populates the Division Dropdown with all Divisions in the database for the currently selected Country.
     * @param actionEvent Represents a ChoiceBox Selection fire event.
     */
    public void handleCountrySelection(ActionEvent actionEvent) {
        divisionDropdown.setItems(CustomerDaoImpl.getDivisions(countryDropdown.getSelectionModel().getSelectedItem()));
    }
}
