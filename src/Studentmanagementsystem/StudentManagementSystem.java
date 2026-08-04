package Studentmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulated Student Model.
 */
class Student {
    private String name;
    private String id;
    private int age;
    private double grade;

    public Student(String name, String id, int age, double grade) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.grade = grade;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

/**
 * Business Logic Repository maintaining student records and course enrollments.
 */
class StudentManagement {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static ArrayList<String> courseList = new ArrayList<>();
    // Key: Student ID, Value: Map<Course Name, Grade>
    private static Map<String, Map<String, String>> enrollments = new HashMap<>();

    static {
        // Default Courses
        courseList.add("CS1101 - Programming Fundamentals");
        courseList.add("CS1102 - Computer Programming 1");
        courseList.add("CS1103 - Computer Programming 2");
    }

    public static boolean addStudent(Student student) {
        if (findStudentById(student.getId()) != null) {
            return false;
        }
        studentList.add(student);
        enrollments.put(student.getId(), new HashMap<>());
        return true;
    }

    public static Student findStudentById(String id) {
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public static boolean updateStudent(String id, String newName, int newAge, double newGrade) {
        Student s = findStudentById(id);
        if (s != null) {
            s.setName(newName);
            s.setAge(newAge);
            s.setGrade(newGrade);
            return true;
        }
        return false;
    }

    public static boolean enrollStudentInCourse(String studentId, String course) {
        Map<String, String> studentCourses = enrollments.get(studentId);
        if (studentCourses != null && !studentCourses.containsKey(course)) {
            studentCourses.put(course, "N/A"); // Default grade until assigned
            return true;
        }
        return false;
    }

    public static boolean assignCourseGrade(String studentId, String course, String grade) {
        Map<String, String> studentCourses = enrollments.get(studentId);
        if (studentCourses != null && studentCourses.containsKey(course)) {
            studentCourses.put(course, grade);
            return true;
        }
        return false;
    }

    public static ArrayList<Student> getStudentList() { return studentList; }
    public static ArrayList<String> getCourseList() { return courseList; }
    public static Map<String, String> getStudentEnrollments(String studentId) {
        return enrollments.get(studentId);
    }
}

/**
 * Dynamic GUI Implementation replacing Console Interface.
 */
public class StudentManagementSystem extends JFrame {

    // Student Tab Controls
    private JTextField txtName, txtId, txtAge, txtGrade;
    private JTable studentTable;
    private DefaultTableModel studentTableModel;

    // Course Enrollment Tab Controls
    private JComboBox<Student> cbEnrollStudent;
    private JComboBox<String> cbEnrollCourse;

    // Grade Management Tab Controls
    private JComboBox<Student> cbGradeStudent;
    private JComboBox<String> cbStudentCourses;
    private JTextField txtCourseGrade;
    private JTable gradeTable;
    private DefaultTableModel gradeTableModel;

    public StudentManagementSystem() {
        setTitle("Student Management System - Administrative Portal");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Student Records", createStudentPanel());
        tabbedPane.addTab("Course Enrollment", createEnrollmentPanel());
        tabbedPane.addTab("Grade Management", createGradePanel());

        add(tabbedPane);
        
        // Populate sample initial data
        StudentManagement.addStudent(new Student("Aung Aung", "S101", 20, 3.8));
        StudentManagement.addStudent(new Student("Kyaw Kyaw", "S102", 21, 3.5));
        refreshAllViews();
    }

    // 1. Student Records Panel (Add, Update, View)
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details Input"));

        formPanel.add(new JLabel("Full Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        formPanel.add(txtId);

        formPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        formPanel.add(txtAge);

        formPanel.add(new JLabel("GPA Grade (0.0 - 4.0):"));
        txtGrade = new JTextField();
        formPanel.add(txtGrade);

        JButton btnAdd = new JButton("Add Student");
        JButton btnUpdate = new JButton("Update Selected Student");
        formPanel.add(btnAdd);
        formPanel.add(btnUpdate);

        panel.add(formPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Age", "GPA"};
        studentTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        studentTable = new JTable(studentTableModel);
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Event Handlers
        btnAdd.addActionListener(e -> handleAddStudent());
        btnUpdate.addActionListener(e -> handleUpdateStudent());

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row >= 0) {
                txtId.setText((String) studentTableModel.getValueAt(row, 0));
                txtName.setText((String) studentTableModel.getValueAt(row, 1));
                txtAge.setText(studentTableModel.getValueAt(row, 2).toString());
                txtGrade.setText(studentTableModel.getValueAt(row, 3).toString());
                txtId.setEditable(false); // ID remains immutable during update
            }
        });

        return panel;
    }

    // 2. Course Enrollment Panel
    private JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbEnrollStudent = new JComboBox<>();
        cbEnrollCourse = new JComboBox<>(StudentManagement.getCourseList().toArray(new String[0]));
        JButton btnEnroll = new JButton("Enroll Student");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Select Student:"), gbc);
        gbc.gridx = 1;
        panel.add(cbEnrollStudent, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Select Course:"), gbc);
        gbc.gridx = 1;
        panel.add(cbEnrollCourse, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnEnroll, gbc);

        btnEnroll.addActionListener(e -> handleEnrollStudent());

        return panel;
    }

