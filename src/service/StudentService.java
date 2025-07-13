// src/service/StudentService.java
package service;

import model.Mark;
import java.util.ArrayList;
import java.util.List;

// StudentService provides business logic for Student operations, specifically viewing their marks.
public class StudentService {

    // Retrieves all marks for a specific student.
    public List<Mark> getMarksByStudent(String studentUsername) {
        List<Mark> allMarks = DataStorage.loadMarks();
        List<Mark> studentMarks = new ArrayList<>();
        for (Mark mark : allMarks) {
            if (mark.getStudentUsername().equals(studentUsername)) {
                studentMarks.add(mark);
            }
        }
        return studentMarks;
    }
}
