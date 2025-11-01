package model;

public class CareerStaff extends User {

    protected String staffDepartment;

    public CareerStaff(String ID, String name, String staffDepartment) {
        super(ID, name);
        this.staffDepartment = staffDepartment;
    }

    public String getStaffDepartment() {
        return staffDepartment;
    }
}