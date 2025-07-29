// src/view/AdminDashboardFrame.java
package view;

import model.Teacher; // Import Teacher model
import model.Student; // Import Student model
import service.AdminService; // Import AdminService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// AdminDashboardFrame class extends JFrame to create the admin dashboard window
public class AdminDashboardFrame extends JFrame {
    private AdminService adminService; // Instance of AdminService to perform admin operations
    private AuthService authService; // Instance of AuthService for logout functionality

    // Components for Teacher Management
    private JTextField teacherUsernameField;
    private JPasswordField teacherPasswordField;
    private JTextField teacherNameField;
    private JTextField teacherSubjectField;
    private JButton addTeacherButton;
    private JButton updateTeacherButton;
    private JButton deleteTeacherButton;
    private JButton viewTeachersButton;
    private JTable teacherTable;
    private DefaultTableModel teacherTableModel;

    // Components for Student Management
    private JTextField studentUsernameField;
    private JPasswordField studentPasswordField;
    private JTextField studentNameField;
    // Removed private JTextField studentGradeField;
    private JButton addStudentButton;
    private JButton updateStudentButton;
    private JButton deleteStudentButton;
    private JButton viewStudentsButton;
    private JTable studentTable;
    private DefaultTableModel studentTableModel;

    private JButton logoutButton; // Logout button

    // Constructor for AdminDashboardFrame
    public AdminDashboardFrame() {
        // Set the title of the admin dashboard window
        setTitle("Admin Dashboard");
        // Set the default close operation to exit the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the admin dashboard window
        setSize(1000, 700);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout());

        // Initialize services
        adminService = new AdminService();
        authService = new AuthService();

        // Create a tabbed pane to separate Teacher and Student management
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Teacher Management Panel ---
        JPanel teacherPanel = new JPanel(new BorderLayout());
        teacherPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input form for teachers
        JPanel teacherFormPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        teacherUsernameField = new JTextField(20);
        teacherPasswordField = new JPasswordField(20);
        teacherNameField = new JTextField(20);
        teacherSubjectField = new JTextField(20);

        teacherFormPanel.add(new JLabel("Username:"));
        teacherFormPanel.add(teacherUsernameField);
        teacherFormPanel.add(new JLabel("Password:"));
        teacherFormPanel.add(teacherPasswordField);
        teacherFormPanel.add(new JLabel("Name:"));
        teacherFormPanel.add(teacherNameField);
        teacherFormPanel.add(new JLabel("Subject:"));
        teacherFormPanel.add(teacherSubjectField);

        // Buttons for teacher operations
        JPanel teacherButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addTeacherButton = new JButton("Add Teacher");
        updateTeacherButton = new JButton("Update Teacher");
        deleteTeacherButton = new JButton("Delete Teacher");
        viewTeachersButton = new JButton("View All Teachers");

        teacherButtonPanel.add(addTeacherButton);
        teacherButtonPanel.add(updateTeacherButton);
        teacherButtonPanel.add(deleteTeacherButton);
        teacherButtonPanel.add(viewTeachersButton);

        // Table to display teachers
        String[] teacherColumnNames = {"Username", "Name", "Subject"};
        teacherTableModel = new DefaultTableModel(teacherColumnNames, 0);
        teacherTable = new JTable(teacherTableModel);
        JScrollPane teacherScrollPane = new JScrollPane(teacherTable);

        teacherPanel.add(teacherFormPanel, BorderLayout.NORTH);
        teacherPanel.add(teacherButtonPanel, BorderLayout.CENTER);
        teacherPanel.add(teacherScrollPane, BorderLayout.SOUTH);

        // Add teacher panel to tabbed pane
        tabbedPane.addTab("Manage Teachers", teacherPanel);

        // --- Student Management Panel ---
        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input form for students
        JPanel studentFormPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // Changed to 3 rows
        studentUsernameField = new JTextField(20);
        studentPasswordField = new JPasswordField(20);
        studentNameField = new JTextField(20);
        // Removed studentGradeField initialization

        studentFormPanel.add(new JLabel("Username:"));
        studentFormPanel.add(studentUsernameField);
        studentFormPanel.add(new JLabel("Password:"));
        studentFormPanel.add(studentPasswordField);
        studentFormPanel.add(new JLabel("Name:"));
        studentFormPanel.add(studentNameField);
        // Removed studentGradeField from panel

        // Buttons for student operations
        JPanel studentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addStudentButton = new JButton("Add Student");
        updateStudentButton = new JButton("Update Student");
        deleteStudentButton = new JButton("Delete Student");
        viewStudentsButton = new JButton("View All Students");

        studentButtonPanel.add(addStudentButton);
        studentButtonPanel.add(updateStudentButton);
        studentButtonPanel.add(deleteStudentButton);
        studentButtonPanel.add(viewStudentsButton);

