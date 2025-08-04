// src/service/AuthService.java
package service;

import model.User;
import model.Admin;

import java.util.List;

public class AuthService {
    public AuthService() {
        initializeAdmin();
    }

    public User authenticateUser(String username, String password, String role) {
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password) &&
                user.getRole().equalsIgnoreCase(role)) {
                return user;
            }
        }
        return null;
    }

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
            Admin defaultAdmin = new Admin("admin", "admin123");
            users.add(defaultAdmin);
            DataStorage.saveUsers(users);
            System.out.println("Default Admin user created: username 'admin', password 'admin123'");
        }
    }

    public boolean isUsernameTaken(String username) {
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
