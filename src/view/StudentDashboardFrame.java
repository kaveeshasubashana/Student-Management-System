// src/view/StudentDashboardFrame.java
package view;

import model.Mark; // Import Mark model
import service.StudentService; // Import StudentService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// StudentDashboardFrame class extends JFrame to create the student dashboard window
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
        setSize(800, 500);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout());

        // Initialize services
        studentService = new StudentService();
        authService = new AuthService();

        // --- View My Marks Panel ---
        JPanel viewMyMarksPanel = new JPanel(new BorderLayout());
        viewMyMarksPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table to display student's marks
        String[] marksColumnNames = {"Subject", "Marks", "Grade", "Assigned By Teacher"};
        marksTableModel = new DefaultTableModel(marksColumnNames, 0);
        marksTable = new JTable(marksTableModel);
        JScrollPane marksScrollPane = new JScrollPane(marksTable);

        viewMyMarksButton = new JButton("View My Marks");
        JPanel marksButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        marksButtonPanel.add(viewMyMarksButton);

        viewMyMarksPanel.add(marksButtonPanel, BorderLayout.NORTH);
        viewMyMarksPanel.add(marksScrollPane, BorderLayout.CENTER);

        // Add the marks panel to the center of the frame
        add(viewMyMarksPanel, BorderLayout.CENTER);

        // Logout button panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutPanel.add(logoutButton);
        add(logoutPanel, BorderLayout.NORTH); // Add logout button to the top right

        // --- Action Listener for View My Marks Button ---
        viewMyMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMyMarks();
            }
        });

        // --- Action Listener for Logout Button ---
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        // Initial load of data when dashboard opens
        viewMyMarks();
    }

    // --- View My Marks Operation ---
    private void viewMyMarks() {
        marksTableModel.setRowCount(0); // Clear existing data
        List<Mark> myMarks = studentService.getMarksByStudent(loggedInStudentUsername);
        for (Mark mark : myMarks) {
            marksTableModel.addRow(new Object[]{mark.getSubject(), mark.getMarks(), mark.getGrade(), mark.getTeacherUsername()});
        }
        if (myMarks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No marks available yet.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // --- Logout Operation ---
    private void logout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
