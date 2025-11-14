package student;

import java.io.IOException;
import java.util.*;

import CSVMethods.CSVBeutify;
import dtos.InternshipFilter;
import model.*;
import internship_rs.*;
import internshipFilter.*;

import enums.*;


public class ExploreInternshipService {

    // Initialization
    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: View Internship Details");
        System.out.println("2: Edit Filters");
        System.out.println("9: Return");
        System.out.println();
        System.out.print("Input: ");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    private static void openFilterMenu(InternshipFilter filter, Student student) {
        while (true) {
            System.out.println("\n--- Edit Filters ---");
            System.out.println("1: Filter by Level");
            System.out.println("2: Filter by Closing Date");
            System.out.println("9: Back to Internships");
            System.out.print("Enter choice: ");
            String choice = sc.next();
            sc.nextLine(); // consume newline

            switch (choice) {
                case "1":
                    if (student.getYearOfStudy() < 3) {
                        System.out.println("Filter by level is not available for Y1/2 Students.");
                        break;
                    }
                    InternshipFilterLevel levelFilter = new InternshipFilterLevel(filter, sc);
                    levelFilter.InternshipFilterLevelController();
                    break;
                case "2":
                    InternshipFilterClosingDate closingDateFilter = new InternshipFilterClosingDate(filter, sc);
                    closingDateFilter.InternshipFilterClosingDateController();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void exploreInternship(Student student) {

        // initialize internship application service
        InternshipApplicationService internshipApp = new InternshipApplicationService();
        List<Internship> internships = new ArrayList<>();

        // Initialize filter and set default level based on student's year of study
        InternshipFilter internshipFilter = new InternshipFilter();
        Set<InternshipLevel> defaultLevels = new HashSet<>();
        defaultLevels.add(InternshipLevel.BASIC);
        if (student.getYearOfStudy() > 2) {
            defaultLevels.add(InternshipLevel.INTERMEDIATE);
            defaultLevels.add(InternshipLevel.ADVANCED);
        }
        internshipFilter.setLevels(defaultLevels);


        // Prompt user for input (apply/exit)
        while (true) {
            // retrieve available internships for student
            try {
                internships = internshipApp.getFilteredInternships(student, internshipFilter);
            } catch (IOException e) {
                System.out.println(e);
                return;
            }

            // check if there are records
            if (internships == null || internships.isEmpty()) {
                System.out.println("No internship available for your major and current filters.");
            } else {
                // Print out internships using CSVBeutify class
                CSVBeutify.BeutifyNewFilter("AVAILABLE INTERNSHIP", internships, "title", "title", "companyName", "major", "level", "closingDate");
                
            }

            System.out.println();
            int selection = openMenu();

            switch (selection) {
                // View Internship Detais
                case 1:
                    if (internships.isEmpty()) {
                        System.out.println("No internships to view.");
                        break;
                    }
                    System.out.print("Select index to view details: ");
                    int choice = sc.nextInt();
                    if (choice > 0 && choice <= internships.size()) {
                        // Get details
                        ViewInternshipDetails.viewDetails(student, internships.get(choice - 1));
                    }
                    else {
                        System.out.println("Invalid index");
                    }
                    break;

                // Edit Filters
                case 2:
                    openFilterMenu(internshipFilter, student);
                    break;
                case 9:
                    System.out.println("Going back to main menu..");
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}