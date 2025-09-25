package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Name;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.enums.Semester;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class ImportExportService {
    private final StudentService studentService;
    private final CourseService courseService;

    public ImportExportService(StudentService ss, CourseService cs) {
        this.studentService = ss; this.courseService = cs;
    }

    public void importStudents(Path csv) throws IOException {
        try (Stream<String> lines = Files.lines(csv)) {
            lines.skip(1).forEach(line -> { // skip header
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String reg = parts[0].trim();
                    String full = parts[1].trim();
                    String email = parts[2].trim();
                    String[] names = full.split(" ");
                    Name n = new Name(names[0], names.length==3?names[1]:"", names[names.length-1]);
                    studentService.addStudent(reg, n, email);
                }
            });
        }
    }

    public void importCourses(Path csv) throws IOException {
        try (Stream<String> lines = Files.lines(csv)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String code = parts[0].trim();
                    String title = parts[1].trim();
                    int credits = Integer.parseInt(parts[2].trim());
                    String dept = parts[3].trim();
                    Semester sem = Semester.valueOf(parts[4].trim().toUpperCase());
                    Course c = new Course.Builder(code).title(title).credits(credits).department(dept).semester(sem).build();
                    courseService.addCourse(c);
                }
            });
        }
    }

    public void exportStudents(Path out) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("regNo,name,email\n");
        for (Student s : studentService.listAll()) {
            sb.append(String.format("%s,%s,%s\n", s.getRegNo(), s.getName().fullName(), s.getEmail()));
        }
        Files.createDirectories(out.getParent());
        Files.writeString(out, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
