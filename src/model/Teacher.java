// src/model/Teacher.java
package model;

// Teacher class extends the abstract User class.
// It represents a teacher in the system with additional properties like name and subject.
public class Teacher extends User {
    private String name;    // Name of the teacher
    private String subject; // Subject taught by the teacher

    // Constructor for Teacher. It calls the superclass (User) constructor
    // and sets the role specifically to "Teacher".
    public Teacher(String username, String password, String name, String subject) {
        super(username, password, "Teacher");
        this.name = name;
        this.subject = subject;
    }

    // Getter method for teacher's name
    public String getName() {
        return name;
    }

    // Setter method for teacher's name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for teacher's subject
    public String getSubject() {
        return subject;
    }

    // Setter method for teacher's subject
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Implementation of the abstract describeUser method from the User class.
    @Override
    public String describeUser() {
        return "Teacher: " + getName() + " (" + getUsername() + ") - Subject: " + getSubject();
    }

    @Override
    public String toString() {
        return super.toString() + ", Name: " + name + ", Subject: " + subject;
    }
}
