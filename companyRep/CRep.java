package companyRep;

import java.util.Scanner;

import model.CompanyRep;

public class CRep {

    private CompanyRep companyRep;
    Scanner sc;

    public CRep(CompanyRep companyRep) {
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
        sc = new Scanner(System.in);
        System.out.println("Welcome " + companyRep.getName());
        int choice = 0;
        while (choice != 9) {
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1 -> System.out.println("1"); // Call edit profile method (edit password, or more)
                case 2 -> System.out.println("2"); // Call create new internship opportunity method
                case 3 -> System.out.println("3"); // Call view internship posted
                case 4 -> System.out.println("4"); // Call view internship applications for review
                default -> System.out.println("Fail");
            }
        }

        sc.close();
    }
}
