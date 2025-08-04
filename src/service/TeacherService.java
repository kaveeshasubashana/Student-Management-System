// src/service/TeacherService.java
package service;

import model.Student;
import model.Mark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeacherService {
    public List<Student> getAllStudents() {
        return DataStorage.loadStudents();
    }

    public boolean assignMarks(String teacherUsername, String studentUsername, String subject, double marks, String grade) {
        boolean studentExists = false;
        List<Student> students = DataStorage.loadStudents();
        for (Student student : students) {
            if (student.getUsername().equals(studentUsername)) {
                studentExists = true;
                break;
            }
        }
        if (!studentExists) {
            return false;
        }

        List<Mark> existingMarks = DataStorage.loadMarks();
        for (Mark mark : existingMarks) {
            if (mark.getStudentUsername().equals(studentUsername) &&
                mark.getSubject().equalsIgnoreCase(subject) &&
                mark.getTeacherUsername().equals(teacherUsername)) {
                return false;
            }
        }

        existingMarks.add(new Mark(teacherUsername, studentUsername, subject, marks, grade));
        DataStorage.saveMarks(existingMarks);
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
