package JMartin_886079_SW2.view_controller;

import static JMartin_886079_SW2.dao.CustomerDaoImpl.*;
import JMartin_886079_SW2.model.Customer;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * JavaFX Screen Controller to edit an existing customer
 */
public class editCustomerScreen implements Initializable {
    /** JavaFX TextField for customer id data */
    @FXML private TextField idTextbox;
    /** JavaFX TextField for name data */
    @FXML private TextField nameTextbox;
    /** JavaFx TextField for address data */
    @FXML private TextField addressTextbox;
    /** JavaFX ComboBox for division data */
    @FXML private ComboBox<String> divisionDropdown;
    /** JavaFX ComboBox for country data */
    @FXML private ComboBox<String> countryDropdown;
    /** JavaFX TextField for phone number data */
    @FXML private TextField phoneTextbox;
    /** JavaFX TextField for zip code data */
    @FXML private TextField postalCodeTextbox;
    /** The customer object of the selected customer */
    Customer selectedCustomer;

    /**
     * Called by a different controller to pass data into this controller
     * @param customer the customer object that is being passed to this controller.
     */
    public void initData(Customer customer) {
        selectedCustomer = customer;

        idTextbox.setText(Integer.toString(selectedCustomer.getID()));
        nameTextbox.setText(selectedCustomer.getName());
        addressTextbox.setText(selectedCustomer.getAddress());
        divisionDropdown.getSelectionModel().select(selectedCustomer.getDivision());
        countryDropdown.getSelectionModel().select(selectedCustomer.getCountry());
        phoneTextbox.setText(selectedCustomer.getPhone());
        postalCodeTextbox.setText(selectedCustomer.getPostalCode());

        countryDropdown.setItems(getCountries());
        divisionDropdown.setItems(getDivisions(selectedCustomer.getCountry()));
    }
    /** Initializes the controller by setting up display field defaults.
     * Lambda allows the division dropdown to automatically populate with valid values based on the Country Dropdown Selection
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /** Lambda Expression allows the division dropdown to automatically populate with valid values based on the Country Dropdown Selection */
        countryDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            divisionDropdown.getItems().clear();
            divisionDropdown.setItems(getDivisions(newValue));
        });
    }
    /**
     * Loads the previous screen (Customer Search Screen)
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("customerSearchScreen","Customer Search",actionEvent);
    }
    /**
     * Adds the updated customer data to the database.
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleAddButton(ActionEvent actionEvent) throws IOException {
        try {
            updateCustomer(Integer.parseInt(idTextbox.getText()),
                                            nameTextbox.getText(),
                                            addressTextbox.getText(),
                                            postalCodeTextbox.getText(),
                                            phoneTextbox.getText(),
                                            returnDivisionID(divisionDropdown.getValue()));
        } catch (SQLException e) {
            AlertBox.display(2, "SQLException in handleAddButton",e.getMessage());
        } finally {
            ScreenLoader.display("customerSearchScreen","Customer Search",actionEvent);
        }
    }

    /**
     * Called when a Country is selected.
     * @param actionEvent Represents a ChoiceBox Selection fire event.
     */
    public void handleCountrySelection(ActionEvent actionEvent) {
    }
}
