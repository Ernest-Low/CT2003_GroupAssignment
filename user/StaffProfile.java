package user;

public class StaffProfile {

    private final String userID;
    private String staffDepartment;

	public StaffProfile(String userID, String staffDepartment) {
        this.userID = userID;
        this.staffDepartment = staffDepartment;
	}
	
	public String getStaffDepartment(String role) {
        return staffDepartment;
	}

    public void amendStaffDepartment(String staffDepartment_Amended) {
        staffDepartment = staffDepartment_Amended;
    }

}
