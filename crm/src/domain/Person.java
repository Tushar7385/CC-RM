package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    protected final String id;
    protected Name name;
    protected String email;
    protected LocalDate createdAt;

    protected Person(String id, Name name, String email) {
        assert id != null && !id.isBlank();
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = LocalDate.now();
    }

    public String getId() { return id; }
    public Name getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getCreatedAt() { return createdAt; }

    public abstract String profile();
}
