package application;
/**
 * @author Blas Martos Ortega - Arnau Cepeda Vivas
 * @version 1.0
 *
 */
import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> users;

    /**
     * Constructor for the UserList class. Initializes an empty user list.
     */
    public UserList() {
        this.users = new ArrayList<>();
    }

    /**
     * Adds a new user to the user list.
     *
     * @param newUser The new user to be added.
     */
    public void add(User newUser) {
        this.users.add(newUser);
    }

    /**
     * Gets the list of users.
     *
     * @return The list of users.
     */
    public List<User> getUsers() {
        return users;
    }
}
