package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.util.Objects;

public class EnrollmentService {
    private final int MAX_CREDITS = 24; // business rule

    public void enroll(Student s, Course c) throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        Objects.requireNonNull(s);
        Objects.requireNonNull(c);
        if (s.getEnrollments().stream().anyMatch(e -> e.getCourse().getCode().equals(c.getCode()))) {
            throw new DuplicateEnrollmentException("Student already enrolled in: " + c.getCode());
        }
        int current = s.getEnrollments().stream().mapToInt(e -> e.getCourse().getCredits()).sum();
        if (current + c.getCredits() > MAX_CREDITS) {
            throw new MaxCreditLimitExceededException("Cannot enroll: credit limit exceeded");
        }
        Enrollment en = new Enrollment(c);
        s.enroll(en);
    }

    public void unenroll(Student s, String courseCode) {
        s.unenroll(courseCode);
    }

    public void assignGrade(Student s, String courseCode, edu.ccrm.domain.enums.Grade grade) {
        s.getEnrollments().stream()
                .filter(e -> e.getCourse().getCode().equals(courseCode))
                .findFirst()
                .ifPresent(e -> e.setGrade(grade));
    }
}
