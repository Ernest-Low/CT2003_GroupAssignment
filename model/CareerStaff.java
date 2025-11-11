package model;

public class CareerStaff extends User {

    protected String staffDepartment;

    // * GUID follows: C000001S, C000002S, C000003S
    public CareerStaff(String GUID, String ID, String name, String staffDepartment) {
        super(GUID, ID, name);
        this.staffDepartment = staffDepartment;
    }

    public String getStaffDepartment() {
        return staffDepartment;
    }
}