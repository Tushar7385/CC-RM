package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Name;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.enums.Semester;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.util.Validators;
import edu.ccrm.exceptions.*;

import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final StudentService students = new StudentService();
    private static final CourseService courses = new CourseService();
    private static final EnrollmentService enrollments = new EnrollmentService();
    private static final TranscriptService transcripts = new TranscriptService();
    private static final ImportExportService ioService = new ImportExportService(students, courses);
    private static final BackupService backup = new BackupService();

    public static void main(String[] args) {
        System.out.println("Starting CCRM — data dir: " + AppConfig.getInstance().getDataDir());
        seedSampleData();
        try (Scanner sc = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                printMenu();
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1" -> addStudent(sc);
                    case "2" -> listStudents();
                    case "3" -> addCourse(sc);
                    case "4" -> listCourses();
                    case "5" -> enrollStudent(sc);
                    case "6" -> assignGrade(sc);
                    case "7" -> exportData();
                    case "8" -> backupAndShowSize();
                    case "9" -> {
                        System.out.println("Goodbye"); exit = true; }
                    default -> System.out.println("Unknown option");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("\n=== CCRM Menu ===\n1) Add Student\n2) List Students\n3) Add Course\n4) List Courses\n5) Enroll Student\n6) Assign Grade\n7) Export Students\n8) Backup & Show Size\n9) Exit\nChoose:");
    }

    private static void addStudent(Scanner sc) {
        System.out.print("regNo: "); String reg = sc.nextLine();
        System.out.print("full name: "); String name = sc.nextLine();
        System.out.print("email: "); String email = sc.nextLine();
        if (!Validators.emailValid(email)) { System.out.println("Invalid email"); return; }
        String[] parts = name.split(" ");
        Name n = new Name(parts[0], parts.length==3?parts[1]:"", parts[parts.length-1]);
        var s = students.addStudent(reg, n, email);
        System.out.println("Added: " + s.getId());
    }

    private static void listStudents() {
        students.listAll().forEach(System.out::println);
    }

    private static void addCourse(Scanner sc) {
        try {
            System.out.print("code: "); String code = sc.nextLine();
            System.out.print("title: "); String title = sc.nextLine();
            System.out.print("credits: "); int cr = Integer.parseInt(sc.nextLine());
            System.out.print("department: "); String dept = sc.nextLine();
            System.out.print("semester(SPRING/SUMMER/FALL): "); Semester s = Semester.valueOf(sc.nextLine().toUpperCase());
            Course c = new Course.Builder(code).title(title).credits(cr).department(dept).semester(s).build();
            courses.addCourse(c);
            System.out.println("Course added.");
        } catch (Exception e) {
            System.out.println("Failed to add course: " + e.getMessage());
        }
    }

    private static void listCourses() { courses.listAll().forEach(System.out::println); }

    private static void enrollStudent(Scanner sc) {
        System.out.print("studentId: "); String sid = sc.nextLine();
        System.out.print("courseCode: "); String cc = sc.nextLine();
        var optS = students.findById(sid);
        var optC = courses.find(cc);
        if (optS.isEmpty() || optC.isEmpty()) { System.out.println("Invalid ids"); return; }
        try {
            enrollments.enroll(optS.get(), optC.get());
            System.out.println("Enrolled ok.");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException ex) {
            System.out.println("Enroll failed: " + ex.getMessage());
        }
    }

    private static void assignGrade(Scanner sc) {
        System.out.print("studentId: "); String sid = sc.nextLine();
        System.out.print("courseCode: "); String cc = sc.nextLine();
        System.out.print("grade (S/A/B/C/D/E/F): "); String g = sc.nextLine();
        var s = students.findById(sid);
        if (s.isEmpty()) { System.out.println("Student not found"); return; }
        try {
            enrollments.assignGrade(s.get(), cc, edu.ccrm.domain.enums.Grade.valueOf(g.toUpperCase()));
            System.out.println("Grade set.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid grade.");
        }
    }

    private static void exportData() {
        try {
            ioService.exportStudents(Paths.get("data", "students_export.csv"));
            System.out.println("Exported students to data/students_export.csv");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void backupAndShowSize() {
        try {
            var folder = backup.backupFolder();
            long size = backup.sizeRec(folder);
            System.out.println("Backup created: " + folder + " size=" + size + " bytes");
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    // seed sample data demonstrating usage
    private static void seedSampleData() {
        students.addStudent("REG100", new Name("Alice", "M", "K"), "alice@example.com");
        students.addStudent("REG101", new Name("Bob", "", "Smith"), "bob@ex.com");
        courses.addCourse(new Course.Builder("CS101").title("Intro to CS").credits(4).department("CSE").semester(Semester.FALL).build());
        courses.addCourse(new Course.Builder("MA101").title("Calculus").credits(3).department("MATH").semester(Semester.FALL).build());
    }
}
