package edu.ccrm.domain;

import java.time.LocalDateTime;
import edu.ccrm.domain.ImmutableValueObjects.Name;

public abstract class Person {
    protected final String id;
    protected Name name;
    protected String email;
    protected LocalDateTime createdAt;

    public Person(String id, Name name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Name getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public abstract String profile();

    @Override
    public String toString() {
        return profile();
    }
}