// src/view/StudentDashboardFrame.java
package view;

import model.Mark; // Import Mark model
import service.StudentService; // Import StudentService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// StudentDashboardFrame class extends JFrame to create the student dashboard window with a modern look
public class StudentDashboardFrame extends JFrame {
    private StudentService studentService; // Instance of StudentService to perform student operations
    private AuthService authService; // Instance of AuthService for logout functionality
    private String loggedInStudentUsername; // To store the username of the logged-in student

    // Components for viewing marks
    private JTable marksTable;
    private DefaultTableModel marksTableModel;
    private JButton viewMyMarksButton;

    private JButton logoutButton; // Logout button

    // Constructor for StudentDashboardFrame
    public StudentDashboardFrame(String loggedInStudentUsername) {
        this.loggedInStudentUsername = loggedInStudentUsername; // Store the logged-in student's username

        // Set the title of the student dashboard window
        setTitle("Student Dashboard - " + loggedInStudentUsername);
        // Set the default close operation to exit the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the student dashboard window
        setSize(900, 600); // Increased size
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout(15, 15)); // Add spacing

        // Initialize services
        studentService = new StudentService();
        authService = new AuthService();

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 90, 150)); // Dark blue header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Student Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 50, 50)); // Red logout button
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        JPanel logoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButtonPanel.setOpaque(false);
        logoutButtonPanel.add(logoutButton);
        headerPanel.add(logoutButtonPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- View My Marks Panel ---
        JPanel viewMyMarksPanel = new JPanel(new BorderLayout(15, 15));
        viewMyMarksPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        viewMyMarksPanel.setBackground(new Color(240, 248, 255)); // Alice Blue background

        // Table to display student's marks
        String[] marksColumnNames = {"Subject", "Marks", "Grade", "Assigned By Teacher"};
        marksTableModel = new DefaultTableModel(marksColumnNames, 0);
        marksTable = new JTable(marksTableModel);
        styleTable(marksTable); // Apply table styling
        JScrollPane marksScrollPane = new JScrollPane(marksTable);
        marksScrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Table border

        viewMyMarksButton = new JButton("View My Marks");
        styleButton(viewMyMarksButton, new Color(70, 130, 180), new Font("Segoe UI", Font.BOLD, 14)); // Steel Blue button
        JPanel marksButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        marksButtonPanel.setBackground(new Color(240, 248, 255));
        marksButtonPanel.add(viewMyMarksButton);

        viewMyMarksPanel.add(marksButtonPanel, BorderLayout.NORTH);
        viewMyMarksPanel.add(marksScrollPane, BorderLayout.CENTER);

        // Add the marks panel to the center of the frame
        add(viewMyMarksPanel, BorderLayout.CENTER);

        // --- Action Listener for View My Marks Button (functionality unchanged) ---
        viewMyMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMyMarks();
            }
        });

        // --- Action Listener for Logout Button (functionality unchanged) ---
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        // Initial load of data when dashboard opens
        viewMyMarks();
    }

    // Helper method to style buttons
    private void styleButton(JButton button, Color bgColor, Font font) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Helper method to style tables
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(200, 200, 200)); // Light gray grid lines
        table.setSelectionBackground(new Color(173, 216, 230)); // Light blue selection
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false); // Prevent column reordering
    }

    // --- View My Marks Operation (functionality unchanged) ---
    private void viewMyMarks() {
        marksTableModel.setRowCount(0); // Clear existing data
        List<Mark> myMarks = studentService.getMarksByStudent(loggedInStudentUsername);
        for (Mark mark : myMarks) {
            marksTableModel.addRow(new Object[]{mark.getSubject(), mark.getMarks(), mark.getGrade(), mark.getTeacherUsername()});
        }
        if (myMarks.isEmpty()) {
            // Changed from JOptionPane.showMessageDialog to a JLabel for a more integrated message
            // You might want to add a dedicated JLabel for this in the UI if it's a frequent message
            // For now, it will just clear the table and show nothing.
            // If you want a message, consider adding a JLabel below the table.
        }
    }

    // --- Logout Operation (functionality unchanged) ---
    private void logout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
