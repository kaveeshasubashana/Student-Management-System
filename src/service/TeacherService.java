// src/service/TeacherService.java
package service;

import model.Student;
import model.Mark;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TeacherService provides business logic for Teacher operations, primarily managing student marks.
public class TeacherService {

    // Retrieves a list of all students.
    public List<Student> getAllStudents() {
        return DataStorage.loadStudents();
    }

    // Assigns marks to a student for a specific subject.
    // Returns true if successful, false if student not found or marks already exist for this subject.
    public boolean assignMarks(String teacherUsername, String studentUsername, String subject, double marks, String grade) {
        // First, check if the student exists
        boolean studentExists = false;
        List<Student> students = DataStorage.loadStudents();
        for (Student student : students) {
            if (student.getUsername().equals(studentUsername)) {
                studentExists = true;
                break;
            }
        }
        if (!studentExists) {
            return false; // Student not found
        }

        // Check if marks for this student and subject (by this teacher) already exist
        List<Mark> existingMarks = DataStorage.loadMarks();
        for (Mark mark : existingMarks) {
            if (mark.getStudentUsername().equals(studentUsername) &&
                mark.getSubject().equalsIgnoreCase(subject) &&
                mark.getTeacherUsername().equals(teacherUsername)) {
                return false; // Marks already assigned for this subject by this teacher
            }
        }

        // If student exists and marks don't exist, add new marks
        existingMarks.add(new Mark(teacherUsername, studentUsername, subject, marks, grade));
        DataStorage.saveMarks(existingMarks);
        return true;
    }

    // Updates existing marks for a student in a specific subject.
    // Returns true if successful, false if the mark entry is not found.
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
            DataStorage.saveMarks(marks);
            return true;
        }
        return false; // Mark entry not found
    }

    // Deletes marks for a student in a specific subject.
    // Returns true if successful, false if the mark entry is not found.
    public boolean deleteMarks(String teacherUsername, String studentUsername, String subject) {
        List<Mark> marks = DataStorage.loadMarks();
        Iterator<Mark> iterator = marks.iterator();
        boolean deleted = false;
        while (iterator.hasNext()) {
            Mark mark = iterator.next();
            if (mark.getTeacherUsername().equals(teacherUsername) &&
                mark.getStudentUsername().equals(studentUsername) &&
                mark.getSubject().equalsIgnoreCase(subject)) {
                iterator.remove();
                deleted = true;
                break;
            }
        }
        if (deleted) {
            DataStorage.saveMarks(marks);
            return true;
        }
        return false; // Mark entry not found
    }

    // Retrieves all marks assigned by a specific teacher.
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
