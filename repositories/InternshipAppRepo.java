package repositories;

import java.util.List;

import model.InternshipApp;

public interface InternshipAppRepo {

    InternshipApp findFirstByColumn(String value, int columnNo);

    List<InternshipApp> findAllByColumn(String value, int columnNo);

    void save(InternshipApp internshipApp);

    void update(InternshipApp internshipApp);

    void delete(String studentID, String internshipID);

    List<InternshipApp> findAll();

}
