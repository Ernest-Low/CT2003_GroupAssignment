package companyRep;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

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

    private void openMenu() {
        System.out.println("\nPlease select an action");
        System.out.println("1: Display Internships");
        System.out.println("2: Edit Filters");
        System.out.println("X: Return");
        System.out.print("\nEnter choice: ");
    }

    private void viewFilteredInternship() {
        List<Internship> results = services.internshipService.filterInternships(internshipFilter);

        String format = "%-33s %-18s %-12s %-12s %-12s %-12s %-60s";

        System.out.println("\nYour Internships:");
        String header = String.format(
                format,
                "Title", "Major", "Level", "Opening", "Closing", "Status", "Description");
        StringBuilder sb = new StringBuilder();
        sb.append(header).append(System.lineSeparator());

        for (Internship i : results) {
            sb.append(String.format(
                    format,
                    i.getTitle(),
                    i.getMajor().getDisplayName(),
                    i.getLevel().getDisplayName(),
                    i.getOpeningDate(),
                    i.getClosingDate(),
                    i.getStatus().name(),
                    i.getDescription())).append(System.lineSeparator());
        }

        String table = sb.toString();
        System.out.println(table);
    }

    public void CRepViewInternshipController() {
        String choice = "";
        while (true) {
            try {
                openMenu();
                choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        viewFilteredInternship();
                        break;
                    case "2":
                        internshipFilterMain.InternshipFilterController();
                        break;
                    case "x", "X":
                        return; // Exit back to menu
                    default:
                        System.out.println("\nNot a valid input. Try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\nInput was closed. Try again.");
            }
        }
    }
}
