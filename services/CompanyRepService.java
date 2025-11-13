package services;

import model.CompanyRep;
import repositories.CompanyRepRepo;

public class CompanyRepService {

    private final CompanyRepRepo companyRepRepo;

    public CompanyRepService(CompanyRepRepo companyRepRepo) {
        this.companyRepRepo = companyRepRepo;
    }

    public CompanyRep getCompanyRepByGUID(String GUID) {
        return companyRepRepo.findFirstByColumn(GUID, 0);
    }

    public CompanyRep getCompanyRepByID(String id) {
        return companyRepRepo.findFirstByColumn(id, 1);
    }

}
