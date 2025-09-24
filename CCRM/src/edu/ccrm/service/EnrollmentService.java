package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Enrollment enroll(String studentId, String courseCode);
    Optional<Enrollment> find(String studentId, String courseCode);
    void unenroll(String studentId, String courseCode);
    void recordMarks(String studentId, String courseCode, double marks);
    List<Enrollment> listByStudent(String studentId);
}