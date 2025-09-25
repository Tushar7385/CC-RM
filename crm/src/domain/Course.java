package edu.ccrm.domain;

import edu.ccrm.domain.enums.Semester;
import java.util.Objects;

public class Course {
    private final String code; // immutable identifier
    private String title;
    private int credits;
    private String instructor; // instructor id or name for simplicity
    private Semester semester;
    private String department;

    private Course(Builder b) {
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
        this.department = b.department;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("Course[%s | %s | %dcr | %s | %s | %s]", code, title, credits,
                instructor, semester, department);
    }

    public static class Builder {
        private final String code;
        private String title = "";
        private int credits = 3;
        private String instructor = "TBD";
        private Semester semester = Semester.FALL;
        private String department = "General";

        public Builder(String code) {
            this.code = Objects.requireNonNull(code);
        }
        public Builder title(String t) { this.title = t; return this; }
        public Builder credits(int c) { this.credits = c; return this; }
        public Builder instructor(String i) { this.instructor = i; return this; }
        public Builder semester(Semester s) { this.semester = s; return this; }
        public Builder department(String d) { this.department = d; return this; }
        public Course build() { return new Course(this); }
    }
}
