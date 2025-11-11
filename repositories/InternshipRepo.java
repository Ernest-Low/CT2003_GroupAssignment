package repositories;

import java.util.List;

import model.Internship;

public interface InternshipRepo {
    Internship findById(String id);

    void save(Internship internship);

    void update(Internship internship);

    void delete(String id);

    List<Internship> findAll();

}
