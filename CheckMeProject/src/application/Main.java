/**
 * The Main class is the entry point of the application.
 * It sets up the primary stage and launches the JavaFX application.
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
/**
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @date 1/06/2023
 * @version 1.0
 *
 */
public class Main extends Application {

    public static Stage primaryStage;

    /**
     * The start method is called when the JavaFX application is launched.
     * It loads the FXML file, sets up the primary stage, and displays it.
     *
     * @param primaryStage the primary stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewLogin.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Set the application icon
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logoCheckME.png")));

            // Set the scene on the primary stage and show it
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method is the entry point of the Java application.
     * It launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
