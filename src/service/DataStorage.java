// src/service/DataStorage.java
package service;

import model.User;
import model.Admin;
import model.Teacher;
import model.Student;
import model.Mark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String DB_URL = "jdbc:sqlite:data/sms.db";
    private static Connection connection;

    static {
        try {
            // Create data directory if it doesn't exist
            new java.io.File("data").mkdirs();
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    private static void createTables() throws SQLException {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, role TEXT)";
        String teachersTable = "CREATE TABLE IF NOT EXISTS teachers (username TEXT PRIMARY KEY, password TEXT, name TEXT, subject TEXT)";
        String studentsTable = "CREATE TABLE IF NOT EXISTS students (username TEXT PRIMARY KEY, password TEXT, name TEXT, grade TEXT)";
        String marksTable = "CREATE TABLE IF NOT EXISTS marks (" +
    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
    "teacher_username TEXT, " +
    "student_username TEXT, " +
    "subject TEXT, " +
    "marks REAL, " +
    "grade TEXT, " +
    "UNIQUE(teacher_username, student_username, subject))";


        try (Statement stmt = connection.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(teachersTable);
            stmt.execute(studentsTable);
            stmt.execute(marksTable);
        }
    }

    // User Data Storage
    public static void saveUsers(List<User> users) {
        String sql = "INSERT OR REPLACE INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (User user : users) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getRole());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                switch (role) {
                    case "Admin":
                        users.add(new Admin(username, password));
                        break;
                    case "Teacher":
                        users.add(new Teacher(username, password, "", ""));
                        break;
                    case "Student":
                        users.add(new Student(username, password, "", ""));
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Teacher Data Storage
    public static void saveTeachers(List<Teacher> teachers) {
        String sql = "INSERT OR REPLACE INTO teachers (username, password, name, subject) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Teacher teacher : teachers) {
                pstmt.setString(1, teacher.getUsername());
                pstmt.setString(2, teacher.getPassword());
                pstmt.setString(3, teacher.getName());
                pstmt.setString(4, teacher.getSubject());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error saving teachers: " + e.getMessage());
        }
    }

    public static List<Teacher> loadTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("subject")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading teachers: " + e.getMessage());
        }
        return teachers;
    }

    // Student Data Storage
    public static void saveStudents(List<Student> students) {
        String sql = "INSERT OR REPLACE INTO students (username, password, name, grade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Student student : students) {
                pstmt.setString(1, student.getUsername());
                pstmt.setString(2, student.getPassword());
                pstmt.setString(3, student.getName());
                pstmt.setString(4, student.getGrade());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("grade")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    // Mark Data Storage
  public static void saveMarks(List<Mark> marks) {
    String sql = "INSERT OR IGNORE INTO marks (teacher_username, student_username, subject, marks, grade) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        for (Mark mark : marks) {
            pstmt.setString(1, mark.getTeacherUsername());
            pstmt.setString(2, mark.getStudentUsername());
            pstmt.setString(3, mark.getSubject());
            pstmt.setDouble(4, mark.getMarks());
            pstmt.setString(5, mark.getGrade());
            pstmt.executeUpdate();
        }
    } catch (SQLException e) {
        System.err.println("Error saving marks: " + e.getMessage());
    }
}


    public static List<Mark> loadMarks() {
        List<Mark> marks = new ArrayList<>();
        String sql = "SELECT * FROM marks";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                marks.add(new Mark(
                        rs.getString("teacher_username"),
                        rs.getString("student_username"),
                        rs.getString("subject"),
                        rs.getDouble("marks"),
                        rs.getString("grade")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading marks: " + e.getMessage());
        }
        return marks;
    }

    // Delete methods
    public static void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public static void deleteTeacher(String username) {
        String sql = "DELETE FROM teachers WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting teacher: " + e.getMessage());
        }
    }

    public static void deleteStudent(String username) {
        String sql = "DELETE FROM students WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }

    public static void deleteMarksByTeacher(String teacherUsername) {
        String sql = "DELETE FROM marks WHERE teacher_username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, teacherUsername);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting marks by teacher: " + e.getMessage());
        }
    }

    public static void deleteMarksByStudent(String studentUsername) {
        String sql = "DELETE FROM marks WHERE student_username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, studentUsername);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting marks by student: " + e.getMessage());
        }
    }

    public static void deleteSpecificMark(String teacherUsername, String studentUsername, String subject) {
        String sql = "DELETE FROM marks WHERE teacher_username = ? AND student_username = ? AND subject = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, teacherUsername);
            pstmt.setString(2, studentUsername);
            pstmt.setString(3, subject);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting specific mark: " + e.getMessage());
        }
    }

    public static void updateMark(String teacherUsername, String studentUsername, String subject, double newMarks, String newGrade) {
        String sql = "UPDATE marks SET marks = ?, grade = ? WHERE teacher_username = ? AND student_username = ? AND subject = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, newMarks);
            pstmt.setString(2, newGrade);
            pstmt.setString(3, teacherUsername);
            pstmt.setString(4, studentUsername);
            pstmt.setString(5, subject);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating mark: " + e.getMessage());
        }
    }
}
