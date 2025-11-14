package companyRep;

import java.util.NoSuchElementException;
import java.util.Scanner;

import config.Services;
import dtos.InternshipFilter;
import internshipFilter.InternshipFilterMain;
import login.UpdatePasswordController;
import model.CompanyRep;

public class CompanyRepMain {

    private CompanyRep companyRep;
    private final Scanner sc;

    private final CompanyRepPostInternship companyRepPostInternship;
    // private final CompanyRepEditProfile cRepEditProfile;
    private final CompanyRepViewInternship companyRepViewInternship;
    private final CompanyRepViewInternshipApp companyRepViewInternshipApp;
    private final Services services;
    private final InternshipFilter internshipFilter;
    private UpdatePasswordController updatePasswordController;

    public CompanyRepMain(Services services, CompanyRep companyRep) {
        this.sc = new Scanner(System.in);
        this.companyRep = companyRep;
        this.services = services;

        InternshipFilterMain internshipFilterMain = new InternshipFilterMain(companyRep);
        this.internshipFilter = internshipFilterMain.getInternshipFilter();
        this.companyRepPostInternship = new CompanyRepPostInternship(this.services, this.companyRep, sc);
        // this.cRepEditProfile = new CompanyRepEditProfile(this.services, this.companyRep, sc);
        this.companyRepViewInternship = new CompanyRepViewInternship(services, sc, internshipFilter, internshipFilterMain);
        this.companyRepViewInternshipApp = new CompanyRepViewInternshipApp(services, companyRep, sc);
    }

    private String openMenu() {
        System.out.println("\nPlease select an action");
        System.out.println("1: Edit Password");
        System.out.println("2: Post new internship");
        System.out.println("3: View posted internships");
        System.out.println("4: View internship applications");
        System.out.println("X: Logout");
        System.out.print("\nEnter choice: ");
        String input = sc.nextLine().toLowerCase();

        return input;
    }

    public void CompanyRepController() {
        updatePasswordController = new UpdatePasswordController();
        // System.out.println("\nWelcome " + companyRep.getName());
        String choice = "";
        while (!choice.equalsIgnoreCase("X")) {
            try {
                choice = openMenu();
                switch (choice) {
                    case "1" -> updatePasswordController.updatePassword(companyRep.getId()); // Call edit profile method
                    case "2" -> companyRepPostInternship.CRepPostInternshipController(); // Call create new internship opportunity method
                    case "3" -> companyRepViewInternship.CRepViewInternshipController(); // Call view internship posted
                    case "4" -> companyRepViewInternshipApp.viewInternshipAppController(); // Call view internship applications for review
                    case "x" -> System.out.println("\nExiting..."); // Exit
                    default -> System.out.println("\nNot a valid input. Try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\nInput was closed. Try again.");
            }
        }

        sc.close();
    }
}
