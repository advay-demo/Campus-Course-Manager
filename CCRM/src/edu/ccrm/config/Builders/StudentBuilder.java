package edu.ccrm.config.builders;

import java.time.LocalDate;
import edu.ccrm.domain.ImmutableValueObjects.Name;
import edu.ccrm.domain.Student;

public class StudentBuilder {
    private String id;
    private Name name = new Name("", "");
    private String email = "";
    private String regNo = "";
    private boolean status = true;
    private LocalDate dob = LocalDate.now();

    public StudentBuilder id(String id) {
        this.id = id;
        return this;
    }

    public StudentBuilder name(Name name) {
        if (name != null) this.name = name;
        return this;
    }

    public StudentBuilder email(String email) {
        if (email != null) this.email = email;
        return this;
    }

    public StudentBuilder regNo(String regNo) {
        if (regNo != null && !regNo.isBlank()) this.regNo = regNo;
        return this;
    }

    public StudentBuilder status(boolean status) {
        this.status = status;
        return this;
    }

    public StudentBuilder dob(LocalDate dob) {
        if (dob != null) this.dob = dob;
        return this;
    }

    public Student build() {
        // Use the internal Student.Builder
        return new Student.Builder(id)
                .name(name)
                .email(email)
                .regNo(regNo)
                .dob(dob)
                .status(status)
                .build();
    }
}
