// src/model/Student.java
package model;

// Student class extends the abstract User class.
// It represents a student in the system with additional properties like name and grade.
public class Student extends User {
    private String name;  // Name of the student
    private String grade; // Grade/class of the student

    // Constructor for Student. It calls the superclass (User) constructor
    // and sets the role specifically to "Student".
    public Student(String username, String password, String name, String grade) {
        super(username, password, "Student");
        this.name = name;
        this.grade = grade;
    }

    // Getter method for student's name
    public String getName() {
        return name;
    }

    // Setter method for student's name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for student's grade
    public String getGrade() {
        return grade;
    }

    // Setter method for student's grade
    public void setGrade(String grade) {
        this.grade = grade;
    }

    // Implementation of the abstract describeUser method from the User class.
    @Override
    public String describeUser() {
        return "Student: " + getName() + " (" + getUsername() + ") - Grade: " + getGrade();
    }

    @Override
    public String toString() {
        return super.toString() + ", Name: " + name + ", Grade: " + grade;
    }
}
