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
import enums.InternshipStatus;

public class InternshipFilterStatus {

    private InternshipFilter internshipFilter;
    private Scanner sc;
    private boolean addFilter;

    public InternshipFilterStatus(InternshipFilter incomingFilter, Scanner sc) {
        this.internshipFilter = incomingFilter;
        this.sc = sc;
    }

    private void filterStatus() {

        String input = "";
        while (true) {
            Set<InternshipStatus> statuses = new LinkedHashSet<>(
                    internshipFilter.getStatuses() == null ? Collections.emptySet() : internshipFilter.getStatuses());
            String mode = this.addFilter ? "add" : "remove";
            System.out.println("\n-- Status filter (" + (this.addFilter ? "ADDING" : "REMOVING") + ") --");

            if (statuses.isEmpty()) {
                System.out.println("Current filter: <none>");
            } else {
                System.out.print("Current filter: ");
                System.out.println(statuses.stream().map(Enum::name).collect(Collectors.joining(", ")));
            }
            InternshipStatus[] allStatuses = InternshipStatus.values();
            Map<Integer, InternshipStatus> menuMap = new LinkedHashMap<>();
            int idx = 1;
            for (InternshipStatus s : allStatuses) {
                boolean showInMenu = this.addFilter ? !statuses.contains(s) : statuses.contains(s);
                if (showInMenu) {
                    menuMap.put(idx++, s);
                }
            }
            if (menuMap.isEmpty()) {
                System.out.println(this.addFilter
                        ? "No remaining statuses to add."
                        : "No statuses in filter to remove.");
                System.out.println("Enter X to go back or press A to toggle add/remove mode.");
            } else {
                System.out.println("Select a status to " + mode + ":");
                menuMap.forEach((number, status) -> System.out.println(number + ": " + status.name()));
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
                internshipFilter.setStatuses(statuses);
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
            InternshipStatus chosen = menuMap.get(selected);
            if (chosen == null) {
                System.out.println("Selection out of range. Try again.");
                continue;
            }

            if (this.addFilter) {
                if (statuses.add(chosen)) { // Adding to set
                    System.out.println(chosen.name() + " added to filter.");
                } else {
                    System.out.println(chosen.name() + " was already in the filter.");
                }
            } else {
                if (statuses.remove(chosen)) { // Removing from set
                    System.out.println(chosen.name() + " removed from filter.");
                } else {
                    System.out.println(chosen.name() + " was not in the filter.");
                }
            }
            internshipFilter.setStatuses(statuses);
        }
    }

    public void InternshipFilterStatusController() {
        this.addFilter = InternshipFilterUtil.addOrRemove(this.addFilter, sc);
        filterStatus();
    }
}
