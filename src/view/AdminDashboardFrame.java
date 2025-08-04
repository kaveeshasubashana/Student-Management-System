// src/view/AdminDashboardFrame.java
package view;

import model.Teacher; // Import Teacher model
import model.Student; // Import Student model
import service.AdminService; // Import AdminService for business logic
import service.AuthService; // Import AuthService for logout
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter; // Added for search filtering
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.RowFilter; // Added for filtering
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
    private JTable teacherTable;
    private DefaultTableModel teacherTableModel;
    private JTextField teacherSearchField; // Added for search

    // Components for Student Management
    private JTextField studentUsernameField;
    private JPasswordField studentPasswordField;
    private JTextField studentNameField;
    private JButton addStudentButton;
    private JButton updateStudentButton;
    private JButton deleteStudentButton;
    private JTable studentTable;
    private DefaultTableModel studentTableModel;
    private JTextField studentSearchField; // Added for search

    private JButton logoutButton; // Logout button
    private boolean isDarkMode = false; // For dark mode toggle

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
            System.err.println("Could not set Nimbus Look and Feel: " + e.getMessage());
        }

        // Set the title of the admin dashboard window
        setTitle("Admin Dashboard");
        // Set the default close operation to exit the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the size of the admin dashboard window
        setSize(1200, 750); // Increased size for better layout
        // Make window resizable
        setResizable(true);
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Set the layout manager for the frame to BorderLayout
        setLayout(new BorderLayout(10, 10)); // Increased spacing for overall layout

        // Set initial background color
        getContentPane().setBackground(new Color(240, 248, 255)); // Soft alice blue (light mode)

        // Initialize services
        adminService = new AdminService();
        authService = new AuthService();

        // Create menu bar
        createMenuBar();

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 75, 120)); // Deeper blue header
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Increased padding

        JLabel titleLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32)); // Larger, bolder font
        titleLabel.setForeground(Color.WHITE); // Header title remains white for contrast
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Logout button
        logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(231, 76, 60), new Font("Segoe UI", Font.BOLD, 16)); // Alizarin Red
        logoutButton.setToolTipText("Click to logout");
        JPanel logoutButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButtonPanel.setOpaque(false); // Make it transparent to show header background
        logoutButtonPanel.add(logoutButton);
        headerPanel.add(logoutButtonPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Create a tabbed pane to separate Teacher and Student management
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger font for tabs
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        tabbedPane.setBackground(new Color(173, 216, 230));

        // --- Teacher Management Panel ---
        JPanel teacherPanel = new JPanel(new GridBagLayout());
        teacherPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        teacherPanel.setBackground(new Color(248, 248, 255)); // Ghost White background

        // Input form for teachers
        JPanel teacherFormPanel = new JPanel(new GridBagLayout());
        teacherFormPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2), "Teacher Details", 0, 0, new Font("Segoe UI", Font.BOLD, 18), Color.BLACK)); // Blue border, Black title
        teacherFormPanel.setBackground(Color.WHITE); // White background for form
        GridBagConstraints gbcTeacher = new GridBagConstraints();
        gbcTeacher.insets = new Insets(12, 15, 12, 15); // Increased padding
        gbcTeacher.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16); // Larger field font
        Font labelFieldFont = new Font("Segoe UI", Font.BOLD, 16); // Larger label font

        gbcTeacher.gridx = 0; gbcTeacher.gridy = 0;
        JLabel teacherUsernameLabel = new JLabel("Username:");
        teacherUsernameLabel.setFont(labelFieldFont);
        teacherUsernameLabel.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherUsernameLabel, gbcTeacher);

        gbcTeacher.gridx = 1; gbcTeacher.gridy = 0;
        teacherUsernameField = new JTextField(25);
        teacherUsernameField.setFont(fieldFont);
        teacherUsernameField.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherUsernameField, gbcTeacher);

        gbcTeacher.gridx = 0; gbcTeacher.gridy = 1;
        JLabel teacherPasswordLabel = new JLabel("Password:");
        teacherPasswordLabel.setFont(labelFieldFont);
        teacherPasswordLabel.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherPasswordLabel, gbcTeacher);

        gbcTeacher.gridx = 1; gbcTeacher.gridy = 1;
        teacherPasswordField = new JPasswordField(25);
        teacherPasswordField.setFont(fieldFont);
        teacherPasswordField.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherPasswordField, gbcTeacher);

        gbcTeacher.gridx = 0; gbcTeacher.gridy = 2;
        JLabel teacherNameLabel = new JLabel("Name:");
        teacherNameLabel.setFont(labelFieldFont);
        teacherNameLabel.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherNameLabel, gbcTeacher);

        gbcTeacher.gridx = 1; gbcTeacher.gridy = 2;
        teacherNameField = new JTextField(25);
        teacherNameField.setFont(fieldFont);
        teacherNameField.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherNameField, gbcTeacher);

        gbcTeacher.gridx = 0; gbcTeacher.gridy = 3;
        JLabel teacherSubjectLabel = new JLabel("Subject:");
        teacherSubjectLabel.setFont(labelFieldFont);
        teacherSubjectLabel.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherSubjectLabel, gbcTeacher);

        gbcTeacher.gridx = 1; gbcTeacher.gridy = 3;
        teacherSubjectField = new JTextField(25);
        teacherSubjectField.setFont(fieldFont);
        teacherSubjectField.setForeground(Color.BLACK);
        teacherFormPanel.add(teacherSubjectField, gbcTeacher);

        // Buttons for teacher operations
        JPanel teacherButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25)); // Increased spacing
        teacherButtonPanel.setBackground(new Color(248, 248, 255)); // Match panel background
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16); // Larger button font
        Color addButtonColor = new Color(46, 204, 113); // Emerald Green
        Color updateButtonColor = new Color(52, 152, 219); // Peter River Blue
        Color deleteButtonColor = new Color(231, 76, 60); // Alizarin Red

        addTeacherButton = new JButton("Add Teacher");
        styleButton(addTeacherButton, addButtonColor, buttonFont);
        addTeacherButton.setToolTipText("Add a new teacher");

        updateTeacherButton = new JButton("Update Teacher");
        styleButton(updateTeacherButton, updateButtonColor, buttonFont);
        updateTeacherButton.setToolTipText("Update selected teacher");

        deleteTeacherButton = new JButton("Delete Teacher");
        styleButton(deleteTeacherButton, deleteButtonColor, buttonFont);
        deleteTeacherButton.setToolTipText("Delete selected teacher");

        teacherButtonPanel.add(addTeacherButton);
        teacherButtonPanel.add(updateTeacherButton);
        teacherButtonPanel.add(deleteTeacherButton);

        // Search field for teachers
        JPanel teacherSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel teacherSearchLabel = new JLabel("Search:");
        teacherSearchLabel.setFont(labelFieldFont);
        teacherSearchField = new JTextField(20);
        teacherSearchField.setFont(fieldFont);
        teacherSearchPanel.add(teacherSearchLabel);
        teacherSearchPanel.add(teacherSearchField);
        teacherSearchPanel.setBackground(new Color(248, 248, 255));

        // Table to display teachers
        String[] teacherColumnNames = {"Username", "Name", "Subject"};
        teacherTableModel = new DefaultTableModel(teacherColumnNames, 0);
        teacherTable = createStyledTable(teacherTableModel);
        teacherTable.setForeground(Color.BLACK);
        JScrollPane teacherScrollPane = new JScrollPane(teacherTable);
        teacherScrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1));

        JTableHeader header = teacherTable.getTableHeader();
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Larger header font

        // Add filtering for teacher table
        TableRowSorter<DefaultTableModel> teacherSorter = new TableRowSorter<>(teacherTableModel);
        teacherTable.setRowSorter(teacherSorter);
        teacherSearchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(teacherSorter, teacherSearchField.getText()); }
            public void removeUpdate(DocumentEvent e) { filterTable(teacherSorter, teacherSearchField.getText()); }
            public void changedUpdate(DocumentEvent e) { filterTable(teacherSorter, teacherSearchField.getText()); }
        });

        // Layout for teacher panel
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.insets = new Insets(10, 0, 10, 0);

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.weightx = 1.0;
        gbcPanel.weighty = 0.05;
        teacherPanel.add(teacherFormPanel, gbcPanel);

        gbcPanel.gridy = 1;
        gbcPanel.weighty = 0.02;
        teacherPanel.add(teacherButtonPanel, gbcPanel);

        gbcPanel.gridy = 2;
        gbcPanel.weighty = 0.02;
        teacherPanel.add(teacherSearchPanel, gbcPanel);

        gbcPanel.gridy = 3;
        gbcPanel.weighty = 0.91;
        teacherPanel.add(teacherScrollPane, gbcPanel);

        tabbedPane.addTab("Manage Teachers", teacherPanel);

        // --- Student Management Panel ---
        // Similar improvements as teacher panel (copy the structure for student panel)
        // For brevity, implement similarly: add search field, tooltips, larger fonts, etc.

        JPanel studentPanel = new JPanel(new GridBagLayout());
        studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        studentPanel.setBackground(new Color(248, 248, 255));

        JPanel studentFormPanel = new JPanel(new GridBagLayout());
        studentFormPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2), "Student Details", 0, 0, new Font("Segoe UI", Font.BOLD, 18), Color.BLACK));
        studentFormPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcStudent = new GridBagConstraints();
        gbcStudent.insets = new Insets(12, 15, 12, 15);
        gbcStudent.fill = GridBagConstraints.HORIZONTAL;

        gbcStudent.gridx = 0; gbcStudent.gridy = 0;
        JLabel studentUsernameLabel = new JLabel("Username:");
        studentUsernameLabel.setFont(labelFieldFont);
        studentUsernameLabel.setForeground(Color.BLACK);
        studentFormPanel.add(studentUsernameLabel, gbcStudent);

        gbcStudent.gridx = 1; gbcStudent.gridy = 0;
        studentUsernameField = new JTextField(25);
        studentUsernameField.setFont(fieldFont);
        studentUsernameField.setForeground(Color.BLACK);
        studentFormPanel.add(studentUsernameField, gbcStudent);

        gbcStudent.gridx = 0; gbcStudent.gridy = 1;
        JLabel studentPasswordLabel = new JLabel("Password:");
        studentPasswordLabel.setFont(labelFieldFont);
        studentPasswordLabel.setForeground(Color.BLACK);
        studentFormPanel.add(studentPasswordLabel, gbcStudent);

        gbcStudent.gridx = 1; gbcStudent.gridy = 1;
        studentPasswordField = new JPasswordField(25);
        studentPasswordField.setFont(fieldFont);
        studentPasswordField.setForeground(Color.BLACK);
        studentFormPanel.add(studentPasswordField, gbcStudent);

        gbcStudent.gridx = 0; gbcStudent.gridy = 2;
        JLabel studentNameLabel = new JLabel("Name:");
        studentNameLabel.setFont(labelFieldFont);
        studentNameLabel.setForeground(Color.BLACK);
        studentFormPanel.add(studentNameLabel, gbcStudent);

        gbcStudent.gridx = 1; gbcStudent.gridy = 2;
        studentNameField = new JTextField(25);
        studentNameField.setFont(fieldFont);
        studentNameField.setForeground(Color.BLACK);
        studentFormPanel.add(studentNameField, gbcStudent);

        // Student buttons
        JPanel studentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
        studentButtonPanel.setBackground(new Color(248, 248, 255));

        addStudentButton = new JButton("Add Student");
        styleButton(addStudentButton, addButtonColor, buttonFont);
        addStudentButton.setToolTipText("Add a new student");

        updateStudentButton = new JButton("Update Student");
        styleButton(updateStudentButton, updateButtonColor, buttonFont);
        updateStudentButton.setToolTipText("Update selected student");

        deleteStudentButton = new JButton("Delete Student");
        styleButton(deleteStudentButton, deleteButtonColor, buttonFont);
        deleteStudentButton.setToolTipText("Delete selected student");

        studentButtonPanel.add(addStudentButton);
        studentButtonPanel.add(updateStudentButton);
        studentButtonPanel.add(deleteStudentButton);

        // Search field for students
        JPanel studentSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel studentSearchLabel = new JLabel("Search:");
        studentSearchLabel.setFont(labelFieldFont);
        studentSearchField = new JTextField(20);
        studentSearchField.setFont(fieldFont);
        studentSearchPanel.add(studentSearchLabel);
        studentSearchPanel.add(studentSearchField);
        studentSearchPanel.setBackground(new Color(248, 248, 255));

        // Table for students
        String[] studentColumnNames = {"Username", "Name"};
        studentTableModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = createStyledTable(studentTableModel);
        studentTable.setForeground(Color.BLACK);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1));

        JTableHeader header2 = studentTable.getTableHeader();
        header2.setForeground(Color.BLACK);
        header2.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Add filtering for student table
        TableRowSorter<DefaultTableModel> studentSorter = new TableRowSorter<>(studentTableModel);
        studentTable.setRowSorter(studentSorter);
        studentSearchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(studentSorter, studentSearchField.getText()); }
            public void removeUpdate(DocumentEvent e) { filterTable(studentSorter, studentSearchField.getText()); }
            public void changedUpdate(DocumentEvent e) { filterTable(studentSorter, studentSearchField.getText()); }
        });

        // Layout for student panel
        GridBagConstraints gbcStudentPanel = new GridBagConstraints();
        gbcStudentPanel.fill = GridBagConstraints.BOTH;
        gbcStudentPanel.insets = new Insets(10, 0, 10, 0);

        gbcStudentPanel.gridx = 0;
        gbcStudentPanel.gridy = 0;
        gbcStudentPanel.weightx = 1.0;
        gbcStudentPanel.weighty = 0.05;
        studentPanel.add(studentFormPanel, gbcStudentPanel);

        gbcStudentPanel.gridy = 1;
        gbcStudentPanel.weighty = 0.02;
        studentPanel.add(studentButtonPanel, gbcStudentPanel);

        gbcStudentPanel.gridy = 2;
        gbcStudentPanel.weighty = 0.02;
        studentPanel.add(studentSearchPanel, gbcStudentPanel);

        gbcStudentPanel.gridy = 3;
        gbcStudentPanel.weighty = 0.91;
        studentPanel.add(studentScrollPane, gbcStudentPanel);

        tabbedPane.addTab("Manage Students", studentPanel);

        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // --- Action Listeners ---
        addTeacherButton.addActionListener(e -> addTeacher());
        updateTeacherButton.addActionListener(e -> updateTeacher());
        deleteTeacherButton.addActionListener(e -> deleteTeacher());

        teacherTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && teacherTable.getSelectedRow() != -1) {
                int selectedRow = teacherTable.getSelectedRow();
                teacherUsernameField.setText((String) teacherTableModel.getValueAt(selectedRow, 0));
                teacherNameField.setText((String) teacherTableModel.getValueAt(selectedRow, 1));
                teacherSubjectField.setText((String) teacherTableModel.getValueAt(selectedRow, 2));
                teacherPasswordField.setText(""); // Clear password
            }
        });

        addStudentButton.addActionListener(e -> addStudent());
        updateStudentButton.addActionListener(e -> updateStudent());
        deleteStudentButton.addActionListener(e -> deleteStudent());

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int selectedRow = studentTable.getSelectedRow();
                studentUsernameField.setText((String) studentTableModel.getValueAt(selectedRow, 0));
                studentNameField.setText((String) studentTableModel.getValueAt(selectedRow, 1));
                studentPasswordField.setText(""); // Clear password
            }
        });

        logoutButton.addActionListener(e -> logout());

        // Initial load of data
        viewAllTeachers();
        viewAllStudents();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu themeMenu = new JMenu("Theme");
        JMenuItem darkModeItem = new JMenuItem("Toggle Dark Mode");
        darkModeItem.addActionListener(e -> toggleDarkMode());
        themeMenu.add(darkModeItem);

        menuBar.add(fileMenu);
        menuBar.add(themeMenu);
        setJMenuBar(menuBar);
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        Color bgColor = isDarkMode ? new Color(30, 30, 30) : new Color(240, 248, 255);
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;
        getContentPane().setBackground(bgColor);
        // Apply to panels, labels, tables, etc. (you may need to traverse components)
        // For simplicity, repaint
        revalidate();
        repaint();
    }

    private void filterTable(TableRowSorter<DefaultTableModel> sorter, String text) {
        if (text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    // Helper method to style buttons
    private void styleButton(JButton button, Color bgColor, Font font) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25)); // Increased padding
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    // Helper method to create styled tables
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
                c.setForeground(Color.BLACK);
                return c;
            }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Larger font
        table.setRowHeight(30); // Increased row height
        table.setGridColor(new Color(173, 216, 230));
        table.setShowGrid(true);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
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

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all student fields (Username, Password, Name).", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (adminService.addStudent(username, password, name)) {
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

        // Fetch the existing student's grade from the data storage
        List<Student> students = adminService.getAllStudents();
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                grade = student.getGrade();
                break;
            }
        }

        if (adminService.updateStudent(username, password.isEmpty() ? null : password, name, grade)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearStudentFields();
            viewAllStudents(); // Refresh table
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
            studentTableModel.addRow(new Object[]{student.getUsername(), student.getName()});
        }
    }

    private void clearStudentFields() {
        studentUsernameField.setText("");
        studentPasswordField.setText("");
        studentNameField.setText("");
        studentTable.clearSelection();
    }

    // --- Logout Operation ---
    private void logout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
