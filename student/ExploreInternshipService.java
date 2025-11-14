package student;

import java.io.IOException;
import java.util.*;

import CSVMethods.CSVBeutify;
import model.*;
import internship_rs.*;

import enums.*;


public class ExploreInternshipService {

    // Initialization
    private static Scanner sc = new Scanner(System.in);
    private static List<InternshipLevel> levels = List.of(
        InternshipLevel.BASIC,
        InternshipLevel.INTERMEDIATE,
        InternshipLevel.ADVANCED
    );

    private static int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: View Internship Details");
        System.out.println("2: Filter Level");
        System.out.println("9: Return");
        System.out.println();
        System.out.print("Input: ");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    // Help to initialize based on student YOS and reseting of filter as well.
    private static Set<InternshipLevel> resetLevelFilter(Student student, Set<InternshipLevel> levelSet) {
        // Add filter based on student yearOfStudy
        levelSet.clear();
        levelSet.add(levels.get(0));
        if (student.getYearOfStudy() > 2) {
            levelSet.add(levels.get(1));
            levelSet.add(levels.get(2));
        }
        return levelSet;
    }

    public static void exploreInternship(Student student) {

        // initialize internship application service
        InternshipApplicationService internshipApp = new InternshipApplicationService();
        List<Internship> internships = new ArrayList<>();

        // Add filter based on student yearOfStudy
        Set<InternshipLevel> levelSet = new HashSet<>();
        levelSet = resetLevelFilter(student, levelSet);

        // retrieve available internships for student
        try {
            internships = internshipApp.getFilteredInternships(student, levelSet);
        } catch (IOException e) {
            System.out.println(e);
        }

        // check if there are records
        if (internships == null || internships.isEmpty()) {
            System.out.println("No internship available for your major and year level at the moment.");
            return;
        }

        // Print out internships using CSVBeutify class
        CSVBeutify.BeutifyNewFilter("AVAILABLE INTERNSHIP", internships, "title", "companyName", "major", "level", "closingDate");

        // Prompt user for input (apply/exit)
        while (true) {

            System.out.println();
            int selection = openMenu();

            switch (selection) {
                // View Internship Detais
                case 1:
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

                // Filter base on student preference (ONLY FOR Y3/4 STUDENT)
                case 2:
                    if (student.getYearOfStudy() < 3) {
                        System.out.println("Filter not available for Y1/2 Students.");
                        break;
                    }
                    else {
                        while (true) {

                            try {
                                System.out.println();
                                System.out.print("Filter by 1.BASIC  |  2.INTERMEDIATE  |  3.ADVANCED  |  4.reset  |  0.Return  |  Input: ");
                                int input = sc.nextInt();
                                if (input == 0) break; // Go back
                                else if (input == 4) {
                                    levelSet = resetLevelFilter(student, levelSet);
                                }
                                else if (input < 1 || input > levels.size()) {
                                    System.out.println("Invalid choice.");
                                    continue;
                                }
                                else {
                                    levelSet.clear();
                                    levelSet.add(levels.get(input - 1));
                                }

                                internships = internshipApp.getFilteredInternships(student, levelSet);
                                CSVBeutify.BeutifyNewFilter("AVAILABLE INTERNSHIP", internships, "title", "companyName", "major", "level", "closingDate");

                            } catch (IOException e) {
                                System.out.println("Error filtering internships: " + e.getMessage());
                            }
                        }
                    }
                    break;
                case 9:
                    System.out.println("Going back to main menu..");
                    return;
            }
    
            /*
            // If user apply internship
            if (selection > 0 && selection <=internships.size()) {
                internshipApp.applyForInternship(student, internships.get(selection - 1));
                System.out.println("Going back to main menu..");
                return;
            }
            else if (selection != 0) {
                System.out.println("Invalid input!");
            }
            else {
                return;
            }
             */
        }
    }
}