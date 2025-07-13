// src/model/User.java
package model;

// User is an abstract class, meaning it cannot be instantiated directly.
// It serves as a base class for Admin, Teacher, and Student, providing common attributes.
public abstract class User {
    private String username; // Unique identifier for the user
    private String password; // Password for user authentication
    private String role;     // Role of the user (e.g., "Admin", "Teacher", "Student")

    // Constructor to initialize a User object
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter method for role
    public String getRole() {
        return role;
    }

    // Setter method for role
    public void setRole(String role) {
        this.role = role;
    }

    // Abstract method to describe the user.
    // Subclasses must implement this method.
    public abstract String describeUser();

    @Override
    public String toString() {
        return "Username: " + username + ", Role: " + role;
    }
}
