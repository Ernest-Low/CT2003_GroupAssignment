package student;

import java.io.IOException;
import java.util.*;

import CSVMethods.CSVBeutify2;
import model.*;
import internship_rs.*;

import enums.*;

public class ExploreInternshipService {
    private static Scanner sc = new Scanner(System.in);

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

        CSVBeutify2 beutify = new CSVBeutify2();
        beutify.BeutifyNewFilter("AVAILABLE INTERNSHIP", internships, "title", "companyName", "major", "description");
        /*
        // print out available records
        System.out.println();
        System.out.println("-- AVAILABLE INTERNSHIP --");
        // NEEDS FILTERING IF USER IS Y1/2 AND BY MAJOR (MISSING)
        for (int i = 1; i <= internships.size(); i++) {
            Internship internship = internships.get(i-1);
            System.out.println(i + ". " + internship.getCompanyName() + ": " + internship.getTitle() + " (" + internship.getLevel().getDisplayName() + ")");
        }
         */

        // Prompt user for input (apply/exit)
        while (true) {
            System.out.println();
            System.out.print("Enter internship index to apply ('0' to exit): ");
            int selection = sc.nextInt();
            System.out.println();

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