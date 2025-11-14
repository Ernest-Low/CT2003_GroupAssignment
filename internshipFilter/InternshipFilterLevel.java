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
import enums.InternshipLevel;

public class InternshipFilterLevel {

    private InternshipFilter internshipFilter;
    private Scanner sc;
    private boolean addFilter;

    public InternshipFilterLevel(InternshipFilter incomingFilter, Scanner sc) {
        this.internshipFilter = incomingFilter;
        this.sc = sc;
    }

    private void filterLevel() {

        String input = "";
        while (true) {
            Set<InternshipLevel> levels = new LinkedHashSet<>(
                    internshipFilter.getLevels() == null ? Collections.emptySet() : internshipFilter.getLevels());
            String mode = this.addFilter ? "add" : "remove";
            System.out.println("\n-- Level filter (" + (this.addFilter ? "ADDING" : "REMOVING") + ") --");

            if (levels.isEmpty()) {
                System.out.println("Current filter: <none>");
            } else {
                System.out.print("Current filter: ");
                System.out.println(levels.stream().map(Enum::name).collect(Collectors.joining(", ")));
            }
            InternshipLevel[] allLevels = InternshipLevel.values();
            Map<Integer, InternshipLevel> menuMap = new LinkedHashMap<>();
            int idx = 1;
            for (InternshipLevel l : allLevels) {
                boolean showInMenu = this.addFilter ? !levels.contains(l) : levels.contains(l);
                if (showInMenu) {
                    menuMap.put(idx++, l);
                }
            }
            if (menuMap.isEmpty()) {
                System.out.println(this.addFilter
                        ? "No remaining levels to add."
                        : "No levels in filter to remove.");
                System.out.println("Enter X to go back or press A to toggle add/remove mode.");
            } else {
                System.out.println("Select a level to " + mode + ":");
                menuMap.forEach((number, level) -> System.out.println(number + ": " + level.getDisplayName()));
                System.out.println("A: Toggle add/remove");
                System.out.println("X: Back");
            }
            System.out.print("Enter choice: ");
            try {
                input = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("\nInput was closed. Returning to previous menu.");
                return;
            }
            input = input.trim();
            if (input.equalsIgnoreCase("X")) {
                internshipFilter.setLevels(levels);
                return;
            }
            if (input.equalsIgnoreCase("A")) {
                this.addFilter = !this.addFilter;
                continue;
            }
            int selected;
            try {
                selected = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid selection. Enter a number, A to toggle mode, or X to go back.");
                continue;
            }
            InternshipLevel chosen = menuMap.get(selected);
            if (chosen == null) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            if (this.addFilter) {
                if (levels.add(chosen)) { // Adding to set
                    System.out.println(chosen.getDisplayName() + " added to filter.");
                } else {
                    System.out.println(chosen.getDisplayName() + " was already in the filter.");
                }
            } else {
                if (levels.remove(chosen)) { // Removing from set
                    System.out.println(chosen.getDisplayName() + " removed from filter.");
                } else {
                    System.out.println(chosen.getDisplayName() + " was not in the filter.");
                }
            }
            internshipFilter.setLevels(levels);
        }
    }

    public void InternshipFilterLevelController() {
        this.addFilter = InternshipFilterUtil.addOrRemove(this.addFilter, sc);
        filterLevel();
    }
}
