package model;

public class CompanyRep extends User {

    protected String companyName;
    protected String department;
    protected String position;

    public CompanyRep(String GUID, String ID, String name, String companyName, String department, String position) {
        super(GUID, ID, name);
        this.companyName = companyName;
        this.department = department;
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return this.position;
    }

}