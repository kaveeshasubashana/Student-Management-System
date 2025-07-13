// src/service/DataStorage.java
package service;

import model.User;
import model.Admin;
import model.Teacher;
import model.Student;
import model.Mark;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// DataStorage class handles reading from and writing to text files for persistence.
public class DataStorage {
    // File paths for different data types
    private static final String USERS_FILE = "data/users.txt";
    private static final String TEACHERS_FILE = "data/teachers.txt";
    private static final String STUDENTS_FILE = "data/students.txt";
    private static final String MARKS_FILE = "data/marks.txt";

    // Static block to ensure data directory exists when the class is loaded
    static {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // Create the data directory if it doesn't exist
        }
        // Create empty files if they don't exist, to prevent FileNotFoundException later
        try {
            new File(USERS_FILE).createNewFile();
            new File(TEACHERS_FILE).createNewFile();
            new File(STUDENTS_FILE).createNewFile();
            new File(MARKS_FILE).createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating data files: " + e.getMessage());
        }
    }

    // --- User Data Storage ---

    // Saves a list of User objects to the users.txt file.
    // Each user is stored as a comma-separated string: username,password,role
    public static void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine(); // Move to the next line for the next user
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Loads a list of User objects from the users.txt file.
    // Parses each line to reconstruct User objects based on their role.
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) { // Expecting username, password, role
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    // Create appropriate User subclass based on role
                    switch (role) {
                        case "Admin":
                            users.add(new Admin(username, password));
                            break;
                        case "Teacher":
                            // Teachers will have their full details loaded from teachers.txt later
                            // For now, we only need basic User info for authentication
                            users.add(new Teacher(username, password, "", "")); // Placeholder name, subject
                            break;
                        case "Student":
                            // Students will have their full details loaded from students.txt later
                            users.add(new Student(username, password, "", "")); // Placeholder name, grade
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // --- Teacher Data Storage ---

    // Saves a list of Teacher objects to the teachers.txt file.
    // Each teacher is stored as: username,password,name,subject
    public static void saveTeachers(List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEACHERS_FILE))) {
            for (Teacher teacher : teachers) {
                writer.write(teacher.getUsername() + "," + teacher.getPassword() + "," + teacher.getName() + "," + teacher.getSubject());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving teachers: " + e.getMessage());
        }
    }

    // Loads a list of Teacher objects from the teachers.txt file.
    public static List<Teacher> loadTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // Expecting username, password, name, subject
                    teachers.add(new Teacher(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading teachers: " + e.getMessage());
        }
        return teachers;
    }

    // --- Student Data Storage ---

    // Saves a list of Student objects to the students.txt file.
    // Each student is stored as: username,password,name,grade
    public static void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.write(student.getUsername() + "," + student.getPassword() + "," + student.getName() + "," + student.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    // Loads a list of Student objects from the students.txt file.
    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // Expecting username, password, name, grade
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    // --- Mark Data Storage ---

    // Saves a list of Mark objects to the marks.txt file.
    // Each mark is stored as: teacherUsername,studentUsername,subject,marks,grade
    public static void saveMarks(List<Mark> marks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MARKS_FILE))) {
            for (Mark mark : marks) {
                writer.write(mark.getTeacherUsername() + "," +
                             mark.getStudentUsername() + "," +
                             mark.getSubject() + "," +
                             mark.getMarks() + "," +
                             mark.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving marks: " + e.getMessage());
        }
    }

    // Loads a list of Mark objects from the marks.txt file.
    public static List<Mark> loadMarks() {
        List<Mark> marks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MARKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Expecting teacherUsername, studentUsername, subject, marks, grade
                    try {
                        double marksValue = Double.parseDouble(parts[3]);
                        marks.add(new Mark(parts[0], parts[1], parts[2], marksValue, parts[4]));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid marks format in marks.txt: " + parts[3]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading marks: " + e.getMessage());
        }
        return marks;
    }
}
