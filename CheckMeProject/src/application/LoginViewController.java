package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The LoginViewController class controls the behavior of the login view.
 * 
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @date 1/06/2023
 * @version 1.0
 *
 */
 
public class LoginViewController {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Button enterButton;
    @FXML
    private Button signInButton;

    /**
     * Called when the enter button is clicked.
     * It validates the credentials, logs the login event, and navigates to the tasks view if the credentials are valid.
     *
     * @param event the action event
     */
    @FXML
    private void enterButtonClicked(ActionEvent event) {
        // Obtain the username and password entered by the user
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();

        // Verify the credentials in the database
        boolean credentialsValid = validCredentials(username, password);

        if (credentialsValid) {
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = now.format(formatter);

            // Create the log message to write in the log file
            String logMessage = "The user " + username + " with ID " + getUserId() + " has logged in on " + dateTime + "\n";

            // Write the message to the log file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
                writer.write(logMessage);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Load the ViewTasks view from the FXML file
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTasks.fxml"));
                Parent root = loader.load();

                // Create a new scene
                Scene scene = new Scene(root);

                // Get the reference of the current window and close it
                Main.primaryStage = (Stage) enterButton.getScene().getWindow();
                Main.primaryStage.close();

                // Create a new stage and show the scene
                Main.primaryStage.setScene(scene);
                Main.primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("The username or password is incorrect. Please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Called when the sign-in button is clicked.
     * It navigates to the sign-in view.
     *
     * @param event the action event
     */
    @FXML
    private void signInButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSignIn.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Main.primaryStage = (Stage) signInButton.getScene().getWindow();
            Main.primaryStage.close();

            Stage currentStage = (Stage) signInButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int userId;

    /**
     * Validates the provided username and password against the database.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @return true if the credentials are valid, false otherwise
     */
    private boolean validCredentials(String username, String password) {
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7620883",
                    "sql7620883", "ZxlrhkpBp1");

            // Create the SQL statement to verify the credentials
            String selectQuery = "SELECT * FROM User WHERE user = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the SQL query
            ResultSet resultSet = statement.executeQuery();

            // Check if a result was found in the database
            boolean valid = resultSet.next();

            // Get the ID of the authenticated user and assign it to the userId variable
            if (valid) {
                userId = resultSet.getInt("codiClient");
            }

            // Close the connection and release resources
            resultSet.close();
            statement.close();
            connection.close();

            return valid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the ID of the authenticated user.
     *
     * @return the ID of the authenticated user
     */
    public static int getUserId() {
        return userId;
    }
}
