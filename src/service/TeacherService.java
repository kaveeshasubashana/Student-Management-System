// src/service/TeacherService.java
package service;

import model.Student;
import model.Mark;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    public List<Student> getAllStudents() {
        return DataStorage.loadStudents();
    }

public boolean assignMarks(String teacherUsername, String studentUsername, String subject, double marks, String grade) {
    // Check if student exists
    List<Student> students = DataStorage.loadStudents();
    boolean studentExists = students.stream()
            .anyMatch(student -> student.getUsername().equals(studentUsername));
    if (!studentExists) return false;

    // Check for duplicate mark
    List<Mark> existingMarks = DataStorage.loadMarks();
    for (Mark mark : existingMarks) {
        if (mark.getTeacherUsername().equals(teacherUsername) &&
            mark.getStudentUsername().equals(studentUsername) &&
            mark.getSubject().equalsIgnoreCase(subject)) {
            return false; // Already assigned
        }
    }

    // ✅ Only save the new one, not whole list
    List<Mark> newMarkList = new ArrayList<>();
    newMarkList.add(new Mark(teacherUsername, studentUsername, subject, marks, grade));
    DataStorage.saveMarks(newMarkList);
    return true;
}


    public boolean updateMarks(String teacherUsername, String studentUsername, String subject, double newMarks, String newGrade) {
        List<Mark> marks = DataStorage.loadMarks();
        boolean updated = false;
        for (Mark mark : marks) {
            if (mark.getTeacherUsername().equals(teacherUsername) &&
                mark.getStudentUsername().equals(studentUsername) &&
                mark.getSubject().equalsIgnoreCase(subject)) {
                mark.setMarks(newMarks);
                mark.setGrade(newGrade);
                updated = true;
                break;
            }
        }
        if (updated) {
            DataStorage.updateMark(teacherUsername, studentUsername, subject, newMarks, newGrade);
            return true;
        }
        return false;
    }

    public boolean deleteMarks(String teacherUsername, String studentUsername, String subject) {
        List<Mark> marks = DataStorage.loadMarks();
        boolean deleted = false;
        for (Mark mark : marks) {
            if (mark.getTeacherUsername().equals(teacherUsername) &&
                mark.getStudentUsername().equals(studentUsername) &&
                mark.getSubject().equalsIgnoreCase(subject)) {
                deleted = true;
                break;
            }
        }
        if (deleted) {
            DataStorage.deleteSpecificMark(teacherUsername, studentUsername, subject);
            return true;
        }
        return false;
    }

    public List<Mark> getMarksByTeacher(String teacherUsername) {
        List<Mark> allMarks = DataStorage.loadMarks();
        List<Mark> teacherMarks = new ArrayList<>();
        for (Mark mark : allMarks) {
            if (mark.getTeacherUsername().equals(teacherUsername)) {
                teacherMarks.add(mark);
            }
        }
        return teacherMarks;
    }
}
