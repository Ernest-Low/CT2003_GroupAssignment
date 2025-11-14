package companyRep;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import config.Services;
import dtos.InternshipAppFilter;
import enums.InternshipApplicationStatus;
import model.CompanyRep;
import model.Internship;
import model.InternshipApp;
import model.Student;

public class CompanyRepViewInternshipApp {

    private final Scanner sc;
    private final Services services;
    private final CompanyRep companyRep;
    private final InternshipAppFilter internshipAppFilter;
    private List<Internship> internships;
    private List<InternshipApp> internshipApps;
    private List<Student> students;

    private Map<Integer, String> menuMap; // Display map
    private Map<Integer, InternshipApp> internshipAppMap ; // Access map

    public CompanyRepViewInternshipApp(Services services, CompanyRep companyRep, Scanner sc) {
        this.services = services;
        this.companyRep = companyRep;
        this.sc = sc;

        this.internships = List.of();
        this.internshipApps = List.of();
        this.students = List.of();
        this.internshipAppFilter = new InternshipAppFilter();
        this.menuMap = new LinkedHashMap<>();
        this.internshipAppMap = new LinkedHashMap<>();

    }

    // Clear and recreate map, then display it
    private void createDisplayMap() {
        this.menuMap.clear();
        this.internshipAppMap.clear();

        int idx = 1;
        for (InternshipApp internshipApp : internshipApps) { // For every app, make a nice string
            Internship internship = internships.stream()
                    .filter(item -> item.getID() == internshipApp.getInternshipID()).findFirst().orElse(null);
            Student student = students.stream().filter(item -> item.getId() == internshipApp.getStudentID())
                    .findFirst().orElse(null);
            String description = internship.getTitle() + " -- " + student.getName() + "(" + student.getYearOfStudy() // Internship name -- Student Name (Year of Study) - Major
                    + ")" + " - " + student.getMajor();
            this.menuMap.put(idx, description);
            this.internshipAppMap.put(idx, internshipApp);
            idx++;
        }
        System.out.println("\nSelect an internship application");
        menuMap.forEach((number, description) -> System.out.println(number + ": " + description));
        System.out.println("X: Back\n");
    }

    private void findInternships() {
        this.internships = this.services.internshipService.getInternships(companyRep.getGUID(), 3); // Internship companyID is GUID of Company Rep
    }

    private void findInternshipApps() {
        for (Internship internship : internships) {
            Set<String> internshipIDs = Set.of(internship.getID());
            Set<InternshipApplicationStatus> internshipAppStatuses = Set.of(InternshipApplicationStatus.PENDING);
            internshipAppFilter.setInternshipApplicationStatuses(internshipAppStatuses);
            internshipAppFilter.setInternshipIDs(internshipIDs);
            List<InternshipApp> newInternshipApps = this.services.internshipAppService
                    .filterInternshipApps(internshipAppFilter);
            for (InternshipApp internshipApp : newInternshipApps) {
                this.internshipApps.add(internshipApp);
            }
        }
    }

    private void findStudents() { // StudentIDs set
        Set<String> studentIDs = Set.of();
        for (InternshipApp internshipApp : this.internshipApps) {
            studentIDs.add(internshipApp.getStudentID());
        }
        // Use the studentIDs set to get corresponding students
        for (String studentID : studentIDs) {
            Student student = services.studentService.getStudentByID(studentID);
            students.add(student);
        }
    }

    private void handleInternshipApp(InternshipApp selectedApp, int selected) {
        while (true) {
            System.out.println("\nSelected: " + menuMap.get(selected));
            System.out.println("1: Accept");
            System.out.println("2: Reject");
            System.out.println("X: Return");
            String input = "";
            System.out.print("\nEnter choice: ");
            try {
                input = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("\nInput was closed. Try again.");
            }
            if (input.equalsIgnoreCase("X")) {
                return;
            }
            switch (input) {
                case "1":
                    selectedApp.setStatus(InternshipApplicationStatus.SUCCESSFUL);
                    services.internshipAppService.updateInternshipApp(selectedApp);
                    System.out.println("\nAccepted the application, awaiting student confirmation.");
                    return;
                case "2":
                    selectedApp.setStatus(InternshipApplicationStatus.UNSUCCESSFUL);
                    services.internshipAppService.updateInternshipApp(selectedApp);
                    System.out.println("\nRejected the application.");
                    return;
                default:
                    System.out.println("\nNot a valid input. Try again.");
            }
        }
    }

    // * Thoughts: If there was time, would've liked to give a function to check fully accepted candidates (in line with real world)
    // * Like maybe it'll be shown when closing the internship opportunity - then it gives the list of candidates that are confirmed
    public void viewInternshipAppController() {

        // ? The calls to CSVs, try to avoid recalling it all the time (as if it's a DB)
        findInternships(); // Get Internships belonging to company rep

        if (internshipApps == null || internshipApps.isEmpty()) {
            System.out.println("\nNo current internship applications awaiting action. Returning.");
            return;
        }
        findInternshipApps(); // Use Internships to find InternshipApps, populate internshipApps
        findStudents(); // Now to find the students using InternshipApps

        while (true) {
            createDisplayMap();
            String input = "";
            System.out.print("\nEnter choice: ");
            try {
                input = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("\nInput was closed. Try again.");
            }
            if (input.equalsIgnoreCase("X")) {
                return;
            }
            int selected;
            try {
                selected = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid selection. Enter a number or X to go back.");
                continue;
            }
            InternshipApp selectedApp = internshipAppMap.get(selected);
            if (selectedApp == null) {
                System.out.println("\nSelection out of range. Try again.");
                continue;
            }
            handleInternshipApp(selectedApp, selected);
        }

    }

}
