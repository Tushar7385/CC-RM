package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;

public class TranscriptService {
    public double computeGPA(Student s) {
        double totalPoints = s.getEnrollments().stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getGrade().points() * e.getCourse().getCredits())
                .sum();

        int totalCredits = s.getEnrollments().stream()
                .filter(e -> e.getGrade() != null)
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        if (totalCredits == 0) return Double.NaN;
        return totalPoints / totalCredits;
    }

    public String printTranscript(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(s.getName()).append('\n');
        for (Enrollment e : s.getEnrollments()) {
            sb.append(String.format("%s | %s | grade=%s\n", e.getCourse().getCode(), e.getCourse().getTitle(), e.getGrade()));
        }
        double gpa = computeGPA(s);
        if (!Double.isNaN(gpa)) sb.append(String.format("GPA: %.2f\n", gpa));
        return sb.toString();
    }
}
