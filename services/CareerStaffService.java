package services;

import model.CareerStaff;
import repositories.CareerStaffRepo;

public class CareerStaffService {

    private final CareerStaffRepo careerStaffRepo;

    public CareerStaffService(CareerStaffRepo careerStaffRepo) {
        this.careerStaffRepo = careerStaffRepo;
    }

    public CareerStaff getCareerStaffByGUID(String GUID) {
        return careerStaffRepo.findFirstByColumn(GUID, 0);
    }

    public CareerStaff getCareerStaffByID(String id) {
        return careerStaffRepo.findFirstByColumn(id, 1);
    }

}
