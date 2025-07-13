// src/view/LoginFrame.java
package view;

import service.AuthService; // Import the AuthService to handle login logic
import model.User; // Import the User model for role checking

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// LoginFrame class extends JFrame to create the login window
public class LoginFrame extends JFrame {
    private JTextField usernameField; // Text field for username input
    private JPasswordField passwordField; // Password field for password input
    private JComboBox<String> roleComboBox; // Combo box to select user role
    private JButton loginButton; // Button to trigger login action
    private JLabel messageLabel; // Label to display login messages (e.g., success/failure)

    private AuthService authService; // Instance of AuthService to perform authentication

    // Constructor for LoginFrame
    public LoginFrame() {
        // Set the title of the login window
        setTitle("Student Management System - Login");
        // Set the default close operation to exit the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the login window
        setSize(400, 300);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout());

        // Initialize AuthService
        authService = new AuthService();

        // Create a panel for the input fields and labels
        JPanel inputPanel = new JPanel();
        // Set the layout for the input panel to GridLayout for organized arrangement
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Rows, Cols, HGap, VGap
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Initialize components
        usernameField = new JTextField(20); // Username text field with preferred column width
        passwordField = new JPasswordField(20); // Password field
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Teacher", "Student"}); // Role selection combo box
        loginButton = new JButton("Login"); // Login button
        messageLabel = new JLabel("", SwingConstants.CENTER); // Message label, centered text

        // Add components to the input panel
        inputPanel.add(new JLabel("Username:")); // Label for username
        inputPanel.add(usernameField); // Username input field
        inputPanel.add(new JLabel("Password:")); // Label for password
        inputPanel.add(passwordField); // Password input field
        inputPanel.add(new JLabel("Role:")); // Label for role selection
        inputPanel.add(roleComboBox); // Role combo box

        // Add the input panel to the center of the frame
        add(inputPanel, BorderLayout.CENTER);

        // Create a panel for the login button and message label
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // Add padding

        // Add login button to the button panel (at the top of this panel)
        buttonPanel.add(loginButton, BorderLayout.NORTH);
        // Add message label to the button panel (at the bottom of this panel)
        buttonPanel.add(messageLabel, BorderLayout.SOUTH);

        // Add the button panel to the south of the main frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Add an ActionListener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the login method when the button is clicked
                login();
            }
        });
    }

    // Method to handle the login process
    private void login() {
        // Get the username, password, and selected role from the input fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        // Attempt to authenticate the user using AuthService
        User loggedInUser = authService.authenticateUser(username, password, role);

        // Check if authentication was successful
        if (loggedInUser != null) {
            messageLabel.setText("Login Successful!"); // Display success message
            messageLabel.setForeground(Color.BLUE); // Set message color to blue

            // Open the appropriate dashboard based on the user's role
            openDashboard(loggedInUser);
            // Hide the login window after successful login
            this.dispose();
        } else {
            messageLabel.setText("Invalid Username, Password, or Role."); // Display error message
            messageLabel.setForeground(Color.RED); // Set message color to red
        }
    }

    // Method to open the correct dashboard based on the logged-in user's role
    private void openDashboard(User user) {
        // Use a switch statement to handle different roles
        switch (user.getRole()) {
            case "Admin":
                // Create and display AdminDashboardFrame
                new AdminDashboardFrame().setVisible(true);
                break;
            case "Teacher":
                // Create and display TeacherDashboardFrame, passing the logged-in teacher's username
                new TeacherDashboardFrame(user.getUsername()).setVisible(true);
                break;
            case "Student":
                // Create and display StudentDashboardFrame, passing the logged-in student's username
                new StudentDashboardFrame(user.getUsername()).setVisible(true);
                break;
            default:
                // If an unknown role is encountered, display an error message
                JOptionPane.showMessageDialog(this, "Unknown user role.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}