package companyRep;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import config.Services;
import dtos.InternshipFilter;
import enums.InternshipStatus;
import internshipFilter.InternshipFilterMain;
import model.CompanyRep;
import model.Internship;

public class CompanyRepViewInternship {

    private final Services services;
    private final InternshipFilter internshipFilter;
    private final InternshipFilterMain internshipFilterMain;
    private final Scanner sc;

    private final CompanyRep companyRep;

    private Map<Integer, Internship> internshipAppMap; // Access map

    public CompanyRepViewInternship(Services services, Scanner sc,
            InternshipFilter internshipFilter, InternshipFilterMain internshipFilterMain, CompanyRep companyRep) {
        this.services = services;
        this.sc = sc;
        this.companyRep = companyRep;

        this.internshipFilter = internshipFilter;
        this.internshipFilterMain = internshipFilterMain;
        this.internshipAppMap = new LinkedHashMap<>();

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

        if (results.size() == 0) {
            System.out.println("You have no internships currently.");
            return;
        }

        String format = "%-6s %-33s %-18s %-12s %-12s %-12s %-12s %-60s";

        System.out.println("\nYour Internships:");
        String header = String.format(
                format,
                "Index", "Title", "Major", "Level", "Opening", "Closing", "Status", "Description");
        StringBuilder sb = new StringBuilder();
        sb.append(header).append(System.lineSeparator());
        int idx = 1;
        for (Internship internship : results) {
            sb.append(String.format(
                    format,
                    idx,
                    internship.getTitle(),
                    internship.getMajor() != null ? internship.getMajor().getDisplayName() : "",
                    internship.getLevel() != null ? internship.getLevel().getDisplayName() : "",
                    internship.getOpeningDate(),
                    internship.getClosingDate(),
                    internship.getStatus() != null ? internship.getStatus().name() : "",
                    internship.getDescription() != null ? internship.getDescription() : ""))
                    .append(System.lineSeparator());
            this.internshipAppMap.put(idx, internship);
            idx++;
        }

        String table = sb.toString();
        System.out.println(table);
        System.out.println("Select an internship index to view/edit, or press X to go back.");
        viewInternshipDetails();
    }

    public void viewInternshipDetails() {
        String input = "";

        while (true) {
            System.out.print("\nEnter choice: ");

            try {
                input = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("\nInput was closed. Try again.");
            }
            if (input.equalsIgnoreCase("X")) {
                return;
            }
            int inputInt;
            try {
                inputInt = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid selection. Enter a number or X to go back.");
                continue;
            }
            Internship selected = internshipAppMap.get(inputInt);
            if (selected == null) {
                System.out.println("\nSelection out of range. Try again.");
                continue;
            }

            while (true) {
                System.out.println("\nSelected: " + selected.getTitle());

                String format = "%-33s %-18s %-12s %-18s %-18s %-12s %-12s %-12s %-60s";
                String header = String.format(
                        format,
                        "Title", "Major", "Level", "Accepted Count", "Student Limit", "Opening", "Closing", "Status",
                        "Description");
                StringBuilder sb = new StringBuilder();
                sb.append(header).append(System.lineSeparator());
                sb.append(String.format(
                        format,
                        selected.getTitle(),
                        selected.getMajor() != null ? selected.getMajor().getDisplayName() : "",
                        selected.getLevel() != null ? selected.getLevel().getDisplayName() : "",
                        selected.getCounter(),
                        selected.getSlots(),
                        selected.getOpeningDate(),
                        selected.getClosingDate(),
                        selected.getStatus() != null ? selected.getStatus().name() : "",
                        selected.getDescription() != null ? selected.getDescription() : ""))
                        .append(System.lineSeparator());
                String table = sb.toString();
                System.out.println(table);
                // Print all here

                if (selected.getStatus() != InternshipStatus.PENDING) {
                    System.out.println("Can't edit internship as it's no longer pending.");
                    return;
                }
                System.out.println("1: Edit");
                System.out.println("2: Delete");
                System.out.println("X: Return");
                String input2 = "";
                System.out.print("\nEnter choice: ");
                try {
                    input2 = sc.nextLine();
                } catch (NoSuchElementException e) {
                    System.out.println("\nInput was closed. Try again.");
                }
                if (input2.equalsIgnoreCase("X")) {
                    return;
                }
                switch (input2) {
                    case "1":
                        CompanyRepEditInternship companyRepEditInternship = new CompanyRepEditInternship(services, sc,
                                selected, companyRep);
                        companyRepEditInternship.CRepEditInternshipController();
                        break;
                    case "2":
                        services.internshipService.deleteInternship(selected.getID(), companyRep.getGUID());
                        System.out.println("Successfully Deleted.");
                        return;
                    case "X":
                        break;
                    default:
                        System.out.println("\nNot a valid input. Try again.");
                }
            }
        }

    }

    // private void handleInternship(Internship selected) {
    //     while (true) {
    //         System.out.println("\nSelected: " + selected.getTitle());

    //         String format = "%-33s %-18s %-12s %-12s %-12s %-12s %-12s %-12s %-60s";
    //         String header = String.format(
    //                 format,
    //                 "Title", "Major", "Level", "Accepted Students", "Student Limit", "Opening", "Closing", "Status",
    //                 "Description");
    //         StringBuilder sb = new StringBuilder();
    //         sb.append(header).append(System.lineSeparator());
    //         sb.append(String.format(
    //                 format,
    //                 selected.getTitle(),
    //                 selected.getMajor() != null ? selected.getMajor().getDisplayName() : "",
    //                 selected.getLevel() != null ? selected.getLevel().getDisplayName() : "",
    //                 selected.getCounter(),
    //                 selected.getSlots(),
    //                 selected.getOpeningDate(),
    //                 selected.getClosingDate(),
    //                 selected.getStatus() != null ? selected.getStatus().name() : "",
    //                 selected.getDescription() != null ? selected.getDescription() : ""))
    //                 .append(System.lineSeparator());
    //         String table = sb.toString();
    //         System.out.println(table);
    //         // Print all here

    //         if (selected.getStatus() != InternshipStatus.PENDING) {
    //             System.out.println("Can't edit internship as it's no longer pending.");
    //             return;
    //         }
    //         System.out.println("1: Edit");
    //         System.out.println("X: Return");
    //         String input = "";
    //         System.out.print("\nEnter choice: ");
    //         try {
    //             input = sc.nextLine();
    //         } catch (NoSuchElementException e) {
    //             System.out.println("\nInput was closed. Try again.");
    //         }
    //         if (input.equalsIgnoreCase("X")) {
    //             return;
    //         }
    //         switch (input) {
    //             case "1":
    //                 CompanyRepEditInternship companyRepEditInternship = new CompanyRepEditInternship(services, sc,
    //                         selected, companyRep);
    //                 companyRepEditInternship.CRepEditInternshipController();
    //                 return;
    //             case "X":
    //                 return;
    //             default:
    //                 System.out.println("\nNot a valid input. Try again.");
    //         }
    //     }
    // }

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
