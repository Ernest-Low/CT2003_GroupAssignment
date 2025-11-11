package student;

import java.io.IOError;
import java.io.IOException;
import java.util.*;

import model.*;
import internship_rs.*;

public class StudentAcceptInternship {

    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        System.out.println("9: Return");
        System.out.print("Input: ");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public static void acceptInternship(Student student, List<Internship> internships) {
        // Print successful applications
        System.out.println();
        int count = internships.size();
        if (count > 0) {
            System.out.println("You have " + count + " successful application(s)!");
            System.out.println("Please select an action");
            for (int i = 1; i <= count; i++) {
                System.out.println(i + ". ACCEPT - " + internships.get(i-1).getCompanyName() + ": " + internships.get(i-1).getTitle() + " ("+ internships.get(i-1).getLevel().getDisplayName() + ") ");
            }
        }
        else {
            System.out.println("You have no successful application.");
        }
        int choice = openMenu();

        // User input apply application
        if (choice <= count && count > 0) {
            // Update csv status -- to be run with method implemented by Daryl
            InternshipApplicationService internshipApp = new InternshipApplicationService();
            try {
                boolean accepted = internshipApp.acceptInternshipOffer(student, internships.get(choice - 1));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        // Return to main menu
    }
}