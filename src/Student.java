public class Student extends User{
	private int year; 
	private String major; /* year & major, for determining which internship can be applied/shown */
	private Internship[] internship = new Internship[3]; /* if there's no internship class, can be just a list of internship id from csv */
	private int[] internshipId = new int[3]; /* alternative */
	
	
	/* register all students */
	public static void register() {
		/* auto register by reading CSV file */
		return;
	}
	
	public void exploreInternships() {
		/* shows only internship that are eligible for student. Y1/2 - Basic, Y3 - all */
		return;
	}
	
	public void applyInternship() {
		/* max 3 applications */
		/* default status will be 'Pending' */
		return;
	}
	
	public void viewApplications() {
		/* application status, "Successful" > can be accepted */
		/* remove other applications if 1 is accepted */
		return;
	}
	
}
