package JMartin_886079_SW2.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utility for displaying a JavaFX Alert Dialog
 */
public class AlertBox {
    /**
     * Displays a JavaFX Alert Dialog
     * @param messageType Provide the type of message to change the icon of the alert dialog.
     *                    0 = Informational
     *                    1 = Warning
     *                    2 = Error
     *                    3 = Confirmation
     * @param title Provide the title of the alert dialog
     * @param message Provide the message of the alert dialog
     * @return the button that was clicked.
     */
    public static Optional<ButtonType> display(int messageType, String title, String message) {
        Alert alertBox = null;
        switch (messageType) {
            case 0: alertBox = new Alert(Alert.AlertType.INFORMATION); break;
            case 1: alertBox = new Alert(Alert.AlertType.WARNING); break;
            case 2: alertBox = new Alert(Alert.AlertType.ERROR); break;
            case 3: alertBox = new Alert(Alert.AlertType.CONFIRMATION); break;
        }
        alertBox.setHeaderText(title);
        alertBox.setContentText(message);
        return alertBox.showAndWait();
    }
}
