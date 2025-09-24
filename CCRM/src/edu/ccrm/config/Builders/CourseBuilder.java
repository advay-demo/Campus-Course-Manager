package edu.ccrm.config.builders;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import java.util.Objects;

public class CourseBuilder {
    private String code;
    private String title = "Untitled";
    private int credits = 3;
    private Instructor instructor;
    private Semester semester = Semester.FALL;
    private String department = "General";

    public CourseBuilder code(String code) {
        this.code = Objects.requireNonNull(code, "Course code cannot be null");
        return this;
    }

    public CourseBuilder title(String title) {
        if (title != null && !title.isBlank()) this.title = title;
        return this;
    }

    public CourseBuilder credits(int credits) {
        if (credits > 0) this.credits = credits;
        return this;
    }

    public CourseBuilder instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }

    public CourseBuilder semester(Semester semester) {
        if (semester != null) this.semester = semester;
        return this;
    }

    public CourseBuilder department(String department) {
        if (department != null && !department.isBlank()) this.department = department;
        return this;
    }

    public Course build() {
        // Use the internal Course.Builder
        Course.Builder internal = new Course.Builder(code)
                .title(title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department);

        return internal.build();
    }
}
