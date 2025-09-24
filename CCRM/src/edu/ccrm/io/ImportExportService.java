package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.ImmutableValueObjects.Name;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.impl.InMemoryCourseService;
import edu.ccrm.service.impl.InMemoryStudentService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

public class ImportExportService {
    private final Path base;

    public ImportExportService(Path baseDir) {
        this.base = baseDir;
        try { Files.createDirectories(base); } catch (IOException ignored) {}
    }

    public void exportStudents(StudentService ss, Path outFile) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(outFile, StandardCharsets.UTF_8)) {
            for (Student s : ss.listAll()) {
                String line = String.join(",",
                        s.getId(),
                        s.getRegNo(),
                        s.getName().getFirst(),
                        s.getName().getLast(),
                        s.getEmail(),
                        s.getDob().toString()
                );
                w.write(line);
                w.newLine();
            }
        }
    }

    public void exportCourses(CourseService cs, Path outFile) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(outFile, StandardCharsets.UTF_8)) {
            for (Course c : cs.listAll()) {
                String line = String.join(",",
                        c.getCode(),
                        c.getTitle(),
                        Integer.toString(c.getCredits()),
                        c.getDepartment(),
                        c.getSemester().name()
                );
                w.write(line);
                w.newLine();
            }
        }
    }

    public void importStudents(Path inFile, StudentService ss) throws IOException {
        try (BufferedReader r = Files.newBufferedReader(inFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                String id = parts[0].isEmpty() ? UUID.randomUUID().toString() : parts[0];
                Student s = new Student.Builder(id)
                        .regNo(parts[1])
                        .name(new Name(parts[2], parts[3]))
                        .email(parts[4])
                        .dob(LocalDate.parse(parts[5]))
                        .build();
                ss.addStudent(s);
            }
        }
    }

    public void importCourses(Path inFile, CourseService cs) throws IOException {
        try (BufferedReader r = Files.newBufferedReader(inFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 5) continue;
                Course c = new Course.Builder(p[0])
                        .title(p[1])
                        .credits(Integer.parseInt(p[2]))
                        .department(p[3])
                        .semester(Enum.valueOf(java.time.Month.class, "JAN")==null? null : null) // placeholder
                        .build();
                cs.addCourse(c);
            }
        }
    }
}