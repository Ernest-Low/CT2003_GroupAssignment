package repositories;

import java.util.List;

import model.Internship;

public interface InternshipRepo {
    Internship findById(String id);

    List<Internship> findAllByColumn(String value, int columnNo);

    void save(Internship internship);

    void update(Internship internship);

    void delete(String id);

    List<Internship> findAll();

}
