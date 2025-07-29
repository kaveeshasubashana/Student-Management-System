// src/view/TeacherDashboardFrame.java
package view;

import model.Student; // Import Student model
import model.Mark;    // Import Mark model
import service.TeacherService; // Import TeacherService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader; // Added for table styling
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// TeacherDashboardFrame class extends JFrame to create the teacher dashboard window with a modern look
public class TeacherDashboardFrame extends JFrame {
    private TeacherService teacherService; // Instance of TeacherService to perform teacher operations
    private AuthService authService; // Instance of AuthService for logout functionality
    private String loggedInTeacherUsername; // To store the username of the logged-in teacher

    // Components for Student Viewing
    private JTable studentTable;
    private DefaultTableModel studentTableModel;
    private JButton viewAllStudentsButton;

    // Components for Mark Assignment
    private JTextField studentUsernameField;
    private JTextField subjectField;
    private JTextField marksField;
    private JTextField gradeField;
    private JButton assignMarksButton;
    private JButton updateMarksButton;
    private JButton deleteMarksButton;
    private JTable marksTable;
    private DefaultTableModel marksTableModel;

    private JButton logoutButton; // Logout button

    // Constructor for TeacherDashboardFrame
    public TeacherDashboardFrame(String loggedInTeacherUsername) {
        this.loggedInTeacherUsername = loggedInTeacherUsername; // Store the logged-in teacher's username

        // Set the title of the teacher dashboard window
        setTitle("Teacher Dashboard - " + loggedInTeacherUsername);
        // Set the default close operation to exit the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the teacher dashboard window
        setSize(1200, 750); // Increased size
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout(15, 15)); // Add spacing

