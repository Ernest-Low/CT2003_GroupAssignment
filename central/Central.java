package central;

import enums.Major;
import enums.UserType;

import model.Student;
import model.CareerStaff;
import model.CompanyRep;
import companyRep.CRep;
import config.Services;
import dtos.LoginInfo;
import student.*;
import StaffFiles.*;

import dtos.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CSVMethods.*;

import login.AuthController;
import login.UpdatePasswordController;

public class Central {

    private final Services services;

    private CRep crep;

    //CSV files
    private static final String STAFF_CSV = "data/careerStaff_list.csv";
    private static final String STUDENT_CSV = "data/students_list.csv";
    private static final String CREP_CSV = "data/companyReps_list.csv";

    public Central(Services services) {
        this.services = services;
    }

    public void centralController() {

        // * Entry point from main

        // ! Login, get user ID & user type back
        AuthController authController = new AuthController();
        LoginInfo loginInfo = null;
        while (loginInfo == null) {
            loginInfo = authController.openMenu();
            System.out.println();
        }

        System.out.println("Welcome, " + loginInfo.getID() + " (" + loginInfo.getUserType() + ")");

        // UpdatePasswordController updatePasswordController = new UpdatePasswordController();
        // updatePasswordController.updatePassword(loginInfo.getID());

        // ! Temp, pretend got the logininfo back from login
        // ! Change this logininfo's UserType to your menu

        // TODO: Pull from CSV the user
        // TODO: Maybe update CSV to pull by ID, maybe add GUID (mimic database fully)

        // TODO: Pull from CSV the user based on usertype
        // TODO: It will be calling the different services anyway, should I just put it there?
        // TODO: Could rename it as gateway (idk why i feel like naming it that)

        // ! Temp, mock up a user for each
        //Student fakeStudent = new Student("S000001T", "U1000001A", "Aaron Tan", 3, Major.COMPUTER_SCIENCE);
        //CareerStaff fakeCareerStaff = new CareerStaff("C000001S", "jtan001", "John Tan", "Career Advisory");

        switch (loginInfo.getUserType()) {

            case STUDENT:

                List<String []> userData = new ArrayList<>();

                userData = CSVRead.readByID(STUDENT_CSV, loginInfo.getID(), "ID");

                String[] userRow = userData.get(0);
                int year = Integer.parseInt(userRow[3]);
                Major Major = enums.Major.valueOf(userRow[4].toUpperCase());
                //System.out.println(Arrays.toString(userData));
                Student Student = new Student(userRow[0], userRow[1], userRow[2], year, Major);
            
                studentMenu(Student, loginInfo);
            case CAREERSTAFF: 
            
                //careerStaffMenu(fakeCareerStaff);
                break;
            case COMPANYREP:
            
                cRepGateway(loginInfo);
                break;

            default:
                System.out.println("Logic error");
        }
    }

    private void studentMenu(Student student, LoginInfo loginInfo) {
        // * Entry point Student
        // ? Replace with your own method call (be it static / instance)
        StudentMenu studentController = new StudentMenu(student);
        studentController.StudentController(loginInfo);
    }

    private void careerStaffMenu(CareerStaff careerStaff) {
        // * Entry point Career Staff
        // ? Replace with your own method call (be it static / instance)

        staffmainnew staffStart = new staffmainnew(careerStaff);
        staffStart.staffEntry();

        // CareerStaffController careerStaffController = new CareerStaffController();
        // careerStaffController.openMenu(careerStaff);
    }

    private void cRepGateway(LoginInfo loginInfo) {
        // * Entry point Company Rep

        // TODO: Utilize Logininfo into crep service to get crep out

        // ! Mock user
        CompanyRep companyRep = new CompanyRep("C000001R", "alex.choi@novalink.com", "Alex Choi", "Novalink",
                "Engineering",
                "Software Engineer");

        this.crep = new CRep(services, companyRep);
        crep.CompanyRepController();
    }

}
