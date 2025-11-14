package student;

import java.util.Scanner;

import dtos.LoginInfo;
import model.*;
import login.*;


public class StudentMenu {

    private Student student;
    Scanner sc;

    public StudentMenu(Student student) {
        this.student = student;
        this.sc = new Scanner(System.in);
    }

    private int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Change password");
        System.out.println("2: Explore Internship Opportunity");
        System.out.println("3: View Internships Applications");
        System.out.println("9: Logout");
        System.out.println();
        System.out.print("Input: ");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public void StudentController(LoginInfo loginInfo) {
        sc = new Scanner(System.in);

        int choice = 0;
        while (choice != 9) {
            System.out.println();
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1: // Call edit profile method
                    UpdatePasswordController updatePasswordController = new UpdatePasswordController();
                    updatePasswordController.updatePassword(loginInfo.getID());
                    break;
                case 2: // Call explore internship opportunity method
                    ExploreInternshipService.exploreInternship(student);
                    break;
                case 3:  // Call view internship applied
                    StudentViewInternship.viewAppliedInternship(student);
                    break;
                case 9: // Exit
                    System.out.println("Exiting...");
                    break;
                default: 
                    System.out.println("Invalid Input!!");
                    break;
            }
        }

        sc.close();
    }
}