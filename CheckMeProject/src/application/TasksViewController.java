package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The TasksViewController class controls the tasks view in the application.
 * It handles displaying and managing the tasks for a user.
 * 
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @date 1/06/2023
 * @version 1.0
 */
public class TasksViewController {

    @FXML
    private Button deleteTask;
    @FXML
    private Button createTaskMenuItem;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> startDateColumn;
    @FXML
    private TableColumn<Task, String> limitDateColumn;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, Integer> daysLeftColumn;

    // Establish a connection to the database
    private static final String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7620883";
    private static final String DB_USERNAME = "sql7620883";
    private static final String DB_PASSWORD = "ZxlrhkpBp1";

    /**
     * Initializes the tasks view.
     * Configures table columns and retrieves tasks for the current user.
     */
    @FXML
    private void initialize() {
        // Configure table columns
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        startDateColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        limitDateColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getLimitDate().toString()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Configure days left column
        daysLeftColumn.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            int daysLeft = task.daysLeft();
            return new SimpleIntegerProperty(daysLeft).asObject();
        });

        // Get tasks for the current user and display them in the table
        int userId = LoginViewController.getUserId();
        ObservableList<Task> taskData = getTasksForUser(userId);
        taskTable.setItems(taskData);
    }

    /**
     * Retrieves the tasks for a specific user from the database.
     * 
     * @param userId The ID of the user.
     * @return The list of tasks for the user.
     */
    private ObservableList<Task> getTasksForUser(int userId) {
        ObservableList<Task> taskData = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT name, startDate, limitDate, description FROM Task WHERE id_Client = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                LocalDate limitDate = resultSet.getDate("limitDate").toLocalDate();
                String description = resultSet.getString("description");

                Task task = new Task(name, description, limitDate);
                task.setStartDate(startDate);
                taskData.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskData;
    }

    /**
     * Opens the create task view.
     * 
     * @param event The action event.
     */
    @FXML
    private void openViewCreateTask(ActionEvent event) {
        try {
            // Get the Stage of the current window
            Main.primaryStage = (Stage) createTaskMenuItem.getScene().getWindow();

            // Create a new instance of TaskList
            TaskList taskList = new TaskList();

            // Load the ViewCreateTask.fxml view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewCreateTask.fxml"));
            Parent root = loader.load();

            // Get the CreateTaskViewController
            CreateTaskViewController createTaskController = loader.getController();

            // Pass the TaskList instance to the CreateTaskViewController
            createTaskController.setTaskList(taskList);

            Main.primaryStage.setScene(new Scene(root));
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