        // Initialize services
        teacherService = new TeacherService();
        authService = new AuthService();

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(60, 90, 150)); // Dark blue header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Teacher Panel", SwingConstants.CENTER);
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

        // Create a tabbed pane to separate Student viewing and Mark management
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding for tabs

        // --- View Students Panel ---
        JPanel viewStudentsPanel = new JPanel(new BorderLayout(15, 15));
        viewStudentsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        viewStudentsPanel.setBackground(new Color(240, 248, 255)); // Alice Blue background

        // Table to display all students
        // MODIFICATION START: Removed "Grade" from column names
        String[] studentColumnNames = {"Username", "Name"};
        studentTableModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = new JTable(studentTableModel);
        styleTable(studentTable); // Apply table styling
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Table border

        viewAllStudentsButton = new JButton("View All Students");
        styleButton(viewAllStudentsButton, new Color(100, 100, 100), new Font("Segoe UI", Font.BOLD, 14)); // Gray button
        JPanel studentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        studentButtonPanel.setBackground(new Color(240, 248, 255));
        studentButtonPanel.add(viewAllStudentsButton);

        viewStudentsPanel.add(studentButtonPanel, BorderLayout.NORTH);
        viewStudentsPanel.add(studentScrollPane, BorderLayout.CENTER);

        // Add view students panel to tabbed pane
        tabbedPane.addTab("View Students", viewStudentsPanel);

        // --- Manage Marks Panel ---
        JPanel manageMarksPanel = new JPanel(); // Changed to use BoxLayout
        manageMarksPanel.setLayout(new BoxLayout(manageMarksPanel, BoxLayout.Y_AXIS)); // Set BoxLayout for vertical arrangement
        manageMarksPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        manageMarksPanel.setBackground(new Color(240, 248, 255)); // Alice Blue background

        // Input form for marks
        JPanel marksFormPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Reverted to GridLayout
        marksFormPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)), "Assign/Update Marks", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(70, 130, 180)));
        marksFormPanel.setBackground(new Color(255, 255, 255)); // White background for form
        
        // REVERTED: JLabel creation to original form (without direct font in constructor)
        marksFormPanel.add(new JLabel("Student Username:"));
        studentUsernameField = new JTextField(20); // Reverted to original size
        marksFormPanel.add(studentUsernameField);
        marksFormPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField(20); // Reverted to original size
        marksFormPanel.add(subjectField);
        marksFormPanel.add(new JLabel("Marks:"));
        marksField = new JTextField(20); // Reverted to original size
        marksFormPanel.add(marksField);
        marksFormPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField(20); // Reverted to original size
        marksFormPanel.add(gradeField);
        // END REVERTED SECTION
        
        // Buttons for mark operations (styling remains for modern look)
        JPanel marksButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        marksButtonPanel.setBackground(new Color(240, 248, 255));
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Color assignButtonColor = new Color(50, 150, 50); // Green
        Color updateButtonColor = new Color(70, 130, 180); // Steel Blue
        Color deleteButtonColor = new Color(220, 50, 50); // Red

        assignMarksButton = new JButton("Assign Marks"); styleButton(assignMarksButton, assignButtonColor, buttonFont);
        updateMarksButton = new JButton("Update Marks"); styleButton(updateMarksButton, updateButtonColor, buttonFont);
        deleteMarksButton = new JButton("Delete Marks"); styleButton(deleteMarksButton, deleteButtonColor, buttonFont);

        marksButtonPanel.add(assignMarksButton);
        marksButtonPanel.add(updateMarksButton);
        marksButtonPanel.add(deleteMarksButton);

        // Table to display marks (styling remains for modern look)
        String[] marksColumnNames = {"Student Username", "Subject", "Marks", "Grade"};
        marksTableModel = new DefaultTableModel(marksColumnNames, 0);
        marksTable = new JTable(marksTableModel);
        styleTable(marksTable); // Apply table styling
        JScrollPane marksScrollPane = new JScrollPane(marksTable);
        marksScrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1)); // Table border

        // Add components to the manageMarksPanel using BoxLayout
        manageMarksPanel.add(marksFormPanel);
        manageMarksPanel.add(Box.createVerticalStrut(15)); // Add some vertical space
        manageMarksPanel.add(marksButtonPanel);
        manageMarksPanel.add(Box.createVerticalStrut(15)); // Add some vertical space
        manageMarksPanel.add(marksScrollPane);

        // Add manage marks panel to tabbed pane
        tabbedPane.addTab("Manage Marks", manageMarksPanel);

        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // --- Action Listeners for Student Viewing (functionality unchanged) ---
        viewAllStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllStudents();
            }
        });

        // --- Action Listeners for Mark Management (functionality unchanged) ---
        assignMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignMarks();
            }
        });

        updateMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMarks();
            }
        });

        deleteMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMarks();
            }
        });

        // Add a ListSelectionListener to the marks table to populate fields when a row is selected
        marksTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && marksTable.getSelectedRow() != -1) {
                int selectedRow = marksTable.getSelectedRow();
                studentUsernameField.setText(marksTableModel.getValueAt(selectedRow, 0).toString());
                subjectField.setText(marksTableModel.getValueAt(selectedRow, 1).toString());
                marksField.setText(marksTableModel.getValueAt(selectedRow, 2).toString());
                gradeField.setText(marksTableModel.getValueAt(selectedRow, 3).toString());
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
        viewAllStudents();
        viewMarksForTeacher(); // View marks assigned by this teacher
    }

    // Helper method to style buttons (unchanged)
    private void styleButton(JButton button, Color bgColor, Font font) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Helper method to style tables (unchanged)
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

    // --- Student Viewing Operations ---
    private void viewAllStudents() {
        studentTableModel.setRowCount(0); // Clear existing data
        List<Student> students = teacherService.getAllStudents();
        for (Student student : students) {
            // MODIFICATION START: Only add Username and Name to the table
            studentTableModel.addRow(new Object[]{student.getUsername(), student.getName()});
            // MODIFICATION END
        }
    }

    // --- Mark Management Operations (functionality unchanged) ---
    private void assignMarks() {
        String studentUsername = studentUsernameField.getText();
        String subject = subjectField.getText();
        String marksStr = marksField.getText();
        String grade = gradeField.getText();

        if (studentUsername.isEmpty() || subject.isEmpty() || marksStr.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all mark fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double marks = Double.parseDouble(marksStr);
            if (teacherService.assignMarks(loggedInTeacherUsername, studentUsername, subject, marks, grade)) {
                JOptionPane.showMessageDialog(this, "Marks assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearMarksFields();
                viewMarksForTeacher(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to assign marks. Student or subject might be invalid, or marks already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks (a number).", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMarks() {
        String studentUsername = studentUsernameField.getText();
        String subject = subjectField.getText();
        String marksStr = marksField.getText();
        String grade = gradeField.getText();

        if (studentUsername.isEmpty() || subject.isEmpty() || marksStr.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a mark entry and fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double marks = Double.parseDouble(marksStr);
            if (teacherService.updateMarks(loggedInTeacherUsername, studentUsername, subject, marks, grade)) {
                JOptionPane.showMessageDialog(this, "Marks updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearMarksFields();
                viewMarksForTeacher(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update marks. Mark entry not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks (a number).", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMarks() {
        String studentUsername = studentUsernameField.getText();
        String subject = subjectField.getText();

        if (studentUsername.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a mark entry to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete marks for " + studentUsername + " in " + subject + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (teacherService.deleteMarks(loggedInTeacherUsername, studentUsername, subject)) {
                JOptionPane.showMessageDialog(this, "Marks deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearMarksFields();
                viewMarksForTeacher(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete marks. Mark entry not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewMarksForTeacher() {
        marksTableModel.setRowCount(0); // Clear existing data
        List<Mark> marks = teacherService.getMarksByTeacher(loggedInTeacherUsername);
        for (Mark mark : marks) {
            marksTableModel.addRow(new Object[]{mark.getStudentUsername(), mark.getSubject(), mark.getMarks(), mark.getGrade()});
        }
    }

    private void clearMarksFields() {
        studentUsernameField.setText("");
        subjectField.setText("");
        marksField.setText("");
        gradeField.setText("");
        marksTable.clearSelection();
    }

    // --- Logout Operation (functionality unchanged) ---
    private void logout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
