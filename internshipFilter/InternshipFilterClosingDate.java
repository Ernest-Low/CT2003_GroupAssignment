package internshipFilter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatterBuilder;

import dtos.InternshipFilter;

public class InternshipFilterClosingDate {

    private final InternshipFilter internshipFilter;
    private final Scanner sc;

    private final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral('-')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter();

    public InternshipFilterClosingDate(InternshipFilter incomingFilter, Scanner sc) {
        this.internshipFilter = incomingFilter;
        this.sc = sc;
    }

    private void showMenu() {
        System.out.println("\nClosing date filter");
        System.out.println("1: Within next 7 days");
        System.out.println("2: Within next 14 days");
        System.out.println("3: Within next 1 month");
        System.out.println("4: Within next 3 months");
        System.out.println("5: Custom range (DD-MM-YYYYYY)");
        System.out.println("6: Clear closing date filter");
        System.out.println("X: Back");
        System.out.print("Enter choice: ");
    }

    private void applyPreset(int preset) {
        LocalDate today = LocalDate.now();
        LocalDate start = today;
        LocalDate end = switch (preset) {
            case 1 -> today.plusDays(7);
            case 2 -> today.plusDays(14);
            case 3 -> today.plusMonths(1);
            case 4 -> today.plusMonths(3);
            default -> null;
        };
        if (end == null)
            return;

        internshipFilter.setClosingDateAfter(start);
        internshipFilter.setClosingDateBefore(end);
        System.out.println("\nFilter set: " + start.format(dateFormat) + " - " + end.format(dateFormat));
    }

    private void handleCustomRange() {
        LocalDate start = null;
        LocalDate end = null;

        while (true) {
            System.out.print("\nEnter start date (DD-MM-YYYYYY) or blank for none: ");
            String raw = readLine();
            if (raw == null)
                return;
            raw = raw.trim();

            if (raw.isEmpty()) {
                start = null;
                break;
            }

            try {
                start = LocalDate.parse(raw, dateFormat);
                break;
            } catch (DateTimeParseException ex) {
                System.out.println("\nInvalid date format. Try again.");
            }
        }

        while (true) {
            System.out.print("\nEnter end date (DD-MM-YYYYYY) or blank for none: ");
            String raw = readLine();
            if (raw == null)
                return;
            raw = raw.trim();
            if (raw.isEmpty()) {
                end = null;
                break;
            }
            try {
                end = LocalDate.parse(raw, dateFormat);
            } catch (DateTimeParseException ex) {
                System.out.println("\nInvalid date format. Try again.");
                continue;
            }
            if (start != null && end.isBefore(start)) {
                System.out.println("\nEnd date must not be before start date. Enter again.");
                continue;
            }

            break;
        }

        internshipFilter.setClosingDateAfter(start);
        internshipFilter.setClosingDateBefore(end);

        String af = (start == null) ? "<none>" : start.format(dateFormat);
        String bf = (end == null) ? "<none>" : end.format(dateFormat);
        System.out.println("\nCustom filter set: after=" + af + " before=" + bf);
    }

    private void printCurrentFilter() {
        LocalDate after = internshipFilter.getClosingDateAfter();
        LocalDate before = internshipFilter.getClosingDateBefore();
        if (after == null && before == null) {
            System.out.println("\nCurrent filter: <none>");
        } else {
            String af = (after == null) ? "<none>" : after.format(dateFormat);
            String bf = (before == null) ? "<none>" : before.format(dateFormat);
            System.out.println("\nCurrent filter: after=" + af + " before=" + bf);
        }
    }

    private String readLine() {
        try {
            return sc.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("\nInput was closed. Returning to previous menu.");
            return null;
        }
    }

    public void InternshipFilterClosingDateController() {
        String input;
        while (true) {
            printCurrentFilter();
            showMenu();
            input = readLine();
            if (input == null)
                return;
            input = input.trim();
            if (input.equalsIgnoreCase("X"))
                return;

            switch (input) {
                case "1", "2", "3", "4" -> applyPreset(Integer.parseInt(input));
                case "5" -> handleCustomRange(); 
                case "6" -> {
                    internshipFilter.setClosingDateAfter(null);
                    internshipFilter.setClosingDateBefore(null);
                    System.out.println("\nClosing date filter cleared.");
                }
                default -> System.out.println("\nInvalid selection. Choose 1-6 or X to go back.");
            }
        }
    }
}