package companyRep;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import CSVMethods.CSVBeutify2;
import config.Services;
import dtos.InternshipFilter;
import internshipFilter.InternshipFilterMain;
import model.Internship;

public class CompanyRepViewInternship {

    private final Services services;
    private final InternshipFilter internshipFilter;
    private final InternshipFilterMain internshipFilterMain;
    private final Scanner sc;
    // private boolean addFilter = true;

    public CompanyRepViewInternship(Services services, Scanner sc,
            InternshipFilter internshipFilter, InternshipFilterMain internshipFilterMain) {
        this.services = services;
        this.sc = sc;
        this.internshipFilter = internshipFilter;
        this.internshipFilterMain = internshipFilterMain;
    }

    private String openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Display Internships");
        System.out.println("2: Edit Filters");
        System.out.println("X: Return");
        System.out.print("Enter choice: ");
        String input = sc.nextLine();

        return input;
    }

    private void viewFilteredInternship() {
        List<Internship> results = services.internshipService.filterInternships(internshipFilter);

        // * Will make this better later
        CSVBeutify2.BeutifyNewFilter("Your Internships", results, "ID", "Title", "Company Name", "CompanyID", "Major",
                "Level", "Counter", "Opening Date", "Closing Date", "Status", "Description");

        // String ID, String title, String companyName, String companyID, Major major, InternshipLevel level,
        //     int counter,
        //     LocalDate openingDate, LocalDate closingDate, InternshipStatus status, String description

    }

    public void CRepViewInternshipController() {
        System.out.println("Entering view internship controller");
        String choice = "";
        choice = openMenu();
        while (choice.equalsIgnoreCase("x")) {
            try {
                choice = openMenu();

                switch (choice) {
                    case "1" -> viewFilteredInternship();
                    case "2" -> internshipFilterMain.InternshipFilterController();
                    case "x", "X" -> System.out.println("Returning..."); // Exit back to menu
                    default -> System.out.println("Not a valid input. Try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Input was closed. Try again.");
            }
        }
        return;

    }
}
