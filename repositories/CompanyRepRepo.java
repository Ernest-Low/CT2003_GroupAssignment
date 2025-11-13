package repositories;

import java.util.List;

import model.CompanyRep;

public interface CompanyRepRepo {
    CompanyRep findFirstByColumn(String value, int columnNo);

    void save(CompanyRep companyRep);

    void update(CompanyRep companyRep);

    void delete(String GUID);

    List<CompanyRep> findAll();
}
