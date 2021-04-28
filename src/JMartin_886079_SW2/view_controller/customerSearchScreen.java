package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.dao.CustomerDaoImpl;
import JMartin_886079_SW2.model.Customer;
import JMartin_886079_SW2.utils.AlertBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * JavaFX Screen to add, update, and delete customers.
 */
public class customerSearchScreen implements Initializable {

    //configure the table
    /** JavaFX TableView to list all Customers */
    @FXML private TableView<Customer> customerTableView;
    /** JavaFX TableColumn for name data */
    @FXML private TableColumn<Customer, String> nameColumn;
    /** JavaFX TableColumn for address data */
    @FXML private TableColumn<Customer, String> addressColumn;
    /** JavaFX TableColumn for division data */
    @FXML private TableColumn<Customer, String> divisionColumn;
    /** JavaFX TableColumn for zip code data */
    @FXML private TableColumn<Customer, String> zipColumn;
    /** JavaFX TableColumn for country data */
    @FXML private TableColumn<Customer, String> countryColumn;
    /** JavaFX TableColumn for phone number data */
    @FXML private TableColumn<Customer, String> phoneColumn;
    // @FXML private TextField searchTextbox;

    /** Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Query the Database to get all the customers and place them in the customers object.
        ObservableList<Customer> customers = CustomerDaoImpl.getCustomers();

        //Set up the Columns.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //Populate the table.
        customerTableView.setItems(customers);

    }
    /*
    public void handleSearchButton(ActionEvent actionEvent) {
        String searchValue = searchTextbox.getText();
        if (Customer.lookup(searchValue) != null) {
            customerTableView.getSelectionModel().select(Customer.lookup(searchValue).getID()-1);
        } else {
            AlertBox.display(2,"You must enter a name to search for!","Please enter a name to search for.  Search parameter is blank.");
        }
    } */
    /**
     * Exits our of the Customers Screen and goes back to the Main Menu Screen
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("mainScreen","Main Menu",actionEvent);
    }

    /**
     * Loads the Edit Customer Screen based on the currently selected customer in the TableView
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleEditCustomerButton(ActionEvent actionEvent) throws IOException {
        if (!customerTableView.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("editCustomerScreen.fxml"));
            Parent editCustomerScreenParent = loader.load();

            Scene editCustomerScreenScene = new Scene(editCustomerScreenParent);

            Stage editCustomerScreenStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            editCustomerScreenStage.setScene(editCustomerScreenScene);
            editCustomerScreenStage.setTitle("Edit Customer");

            //Pass the selected data to the Modify screen
            editCustomerScreen controller = loader.getController();
            controller.initData(customerTableView.getSelectionModel().getSelectedItem());
            //Now show the screen.
            editCustomerScreenStage.show();
        } else {
            AlertBox.display(2,"No Customer Selected","Please select a customer first before attempting to click edit.");
        }
    }
    /**
     * Loads the Add Customer Screen
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleAddButton(ActionEvent actionEvent) throws IOException {
        System.out.println(actionEvent.getSource());
        System.out.println(actionEvent.getTarget());
        ScreenLoader.display("addCustomerScreen","Add Customer",actionEvent);
    }

    /**
     * Deletes a customer from the database
     * @param actionEvent Represents a button fire event.
     */
    public void handleDeleteButton(ActionEvent actionEvent) {
        if (!customerTableView.getSelectionModel().isEmpty()) {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You are about to delete the selected customer. Are you really sure you want to do this?",yes,no);
            alert.setTitle("Delete Customer Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                CustomerDaoImpl.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem());
            }
        } else {
            AlertBox.display(2,"No Customer Selected","Please select a customer first before attempting to click delete.");
        }
    }
}
