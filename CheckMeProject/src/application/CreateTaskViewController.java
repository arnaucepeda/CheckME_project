package application;

import javafx.scene.control.TextArea;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The CreateTaskViewController class controls the create task view in the application.
 * It handles creating a new task and saving it to the database and task list.
 * It also provides functionality to write logs and navigate to the tasks view.
 * 
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @version 1.0
 */
public class CreateTaskViewController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private DatePicker limitDateDatePicker;

    @FXML
    private Button createButton;

    private TaskList taskList;

    /**
     * Sets the task list for the CreateTaskViewController.
     * 
     * @param taskList The task list.
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Initializes the create task view.
     * Perform any necessary initialization here.
     */
    @FXML
    private void initialize() {
        // Perform any necessary initialization here
    }

    /**
     * Handles the create button clicked event.
     * Creates a new task and saves it to the database and task list.
     * 
     * @param event The action event.
     */
    @FXML
    void createButtonClicked(ActionEvent event) {
        String name = nameTextField.getText();
        String description = descriptionTextField.getText();
        LocalDate limitDate = limitDateDatePicker.getValue();

        // Create a new Task object with the user-entered data
        Task newTask = new Task(name, description, limitDate);

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7620883",
                    "sql7620883", "ZxlrhkpBp1");

            // Create the SQL statement to insert the task into the corresponding table
            String insertQuery = "INSERT INTO Task (name, description, startDate, limitDate, removed, completed, id_Client) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);

            statement.setString(1, newTask.getName());
            statement.setString(2, newTask.getDescription());
            statement.setString(3, newTask.getStartDate().toString());
            statement.setString(4, newTask.getLimitDate().toString());
            statement.setBoolean(5, false);
            statement.setBoolean(6, newTask.getCompleted());
            statement.setInt(7, LoginViewController.getUserId());

            // Execute the SQL statement to insert the task into the database
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The task has been successfully saved in the database.");
            } else {
                System.out.println("The task could not be saved in the database.");
            }

            // Close the database connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the new Task object to the task list
        taskList.add(newTask);

        // Write to the log
        writeLog("User " + LoginViewController.getUserId() + " created a task with ID " + newTask.getIdTask());

        // Print the task list to the console
        System.out.println("Task List: ");
        for (Task t : taskList.getTasks()) {
            System.out.println(t.toString());
        }

        // Reset the text input fields
        nameTextField.clear();
        descriptionTextField.clear();
        limitDateDatePicker.setValue(null);
    }

    /**
     * Writes a message to the log file.
     * The message includes the current date and time.
     * 
     * @param message The message to write to the log.
     */
    private void writeLog(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = now.format(formatter);

        // Create the message to write to the log file
        String logMessage = message + " on " + dateTime + "\n";

        // Write the message to the log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
            writer.write(logMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int loggedInUserId;

    /**
     * Sets the logged-in user ID.
     * 
     * @param userId The user ID.
     */
    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }

    @FXML
    private Button taskMenu;

    /**
     * Opens the tasks view.
     * 
     * @param event The action event.
     */
    @FXML
    private void openViewTasks(ActionEvent event) {
        try {
            // Get the Stage of the current window
            Main.primaryStage = (Stage) taskMenu.getScene().getWindow();

            // Load the ViewTasks.fxml view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTasks.fxml"));
            Parent root = loader.load();

            Main.primaryStage.setScene(new Scene(root));
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
