
# 🎓 Student Management System

A simple desktop application developed in Java using Object-Oriented Programming (OOP) principles, designed to manage student, teacher, and administrative data. This system uses a flat-file (text file) approach for data persistence instead of a traditional database.

## 📑 Table of Contents
- 📘 [Project Overview](#-project-overview)
- 🚀 [Features](#-features)
  - 👨‍💼 [Admin Role](#-admin-role)
  - 👨‍🏫 [Teacher Role](#-teacher-role)
  - 👨‍🎓 [Student Role](#-student-role)
- 💾 [Data Persistence](#-data-persistence)
- 🛠️ [Technologies Used](#-technologies-used)
- ▶️ [How to Run the Application](#-how-to-run-the-application)
  - 💻 [Option 1: Using Command Line](#-option-1-using-command-line-cmdterminal)
  - 🧑‍💻 [Option 2: Using Visual Studio Code](#-option-2-using-visual-studio-code-vs-code)
- 🧠 [OOP Concepts Applied](#-oop-concepts-applied)
- 🔐 [Default Admin Credentials](#-default-admin-credentials)

## 📘 Project Overview

This Student Management System provides a basic platform for managing academic data. It allows different types of users (Admin, Teacher, Student) to interact with the system based on their roles and permissions. The application features a simple Graphical User Interface (GUI) built with Java Swing.

## 🚀 Features

### 👨‍💼 Admin Role
- Add, update, delete, and view teacher details (username, password, name, subject).
- Add, update, delete, and view student details (username, password, name, grade).
- Assign initial usernames and passwords for new teachers and students.

### 👨‍🏫 Teacher Role
- View a list of all students in the system (username, name, grade).
- Assign, update, and delete marks and grades for students in specific subjects.

### 👨‍🎓 Student Role
- Access and view their own assigned marks and grades for different subjects, along with the assigning teacher's username.

## 💾 Data Persistence

All user, teacher, student, and mark data is stored in plain text files (users.txt, teachers.txt, students.txt, marks.txt) within the `data/` directory.

## 🛠️ Technologies Used

- Language: Java  
- GUI Framework: Java Swing  
- Build Tool: Standard Java Compiler (Javac) - No external build tools like Maven/Gradle are strictly required for this setup.

## ▶️ How to Run the Application

### 💻 Option 1: Using Command Line (CMD/Terminal)

1. Clone the Repository:  
2. `git clone <your-repository-url>`  
3. `cd StudentManagementSystem`  
4. Create data Directory:  
   - Ensure a data directory exists at the root level of the project. It will be created automatically on first run if it doesn't exist.
5. Compile the Source Code:  
   - `mkdir bin`  
   - `javac -d bin src/model/*.java src/service/*.java src/view/*.java src/MainApp.java`
6. Run the Application:  
   - `java -cp bin MainApp`

### 🧑‍💻 Option 2: Using Visual Studio Code (VS Code)

1. Install Java Extension Pack in VS Code.
2. Open the Project in VS Code (open the StudentManagementSystem folder).
3. Run MainApp.java:  
   - Navigate to `src/MainApp.java`.  
   - Click the "Run" button or right-click and select "Run Java".  
   - VS Code will handle the compilation and execution.
4. The `data/` directory will be created automatically if it doesn't exist.

## 🧠 OOP Concepts Applied

- **Encapsulation:** Data in model classes are private and accessed via public getters/setters. Protects data integrity.  
- **Inheritance:** Admin, Teacher, and Student classes extend the abstract User class. Promotes code reuse.  
- **Abstraction:** Abstract User class with common methods. DataStorage class hides file I/O complexity.  
- **Polymorphism:** `describeUser()` method overridden in subclasses. Enables different behaviors.  
- **Separation of Concerns:** Code divided into model, service, and view packages. Improves organization.

## 🔐 Default Admin Credentials

Upon the first run of the application, a default administrator account will be created if one does not already exist:

- Username: `admin`  
- Password: `admin123`
