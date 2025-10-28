import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

// import the relevant java files
import user.Account;
import user.StudentProfile;
import user.StaffProfile;
import user.CompanyRepProfile;

public class LoginApp {

	private static final Scanner sc = new Scanner(System.in);
    private static final HashMap<String, Account> accounts = new HashMap<>();
    private static final HashMap<String, StudentProfile> studentProfiles = new HashMap<>();
    private static final HashMap<String, StaffProfile> staffProfiles = new HashMap<>();
    private static final HashMap<String, CompanyRepProfile> companyRepProfiles = new HashMap<>();

    private static final String STUDENT_CSV_FILE = "data/sample_student_list.csv";
    private static final String STAFF_CSV_FILE = "data/sample_staff_list.csv";
    private static final String COMPANYREP_CSV_FILE = "data/sample_company_representative_list.csv";

    private static boolean checkLoginStatus = false;

    private static String filePath;

	public static void main(String[] args) {

        List<List<String>> data_student = new ArrayList<>();
        List<List<String>> data_staff = new ArrayList<>();
        List<List<String>> data_companyRep = new ArrayList<>();

        getAccountsFromCSV(data_student, STUDENT_CSV_FILE, "Student");
        getAccountsFromCSV(data_staff, STAFF_CSV_FILE, "Career Center Staff");
        getAccountsFromCSV(data_companyRep, COMPANYREP_CSV_FILE, "Company Rep");

		int choice;
		do {
			System.out.println("Perform the following methods:");
			System.out.println("(1) Login");
			System.out.println("(2) Change Password");
            System.out.println("(3) Create Account - WORK IN PROGRESS");
			System.out.println("(4) Exit/n");
			
			choice = sc.nextInt();
			
			switch (choice) {
				case 1:
                    sc.nextLine();
					System.out.println("Username:");
                    String input_username_login = sc.nextLine();
                    System.out.println("Password:");
                    String input_password_login = sc.nextLine();

                    authentication(input_username_login, input_password_login);
					break;
				case 2:
                    System.out.println("Please enter your current Username and Password:");
                    sc.nextLine();
					System.out.println("Username:");
                    String input_username_changePass = sc.nextLine();
                    System.out.println("Password:");
                    String input_password_changePass = sc.nextLine();
                    System.out.println("New Password:");
                    String input_newPassword_changePass = sc.nextLine();

                    accountValidation(input_username_changePass, input_password_changePass, input_newPassword_changePass);
					break;
                case 3:
                    // work in progress zzz
                    break;
				case 4: System.out.println("Program terminating ....");
			}
		} while ((choice < 4) && (checkLoginStatus == false));
	}

    public static void getAccountsFromCSV(List<List<String>> data, String filePath, String role) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<String> lineData = Arrays.asList(values);

