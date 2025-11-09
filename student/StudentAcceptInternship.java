package student;

import java.util.*;
import java.io.*;

import model.*;
import CSVMethods.*;

public class StudentAcceptInternship {

    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Accept Successful Internship");
        System.out.println("9: Return");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public static void acceptInternship(Student student, List<Internship> internships) {
        // Print successful applications

        // User input apply application

        // Update csv status -- to be run with method implemented by Daryl

        // Return to main menu
    }
}