package edu.ccrm.domain;

public class Instructor extends Person {
    private String dept;

    public Instructor(String id, Name name, String email, String dept) {
        super(id, name, email);
        this.dept = dept;
    }

    @Override
    public String profile() {
        return String.format("Instructor[id=%s, name=%s, dept=%s]", id, name, dept);
    }
}
