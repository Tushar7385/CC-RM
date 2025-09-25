package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;

public class Student extends Person {
    private String regNo;
    private boolean active = true;
    private final Map<String, Enrollment> enrolledCourses = new LinkedHashMap<>();

    public Student(String id, String regNo, Name name, String email) {
        super(id, name, email);
        this.regNo = regNo;
    }

    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }

    public void enroll(Enrollment e) { enrolledCourses.put(e.getCourse().getCode(), e); }
    public void unenroll(String courseCode) { enrolledCourses.remove(courseCode); }
    public Collection<Enrollment> getEnrollments() { return enrolledCourses.values(); }

    @Override
    public String profile() {
        return String.format("Student[id=%s, regNo=%s, name=%s, email=%s, active=%s, joined=%s]",
            id, regNo, name, email, active, createdAt);
    }

    @Override
    public String toString() {
        return profile();
    }
}
