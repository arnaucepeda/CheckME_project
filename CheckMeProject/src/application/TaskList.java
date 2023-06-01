package application;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @date 1/06/2023
 * @version 1.0
 *
 */
/**
 * The TaskList class represents a list of tasks in the application.
 */
public class TaskList {

	private List<Task> tasks;

	/**
	 * Constructor for the TaskList class. Initializes an empty task list.
	 */
	public TaskList() {
		this.tasks = new ArrayList<>();
	}

	/**
	 * Adds a new task to the task list.
	 * 
	 * @param newTask The new task to be added.
	 */
	public void add(Task newTask) {
		this.tasks.add(newTask);
	}

	/**
	 * Retrieves the list of tasks.
	 * 
	 * @return The list of tasks.
	 */
	public List<Task> getTasks() {
		return tasks;
	}
}
