package central;

import enums.Major;

import model.Student;
import student.StudentMenu;
import model.CareerStaff;
import model.CompanyRep;
import StaffFiles.staffmainnew;
import companyRep.CompanyRepMain;
import config.Services;
import dtos.LoginInfo;

import login.AuthController;
// import login.UpdatePasswordController;

public class Central {

    private final Services services;
    private CompanyRepMain companyRepMain;
    private StudentMenu studentMain;
    private staffmainnew careerStaffMain;

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

        System.out.println("Welcome, " + loginInfo.getID() + " (" + loginInfo.getUserType() + ")"); // ? Is this debug or intentional?

        // UpdatePasswordController updatePasswordController = new UpdatePasswordController();
        // updatePasswordController.updatePassword(loginInfo.getID());

        switch (loginInfo.getUserType()) {
            case STUDENT -> studentGateway(loginInfo);
            case CAREERSTAFF -> careerStaffGateway(loginInfo);
            case COMPANYREP -> companyRepGateway(loginInfo);
            default -> System.out.println("Logic error");
        }
    }

    private void studentGateway(LoginInfo loginInfo) {
        // * Entry point Student

        Student student = services.studentService.getStudentByID(loginInfo.getID());

        // ! Mock user
        student = new Student("S000001T", "U1000001A", "Aaron Tan", 1, Major.COMPUTER_SCIENCE);

        this.studentMain = new StudentMenu(student);
        this.studentMain.StudentController(loginInfo);
    }

    private void careerStaffGateway(LoginInfo loginInfo) {
        // * Entry point Career Staff

        CareerStaff careerStaff = services.careerStaffService.getCareerStaffByID(loginInfo.getID());

        // ! Mock user
        careerStaff = new CareerStaff("C000001S", "jtan001", "John Tan", "Career Advisory");

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
