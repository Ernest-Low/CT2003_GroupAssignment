package user;

public class CompanyRepProfile {

    private final String userID;
    private final String companyName;
    private String position;
    private String department;
    private boolean isApproved;

	public CompanyRepProfile(String userID, String companyName, String position, String department, boolean isApproved) {
        this.userID = userID;
        this.companyName = companyName;
        this.position = position;
        this.department = department;
        this.isApproved = isApproved;
	}
	
	public String getCompanyName(String role) {
        return companyName;
	}

    public String getPosition(String role) {
        return position;
	}

    public String getDepartment(String role) {
        return department;
	}

    public void amendPosition(String position_Amended) {
        position = position_Amended;
    }

    public void amendDepartment(String department_Amended) {
        department = department_Amended;
    }

    public boolean accountApproved() {
        return isApproved;
    }

    public void approveAccount() {
        if (isApproved == true) {
            System.out.println("ERROR: Account is already approved!");
        } else {
            isApproved = true;
        }
    }

}