    // 3. Grade Management Panel
    private JPanel createGradePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Student:"));
        cbGradeStudent = new JComboBox<>();
        topPanel.add(cbGradeStudent);
        panel.add(topPanel, BorderLayout.NORTH);

        String[] cols = {"Enrolled Course", "Current Grade"};
        gradeTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        gradeTable = new JTable(gradeTableModel);
        panel.add(new JScrollPane(gradeTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Assign Grade"));
        
        bottomPanel.add(new JLabel("Course:"));
        cbStudentCourses = new JComboBox<>();
        bottomPanel.add(cbStudentCourses);

        bottomPanel.add(new JLabel("Grade:"));
        txtCourseGrade = new JTextField(8);
        bottomPanel.add(txtCourseGrade);

        JButton btnAssign = new JButton("Assign Grade");
        bottomPanel.add(btnAssign);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        cbGradeStudent.addActionListener(e -> refreshGradePanel());
        btnAssign.addActionListener(e -> handleAssignGrade());

        return panel;
    }

    // --- Action Handler Logic ---

    private void handleAddStudent() {
        try {
            String name = txtName.getText().trim();
            String id = txtId.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            double grade = Double.parseDouble(txtGrade.getText().trim());

            if (name.isEmpty() || id.isEmpty()) {
                showError("Name and ID fields cannot be empty.");
                return;
            }
            if (age <= 0 || grade < 0.0 || grade > 4.0) {
                showError("Invalid age boundary or GPA grade range (0.0 - 4.0).");
                return;
            }

            if (StudentManagement.addStudent(new Student(name, id, age, grade))) {
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearStudentForm();
                refreshAllViews();
            } else {
                showError("A student with this ID already exists.");
            }
        } catch (NumberFormatException ex) {
            showError("Age must be an integer and GPA must be a decimal number.");
        }
    }

    private void handleUpdateStudent() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            double grade = Double.parseDouble(txtGrade.getText().trim());

            if (StudentManagement.updateStudent(id, name, age, grade)) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearStudentForm();
                refreshAllViews();
            } else {
                showError("Please select a valid student from the table to update.");
            }
        } catch (NumberFormatException ex) {
            showError("Invalid input parameters for Age or GPA Grade.");
        }
    }

    private void handleEnrollStudent() {
        Student selectedStudent = (Student) cbEnrollStudent.getSelectedItem();
        String selectedCourse = (String) cbEnrollCourse.getSelectedItem();

        if (selectedStudent == null || selectedCourse == null) {
            showError("Please select both a student and a course.");
            return;
        }

        if (StudentManagement.enrollStudentInCourse(selectedStudent.getId(), selectedCourse)) {
            JOptionPane.showMessageDialog(this, "Student successfully enrolled!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshGradePanel();
        } else {
            showError("Student is already enrolled in this course.");
        }
    }

    private void handleAssignGrade() {
        Student selectedStudent = (Student) cbGradeStudent.getSelectedItem();
        String selectedCourse = (String) cbStudentCourses.getSelectedItem();
        String grade = txtCourseGrade.getText().trim();

        if (selectedStudent == null || selectedCourse == null || grade.isEmpty()) {
            showError("Select a valid student, course, and enter a grade value.");
            return;
        }

        if (StudentManagement.assignCourseGrade(selectedStudent.getId(), selectedCourse, grade)) {
            JOptionPane.showMessageDialog(this, "Grade assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            txtCourseGrade.setText("");
            refreshGradePanel();
        }
    }

    // --- Dynamic UI Refresh Methods ---

    private void refreshAllViews() {
        studentTableModel.setRowCount(0);
        cbEnrollStudent.removeAllItems();
        cbGradeStudent.removeAllItems();

        for (Student s : StudentManagement.getStudentList()) {
            studentTableModel.addRow(new Object[]{s.getId(), s.getName(), s.getAge(), s.getGrade()});
            cbEnrollStudent.addItem(s);
            cbGradeStudent.addItem(s);
        }
        refreshGradePanel();
    }

    private void refreshGradePanel() {
        Student selected = (Student) cbGradeStudent.getSelectedItem();
        gradeTableModel.setRowCount(0);
        cbStudentCourses.removeAllItems();

        if (selected != null) {
            Map<String, String> userEnrollments = StudentManagement.getStudentEnrollments(selected.getId());
            if (userEnrollments != null) {
                for (Map.Entry<String, String> entry : userEnrollments.entrySet()) {
                    gradeTableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
                    cbStudentCourses.addItem(entry.getKey());
                }
            }
        }
    }

    private void clearStudentForm() {
        txtName.setText("");
        txtId.setText("");
        txtAge.setText("");
        txtGrade.setText("");
        txtId.setEditable(true);
        studentTable.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem().setVisible(true));
    }
}