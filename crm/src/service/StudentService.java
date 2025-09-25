package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Name;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StudentService {
    private final Map<String, Student> store = new LinkedHashMap<>();
    private final AtomicInteger idSeq = new AtomicInteger(1000);

    public Student addStudent(String regNo, Name name, String email) {
        String id = "S" + idSeq.getAndIncrement();
        Student s = new Student(id, regNo, name, email);
        store.put(id, s);
        return s;
    }

    public Optional<Student> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public List<Student> listAll() { return new ArrayList<>(store.values()); }

    public List<Student> searchByName(String term) {
        return store.values().stream()
                .filter(s -> s.getName().fullName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }
}
