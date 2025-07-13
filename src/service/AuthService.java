// src/service/AuthService.java
package service;

import model.User;
import model.Admin;
import model.Teacher;
import model.Student;
import java.util.List;

// AuthService handles user authentication and initial data setup.
public class AuthService {

    // Constructor for AuthService.
    // It ensures that an initial Admin user exists when the application starts.
    public AuthService() {
        initializeAdmin();
    }

    // Authenticates a user based on username, password, and role.
    // Returns the authenticated User object if successful, otherwise null.
    public User authenticateUser(String username, String password, String role) {
        // Load all users from the data storage
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            // Check if username, password, and role match
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password) &&
                user.getRole().equalsIgnoreCase(role)) {
                return user; // Return the authenticated user
            }
        }
        return null; // Authentication failed
    }

    // Initializes the default Admin user if one doesn't already exist.
    // This ensures there's always an Admin to start managing the system.
    private void initializeAdmin() {
        List<User> users = DataStorage.loadUsers();
        boolean adminExists = false;
        for (User user : users) {
            if (user.getRole().equals("Admin")) {
                adminExists = true;
                break;
            }
        }

        if (!adminExists) {
            // If no admin exists, create a default admin
            Admin defaultAdmin = new Admin("admin", "admin123"); // Default admin credentials
            users.add(defaultAdmin);
            DataStorage.saveUsers(users); // Save the updated list of users
            System.out.println("Default Admin user created: username 'admin', password 'admin123'");
        }
    }

    // Helper method to check if a username already exists in the system.
    // Useful when adding new teachers or students to prevent duplicates.
    public boolean isUsernameTaken(String username) {
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true; // Username found
            }
        }
        return false; // Username not found
    }
}
