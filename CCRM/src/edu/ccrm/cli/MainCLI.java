package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;

import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.impl.BasicEnrollmentService;
import edu.ccrm.service.impl.InMemoryCourseService;
import edu.ccrm.service.impl.InMemoryStudentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.TranscriptService;
import edu.ccrm.util.RecursionUtil;
import edu.ccrm.util.Validators;
import edu.ccrm.domain.ImmutableValueObjects.Name;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new InMemoryStudentService();
    private final CourseService courseService = new InMemoryCourseService();
    private final EnrollmentService enrollmentService = new BasicEnrollmentService(studentService, courseService);
    private final ImportExportService ioService;
    private final BackupService backupService;

    public MainCLI() {
        Path base = AppConfig.getInstance().getDataDir();
        this.ioService = new ImportExportService(base);
        this.backupService = new BackupService(base);
    }

    public static void main(String[] args) {
        new MainCLI().start();
    }

    private void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> listStudents();
                case "3" -> addCourse();
                case "4" -> listCourses();
                case "5" -> enroll();
                case "6" -> recordMarks();
                case "7" -> exportData();
                case "8" -> doBackup();
                case "9" -> computeDirSize();
                case "0" -> { running = false; System.out.println("Bye"); }
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== CCRM MENU ===");
        System.out.println("1) Add Student");
        System.out.println("2) List Students");
        System.out.println("3) Add Course");
        System.out.println("4) List Courses");
        System.out.println("5) Enroll Student in Course");
        System.out.println("6) Record Marks");
        System.out.println("7) Export CSV (students,courses)");
        System.out.println("8) Backup exported files");
        System.out.println("9) Compute backup dir size (recursion)");
        System.out.println("0) Exit");
        System.out.print("Choose> ");
    }

    private void addStudent() {
        System.out.print("First name: "); String fn = scanner.nextLine().trim();
        System.out.print("Last name: "); String ln = scanner.nextLine().trim();
        System.out.print("Email: "); String email = scanner.nextLine().trim();
        if (!Validators.isEmail(email)) { System.out.println("Invalid email"); return; }
        System.out.print("DOB (YYYY-MM-DD): "); String dob = scanner.nextLine().trim();

        Student s = new Student.Builder(UUID.randomUUID().toString())
                .name(new Name(fn, ln))
                .email(email)
                .dob(LocalDate.parse(dob))
                .regNo("REG-" + (int)(Math.random()*10000))
                .build();
        studentService.addStudent(s);
        System.out.println("Added: " + s.profile());
    }

    private void listStudents() {
        List<Student> lst = studentService.listAll();
        lst.stream()
                .sorted(Comparator.comparing(st -> st.getName().getLast()))
                .forEach(System.out::println);

        outer:
        for (Student s : lst) {
            for (int i = 0; i < 1; i++) {
                if (!s.isActive()) {
                    System.out.println("Found inactive - breaking outer");
                    break outer;
                }
            }
        }
    }

    private void addCourse() {
        System.out.print("Course code: "); String code = scanner.nextLine().trim();
        System.out.print("Title: "); String title = scanner.nextLine().trim();
        System.out.print("Credits: "); int credits = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Department: "); String dept = scanner.nextLine().trim();
        System.out.print("Semester (SPRING/SUMMER/FALL): "); Semester sem = Semester.valueOf(scanner.nextLine().trim().toUpperCase());

        Course c = new Course.Builder(code)
                .title(title)
                .credits(credits)
                .department(dept)
                .semester(sem)
                .build();
        courseService.addCourse(c);
        System.out.println("Added: " + c);
    }

    private void listCourses() {
        // demonstrate stream filters (search by dept/instructor/semester)
        System.out.print("Filter by department? (enter or skip): ");
        String dept = scanner.nextLine().trim();
        courseService.streamAll()
                .filter(c -> dept.isEmpty() || c.getDepartment().equalsIgnoreCase(dept))
                .sorted((a,b) -> a.getCode().compareTo(b.getCode()))
                .forEach(System.out::println);
    }

    private void enroll() {
        System.out.print("Student ID: "); String sid = scanner.nextLine().trim();
        System.out.print("Course code: "); String cc = scanner.nextLine().trim();
        try {
            enrollmentService.enroll(sid, cc);
            System.out.println("Enrolled.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void recordMarks() {
        System.out.print("Student ID: "); String sid = scanner.nextLine().trim();
        System.out.print("Course code: "); String cc = scanner.nextLine().trim();
        System.out.print("Marks (0-100): "); double m = Double.parseDouble(scanner.nextLine().trim());
        try {
            enrollmentService.recordMarks(sid, cc, m);
            System.out.println("Recorded.");
            // show transcript and computed GPA using course credits lookup lambda
            studentService.findById(sid).ifPresent(s -> {
                System.out.println(TranscriptService.transcriptView(s));
                double gpa = TranscriptService.computeGPA(
                        enrollmentService.listByStudent(sid),
                        courseCode -> courseService.findByCode(courseCode).map(Course::getCredits).orElse(0)
                );
                System.out.printf("Current GPA: %.2f%n", gpa);
            });
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void exportData() {
        try {
            Path base = AppConfig.getInstance().getDataDir();
            Path studentsFile = base.resolve("students.csv");
            Path coursesFile = base.resolve("courses.csv");
            ioService.exportStudents(studentService, studentsFile);
            ioService.exportCourses(courseService, coursesFile);
            System.out.println("Exported to " + studentsFile + " and " + coursesFile);
        } catch (Exception ex) {
            System.out.println("Export failed: " + ex.getMessage());
        }
    }

    private void doBackup() {
        try {
            Path base = AppConfig.getInstance().getDataDir();
            Path dest = backupService.backup(base);
            System.out.println("Backup created at: " + dest);
        } catch (Exception ex) {
            System.out.println("Backup failed: " + ex.getMessage());
        }
    }

    private void computeDirSize() {
        try {
            Path base = AppConfig.getInstance().getDataDir();
            long size = RecursionUtil.directorySize(base);
            System.out.printf("Directory %s size = %d bytes%n", base, size);
        } catch (Exception ex) {
            System.out.println("Failed: " + ex.getMessage());
        }
    }
}