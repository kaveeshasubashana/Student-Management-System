// src/view/AdminDashboardFrame.java
package view;

import model.Teacher; // Import Teacher model
import model.Student; // Import Student model
import service.AdminService; // Import AdminService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader; // Added for table styling
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
    // Removed private JButton viewTeachersButton; // As per previous request
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
    // Removed private JButton viewStudentsButton; // As per previous request
    private JTable studentTable;
    private DefaultTableModel studentTableModel;

    private JButton logoutButton; // Logout button

    // Constructor for AdminDashboardFrame
    public AdminDashboardFrame() {
        // Set modern Nimbus Look and Feel for a beautiful UI
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback to default if Nimbus not available
            System.err.println("Could not set Nimbus Look and Feel: " + e.getMessage());
        }

        // Set the title of the admin dashboard window
        setTitle("Admin Dashboard");
        // Set the default close operation to exit the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the admin dashboard window
        setSize(1200, 750); // Increased size for better layout
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout(5, 5)); // Increased spacing for overall layout

        // Set background color for the main frame
        getContentPane().setBackground(new Color(240, 248, 255)); // Soft alice blue

        // Initialize services
        adminService = new AdminService();
        authService = new AuthService();

        // --- Header Panel  eka ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 75, 120)); // Deeper blue header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25)); // Increased padding

        //header pannel eke thiyana text eka
        JLabel titleLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30)); // Larger, bolder font
        titleLabel.setForeground(Color.WHITE); // Header title remains white for contrast
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        //logout button eka
        logoutButton = new JButton("Logout");
        // Apply styling to logout button using the helper method
        styleButton(logoutButton, new Color(231, 76, 60), new Font("Segoe UI", Font.BOLD, 15)); // Alizarin Red
        JPanel logoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButtonPanel.setOpaque(false); // Make it transparent to show header background
        logoutButtonPanel.add(logoutButton);
        headerPanel.add(logoutButtonPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Create a tabbed pane to separate Teacher and Student management
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Larger font for tabs
        tabbedPane.setForeground(Color.BLACK); // Tab text color set to black
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // Increased padding for tabs
        tabbedPane.setBackground(new Color(173, 216, 230)); // Light blue for tabs (background)

        // --- Teacher Management Panel ---
        // Changed to GridBagLayout for better control over space distribution
        JPanel teacherPanel = new JPanel(new GridBagLayout());
        teacherPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        teacherPanel.setBackground(new Color(248, 248, 255)); // Ghost White background

        // Input form for teachers
        JPanel teacherFormPanel = new JPanel(new GridBagLayout());
        // Title border text color set to black
        teacherFormPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2), "Teacher Details", 0, 0, new Font("Segoe UI", Font.BOLD, 16), Color.BLACK)); // Blue border, Black title
        teacherFormPanel.setBackground(Color.WHITE); // White background for form
        GridBagConstraints gbcTeacher = new GridBagConstraints();
        gbcTeacher.insets = new Insets(9, 12, 9, 12); // Increased padding between components
        gbcTeacher.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15); // Slightly larger field font
        Font labelFieldFont = new Font("Segoe UI", Font.BOLD, 14); // Slightly larger label font

        gbcTeacher.gridx = 0; gbcTeacher.gridy = 0; JLabel teacherUsernameLabel = new JLabel("Username:"); teacherUsernameLabel.setFont(labelFieldFont); teacherUsernameLabel.setForeground(Color.BLACK); teacherFormPanel.add(teacherUsernameLabel, gbcTeacher);
        gbcTeacher.gridx = 1; gbcTeacher.gridy = 0; teacherUsernameField = new JTextField(25); teacherUsernameField.setFont(fieldFont); teacherUsernameField.setForeground(Color.BLACK); teacherFormPanel.add(teacherUsernameField, gbcTeacher);
        gbcTeacher.gridx = 0; gbcTeacher.gridy = 1; JLabel teacherPasswordLabel = new JLabel("Password:"); teacherPasswordLabel.setFont(labelFieldFont); teacherPasswordLabel.setForeground(Color.BLACK); teacherFormPanel.add(teacherPasswordLabel, gbcTeacher);
        gbcTeacher.gridx = 1; gbcTeacher.gridy = 1; teacherPasswordField = new JPasswordField(25); teacherPasswordField.setFont(fieldFont); teacherPasswordField.setForeground(Color.BLACK); teacherFormPanel.add(teacherPasswordField, gbcTeacher);
        gbcTeacher.gridx = 0; gbcTeacher.gridy = 2; JLabel teacherNameLabel = new JLabel("Name:"); teacherNameLabel.setFont(labelFieldFont); teacherNameLabel.setForeground(Color.BLACK); teacherFormPanel.add(teacherNameLabel, gbcTeacher);
        gbcTeacher.gridx = 1; gbcTeacher.gridy = 2; teacherNameField = new JTextField(25); teacherNameField.setFont(fieldFont); teacherNameField.setForeground(Color.BLACK); teacherFormPanel.add(teacherNameField, gbcTeacher);
        gbcTeacher.gridx = 0; gbcTeacher.gridy = 3; JLabel teacherSubjectLabel = new JLabel("Subject:"); teacherSubjectLabel.setFont(labelFieldFont); teacherSubjectLabel.setForeground(Color.BLACK); teacherFormPanel.add(teacherSubjectLabel, gbcTeacher);
        gbcTeacher.gridx = 1; gbcTeacher.gridy = 3; teacherSubjectField = new JTextField(25); teacherSubjectField.setFont(fieldFont); teacherSubjectField.setForeground(Color.BLACK); teacherFormPanel.add(teacherSubjectField, gbcTeacher);

        // Buttons for teacher operations
        JPanel teacherButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // button Increased spacing
        teacherButtonPanel.setBackground(new Color(248, 248, 255)); // Match panel background
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15); // Slightly larger button font
        Color addButtonColor = new Color(46, 204, 113); // Emerald Green
        Color updateButtonColor = new Color(52, 152, 219); // Peter River Blue
        Color deleteButtonColor = new Color(231, 76, 60); // Alizarin Red
        
        addTeacherButton = new JButton("Add Teacher"); styleButton(addTeacherButton, addButtonColor, buttonFont);
        updateTeacherButton = new JButton("Update Teacher"); styleButton(updateTeacherButton, updateButtonColor, buttonFont);
        deleteTeacherButton = new JButton("Delete Teacher"); styleButton(deleteTeacherButton, deleteButtonColor, buttonFont);

        teacherButtonPanel.add(addTeacherButton);
        teacherButtonPanel.add(updateTeacherButton);
        teacherButtonPanel.add(deleteTeacherButton);

        // Table to display teachers
        String[] teacherColumnNames = {"Username", "Name", "Subject"};
        teacherTableModel = new DefaultTableModel(teacherColumnNames, 0);
        teacherTable = createStyledTable(teacherTableModel); // Table styling is handled in createStyledTable
        teacherTable.setForeground(Color.BLACK); // Set table content foreground to black
        JScrollPane teacherScrollPane = new JScrollPane(teacherTable);
        teacherScrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1)); // Blue border for table


        JTableHeader header = teacherTable.getTableHeader();
         header.setForeground(Color.BLACK); // Header text color
        header.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Optional styling

        // MODIFICATION START: Adjusted weighty for even better balance
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.insets = new Insets(10, 0, 10, 0); // Vertical padding between sections

        // Add the form panel
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.weightx = 1.0;
        gbcPanel.weighty = 0.05; // Further reduced weight for form section
        teacherPanel.add(teacherFormPanel, gbcPanel);

        // Add the button panel
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;
        gbcPanel.weighty = 0.02; // Further reduced weight for button section
        teacherPanel.add(teacherButtonPanel, gbcPanel);

        // Add the scroll pane for the table
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 2;
        gbcPanel.weighty = 0.93; // Further increased weight for table section
        teacherPanel.add(teacherScrollPane, gbcPanel);
        // MODIFICATION END

        // Add teacher panel to tabbed pane
        tabbedPane.addTab("Manage Teachers", teacherPanel);

        // --- Student Management Panel ---
        // Changed to GridBagLayout for better control over space distribution
        JPanel studentPanel = new JPanel(new GridBagLayout());
        studentPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        studentPanel.setBackground(new Color(248, 248, 255)); // Ghost White background

        // Input form for students
        JPanel studentFormPanel = new JPanel(new GridBagLayout());
        // Title border text color set to black
        studentFormPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2), "Student Details", 0, 0, new Font("Segoe UI", Font.BOLD, 16), Color.BLACK)); // Blue border, Black title
        studentFormPanel.setBackground(Color.WHITE); // White background for form
        GridBagConstraints gbcStudent = new GridBagConstraints();
        gbcStudent.insets = new Insets(5, 12, 5, 12); // Increased padding between components
        gbcStudent.fill = GridBagConstraints.HORIZONTAL;

        gbcStudent.gridx = 0; gbcStudent.gridy = 0; JLabel studentUsernameLabel = new JLabel("Username:"); studentUsernameLabel.setFont(labelFieldFont); studentUsernameLabel.setForeground(Color.BLACK); studentFormPanel.add(studentUsernameLabel, gbcStudent);
        gbcStudent.gridx = 1; gbcStudent.gridy = 0; studentUsernameField = new JTextField(25); studentUsernameField.setFont(fieldFont); studentUsernameField.setForeground(Color.BLACK); studentFormPanel.add(studentUsernameField, gbcStudent);
        gbcStudent.gridx = 0; gbcStudent.gridy = 1; JLabel studentPasswordLabel = new JLabel("Password:"); studentPasswordLabel.setFont(labelFieldFont); studentPasswordLabel.setForeground(Color.BLACK); studentFormPanel.add(studentPasswordLabel, gbcStudent);
        gbcStudent.gridx = 1; gbcStudent.gridy = 1; studentPasswordField = new JPasswordField(25); studentPasswordField.setFont(fieldFont); studentPasswordField.setForeground(Color.BLACK); studentFormPanel.add(studentPasswordField, gbcStudent);
        gbcStudent.gridx = 0; gbcStudent.gridy = 2; JLabel studentNameLabel = new JLabel("Name:"); studentNameLabel.setFont(labelFieldFont); studentNameLabel.setForeground(Color.BLACK); studentFormPanel.add(studentNameLabel, gbcStudent);
        gbcStudent.gridx = 1; gbcStudent.gridy = 2; studentNameField = new JTextField(25); studentNameField.setFont(fieldFont); studentNameField.setForeground(Color.BLACK); studentFormPanel.add(studentNameField, gbcStudent);
 
        // Buttons for student operations
        JPanel studentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Increased spacing
        studentButtonPanel.setBackground(new Color(248, 248, 255)); // Match panel background
        // Removed addStudentButton = new JButton("Add Student"); styleButton(addStudentButton, addButtonColor, buttonFont); // As per previous request
        // Removed updateStudentButton = new JButton("Update Student"); styleButton(updateButtonColor, updateButtonColor, buttonFont); // As per previous request
        // Removed deleteStudentButton = new JButton("Delete Student"); styleButton(deleteButtonColor, deleteButtonColor, buttonFont); // As per previous request
        // Removed viewStudentsButton = new JButton("View All Students"); styleButton(viewStudentsButton, viewButtonColor, buttonFont); // As per previous request

        addStudentButton = new JButton("Add Student"); styleButton(addStudentButton, addButtonColor, buttonFont);
        updateStudentButton = new JButton("Update Student"); styleButton(updateStudentButton, updateButtonColor, buttonFont);
        deleteStudentButton = new JButton("Delete Student"); styleButton(deleteStudentButton, deleteButtonColor, buttonFont);

        studentButtonPanel.add(addStudentButton);
        studentButtonPanel.add(updateStudentButton);
        studentButtonPanel.add(deleteStudentButton);

        // Table to display students
        String[] studentColumnNames = {"Username", "Name"}; // Modified: Removed "Grade"
        studentTableModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = createStyledTable(studentTableModel); // Table styling is handled in createStyledTable
        studentTable.setForeground(Color.BLACK); // Set table content foreground to black
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1)); // Blue border for table


        JTableHeader header2 = studentTable.getTableHeader();
         header2.setForeground(Color.BLACK); // Header text color
        header2.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Optional styling

        // MODIFICATION START: Adjusted weighty for even better balance
        GridBagConstraints gbcStudentPanel = new GridBagConstraints();
        gbcStudentPanel.fill = GridBagConstraints.BOTH;
        gbcStudentPanel.insets = new Insets(10, 0, 10, 0); // Vertical padding between sections

        

        // Add the form panel
        gbcStudentPanel.gridx = 0;
        gbcStudentPanel.gridy = 0;
        gbcStudentPanel.weightx = 1.0;
        gbcStudentPanel.weighty = 0.05; // Further reduced weight for form section
        studentPanel.add(studentFormPanel, gbcStudentPanel);

        // Add the button panel
        gbcStudentPanel.gridx = 0;
        gbcStudentPanel.gridy = 1;
        gbcStudentPanel.weighty = 0.02; // Further reduced weight for button section
        studentPanel.add(studentButtonPanel, gbcStudentPanel);

        // Add the scroll pane for the table
        gbcStudentPanel.gridx = 0;
        gbcStudentPanel.gridy = 2;
        gbcStudentPanel.weighty = 0.93; // Further increased weight for table section
        studentPanel.add(studentScrollPane, gbcStudentPanel);
        // MODIFICATION END

        // Add student panel to tabbed pane
        tabbedPane.addTab("Manage Students", studentPanel);

        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // --- Action Listeners for Teacher Buttons (functionality unchanged) ---
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

        // --- Action Listeners for Student Buttons (functionality unchanged) ---
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

        // Add a ListSelectionListener to the student table to populate fields when a row is selected
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int selectedRow = studentTable.getSelectedRow();
                studentUsernameField.setText(studentTableModel.getValueAt(selectedRow, 0).toString());
                studentNameField.setText(studentTableModel.getValueAt(selectedRow, 1).toString());
                studentPasswordField.setText(""); // Clear password field for security
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
        viewAllTeachers();
        viewAllStudents();
    }

    // Helper method to create styled labels
    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.BLACK); // Set label text color to black
        return label;
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Use Segoe UI for consistency
        button.setBackground(new Color(70, 130, 180)); // Default Steel blue (will be overridden by styleButton)
        button.setForeground(Color.WHITE); // Button text remains white for contrast
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(25, 25, 112), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20))); // Increased padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Helper method to style buttons (now handles hover effects and specific colors)
    private void styleButton(JButton button, Color bgColor, Font font) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false); // Ensure border is painted by default
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Button padding
        // Add a subtle hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter()); // Lighten color on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor); // Revert to original color
            }
        });
    }

    // Helper method to create styled tables with alternating row colors
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(240, 248, 255)); // Light blue for even rows
                } else {
                    c.setBackground(Color.WHITE); // White for odd rows
                }
                c.setForeground(Color.BLACK); // Set cell content foreground to black
                return c;
            }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Use Segoe UI for consistency
        table.setRowHeight(28); // Increased row height
        table.setGridColor(new Color(173, 216, 230)); // Light blue grid
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // Use Segoe UI for consistency
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE); // Table header text remains white for contrast
        return table;
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
            JOptionPane.showMessageDialog(this, "Teacher added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Teacher updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Teacher deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
