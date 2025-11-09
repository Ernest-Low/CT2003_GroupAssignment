package central;

import enums.Major;
import enums.UserType;

import model.Student;
import model.CareerStaff;
import model.CompanyRep;
import student.*;
import StaffFiles.*;

import dto.LoginInfo;

import login.AuthController;
import login.UpdatePasswordController;

public class Central {

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
        // updatePasswordController.updatePassword();

        // ! Temp, pretend got the logininfo back from login
        // ! Change this logininfo's UserType to your menu

        // TODO: Pull from CSV the user
        // TODO: Maybe update CSV to pull by ID, maybe add GUID (mimic database fully)

        // based on ID, retrieve user from appopriate csv
        // based on user, call the appopriate menu (student, careerStaff, companyRep)

        // ! Temp, mock up a user for each
        Student fakeStudent = new Student("U2310001A", "Aaron Tan", 1, Major.COMPUTER_SCIENCE);
        CareerStaff fakeCareerStaff = new CareerStaff("jtan001", "John Tan", "Career Advisory");
        CompanyRep fakeCompanyRep = new CompanyRep("alex.choi@novalink.com", "Alex Choi", "Novalink", "Engineering",
                "Software Engineer");

        switch (loginInfo.getUserType()) {
            case STUDENT -> studentMenu(fakeStudent);
            case CAREERSTAFF -> careerStaffMenu(fakeCareerStaff);
            case COMPANYREP -> companyRepMenu(fakeCompanyRep);
            default -> System.out.println("Logic error");
        }
    }

    private void studentMenu(Student student) {
        // * Entry point Student
        // ? Replace with your own method call (be it static / instance)
        StudentMenu studentController = new StudentMenu(student);
        studentController.StudentController();
    }

    private void careerStaffMenu(CareerStaff careerStaff) {
        // * Entry point Career Staff
        // ? Replace with your own method call (be it static / instance)

        staffmain staffStart = new staffmain(careerStaff);
        staffStart.staffEntry();

        // CareerStaffController careerStaffController = new CareerStaffController();
        // careerStaffController.openMenu(careerStaff);
    }

    private void companyRepMenu(CompanyRep companyRep) {
        // * Entry point Company Rep
        // ? Replace with your own method call (be it static / instance)

        // CompanyRepController companyRepController = new CompanyRepController();
        // companyRepController.openMenu(companyRep);
    }

}