        // Table to display students
        String[] studentColumnNames = {"Username", "Name"}; // Modified: Removed "Grade"
        studentTableModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = new JTable(studentTableModel);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);

        studentPanel.add(studentFormPanel, BorderLayout.NORTH);
        studentPanel.add(studentButtonPanel, BorderLayout.CENTER);
        studentPanel.add(studentScrollPane, BorderLayout.SOUTH);

        // Add student panel to tabbed pane
        tabbedPane.addTab("Manage Students", studentPanel);

        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // Logout button panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutPanel.add(logoutButton);
        add(logoutPanel, BorderLayout.NORTH); // Add logout button to the top right

        // --- Action Listeners for Teacher Buttons ---
        addTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeacher();
            }
        });

        updateTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeacher();
            }
        });

        deleteTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTeacher();
            }
        });

        viewTeachersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllTeachers();
            }
        });

        // Add a ListSelectionListener to the teacher table to populate fields when a row is selected
        teacherTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && teacherTable.getSelectedRow() != -1) {
                int selectedRow = teacherTable.getSelectedRow();
                teacherUsernameField.setText(teacherTableModel.getValueAt(selectedRow, 0).toString());
                teacherNameField.setText(teacherTableModel.getValueAt(selectedRow, 1).toString());
                teacherSubjectField.setText(teacherTableModel.getValueAt(selectedRow, 2).toString());
                teacherPasswordField.setText(""); // Clear password field for security
            }
        });

        // --- Action Listeners for Student Buttons ---
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllStudents();
            }
        });

        // Add a ListSelectionListener to the student table to populate fields when a row is selected
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int selectedRow = studentTable.getSelectedRow();
                studentUsernameField.setText(studentTableModel.getValueAt(selectedRow, 0).toString());
                studentNameField.setText(studentTableModel.getValueAt(selectedRow, 1).toString());
                // Removed studentGradeField.setText(studentTableModel.getValueAt(selectedRow, 2).toString());
                studentPasswordField.setText(""); // Clear password field for security
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
        viewAllTeachers();
        viewAllStudents();
    }

    // --- Teacher Operations ---
    private void addTeacher() {
        String username = teacherUsernameField.getText();
        String password = new String(teacherPasswordField.getPassword());
        String name = teacherNameField.getText();
        String subject = teacherSubjectField.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all teacher fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (adminService.addTeacher(username, password, name, subject)) {
            JOptionPane.showMessageDialog(this, "Teacher added successfully!");
            clearTeacherFields();
            viewAllTeachers(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add teacher. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTeacher() {
        String username = teacherUsernameField.getText();
        String password = new String(teacherPasswordField.getPassword()); // Password can be empty if not changing
        String name = teacherNameField.getText();
        String subject = teacherSubjectField.getText();

        if (username.isEmpty() || name.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a teacher and fill name/subject fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If password field is empty, it means password is not being updated
        if (adminService.updateTeacher(username, password.isEmpty() ? null : password, name, subject)) {
            JOptionPane.showMessageDialog(this, "Teacher updated successfully!");
            clearTeacherFields();
            viewAllTeachers(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update teacher. Teacher not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTeacher() {
        String username = teacherUsernameField.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a teacher to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + username + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (adminService.deleteTeacher(username)) {
                JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
                clearTeacherFields();
                viewAllTeachers(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete teacher. Teacher not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewAllTeachers() {
        teacherTableModel.setRowCount(0); // Clear existing data
        List<Teacher> teachers = adminService.getAllTeachers();
        for (Teacher teacher : teachers) {
            teacherTableModel.addRow(new Object[]{teacher.getUsername(), teacher.getName(), teacher.getSubject()});
        }
    }

    private void clearTeacherFields() {
        teacherUsernameField.setText("");
        teacherPasswordField.setText("");
        teacherNameField.setText("");
        teacherSubjectField.setText("");
        teacherTable.clearSelection();
    }

    // --- Student Operations ---
    private void addStudent() {
        String username = studentUsernameField.getText();
        String password = new String(studentPasswordField.getPassword());
        String name = studentNameField.getText();
        // Removed String grade = studentGradeField.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) { // Modified condition
            JOptionPane.showMessageDialog(this, "Please fill all student fields (Username, Password, Name).", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (adminService.addStudent(username, password, name)) { // Modified method call
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearStudentFields();
            viewAllStudents(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add student. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     private void updateStudent() {
        String username = studentUsernameField.getText();
        String password = new String(studentPasswordField.getPassword()); // Password can be empty if not changing
        String name = studentNameField.getText();
        String grade = ""; // Initialize with empty string, but we'll fetch the actual grade below

        if (username.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student and fill the name field.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- CRITICAL FIX START ---
        // Fetch the existing student's grade from the data storage
        // This ensures the grade is preserved and not overwritten with an empty string
        List<Student> students = adminService.getAllStudents(); // Load all students
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                grade = student.getGrade(); // Get the existing grade for the selected student
                break; // Found the student, no need to continue loop
            }
        }
        // --- CRITICAL FIX END ---

        // If password field is empty, it means password is not being updated (pass null to service)
        if (adminService.updateStudent(username, password.isEmpty() ? null : password, name, grade)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            clearStudentFields();
            viewAllStudents(); // Refresh table to show updated details
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update student. Student not found or an error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String username = studentUsernameField.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + username + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (adminService.deleteStudent(username)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                clearStudentFields();
                viewAllStudents(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student. Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewAllStudents() {
        studentTableModel.setRowCount(0); // Clear existing data
        List<Student> students = adminService.getAllStudents();
        for (Student student : students) {
            studentTableModel.addRow(new Object[]{student.getUsername(), student.getName()}); // Modified: Removed student.getGrade()
        }
    }

    private void clearStudentFields() {
        studentUsernameField.setText("");
        studentPasswordField.setText("");
        studentNameField.setText("");
        // Removed studentGradeField.setText("");
        studentTable.clearSelection();
    }

    // --- Logout Operation ---
    private void logout() {
        // Here you might want to clear any session data if applicable
        // For this simple app, just dispose the current frame and open the login frame
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}