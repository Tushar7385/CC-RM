package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.enums.Semester;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CourseService {
    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final AtomicInteger seq = new AtomicInteger(100);

    public Course addCourse(Course c) {
        courses.put(c.getCode(), c);
        return c;
    }

    public Optional<Course> find(String code) { return Optional.ofNullable(courses.get(code)); }
    public List<Course> listAll() { return new ArrayList<>(courses.values()); }

    // Stream-based search/filter
    public List<Course> filterByInstructor(String instructor) {
        return courses.values().stream()
                .filter(c -> c.getInstructor() != null && c.getInstructor().equalsIgnoreCase(instructor))
                .collect(Collectors.toList());
    }

    public List<Course> filterByDepartment(String dept) {
        return courses.values().stream()
                .filter(c -> c.getDepartment() != null && c.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }

    public List<Course> filterBySemester(Semester s) {
        return courses.values().stream()
                .filter(c -> c.getSemester() == s)
                .collect(Collectors.toList());
    }
}
