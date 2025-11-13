package internshipFilter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import dtos.InternshipFilter;
import enums.Major;

public class InternshipFilterMajor {

    private static InternshipFilter internshipFilter;
    private static Scanner sc;
    private static boolean addFilter;

    private static void filterMajor() {
        InternshipFilterUtil.addOrRemove(addFilter); // Toggle addFilter
        String input = "";
        while (true) {
            Set<Major> majors = new LinkedHashSet<>(
                    internshipFilter.getMajors() == null ? Collections.emptySet() : internshipFilter.getMajors());
            String mode = addFilter ? "add" : "remove";
            System.out.println("\n-- Major filter (" + (addFilter ? "ADDING" : "REMOVING") + ") --");

            if (majors.isEmpty()) {
                System.out.println("Current filter: <none>");
            } else {
                System.out.print("Current filter: ");
                System.out.println(majors.stream().map(Enum::name).collect(Collectors.joining(", ")));
            }
            Major[] allMajors = Major.values();
            Map<Integer, Major> menuMap = new LinkedHashMap<>();
            int idx = 1;
            for (Major m : allMajors) {
                boolean showInMenu = addFilter ? !majors.contains(m) : majors.contains(m);
                if (showInMenu) {
                    menuMap.put(idx++, m);
                }
            }
            if (menuMap.isEmpty()) {
                System.out.println(addFilter
                        ? "No remaining majors to add."
                        : "No majors in filter to remove.");
                System.out.println("Enter X to go back or press A to toggle add/remove mode.");
            } else {
                System.out.println("Select a major to " + mode + ":");
                menuMap.forEach((number, major) -> System.out.println(number + ": " + major.getDisplayName()));
                System.out.println("A: Toggle add/remove");
                System.out.println("X: Back");
            }
            System.out.print("Enter choice: ");
            try {
                input = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Input was closed. Returning to previous menu.");
                sc.close();
                return;
            }
            if (input == null) {
                System.out.println("Null input received. Returning.");
                sc.close();
                return;
            }
            input = input.trim();
            if (input.equalsIgnoreCase("X")) {
                internshipFilter.setMajors(majors);
                sc.close();
                return;
            }
            if (input.equalsIgnoreCase("A")) {
                InternshipFilterUtil.addOrRemove(addFilter);
            }
            int selected;
            try {
                selected = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid selection. Enter a number, A to toggle mode, or X to go back.");
                continue;
            }
            Major chosen = menuMap.get(selected);
            if (chosen == null) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            if (addFilter) {
                if (majors.add(chosen)) { // Adding to set
                    System.out.println(chosen.getDisplayName() + " added to filter.");
                } else {
                    System.out.println(chosen.getDisplayName() + " was already in the filter.");
                }
            } else {
                if (majors.remove(chosen)) { // Removing from set
                    System.out.println(chosen.getDisplayName() + " removed from filter.");
                } else {
                    System.out.println(chosen.getDisplayName() + " was not in the filter.");
                }
            }
            internshipFilter.setMajors(majors);
        }

    }

    public static void InternshipFilterMajorController(InternshipFilter incomingFilter) {
        internshipFilter = incomingFilter;
        filterMajor();
    }
}
