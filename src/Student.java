import java.io.File;
import java.util.Scanner;

public class Student extends User{
	private static final int MAX_APPLICATION = 3;
	private String studentID; // remove if it's implemented in User class
	private int year; 
	private String major; // year & major, for determining which internship can be applied/shown
	private Internship[] internships = new Internship[MAX_APPLICATION];
	private int applicationCount;

	public Student(String name, String email, String studentID, int year, String major) {
		super(name, email); // assuming name and email are in user class
		this.studentID = studentID;
		this.major = major;
		this.applicationCount = this.count();
	}

	/* register all students */
	public static void register() {
		// auto register by reading CSV file
		return;
	}
	
	public Internship[] exploreInternships(Internship[] listings) {
		// shows only internship that are eligible for student. Y1/2 - Basic, Y3 - all
		
		if (this.year < 3){
			// return internships
		}
		else {
			// display all
		}
	}
	
	public boolean applyInternship(Internship listing) {
		// max 3 applications
		// default status will be 'Pending'
		if (this.applicationCount < 3) {
			this.internship[applicationCount] = listing;

			this.applicationCount++;
			return true;
		}
		// If applcation fails
		return false;
	}
	
	public void viewApplications() {
		// application status, "Successful" > can be accepted */
		// remove other applications if 1 is accepted */
		return;
	}
	
	public int count() {
		// reads student-internship relation csv
		try (Scanner sc = new Scanner(new File("./data/student_internship.csv"))) {
			
		}

	}
}
