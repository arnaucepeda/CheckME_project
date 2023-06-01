package application;
/**
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @date 1/06/2023
 * @version 1.0
 *
 */
/**
 * The User class represents a user in the application.
 */
public class User {
	private int codiClient;
	private static int nextClient;
	private String fullName;
	private String user;
	private String password;

	/**
	 * Constructor for the User class.
	 * 
	 * @param fullName The full name of the user.
	 * @param user     The username of the user.
	 * @param password The password of the user.
	 */
	public User(String fullName, String user, String password) {
		this.fullName = fullName;
		this.user = user;
		this.password = password;
		++nextClient;
		this.codiClient = nextClient;
	}

	/**
	 * Gets the full name of the user.
	 * 
	 * @return The full name of the user.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name of the user.
	 * 
	 * @param fullName The new full name of the user.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the username of the user.
	 * 
	 * @return The username of the user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the username of the user.
	 * 
	 * @param user The new username of the user.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password of the user.
	 * 
	 * @return The password of the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 * 
	 * @param password The new password of the user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the unique ID of the user.
	 * 
	 * @return The unique ID of the user.
	 */
	public int getCodiClient() {
		return codiClient;
	}

	/**
	 * Sets the unique ID of the user.
	 * 
	 * @param codiClient The new unique ID of the user.
	 */
	public void setCodiClient(int codiClient) {
		this.codiClient = codiClient;
	}

	/**
	 * Gets the next available unique ID for a user.
	 * 
	 * @return The next available unique ID for a user.
	 */
	public static int getNextClient() {
		return nextClient;
	}

	/**
	 * Sets the next available unique ID for a user.
	 * 
	 * @param nextClient The new next available unique ID for a user.
	 */
	public static void setNextClient(int nextClient) {
		User.nextClient = nextClient;
	}
}
