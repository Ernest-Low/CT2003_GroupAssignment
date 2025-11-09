package student;

import java.util.Scanner;

import model.*;


public class StudentMenu {

    private Student student;
    Scanner sc;

    public StudentMenu(Student student) {
        this.student = student;
        this.sc = new Scanner(System.in);
    }

    private int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Edit Profile");
        System.out.println("2: Explore Internship Opportunity");
        System.out.println("3: View Internships Applications");
        System.out.println("9: Logout");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public void StudentController() {
        sc = new Scanner(System.in);
        System.out.println("Welcome " + student.getName());
        int choice = 0;
        while (choice != 9) {
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1: // Call edit profile method
                    System.out.println("1");
                    break;
                case 2: // Call explore internship opportunity method
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