                data.add(lineData);
            }

            for (int i = 1; i < data.size(); i++) {
                switch(role) {
                    case "Student":
                        // StudentID,Password,Name,Major,Year,Email
                        List<String> row_student = data.get(i);
                        String studentID = row_student.get(0);
                        String studentSalt = row_student.get(1);
                        String studentHash = row_student.get(2);
                        String studentName = row_student.get(3);
                        String studentMajor = row_student.get(4);
                        int studentYear = Integer.parseInt(row_student.get(5));
                        String studentEmail = row_student.get(6);

                        // String userID, String name, String role, String email
                        accounts.put(studentID, new Account(studentID, studentSalt, studentHash, studentName, "Student", studentEmail));
                        // String userID, int yearOfStudy, String major
                        studentProfiles.put(studentID, new StudentProfile(studentID, studentYear, studentMajor));
                        break;
                    case "Career Center Staff":
                        // StaffID,Password,Name,Role,Department,Email
                        List<String> row_staff = data.get(i);
                        String staffID = row_staff.get(0);
                        String staffSalt = row_staff.get(1);
                        String staffHash = row_staff.get(2);
                        String staffName = row_staff.get(3);
                        String staffDepartment = row_staff.get(5);
                        String staffEmail = row_staff.get(6);

                        // String userID, String name, String role, String email
                        accounts.put(staffID, new Account(staffID, staffSalt, staffHash, staffName, "Career Center Staff", staffEmail));
                        // String userID, String staffDepartment
                        staffProfiles.put(staffID, new StaffProfile(staffID, staffDepartment));
                        break;
                    case "Company Rep":
                         // CompanyRepID,Password,Name,CompanyName,Department,Position,Email,Approved
                        List<String> row_companyRep = data.get(i);
                        String companyRepID = row_companyRep.get(0);
                        String companyRepSalt = row_companyRep.get(1);
                        String companyRepHash = row_companyRep.get(2);
                        String companyRepName = row_companyRep.get(3);
                        String companyRepCompanyName = row_companyRep.get(4);
                        String companyRepDepartment = row_companyRep.get(5);
                        String companyRepPosition = row_companyRep.get(6);
                        String companyRepEmail = row_companyRep.get(7);
                        boolean companyRepStatus = !row_companyRep.get(8).equals("No");

                        // String userID, String name, String role, String email
                        accounts.put(companyRepID, new Account(companyRepID, companyRepSalt, companyRepHash, companyRepName, "Company Rep", companyRepEmail));
                        // String userID, String companyName, String position, String department, boolean isApproved
                        companyRepProfiles.put(companyRepID, new CompanyRepProfile(companyRepID, companyRepCompanyName, companyRepPosition, companyRepDepartment, companyRepStatus));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Unable to read CSV Files");
        }
    }

    public static void authentication(String input_username, String input_password) {
        // find account
        if (accounts.get(input_username) != null) {
            // attempt auth
            boolean loginStatus = accounts.get(input_username).login(input_username, input_password);
            if (loginStatus == true) {
                System.out.println("[SUCCESS] Login Successful");
                checkLoginStatus = true;
                redirect_toHome(accounts.get(input_username));
            } else {
                System.out.println("[ERROR] No Username/Password Match Found");
            }
        } else {
            System.out.println("[ERROR] Account does not exist");
        }
    }

    // to change this method if we want to change mode of "validation" before user can change password - maybe email OTP?
    // or we can move this to the menu AFTER user logs in, meaning they won't need the additional step to validate beforehand
    // or we can have both :D
    public static void accountValidation(String input_username, String input_password, String new_password) {
        // find account
        if (accounts.get(input_username) != null) {
            boolean validationStatus = accounts.get(input_username).doesUsernamePasswordMatch(input_password);
            if (validationStatus == true) {
                changePassword(accounts.get(input_username), new_password);
            } else {
                System.out.println("[ERROR] No Username/Password Match Found");
            }
        } else {
            System.out.println("[ERROR] Account does not exist");
        }
    }

    public static void changePassword(Account validatedAccount, String input_password) {
        List<List<String>> data_TEMP = new ArrayList<>();
        boolean passwordChangeSuccess = validatedAccount.amendPassword(input_password);

        if (passwordChangeSuccess) {
            // update csv
            switch (validatedAccount.getRole()) {
                case "Student":
                    filePath = STUDENT_CSV_FILE;
                    break;
                case "Career Center Staff":
                    filePath = STAFF_CSV_FILE;
                    break;
                case "Company Representative":
                    filePath = COMPANYREP_CSV_FILE;
                    break;
            }

            // try (BufferedReader br = new BufferedReader(new FileReader(filePath));
            // BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

            // try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("data/TEMP_FILE.csv"))) {
                String line;
                boolean first = true;

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    List<String> lineData = Arrays.asList(values);

                    data_TEMP.add(lineData);
                }

                for (int i = 0; i < data_TEMP.size(); i++) {
                    List<String> row_TEMP = data_TEMP.get(i);
                    if (row_TEMP.get(0).equals(validatedAccount.getUserID())) {
                        // replace salt
                        row_TEMP.set(1, validatedAccount.getSalt());
                        // replace hash
                        row_TEMP.set(2, validatedAccount.getHash());
                    }
                    for (int j = 0; j < row_TEMP.size(); j++) {
                        bw.write(row_TEMP.get(j));
                        if (j < row_TEMP.size()-1) {
                            bw.write(",");
                        }
                    }
                    if (i < data_TEMP.size()-1) {
                        bw.newLine();
                    }
                }
            } catch(Exception e) {
                System.out.println("[ERROR] CSV Update Failed");
            }
            
            File oldFile = new File(filePath);
            File newFile = new File("data/TEMP_FILE.csv");
            oldFile.delete();
            newFile.renameTo(oldFile);

            System.out.println("[SUCCESS] Password Changed Successfully");
        } else {
            System.out.println("[ERROR] Password Change Failed");
        }
    }

    public static void redirect_toHome(Account loginAccount) {
        switch (loginAccount.getRole()) {
            case "Student":
                HomeApp_Student homeStudent = new HomeApp_Student();
                // get profile
                StudentProfile studentLoginProfile = studentProfiles.get(loginAccount.getUserID());
                homeStudent.homeStudent_Menu(loginAccount, studentLoginProfile);
                break;
            case "Career Center Staff":
                HomeApp_Staff homeStaff = new HomeApp_Staff();
                // get profile
                StaffProfile staffLoginProfile = staffProfiles.get(loginAccount.getUserID());
                homeStaff.homeStaff_Menu(loginAccount, staffLoginProfile);
                break;
            case "Company Rep":
                HomeApp_CompanyRep homeCompanyRep = new HomeApp_CompanyRep();
                // get profile
                CompanyRepProfile companyRepLoginProfile = companyRepProfiles.get(loginAccount.getUserID());
                homeCompanyRep.homeCompanyRep_Menu(loginAccount, companyRepLoginProfile);
                break;
        }
    }

}
