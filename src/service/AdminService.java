// src/service/AdminService.java
package service;

import model.User;
import model.Teacher;
import model.Mark;
import model.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminService {
    private AuthService authService;

    public AdminService() {
        this.authService = new AuthService();
    }

    // --- Teacher Management ---
    public boolean addTeacher(String username, String password, String name, String subject) {
        if (authService.isUsernameTaken(username)) {
            return false;
        }
        List<User> users = DataStorage.loadUsers();
        users.add(new Teacher(username, password, name, subject));
        DataStorage.saveUsers(users);

        List<Teacher> teachers = DataStorage.loadTeachers();
        teachers.add(new Teacher(username, password, name, subject));
        DataStorage.saveTeachers(teachers);
        return true;
    }

    public boolean updateTeacher(String username, String newPassword, String newName, String newSubject) {
        boolean updated = false;
        List<User> users = DataStorage.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getRole().equals("Teacher")) {
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
            return false;
        }

        List<Teacher> teachers = DataStorage.loadTeachers();
        updated = false;
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
        return false;
    }

    public boolean deleteTeacher(String username) {
        DataStorage.deleteUser(username);
        DataStorage.deleteTeacher(username);
        DataStorage.deleteMarksByTeacher(username);
        return true;
    }

    public List<Teacher> getAllTeachers() {
        return DataStorage.loadTeachers();
    }

    // --- Student Management ---
    public boolean addStudent(String username, String password, String name) {
        if (authService.isUsernameTaken(username)) {
            return false;
        }
        List<User> users = DataStorage.loadUsers();
        users.add(new Student(username, password, name, name));
        DataStorage.saveUsers(users);

        List<Student> students = DataStorage.loadStudents();
        students.add(new Student(username, password, name, name));
        DataStorage.saveStudents(students);
        return true;
    }

    public boolean updateStudent(String username, String newPassword, String newName, String newGrade) {
        boolean updated = false;
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
            return false;
        }

        List<Student> students = DataStorage.loadStudents();
        updated = false;
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
        return false;
    }

    public boolean deleteStudent(String username) {
        DataStorage.deleteUser(username);
        DataStorage.deleteStudent(username);
        DataStorage.deleteMarksByStudent(username);
        return true;
    }

    public List<Student> getAllStudents() {
        return DataStorage.loadStudents();
    }
}
