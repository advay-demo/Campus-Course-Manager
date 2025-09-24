package edu.ccrm.domain;
import edu.ccrm.domain.ImmutableValueObjects.Name;

public class Instructor extends Person {
    private String department;

    public Instructor(String id, Name name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String d) { this.department = d; }

    @Override
    public String profile() {
        return String.format("Instructor[%s] %s (%s) Dept:%s", id, name.full(), email, department);
    }
}