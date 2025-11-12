package student;

import java.io.IOException;
import java.util.*;

import CSVMethods.CSVBeutify2;
import model.*;
import internship_rs.*;


public class ExploreInternshipService {
    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: View Internship Details");
        System.out.println("2: Filter Level");
        System.out.println("9: Return");
        System.out.print("Input: ");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public static void exploreInternship(Student student) {

        // initialize internship application service
        InternshipApplicationService internshipApp = new InternshipApplicationService();
        List<Internship> internships;

        // retrieve available internships
        try {
            internships = internshipApp.getAvailableInternshipsForStudent(student);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        // check if there are records
        if (internships == null || internships.isEmpty()) {
            System.out.println("No internship available for your major and year level at the moment.");
            return;
        }
        
        CSVBeutify2.BeutifyNewFilter("AVAILABLE INTERNSHIP", internships, "title", "companyName", "major", "level", "closingDate");

        // Prompt user for input (apply/exit)
        while (true) {

            int selection = openMenu();

            switch (selection) {
                case 1:
                    System.out.print("Select index to view details: ");
                    int choice = sc.nextInt();
                    if (choice > 0 && choice < internships.size()) {
                        // Get details
                    }
                    else {
                        System.out.println("Invalid index");
                    }
                    break;
                case 2:
                    break;
                case 9:
                    System.out.println("Going back to main menu..");
                    return;
            }
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
        }
    }
}