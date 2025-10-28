package user;

public class StudentProfile {

    private final String userID;
    private int yearOfStudy;
    private String major;

	public StudentProfile(String userID, int yearOfStudy, String major) {
        this.userID = userID;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
	}
	
	public int getYearOfStudy() {
        return yearOfStudy;
	}

    public String getMajor() {
        return major;
	}

    public void amendYearOfStudy(int yearOfStudy_Amended) {
        yearOfStudy = yearOfStudy_Amended;
    }

    public void amendMajor(String major_Amended) {
        major = major_Amended;
    }

}
