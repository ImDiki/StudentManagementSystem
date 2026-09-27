# Student Management System

A Java Swing desktop application created for **CS 1102 - Computer Programming 1 (Programming Assignment Unit 7)**. The project practices object-oriented programming, event-driven GUI development, input validation, and in-memory data management.

## Features

### Student records
- Add students with a duplicate Student ID check.
- Update an existing student's name, age, and GPA.
- View student records in a `JTable`.

### Course enrollment
- Select students and predefined courses using `JComboBox`.
- Enroll students in courses.
- Prevent duplicate enrollment in the same course.

### Grade management
- View courses for the selected student.
- Assign or update course grades.
- Refresh the grade table after changes.

## Implementation

The project is intentionally small and keeps the implementation in a single Java source file:

- `Student` — student data model.
- `StudentManagement` — manages students, courses, and enrollments using `ArrayList` and `HashMap`.
- `StudentManagementSystem` — Swing user interface and event handlers.

Data is stored **in memory only** and is reset when the application closes. There is no database or persistent storage in the current version.

## Tech Stack

- Java
- Java Swing
- AWT layout managers
- Java Collections (`ArrayList`, `HashMap`)

## Screenshots

### Student Records
<img width="1917" height="794" alt="Student records" src="https://github.com/user-attachments/assets/82457cf6-0823-4bca-8eb3-f56d0e636b6c" />

### Course Enrollment
<img width="1919" height="852" alt="Course enrollment" src="https://github.com/user-attachments/assets/4be65476-d52d-4173-8672-9788e02b64ff" />

### Grade Management
<img width="1916" height="1029" alt="Grade management" src="https://github.com/user-attachments/assets/a99b6a2b-bc04-4fce-8b50-80a97a1c8199" />

## Run

Requirements:
- JDK 11 or newer

Clone the repository and run:

`src/Studentmanagementsystem/StudentManagementSystem.java`

from Eclipse, IntelliJ IDEA, NetBeans, or another Java development environment.

## Project Status

This is a course/learning project rather than a production student information system.

Useful future improvements would include persistent storage, separation into multiple source files, automated tests, and clearer separation between UI and data-management responsibilities.
