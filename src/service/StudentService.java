// src/service/StudentService.java
package service;

import model.Mark;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
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
