package companyRep;

import java.util.Scanner;

import config.Services;
import dtos.InternshipFilter;
import model.CompanyRep;

public class CompanyRepViewInternship {

    private final Services services;
    private CompanyRep companyRep;
    private final InternshipFilter internshipFilter;
    private final Scanner sc;
    private boolean addFilter = true;

    public CompanyRepViewInternship(Services services, CompanyRep companyRep, Scanner sc, InternshipFilter internshipFilter) {
        this.services = services;
        this.companyRep = companyRep;
        this.sc = sc;
        this.internshipFilter = internshipFilter;
    }

    private void addOrRemove() {
        System.out.println("1: Add filter");
        System.out.println("2: Remove filter");
        System.out.println("9: Back");
    }

    private void filterMajor() {

    }

    // private Set<String> companyNames;
    // private Set<Major> majors;
    // private Set<InternshipLevel> levels;
    // private Set<InternshipStatus> statuses;
    // private LocalDate closingDateBefore;
    // private LocalDate closingDateAfter;
    // private LocalDate openingDateBefore;
    // private LocalDate openingDateAfter;

    private int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Display Internships");
        System.out.println("2: Filter by Majors");
        System.out.println("3: Filter by Levels");
        System.out.println("4: Filter by Statuses");
        System.out.println("5: Filter by Opening Date");
        System.out.println("6: Filter by Closing Date");
        System.out.println("9: Return");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public void CRepViewInternshipController() {
        System.out.println("Entering view internship controller");
        int choice = 0;
        choice = openMenu();
        while (choice != 9) {
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1 -> System.out.println("1");
                case 2 -> System.out.println("2");
                case 9 -> System.out.println("Returning..."); // Exit back to menu
                default -> System.out.println("Not a valid input. Try again.");
            }
        }
        return;

    }
}
