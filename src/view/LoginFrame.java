// src/view/LoginFrame.java
package view;

import service.AuthService; // Import the AuthService to handle login logic
import model.User; // Import the User model for role checking

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// LoginFrame class extends JFrame to create the login window with a modern look
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
        setSize(500, 400); // Slightly larger for better spacing
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout(20, 20)); // Add some padding around the main panels

        // Initialize AuthService
        authService = new AuthService();

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(60, 90, 150)); // Dark blue header
        JLabel titleLabel = new JLabel("Welcome to SMS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Modern font, larger size
        titleLabel.setForeground(Color.WHITE); // White text
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for flexible positioning
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Components fill horizontal space

        // Username
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        inputPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; // Column 1
        gbc.gridy = 0;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputPanel.add(passwordField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Teacher", "Student"});
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JLabel)roleComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER); // Center text in combobox
        inputPanel.add(roleComboBox, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // --- Footer Panel (Button and Message) ---
        JPanel footerPanel = new JPanel(new BorderLayout(10, 10));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30)); // Padding

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.setFocusPainted(false); // Remove focus border
        loginButton.setBorderPainted(false); // Remove default border
        loginButton.setOpaque(true); // Ensure background color is visible
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Button padding

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        footerPanel.add(loginButton, BorderLayout.NORTH);
        footerPanel.add(messageLabel, BorderLayout.SOUTH);

        add(footerPanel, BorderLayout.SOUTH);

        // Add an ActionListener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the login method when the button is clicked
                login();
            }
        });
    }

    // Method to handle the login process (functionality remains unchanged)
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        User loggedInUser = authService.authenticateUser(username, password, role);

        if (loggedInUser != null) {
            messageLabel.setText("Login Successful!");
            messageLabel.setForeground(new Color(34, 139, 34)); // Forest Green
            // Delay closing the login window slightly to show the success message
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openDashboard(loggedInUser);
                    dispose(); // Close the login window
                }
            });
            timer.setRepeats(false); // Only run once
            timer.start();
        } else {
            messageLabel.setText("Invalid Username, Password, or Role.");
            messageLabel.setForeground(new Color(220, 20, 60)); // Crimson Red
        }
    }

    // Method to open the correct dashboard based on the logged-in user's role (functionality remains unchanged)
    private void openDashboard(User user) {
        switch (user.getRole()) {
            case "Admin":
                new AdminDashboardFrame().setVisible(true);
                break;
            case "Teacher":
                new TeacherDashboardFrame(user.getUsername()).setVisible(true);
                break;
            case "Student":
                new StudentDashboardFrame(user.getUsername()).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown user role.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
