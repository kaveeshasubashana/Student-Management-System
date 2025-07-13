// src/service/AdminService.java
package service;

import model.User;
import model.Teacher;
import model.Mark;
import model.Student;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// AdminService provides business logic for Admin operations related to managing teachers and students.
public class AdminService {

    private AuthService authService; // AuthService dependency to check for existing usernames

    public AdminService() {
        this.authService = new AuthService(); // Initialize AuthService
    }

    // --- Teacher Management ---

    // Adds a new teacher to the system.
    // Returns true if successful, false if username already exists.
    public boolean addTeacher(String username, String password, String name, String subject) {
        if (authService.isUsernameTaken(username)) {
            return false; // Username already exists
        }

        // Add to users.txt
        List<User> users = DataStorage.loadUsers();
        users.add(new Teacher(username, password, name, subject)); // Add as a User (role "Teacher")
        DataStorage.saveUsers(users);

        // Add to teachers.txt
        List<Teacher> teachers = DataStorage.loadTeachers();
        teachers.add(new Teacher(username, password, name, subject));
        DataStorage.saveTeachers(teachers);

        return true;
    }

    // Updates an existing teacher's details.
    // If newPassword is null, the password is not updated.
    // Returns true if successful, false if teacher not found.
    public boolean updateTeacher(String username, String newPassword, String newName, String newSubject) {
        boolean updated = false;

        // Update in users.txt
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getRole().equals("Teacher")) {
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                }
                // Note: User class only holds username, password, role.
                // Full details are in Teacher/Student specific files.
                updated = true;
                break;
            }
        }
        if (updated) {
            DataStorage.saveUsers(users);
        } else {
            return false; // Teacher not found in users.txt
        }


        // Update in teachers.txt
        List<Teacher> teachers = DataStorage.loadTeachers();
        updated = false; // Reset updated flag for teachers list
        for (Teacher teacher : teachers) {
            if (teacher.getUsername().equals(username)) {
                if (newPassword != null && !newPassword.isEmpty()) {
                    teacher.setPassword(newPassword);
                }
                teacher.setName(newName);
                teacher.setSubject(newSubject);
                updated = true;
                break;
            }
        }

        if (updated) {
            DataStorage.saveTeachers(teachers);
            return true;
        }
        return false; // Teacher not found in teachers.txt
    }

    // Deletes a teacher from the system.
    // Returns true if successful, false if teacher not found.
    public boolean deleteTeacher(String username) {
        boolean deletedUser = false;
        boolean deletedTeacher = false;

        // Delete from users.txt
        List<User> users = DataStorage.loadUsers();
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.getUsername().equals(username) && user.getRole().equals("Teacher")) {
                userIterator.remove();
                deletedUser = true;
                break;
            }
        }
        DataStorage.saveUsers(users);

        // Delete from teachers.txt
        List<Teacher> teachers = DataStorage.loadTeachers();
        Iterator<Teacher> teacherIterator = teachers.iterator();
        while (teacherIterator.hasNext()) {
            Teacher teacher = teacherIterator.next();
            if (teacher.getUsername().equals(username)) {
                teacherIterator.remove();
                deletedTeacher = true;
                break;
            }
        }
        DataStorage.saveTeachers(teachers);

        // Additionally, delete any marks assigned by this teacher
        List<Mark> marks = DataStorage.loadMarks();
        Iterator<Mark> markIterator = marks.iterator();
        while (markIterator.hasNext()) {
            Mark mark = markIterator.next();
            if (mark.getTeacherUsername().equals(username)) {
                markIterator.remove();
            }
        }
        DataStorage.saveMarks(marks);

        return deletedUser && deletedTeacher; // Return true if deleted from both files
    }

    // Retrieves all teachers from the system.
    public List<Teacher> getAllTeachers() {
        return DataStorage.loadTeachers();
    }

    // --- Student Management ---

    // Adds a new student to the system.
    // Returns true if successful, false if username already exists.
    public boolean addStudent(String username, String password, String name, String grade) {
        if (authService.isUsernameTaken(username)) {
            return false; // Username already exists
        }

        // Add to users.txt
        List<User> users = DataStorage.loadUsers();
        users.add(new Student(username, password, name, grade)); // Add as a User (role "Student")
        DataStorage.saveUsers(users);

        // Add to students.txt
        List<Student> students = DataStorage.loadStudents();
        students.add(new Student(username, password, name, grade));
        DataStorage.saveStudents(students);

        return true;
    }

    // Updates an existing student's details.
    // If newPassword is null, the password is not updated.
    // Returns true if successful, false if student not found.
    public boolean updateStudent(String username, String newPassword, String newName, String newGrade) {
        boolean updated = false;

        // Update in users.txt
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getRole().equals("Student")) {
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                }
                updated = true;
                break;
            }
        }
        if (updated) {
            DataStorage.saveUsers(users);
        } else {
            return false; // Student not found in users.txt
        }

        // Update in students.txt
        List<Student> students = DataStorage.loadStudents();
        updated = false; // Reset updated flag for students list
        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                if (newPassword != null && !newPassword.isEmpty()) {
                    student.setPassword(newPassword);
                }
                student.setName(newName);
                student.setGrade(newGrade);
                updated = true;
                break;
            }
        }

        if (updated) {
            DataStorage.saveStudents(students);
            return true;
        }
        return false; // Student not found in students.txt
    }

    // Deletes a student from the system.
    // Returns true if successful, false if student not found.
    public boolean deleteStudent(String username) {
        boolean deletedUser = false;
        boolean deletedStudent = false;

        // Delete from users.txt
        List<User> users = DataStorage.loadUsers();
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.getUsername().equals(username) && user.getRole().equals("Student")) {
                userIterator.remove();
                deletedUser = true;
                break;
            }
        }
        DataStorage.saveUsers(users);

        // Delete from students.txt
        List<Student> students = DataStorage.loadStudents();
        Iterator<Student> studentIterator = students.iterator();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();
            if (student.getUsername().equals(username)) {
                studentIterator.remove();
                deletedStudent = true;
                break;
            }
        }
        DataStorage.saveStudents(students);

        // Additionally, delete any marks for this student
        List<Mark> marks = DataStorage.loadMarks();
        Iterator<Mark> markIterator = marks.iterator();
        while (markIterator.hasNext()) {
            Mark mark = markIterator.next();
            if (mark.getStudentUsername().equals(username)) {
                markIterator.remove();
            }
        }
        DataStorage.saveMarks(marks);

        return deletedUser && deletedStudent; // Return true if deleted from both files
    }

    // Retrieves all students from the system.
    public List<Student> getAllStudents() {
        return DataStorage.loadStudents();
    }
}
