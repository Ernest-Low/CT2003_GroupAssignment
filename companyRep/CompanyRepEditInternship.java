package companyRep;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Scanner;

import config.Services;
import enums.InternshipLevel;
import enums.Major;
import model.CompanyRep;
import model.Internship;

public class CompanyRepEditInternship {

    DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral('-')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter();

    private final Services services;
    private final Scanner sc;

    private String title;
    private Major major;
    private InternshipLevel level;
    private int slots;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private String description;
    private Internship internship;
    private final CompanyRep companyRep;

    public CompanyRepEditInternship(Services services, Scanner sc, Internship internship, CompanyRep companyRep) {
        this.services = services;
        this.sc = sc;
        this.internship = internship;
        this.companyRep = companyRep;
    }

    private void EditInternshipInputs() {
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

    public void CRepEditInternshipController() {
        // Reset all to base
        title = "";
        major = null;
        level = null;
        slots = 0;
        openingDate = null;
        closingDate = null;
        description = "";

        EditInternshipInputs();

        // String ID, String title, String companyName, String companyID, Major major, InternshipLevel level, int counter, int slots, LocalDate openingDate, LocalDate closingDate, InternshipStatus status, String description
        Internship editedInternship = new Internship(internship.getID(), this.title, internship.getCompanyName(),
                internship.getCompanyID(), this.major, this.level, internship.getCounter(), this.slots, this.openingDate, this.closingDate,
                internship.getStatus(), this.description);
        services.internshipService.updateInternship(editedInternship, companyRep.getGUID());
        System.out.println("\nSuccessfully edited internship!");
        return;
    }

}
