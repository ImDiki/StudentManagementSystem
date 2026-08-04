# Student Management System (Java Swing GUI)

A robust, event-driven desktop application built in Java designed to transition a traditional console-based system into a fully interactive Graphical User Interface (GUI). This project is developed for **CS 1102 - Computer Programming 1** (Programming Assignment Unit 7).

---

## Executive Summary & System Overview

The **Student Management System** provides an administrative dashboard for managing student academic records, course enrollments, and grade assignments. Built on top of **Java Swing** and decoupled using Clean Architecture principles, the application isolates business logic from the user interface.

### Key Architectural Highlights
- **Encapsulated Models:** Uses a dedicated `Student` class with private fields and controlled getters/setters.
- **Decoupled Data Layer:** Centralized `StudentManagement` repository managing in-memory collections (`ArrayList` and nested `HashMap`).
- **Event-Driven Interface:** Built using `JFrame`, `JTabbedPane`, `JTable`, and `JComboBox` to handle user actions dynamically.
- **Defensive Error Handling:** Employs explicit input validation and numeric bounds checking via `JOptionPane` dialog modals to prevent runtime exceptions.

---

## Features & Modules

### 1. Student Records Management
- Add new students with auto-duplication checks on Student ID.
- Selection-based record updating in real-time.
- View all students in an interactive `JTable`.

### 2. Course Enrollment Module
- Dynamic student selection using `JComboBox`.
- Enrolls students into pre-defined university courses.
- Prevents duplicate course enrollment for individual students.

### 3. Grade Management Module
- Filtered course lists based on selected student.
- Assigns and updates course grades dynamically in memory and updates UI tables instantaneously.

---

## Program Output & System Screenshots

### 1. Student Records Tab
![Student Records Tab]
<img width="1917" height="794" alt="image" src="https://github.com/user-attachments/assets/82457cf6-0823-4bca-8eb3-f56d0e636b6c" />
<img width="1919" height="767" alt="image" src="https://github.com/user-attachments/assets/1c2eb72a-ae8a-4dde-aa6c-095561015717" />

*Figure 1: Main administrative portal displaying active student profiles in a `JTable` alongside data input controls.*

---

### 2. Course Enrollment Tab
![Course Enrollment Tab]
<img width="1919" height="852" alt="image" src="https://github.com/user-attachments/assets/4be65476-d52d-4173-8672-9788e02b64ff" />
<img width="1917" height="825" alt="image" src="https://github.com/user-attachments/assets/6b754ce1-7840-47b3-8e0b-0ac0fc7d88d2" />

*Figure 2: Course enrollment panel utilizing dynamic `JComboBox` components to assign courses to students.*

---

### 3. Grade Management Tab
![Grade Management Tab]
<img width="1916" height="1029" alt="image" src="https://github.com/user-attachments/assets/a99b6a2b-bc04-4fce-8b50-80a97a1c8199" />
<img width="1916" height="1023" alt="image" src="https://github.com/user-attachments/assets/6386da28-0e6e-4c65-8d43-d6c1fc20df99" />

*Figure 3: Grade assignment dashboard displaying real-time table updates upon grade allocation.*

---

### 4. Input Validation & Error Handling
![Validation Error Dialog]
<img width="1916" height="1031" alt="image" src="https://github.com/user-attachments/assets/fcd7a0d6-1163-4d2a-ba1b-3afaab2a7255" />

*Figure 4: Defensive programming in action—modal error dialog (`JOptionPane.ERROR_MESSAGE`) triggering on invalid inputs.*

---

## How to Run the Project

### Prerequisites
- **JDK 11** or higher (Java 17/21 recommended)
- An IDE such as **Eclipse**, **IntelliJ IDEA**, or **NetBeans**

### Execution Steps
1. Clone the repository:
   ```bash
   git clone [https://github.com/ImDiki/StudentManagementSystem.git]
