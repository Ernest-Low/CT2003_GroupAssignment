package central;

import enums.Major;
import enums.UserType;

import model.Student;
import model.CareerStaff;
import model.CompanyRep;
import companyRep.CRep;
import config.Services;
import dtos.LoginInfo;

public class Central {

    private final Services services;

    private CRep crep;

    public Central(Services services) {
        this.services = services;
    }

    public void centralController() {
        // * Entry point from main

        // ! Login, get user ID & user type back
        // ? Replace with method call to login
        // LoginController loginController = new LoginController();
        // LoginInfo logininfo = loginController.openMenu();

        // ! Temp, pretend got the logininfo back from login
        // ! Change this logininfo's UserType to your menu
        LoginInfo logininfo = new LoginInfo("U1000001A", UserType.COMPANYREP);

        // TODO: Pull from CSV the user based on usertype
        // TODO: It will be calling the different services anyway, should I just put it there?
        // TODO: Could rename it as gateway (idk why i feel like naming it that)

        // ! Temp, mock up a user for each
        Student fakeStudent = new Student("S000001T", "U1000001A", "Aaron Tan", 1, Major.COMPUTER_SCIENCE);
        CareerStaff fakeCareerStaff = new CareerStaff("C000001S", "jtan001", "John Tan", "Career Advisory");

        switch (logininfo.getUserType()) {
            case STUDENT -> studentMenu(fakeStudent);
            case CAREERSTAFF -> careerStaffMenu(fakeCareerStaff);
            case COMPANYREP -> cRepGateway(logininfo);
            default -> System.out.println("Logic error");
        }
    }

    private void studentMenu(Student student) {
        // * Entry point Student
        // ? Replace with your own method call (be it static / instance)

        // StudentController studentController = new StudentController();
        // studentController.openMenu(student);
    }

    private void careerStaffMenu(CareerStaff careerStaff) {
        // * Entry point Career Staff
        // ? Replace with your own method call (be it static / instance)

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
