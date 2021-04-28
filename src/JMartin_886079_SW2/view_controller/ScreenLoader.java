package JMartin_886079_SW2.view_controller;

import JMartin_886079_SW2.utils.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Utility to load a new JavaFX Screen and display it
 */
public class ScreenLoader {
    /**
     * Displays a new screen based on the input parameters
     * @param screen Name of the screen
     * @param title Title of the screen
     * @param actionEvent Represents an event that is fired.
     * @throws IOException if there is an error with the JavaFX FXML Source File(s).
     */
    public static void display(String screen, String title, ActionEvent actionEvent) throws IOException {
        try {
            String screenURL = screen + ".fxml";

            Parent screenParent = FXMLLoader.load(ScreenLoader.class.getResource(screenURL));
            Scene screenScene = new Scene(screenParent);
            //this line gets the stage information
            Stage screenStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            screenStage.setScene(screenScene);
            screenStage.setTitle(title);
            screenStage.centerOnScreen();
            screenStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertBox.display(2,"IOException in ScreenLoader.display",e.getMessage());
        }
    }

}
