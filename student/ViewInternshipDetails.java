package student;

import CSVMethods.CSVBeutify;
import internship_rs.InternshipApplicationService;
import model.*;
import java.util.*;

public class ViewInternshipDetails {

    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        
        System.out.println("Please select an action");
        System.out.println("1: Apply internship");
        System.out.println("9: Return");
        System.out.println();
        while (true) {
            try{
                System.out.print("Input: ");
                int num = Integer.parseInt(sc.next());
                    return num;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input!");
            }
        }
    }
    public static void viewDetails(Student student, Internship internship) {
        System.out.println();
        InternshipApplicationService internshipApp = new InternshipApplicationService();
        CSVBeutify.BeutifyNewFilter("INTERNSHIP DETAIL", List.of(internship), "title", "title", "companyName", "major","level", "openingDate","closingDate");
        System.out.println();
        System.out.print("Description: ");
        System.out.println(internship.getDescription());
        System.out.println();
        int choice = 0;
        while (true) {
            choice = openMenu();
            if (choice == 1) {
                internshipApp.applyForInternship(student, internship);
                return;
            }
            else if (choice == 9) {
                return;
            }
            System.out.println("Invalid Input.");
        }
    }
}