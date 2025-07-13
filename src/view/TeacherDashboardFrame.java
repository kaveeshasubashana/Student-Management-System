// src/view/TeacherDashboardFrame.java
package view;

import model.Student; // Import Student model
import model.Mark;    // Import Mark model
import service.TeacherService; // Import TeacherService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// TeacherDashboardFrame class extends JFrame to create the teacher dashboard window
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
        setSize(1000, 700);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout());

        // Initialize services
        teacherService = new TeacherService();
        authService = new AuthService();

        // Create a tabbed pane to separate Student viewing and Mark management
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- View Students Panel ---
        JPanel viewStudentsPanel = new JPanel(new BorderLayout());
        viewStudentsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table to display all students
        String[] studentColumnNames = {"Username", "Name", "Grade"};
        studentTableModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = new JTable(studentTableModel);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);

        viewAllStudentsButton = new JButton("View All Students");
        JPanel studentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        studentButtonPanel.add(viewAllStudentsButton);

        viewStudentsPanel.add(studentButtonPanel, BorderLayout.NORTH);
        viewStudentsPanel.add(studentScrollPane, BorderLayout.CENTER);

        // Add view students panel to tabbed pane
        tabbedPane.addTab("View Students", viewStudentsPanel);

        // --- Manage Marks Panel ---
        JPanel manageMarksPanel = new JPanel(new BorderLayout());
        manageMarksPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input form for marks
        JPanel marksFormPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        studentUsernameField = new JTextField(20);
        subjectField = new JTextField(20);
        marksField = new JTextField(20);
        gradeField = new JTextField(20);

        marksFormPanel.add(new JLabel("Student Username:"));
        marksFormPanel.add(studentUsernameField);
        marksFormPanel.add(new JLabel("Subject:"));
        marksFormPanel.add(subjectField);
        marksFormPanel.add(new JLabel("Marks:"));
        marksFormPanel.add(marksField);
        marksFormPanel.add(new JLabel("Grade:"));
        marksFormPanel.add(gradeField);

        // Buttons for mark operations
        JPanel marksButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        assignMarksButton = new JButton("Assign Marks");
        updateMarksButton = new JButton("Update Marks");
        deleteMarksButton = new JButton("Delete Marks");

        marksButtonPanel.add(assignMarksButton);
        marksButtonPanel.add(updateMarksButton);
        marksButtonPanel.add(deleteMarksButton);

        // Table to display marks
        String[] marksColumnNames = {"Student Username", "Subject", "Marks", "Grade"};
        marksTableModel = new DefaultTableModel(marksColumnNames, 0);
        marksTable = new JTable(marksTableModel);
        JScrollPane marksScrollPane = new JScrollPane(marksTable);

        manageMarksPanel.add(marksFormPanel, BorderLayout.NORTH);
        manageMarksPanel.add(marksButtonPanel, BorderLayout.CENTER);
        manageMarksPanel.add(marksScrollPane, BorderLayout.SOUTH);

        // Add manage marks panel to tabbed pane
        tabbedPane.addTab("Manage Marks", manageMarksPanel);

        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // Logout button panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutPanel.add(logoutButton);
        add(logoutPanel, BorderLayout.NORTH); // Add logout button to the top right

        // --- Action Listeners for Student Viewing ---
        viewAllStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllStudents();
            }
        });

        // --- Action Listeners for Mark Management ---
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

        // --- Action Listener for Logout Button ---
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

    // --- Student Viewing Operations ---
    private void viewAllStudents() {
        studentTableModel.setRowCount(0); // Clear existing data
        List<Student> students = teacherService.getAllStudents();
        for (Student student : students) {
            studentTableModel.addRow(new Object[]{student.getUsername(), student.getName(), student.getGrade()});
        }
    }

    // --- Mark Management Operations ---
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
                JOptionPane.showMessageDialog(this, "Marks assigned successfully!");
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
                JOptionPane.showMessageDialog(this, "Marks updated successfully!");
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
                JOptionPane.showMessageDialog(this, "Marks deleted successfully!");
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

    // --- Logout Operation ---
    private void logout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
