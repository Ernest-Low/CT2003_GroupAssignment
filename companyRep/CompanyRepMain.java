package companyRep;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import config.Services;
import dtos.InternshipFilter;
import login.UpdatePasswordController;
import model.CompanyRep;

public class CompanyRepMain {

    private CompanyRep companyRep;
    private final Scanner sc;

    private final CompanyRepPostInternship cRepPostInternship;
    // private final CompanyRepEditProfile cRepEditProfile;
    private final CompanyRepViewInternship cRepViewInternship;
    private final Services services;
    private final InternshipFilter internshipFilter;
    private UpdatePasswordController updatePasswordController;

    public CompanyRepMain(Services services, CompanyRep companyRep) {
        this.sc = new Scanner(System.in);
        this.companyRep = companyRep;
        this.services = services;
        this.internshipFilter = new InternshipFilter();
        Set<String> companyNames = new HashSet<>(Arrays.asList(companyRep.getCompanyName()));
        this.internshipFilter.setCompanyNames(companyNames);
        this.cRepPostInternship = new CompanyRepPostInternship(this.services, this.companyRep, sc);
        // this.cRepEditProfile = new CompanyRepEditProfile(this.services, this.companyRep, sc);
        this.cRepViewInternship = new CompanyRepViewInternship(services, companyRep, sc, internshipFilter);
    }

    private int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Edit Password");
        System.out.println("2: Post new internship");
        System.out.println("3: View posted internships");
        System.out.println("4: View internship applications");
        System.out.println("9: Logout");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public void CompanyRepController() {
        updatePasswordController = new UpdatePasswordController();
        
        System.out.println("Welcome " + companyRep.getName());
        int choice = 0;
        while (choice != 9) {
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1 -> updatePasswordController.updatePassword(companyRep.getId()); // Call edit profile method
                case 2 -> cRepPostInternship.CRepPostInternshipController(); // Call create new internship opportunity method
                case 3 -> cRepViewInternship.CRepViewInternshipController(); // Call view internship posted
                case 4 -> System.out.println("4"); // Call view internship applications for review
                case 9 -> System.out.println("Exiting..."); // Exit
                default -> System.out.println("Not a valid input. Try again.");
            }
        }

        sc.close();
    }
}
