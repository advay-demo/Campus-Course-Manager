package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;

import java.util.List;
import java.util.OptionalDouble;

public class TranscriptService {
    public static double computeGPA(List<Enrollment> enrollments, java.util.function.Function<String, Integer> courseCreditsLookup) {
        double totalPoints = 0.0;
        int totalCredits = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() == null) continue;
            int credits = courseCreditsLookup.apply(e.getCourseCode());
            totalCredits += credits;
            totalPoints += e.getGrade().points() * credits;
        }
        if (totalCredits == 0) return 0.0;
        return totalPoints / totalCredits;
    }

    public static String transcriptView(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("---- TRANSCRIPT ----\n");
        sb.append(s.profile()).append("\n");
        for (Enrollment e : s.getEnrollments()) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
}