package repositories;

import java.util.List;

import model.CareerStaff;

public interface CareerStaffRepo {
    CareerStaff findFirstByColumn(String value, int columnNo);

    void save(CareerStaff careerStaff);

    void update(CareerStaff careerStaff);

    void delete(String GUID);

    List<CareerStaff> findAll();
}
