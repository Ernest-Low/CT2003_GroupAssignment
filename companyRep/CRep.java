package companyRep;

import java.util.Scanner;

import config.Services;
import model.CompanyRep;

public class CRep {

    private CompanyRep companyRep;
    private final Scanner sc;

    private final CRepPostInternship cRepPostInternship;
    private final Services services;

    public CRep(Services services, CompanyRep companyRep) {
        this.sc = new Scanner(System.in);
        this.services = services;
        this.cRepPostInternship = new CRepPostInternship(this.services, this.companyRep, sc);
        this.companyRep = companyRep;
    }

    private int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Edit Profile");
        System.out.println("2: Post new Internship Opportunity");
        System.out.println("3: View Internships posted");
        System.out.println("4: View internship applications");
        System.out.println("9: Logout");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public void CompanyRepController() {

        System.out.println("Welcome " + companyRep.getName());
        int choice = 0;
        while (choice != 9) {
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1 -> CRepEditProfile.CRepEditProfileController(sc); // Call edit profile method
                case 2 -> cRepPostInternship.CRepPostInternshipController(); // Call create new internship opportunity method
                case 3 -> System.out.println("3"); // Call view internship posted
                case 4 -> System.out.println("4"); // Call view internship applications for review
                case 9 -> System.out.println("Exiting..."); // Exit
                default -> System.out.println("Fail");
            }
        }

        sc.close();
    }
}
