package repositories;

import java.util.List;

import model.Student;

public interface StudentRepo {
    Student findFirstByColumn(String value, int columnNo);

    List<Student> findAllByColumn(String value, int columnNo);

    void save(Student student);

    void update(Student student);

    void delete(String GUID);

    List<Student> findAll();
}
