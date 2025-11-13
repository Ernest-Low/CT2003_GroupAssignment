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
import enums.TableIndex;
import model.CompanyRep;

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
            System.out.print("Enter title (Within 3-50 characters): ");
            title = sc.nextLine().trim();
            if (title.length() > 0 && title.length() < 50) {
                System.out.println("Invalid number of characters. Try again.");
            }
        }
        while (major == null) {
            System.out.println(
                    "Majors: Computer Engineering -- Data Science -- Computer Science -- Information Engineering -- Electrical Engineering -- Mechanical Engineering -- Civil Engineering -- Business Administration -- Economics -- Psychology -- Biology -- Chemistry -- Physics -- Mathematics -- English Literature -- History -- Political Science -- Sociology -- Education -- Fine Arts");
            System.out.print("Enter major: ");
            String input = sc.nextLine().trim();
            try {
                major = Major.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid major. Try again.");
            }
        }
        while (level == null) {
            System.out.print("Enter internship level (Basic, Intermediate, Advanced): ");
            String input = sc.nextLine().trim();
            try {
                level = InternshipLevel.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid level. Try again.");
            }
        }
        while (openingDate == null) {
            System.out.print("Enter opening date (D-M-YYYY): ");
            try {
                openingDate = LocalDate.parse(sc.nextLine().trim(), dateFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
        while (closingDate == null) {
            System.out.print("Enter closing date (D-M-YYYY): ");
            try {
                LocalDate inputDate = LocalDate.parse(sc.nextLine().trim(), dateFormat);
                if (inputDate.isAfter(openingDate)) {
                    closingDate = inputDate;
                } else {
                    System.out.println("Closing date cannot be before opening date. Try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
        while (description.length() < 3 || description.length() > 150) {
            System.out.print("Enter Description (Within 3-150 characters): ");
            description = sc.nextLine().trim();
            if (description.length() > 0 && description.length() < 150) {
                System.out.println("Invalid number of characters. Try again.");
            }
        }
    }

    public void CRepPostInternshipController() {

        System.out.println("Posting new Internship Opportunity...");
        PostInternshipInputs();

        String nextId = services.autoNumberService.generateNextId(TableIndex.INTERNSHIP);
        String companyName = this.companyRep.getCompanyName();
        String companyID = this.companyRep.getId();
        services.internshipService.createInternship(nextId, title,
                companyName,
                companyID, major, level, openingDate,
                closingDate, description);
        System.out.println("Successfully created internship!");
        return;
    }

}
