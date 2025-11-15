package companyRep;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import config.Services;
import dtos.InternshipFilter;
import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;
import enums.TableIndex;
import model.CompanyRep;
import model.Internship;

public class CompanyRepPostInternship {

    DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral('-')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter();

    private final Services services;
    private CompanyRep companyRep;
    private final Scanner sc;

    private String title = "";
    private Major major;
    private InternshipLevel level;
    private int slots = 0;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private String description = "";

    public CompanyRepPostInternship(Services services, CompanyRep companyRep, Scanner sc) {
        this.services = services;
        this.companyRep = companyRep;
        this.sc = sc;
    }

    private void PostInternshipInputs() {
        while (title.length() < 3 || title.length() > 50) {
            System.out.print("\nEnter title (Within 3-50 characters): ");
            title = sc.nextLine().trim();
            if (title.length() < 3 || title.length() > 50) {
                System.out.println("\nInvalid number of characters. Try again.");
            }
        }
        while (major == null) {
            System.out.println(
                    "\nMajors: Computer Engineering -- Data Science -- Computer Science -- Information Engineering -- Electrical Engineering -- Mechanical Engineering -- Civil Engineering -- Business Administration -- Economics -- Psychology -- Biology -- Chemistry -- Physics -- Mathematics -- English Literature -- History -- Political Science -- Sociology -- Education -- Fine Arts");
            System.out.print("\nEnter major: ");
            String input = sc.nextLine().trim();
            try {
                major = Major.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid major. Try again.");
            }
        }
        while (level == null) {
            System.out.print("\nEnter internship level (Basic, Intermediate, Advanced): ");
            String input = sc.nextLine().trim();
            try {
                level = InternshipLevel.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid internship level. Try again.");
            }
        }
        while (slots <= 0) {
            System.out.print("\nEnter number of slots available (1-10): ");
            try {
                int inputSlots = Integer.parseInt(sc.nextLine().trim());
                if (inputSlots > 0 && inputSlots <= 10) {
                    slots = inputSlots;
                } else {
                    System.out.println("\nNumber of slots must be between 1 and 10. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
            }
        }
        while (openingDate == null) {
            System.out.print("\nEnter opening date (D-M-YYYY): ");
            try {
                openingDate = LocalDate.parse(sc.nextLine().trim(), dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format. Try again.");
            }
        }
        while (closingDate == null) {
            System.out.print("\nEnter closing date (D-M-YYYY): ");
            try {
                LocalDate inputDate = LocalDate.parse(sc.nextLine().trim(), dateFormat);
                if (inputDate.isAfter(openingDate)) {
                    closingDate = inputDate;
                } else {
                    System.out.println("\nClosing date cannot be before opening date. Try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
        while (description.length() < 3 || description.length() > 150) {
            System.out.print("\nEnter Description (Within 3-150 characters): ");
            description = sc.nextLine().trim();
            if (description.length() < 3 || description.length() > 150) {
                System.out.println("\nInvalid number of characters. Try again.");
            }
        }
    }

    // Return true if limit reached
    private boolean verifyLimit() {
        InternshipFilter internshipFilter = new InternshipFilter();
        Set<String> companyNames = Set.of(this.companyRep.getCompanyName());
        internshipFilter.setCompanyNames(companyNames);
        List<Internship> internshipcount = services.internshipService.filterInternships(internshipFilter);
        List<Internship> nonRejected = internshipcount.stream()
                .filter(internship -> internship.getStatus() != InternshipStatus.REJECTED).toList();
        if (nonRejected.size() >= 5) {
            return true;
        } else {
            return false;
        }
    }

    public void CRepPostInternshipController() {

        if (verifyLimit()) {
            System.out.println("Non-rejected Internship limit of 5 reached. Returning...");
            return;
        }

        // Reset all to base
        title = "";
        major = null;
        level = null;
        slots = 0;
        openingDate = null;
        closingDate = null;
        description = "";

        PostInternshipInputs();

        String nextId = services.autoNumberService.generateNextId(TableIndex.INTERNSHIP);
        String companyName = this.companyRep.getCompanyName();
        String companyID = this.companyRep.getGUID();
        services.internshipService.createInternship(nextId, title,
                companyName,
                companyID, major, level, slots, openingDate,
                closingDate, description);
        System.out.println("\nSuccessfully created internship!");
        return;
    }

}
