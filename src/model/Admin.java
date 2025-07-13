// src/model/Admin.java
package model;

// Admin class extends the abstract User class.
// It represents an administrator user in the system.
public class Admin extends User {
    // Constructor for Admin. It calls the superclass (User) constructor
    // and sets the role specifically to "Admin".
    public Admin(String username, String password) {
        super(username, password, "Admin");
    }

    // Implementation of the abstract describeUser method from the User class.
    @Override
    public String describeUser() {
        return "Admin User: " + getUsername();
    }
}
