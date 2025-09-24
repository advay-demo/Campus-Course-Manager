package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import edu.ccrm.domain.ImmutableValueObjects.Name;

public class Student extends Person {
    private String id;
    private String regNo;
    private String fullName;
    private String email;
    private boolean status;
    private LocalDate dob;
    private List<Enrollment> enrollments = new ArrayList<>();

    // Constructor is private for Builder usage
    private Student(String id, String regNo, String fullName, String email, boolean status, LocalDate dob) {
        super(id, new Name(
                        fullName.split(" ")[0],
                        fullName.split(" ").length > 1 ? fullName.split(" ")[1] : ""),
                email);
        this.id = id;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.dob = dob;
    }

    // Getters
    public String getId() { return id; }
    public String getRegNo() { return regNo; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public LocalDate getDob() { return dob; }
    public boolean isActive() { return status; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    // Enrollment methods
    public void enroll(Enrollment e) { enrollments.add(e); }
    public void unenroll(String courseCode) { enrollments.removeIf(enrollment -> enrollment.getCourseCode().equals(courseCode)); }
    public void deactivate() { this.status = false; }
    public void activate() { this.status = true; }

    @Override
    public String profile() {
        return "Student Profile:\n" +
                "ID: " + id + "\n" +
                "Reg No: " + regNo + "\n" +
                "Name: " + fullName + "\n" +
                "Email: " + email + "\n" +
                "Status: " + (status ? "Active" : "Inactive") + "\n" +
                "DOB: " + dob + "\n" +
                "Enrollments: " + (enrollments != null ? enrollments.size() : 0);
    }

    // --- Builder inner class ---
    public static class Builder {
        private final String id;
        private String regNo = "";
        private Name name = new Name("", "");
        private String email = "";
        private LocalDate dob = LocalDate.now();
        private boolean status = true;

        public Builder(String id) {
            this.id = id;
        }

        public Builder regNo(String regNo) { this.regNo = regNo; return this; }
        public Builder name(Name name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder dob(LocalDate dob) { this.dob = dob; return this; }
        public Builder status(boolean status) { this.status = status; return this; }

        public Student build() {
            String fullName = name.getFirst() + (name.getLast().isEmpty() ? "" : " " + name.getLast());
            return new Student(id, regNo, fullName, email, status, dob);
        }
    }
}
