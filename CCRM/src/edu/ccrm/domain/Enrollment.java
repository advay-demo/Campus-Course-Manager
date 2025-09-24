package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final LocalDateTime enrolledAt;
    private Double marks;
    private Grade grade;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.enrolledAt = LocalDateTime.now();
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public Optional<Double> getMarks() { return Optional.ofNullable(marks); }
    public Grade getGrade() { return grade; }

    public void recordMarks(double m) {
        this.marks = m;
        this.grade = Grade.fromMarks(m);
    }

    @Override
    public String toString() {
        return String.format("Enrollment[student=%s, course=%s, marks=%s, grade=%s]",
                studentId, courseCode, marks==null?"N/A":marks.toString(), grade==null?"N/A":grade.name());
    }
}