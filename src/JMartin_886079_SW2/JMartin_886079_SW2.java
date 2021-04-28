package JMartin_886079_SW2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Software application that utilizes JavaFX to maintain appointments and customers.
 * @author Josh Martin
 * @version 1.0.45
 * */
public class JMartin_886079_SW2 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./view_controller/loginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        //launch the application (login screen)
        launch(args);
    }

}