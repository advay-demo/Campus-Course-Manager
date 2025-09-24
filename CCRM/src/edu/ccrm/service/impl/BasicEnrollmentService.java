
package edu.ccrm.service.impl;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

public class BasicEnrollmentService implements EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;

    private final Map<String, Map<String, Enrollment>> data = new HashMap<>();

    public BasicEnrollmentService(StudentService ss, CourseService cs) {
        this.studentService = ss;
        this.courseService = cs;
    }

    @Override
    public Enrollment enroll(String studentId, String courseCode) {
        Student s = studentService.findById(studentId).orElseThrow(()-> new IllegalArgumentException("Student not found"));
        Course c = courseService.findByCode(courseCode).orElseThrow(()-> new IllegalArgumentException("Course not found"));

        int currentCredits = listByStudent(studentId).stream()
                .map(ec -> courseService.findByCode(ec.getCourseCode()).orElse(null))
                .filter(Objects::nonNull)
                .mapToInt(Course::getCredits)
                .sum();

        if (currentCredits + c.getCredits() > AppConfig.getInstance().getMaxCreditsPerSemester()) {
            throw new IllegalStateException("Enrolling would exceed max credits per semester");
        }

        data.putIfAbsent(studentId, new HashMap<>());
        Enrollment e = new Enrollment(studentId, courseCode);
        data.get(studentId).put(courseCode, e);

        s.enroll(e);
        return e;
    }

    @Override
    public Optional<Enrollment> find(String studentId, String courseCode) {
        return Optional.ofNullable(data.getOrDefault(studentId, Collections.emptyMap()).get(courseCode));
    }

    @Override
    public void unenroll(String studentId, String courseCode) {
        Map<String, Enrollment> m = data.get(studentId);
        if (m != null) m.remove(courseCode);
        studentService.findById(studentId).ifPresent(s -> s.unenroll(courseCode));
    }

    @Override
    public void recordMarks(String studentId, String courseCode, double marks) {
        Enrollment e = find(studentId, courseCode).orElseThrow(()-> new IllegalArgumentException("Enrollment not found"));
        e.recordMarks(marks);
    }

    @Override
    public List<Enrollment> listByStudent(String studentId) {
        return new ArrayList<>(data.getOrDefault(studentId, Collections.emptyMap()).values());
    }
}