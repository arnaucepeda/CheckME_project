package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @date 1/06/2023
 * @version 1.0
 *
 */
public class SignInViewController {

    @FXML
    private TextField fullNameField;

    @FXML
    private Button goBack;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    private UserList userList; // Instance of UserList

    public SignInViewController() {
        userList = new UserList();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void enterButtonClicked(ActionEvent event) {
        String fullName = fullNameField.getText();
        String user = userField.getText();
        String password = passwordField.getText();

        // Verify if the fields are complete
        if (fullName.isEmpty() || user.isEmpty() || password.isEmpty()) {
            // Show error message
            showAlert("Error", "Please complete all the fields.");
            return;
        }

        // Verify if the user already exists in the database
        if (userExists(user)) {
            showAlert("Error", "The user already exists.");
            return;
        }

        // Create a new user
        User newUser = new User(fullName, user, password);

        // Add the user to the UserList
        userList.getUsers().add(newUser);

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7620883",
                    "sql7620883", "ZxlrhkpBp1");

            // Get the last value of codiClient
            String selectQuery = "SELECT MAX(codiClient) AS lastCodiClient FROM User";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);

            int lastCodiClient = 0;

            if (resultSet.next()) {
                lastCodiClient = resultSet.getInt("lastCodiClient");
            }

            // Increment the last value and assign it to the new user
            int nextCodiClient = lastCodiClient + 1;

            // Create the SQL statement to insert the new user into the corresponding table
            String insertQuery = "INSERT INTO User (codiClient, fullName, user, password) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

            insertStatement.setInt(1, nextCodiClient);
            insertStatement.setString(2, newUser.getFullName());
            insertStatement.setString(3, newUser.getUser());
            insertStatement.setString(4, newUser.getPassword());

            // Execute the SQL statement to insert the new user into the database
            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The user has been successfully saved in the database.");
            } else {
                System.out.println("The user could not be saved in the database.");
            }

            // Close resources and the database connection
            resultSet.close();
            selectStatement.close();
            insertStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewLogin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Main.primaryStage = (Stage) goBack.getScene().getWindow();
            Main.primaryStage.close();

            Stage currentStage = (Stage) goBack.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean userExists(String username) {
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7620883",
                    "sql7620883", "ZxlrhkpBp1");

            // Create the SQL statement to check if the user exists
            String selectQuery = "SELECT * FROM User WHERE user = ?";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setString(1, username);

            // Execute the SQL query
            ResultSet resultSet = statement.executeQuery();

            // Check if a result was found in the database
            boolean exists = resultSet.next();

            // Close the connection and release resources
            resultSet.close();
            statement.close();
            connection.close();

            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewLogin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Main.primaryStage = (Stage) goBack.getScene().getWindow();
            Main.primaryStage.close();

            Stage currentStage = (Stage) goBack.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
