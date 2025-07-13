// src/model/Mark.java
package model;

// Mark class represents a student's mark for a specific subject, assigned by a teacher.
public class Mark {
    private String teacherUsername; // Username of the teacher who assigned the mark
    private String studentUsername; // Username of the student who received the mark
    private String subject;         // Subject for which the mark is given
    private double marks;           // Numerical marks obtained by the student
    private String grade;           // Grade corresponding to the marks

    // Constructor to initialize a Mark object
    public Mark(String teacherUsername, String studentUsername, String subject, double marks, String grade) {
        this.teacherUsername = teacherUsername;
        this.studentUsername = studentUsername;
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
    }

    // Getter method for teacher's username
    public String getTeacherUsername() {
        return teacherUsername;
    }

    // Setter method for teacher's username
    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    // Getter method for student's username
    public String getStudentUsername() {
        return studentUsername;
    }

    // Setter method for student's username
    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    // Getter method for subject
    public String getSubject() {
        return subject;
    }

    // Setter method for subject
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter method for marks
    public double getMarks() {
        return marks;
    }

    // Setter method for marks
    public void setMarks(double marks) {
        this.marks = marks;
    }

    // Getter method for grade
    public String getGrade() {
        return grade;
    }

    // Setter method for grade
    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student: " + studentUsername + ", Subject: " + subject +
               ", Marks: " + marks + ", Grade: " + grade +
               ", Assigned by: " + teacherUsername;
    }
}
