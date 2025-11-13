package central;

// import enums.Major;

import model.Student;
import student.StudentMenu;
import model.CareerStaff;
import model.CompanyRep;
import StaffFiles.staffmainnew;
import companyRep.CompanyRepMain;
import config.Services;
import dtos.LoginInfo;
// import student.*;
// import StaffFiles.*;

// import dtos.*;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import CSVMethods.*;

import login.AuthController;
// import login.UpdatePasswordController;

public class Central {

    private final Services services;
    private CompanyRepMain companyRepMain;
    private StudentMenu studentMain;
    private staffmainnew careerStaffMain;

    //CSV files
    // private static final String STAFF_CSV = "data/careerStaff_list.csv";
    // private static final String STUDENT_CSV = "data/students_list.csv";
    // private static final String CREP_CSV = "data/companyReps_list.csv";

    public Central(Services services) {
        this.services = services;
    }

    public void centralController() {
        // * Entry point from main

        // * Login, get user ID & user type back
        AuthController authController = new AuthController();
        LoginInfo loginInfo = null;
        while (loginInfo == null) {
            loginInfo = authController.openMenu();
            System.out.println();
        }

        System.out.println("Welcome, " + loginInfo.getID() + " (" + loginInfo.getUserType() + ")"); // ? Is this debug or intentional?

        // UpdatePasswordController updatePasswordController = new UpdatePasswordController();
        // updatePasswordController.updatePassword(loginInfo.getID());

        switch (loginInfo.getUserType()) {
            case STUDENT -> studentGateway(loginInfo);
            case CAREERSTAFF -> careerStaffGateway(loginInfo);
            case COMPANYREP -> companyRepGateway(loginInfo);
            default -> System.out.println("Logic error");
        }

        // switch (loginInfo.getUserType()) {

        //     case STUDENT:

        //         List<String []> userData = new ArrayList<>();

        //         userData = CSVRead.readByID(STUDENT_CSV, loginInfo.getID(), "ID");

        //         String[] userRow = userData.get(0);
        //         int year = Integer.parseInt(userRow[3]);
        //         Major Major = enums.Major.valueOf(userRow[4].toUpperCase());
        //         //System.out.println(Arrays.toString(userData));
        //         Student Student = new Student(userRow[0], userRow[1], userRow[2], year, Major);
            
        //         studentMenu(Student, loginInfo);
        //     case CAREERSTAFF:

        //         List<String[]> staffData = new ArrayList<>();

        //         userData = CSVRead.readByID(STAFF_CSV, loginInfo.getID(), "ID");
        //         String[] staffRow = staffData.get(0);

        //         CareerStaff CareerStaff = new CareerStaff(staffRow[0], staffRow[1], staffRow[2], staffRow[3]);
            
        //         careerStaffMenu(CareerStaff, loginInfo);
        //         break;
        //     case COMPANYREP:
            
        //         cRepGateway(loginInfo);
        //         break;

        //     default:
        //         System.out.println("Logic error");
        // }
    }

    private void studentGateway(LoginInfo loginInfo) {
        // * Entry point Student

        Student student = services.studentService.getStudentByID(loginInfo.getID());


        this.studentMain = new StudentMenu(student);
        this.studentMain.StudentController(loginInfo);
    }

    private void careerStaffGateway(LoginInfo loginInfo) {
        // * Entry point Career Staff

        CareerStaff careerStaff = services.careerStaffService.getCareerStaffByID(loginInfo.getID());

        this.careerStaffMain = new staffmainnew(careerStaff);
        this.careerStaffMain.staffEntry();
    }

    private void companyRepGateway(LoginInfo loginInfo) {
        // * Entry point Company Rep
        CompanyRep companyRep = services.companyRepService.getCompanyRepByID(loginInfo.getID());

        // ! Mock user
        companyRep = new CompanyRep("C000001R", "alex.choi@novalink.com", "Alex Choi", "Novalink",
                "Engineering",
                "Software Engineer");

        this.companyRepMain = new CompanyRepMain(services, companyRep);
        this.companyRepMain.CompanyRepController();
    }

}
