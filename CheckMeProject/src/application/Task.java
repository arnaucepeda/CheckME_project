package application;

import java.time.LocalDate;
import java.time.Period;
/**
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @version 1.0
 *
 */
/**
 * The Task class represents a task in an application.
 */
public class Task {

	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate limitDate;
	private int idTask;
	private Boolean completed;
	private static int nextId;
	private int daysLeft;
	
	/**
	 * Constructor for the Task class.
	 * 
	 * @param name         The name of the task.
	 * @param description  The description of the task.
	 * @param limitDate    The limit date of the task.
	 */
	public Task(String name, String description, LocalDate limitDate) {
		this.name = name;
		this.description = description;
		this.startDate = LocalDate.now();
		this.limitDate= limitDate;
		this.completed = false;
		++nextId;
		this.idTask = nextId;
		this.daysLeft = daysLeft();
	}
	
	/**
	 * Calculates the days left until the task's limit date.
	 * 
	 * @return The number of days left.
	 */
	public int daysLeft() {
		return Period.between(getStartDate(), getLimitDate()).getDays();
	}
	
	@Override
	public String toString() {
		return "Task{" +
			"Name='" + name + '\'' +
			", Description='" + description + '\'' +
			", Start Date=" + startDate +
			", Limit Date=" + limitDate +
			", ID Task=" + idTask +
			", Days Left=" + daysLeft +
			", Completed=" + completed +
			'}';
	}

	/**
	 * Gets the name of the task.
	 * 
	 * @return The name of the task.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the task.
	 * 
	 * @param name The new name of the task.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the task.
	 * 
	 * @return The description of the task.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the task.
	 * 
	 * @param description The new description of the task.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the start date of the task.
	 * 
	 * @return The start date of the task.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of the task.
	 * 
	 * @param startDate The new start date of the task.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the limit date of the task.
	 * 
	 * @return The limit date of the task.
	 */
	public LocalDate getLimitDate() {
		return limitDate;
	}

	/**
	 * Sets the limit date of the task.
	 * 
	 * @param limitDate The new limit date of the task.
	 */
	public void setLimitDate(LocalDate limitDate) {
		this.limitDate = limitDate;
	}

	/**
	 * Gets the ID of the task.
	 * 
	 * @return The ID of the task.
	 */
	public int getIdTask() {
		return idTask;
	}

	/**
	 * Sets the ID of the task.
	 * 
	 * @param idTask The new ID of the task.
	 */
	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}

	/**
	 * Checks if the task is completed.
	 * 
	 * @return true if the task is completed, false otherwise.
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * Sets the completed status of the task.
	 * 
	 * @param completed true if the task is completed, false otherwise.
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/**
	 * Gets the next ID for a task.
	 * 
	 * @return The next ID for a task.
	 */
	public static int getNextId() {
		return nextId;
	}

	/**
	 * Sets the next ID for a task.
	 * 
	 * @param nextId The new next ID for a task.
	 */
	public static void setNextId(int nextId) {
		Task.nextId = nextId;
	}
}
