package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.dao.AppointmentsDaoImpl;
import JMartin_886079_SW2.model.Appointment;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * JavaFX Screen Controller to view upcoming appointments this week/this month.
 */
public class viewAppointmentsScreen implements Initializable {
    /** Stores the Start Date and Time of this week. */
    LocalDateTime weekStartDate = LocalDateTime.of(LocalDate.now(), LocalTime.parse("00:00:00"));
    /** Stores the End Date and Time of this week. */
    LocalDateTime weekEndDate = LocalDateTime.of(LocalDate.now().plusDays(7), LocalTime.parse("23:59:59"));
    /** Stores the Start Date and Time of this month. */
    LocalDateTime monthStartDate = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.parse("00:00:00"));
    /** Stores the End Date and Time of this month. */
    LocalDateTime monthEndDate = monthStartDate.plusMonths(1).minusDays(1).plusHours(23).plusMinutes(59);
    /** Stores all of the appointments in the database */
    ObservableList<Appointment> appointments = AppointmentsDaoImpl.getAppointments();
    /** Stores the Appointments Filtered for the monthStartDate and monthEndDate */
    FilteredList<Appointment> filteredMonthList  = new FilteredList<>(appointments, p -> true);
    /** STores the Appointments filtered for the weekStartDate and weekEndDate */
    FilteredList<Appointment> filteredWeekList = new FilteredList<>(appointments, p -> true);
    /** JavaFX Tab for month data */
    @FXML private Tab monthlyTab;
    /** JavaFX TableView for Month data */
    @FXML private TableView<Appointment> monthlyViewTable;
    /** JavaFX TableColumn for month appointment id data */
    @FXML private TableColumn<Appointment, Integer> monthAppointmentIDColumn;
    /** JavaFX TableColumn for month appointment title data */
    @FXML private TableColumn<Appointment, String> monthTitleColumn;
    /** JavaFX TableColumn for month appointment description data */
    @FXML private TableColumn<Appointment, String> monthDescriptionColumn;
    /** JavaFX TableColumn for month appointment location data */
    @FXML private TableColumn<Appointment, String> monthLocationColumn;
    /** JavaFX TableColumn for month appointment contact data */
    @FXML private TableColumn<Appointment, Integer> monthContactColumn;
    /** JavaFX TableColumn for month appointment type data */
    @FXML private TableColumn<Appointment, String> monthTypeColumn;
    /** JavaFX TableColumn for month appointment date data */
    @FXML private TableColumn<Appointment, String> monthAppointmentDate;
    /** JavaFX TableColumn for month appointment start time data */
    @FXML private TableColumn<Appointment, LocalTime> monthStartColumn;
    /** JavaFX TableColumn for month appointment end time data */
    @FXML private TableColumn<Appointment, LocalTime> monthEndColumn;
    /** JavaFX TableColumn for month appointment customer id data */
    @FXML private TableColumn<Appointment, String> monthCustomerIDColumn;

    /** JavaFX Tab for week appointment data */
    @FXML private Tab weeklyTab;
    /** JavaFX TableView for week appointment data */
    @FXML private TableView<Appointment> weeklyViewTable;
    /** JavaFX TableColumn for week appointment id data */
    @FXML private TableColumn<Appointment, Integer> weekAppointmentIDColumn;
    /** JavaFX TableColumn for week appointment title data */
    @FXML private TableColumn<Appointment, String> weekTitleColumn;
    /** JavaFX TableColumn for week appointment description data */
    @FXML private TableColumn<Appointment, String> weekDescriptionColumn;
    /** JavaFX TableColumn for week appointment location data */
    @FXML private TableColumn<Appointment, String> weekLocationColumn;
    /** JavaFX TableColumn for week appointment contact data */
    @FXML private TableColumn<Appointment, Integer> weekContactColumn;
    /** JavaFX TableColumn for week appointment type data */
    @FXML private TableColumn<Appointment, String> weekTypeColumn;
    /** JavaFX TableColumn for week appointment date data */
    @FXML private TableColumn<Appointment, LocalDate> weekAppointmentDate;
    /** JavaFX TableColumn for week appointment start time data */
    @FXML private TableColumn<Appointment, LocalTime> weekStartColumn;
    /** JavaFX TableColumn for week appointment end time data */
    @FXML private TableColumn<Appointment, LocalTime> weekEndColumn;
    /** JavaFX TableColumn for week appointment customer id data */
    @FXML private TableColumn<Appointment, String> weekCustomerIDColumn;

    /** Initializes the controller by setting up display field defaults.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known. (JavaFX 8 Documentation)
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized. (JavaFX 8 Documentation)
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set up the Weekly Tab Columns
        weekTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        weekAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        weekDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weekLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        weekContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        weekTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        weekAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        weekStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        weekEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        weekCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        refreshWeeklyData();

        //Set up the Monthly Tab Columns
        monthTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthAppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        monthDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        monthContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        monthTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        monthStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        monthEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        monthCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        refreshMonthlyData();
        System.out.println("Month Start Date: " + monthStartDate);
        System.out.println("Month End Date: " + monthEndDate);
    }

    /**
     * Refreshes the filtered list for weekly data.
     * Lambda function helps to filter the Week List to only appointments that are within the weekStartDate and weekEndDate.
     */
    public void refreshWeeklyData() {
        /** Lambda function helps to filter the Week List to only appointments that are within the weekStartDate and weekEndDate. */
        filteredWeekList.setPredicate(appointment -> {
            return appointment.getStart().isAfter(weekStartDate) && appointment.getEnd().isBefore(weekEndDate);
        });
        weeklyViewTable.setItems(filteredWeekList);
    }

    /**
     * Refreshes the filtered list for monthly data.
     * Lambda function helps to filter the Month List to only appointments that are within the monthStartDate and monthEndDate
     */
    public void refreshMonthlyData() {
        /** Lambda function helps to filter the Month List to only appointments that are within the monthStartDate and monthEndDate */
        filteredMonthList.setPredicate(appointment -> {
            return appointment.getStart().isAfter(monthStartDate) && appointment.getEnd().isBefore(monthEndDate);
        });
        //Populate the table
        monthlyViewTable.setItems(filteredMonthList);
    }

    /**
     * Displays the Add Appointment screen.
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleAddAppointmentButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("addAppointmentScreen","Add Appointment",actionEvent);
    }

    /**
     * Displays the Edit Appointment screen.
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleEditAppointmentButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editAppointmentScreen.fxml"));
        Parent editAppointmentScreenParent = loader.load();

        Scene editAppointmentScreenScene = new Scene(editAppointmentScreenParent);

        Stage editAppointmentScreenStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        editAppointmentScreenStage.setScene(editAppointmentScreenScene);
        editAppointmentScreenStage.setTitle("Edit Appointment");

        //Pass the selected data to the Modify screen
        editAppointmentScreen controller = loader.getController();
        //are we selecting something on the weekly or monthly tab? Pass the correct data based on what tab is selected.
        if (weeklyTab.isSelected()) { controller.initData(weeklyViewTable.getSelectionModel().getSelectedItem()); }
        else if (monthlyTab.isSelected()) { controller.initData(monthlyViewTable.getSelectionModel().getSelectedItem()); }
        //Now show the screen.
        editAppointmentScreenStage.show();
    }

    /**
     * Deletes the selected appointment.
     * @param actionEvent Represents a button fire event.
     */
    public void handleDeleteAppointmentButton(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"You are about to delete/cancel the selected appointment. Are you really sure you want to do this?",yes,no);
        alert.setTitle("Are You Sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            if (weeklyTab.isSelected()) {
                if (AppointmentsDaoImpl.deleteAppointment(weeklyViewTable.getSelectionModel().getSelectedItem())) {
                    refreshWeeklyData();
                }
            } else {
                if (AppointmentsDaoImpl.deleteAppointment(monthlyViewTable.getSelectionModel().getSelectedItem())) {
                    refreshMonthlyData();
                }
            }
        }
    }

    /**
     * Displays the previous screen (Main Menu)
     * @param actionEvent Represents a button fire event.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public void handleCancelAppointmentButton(ActionEvent actionEvent) throws IOException {
        ScreenLoader.display("mainScreen","Main Menu", actionEvent);
    }

}
