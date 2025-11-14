package services;

import java.util.List;

import model.Student;
import repositories.StudentRepo;

public class StudentService {

    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Student getStudentByGUID(String GUID) {
        return studentRepo.findFirstByColumn(GUID, 0);
    }

    public Student getStudentByID(String id) {
        return studentRepo.findFirstByColumn(id, 1);
    }

    public List<Student> getStudentsByColumn(String value, int ColumnNo) {
        return studentRepo.findAllByColumn(value, ColumnNo);
    }

}
