package student;

import java.util.*;
import java.io.*;

import model.*;
import CSVMethods.*;

import internship_rs.*;

public class StudentViewInternship {

    // INTIALIZATION
    public static final String REL_CSV = "data/student_internship_rel.csv";
    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        System.out.println();
        System.out.println("Please select an action");
        System.out.println("1: Accept Successful Internship Application");
        System.out.println("9: Return");
        System.out.print("Input: ");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public static void viewAppliedInternship(Student student) {
        System.out.println();
        System.out.println("--Your Applications--");

        // Initialization
        CSVRead reader = new CSVRead();
        InternshipDataService dataApp = new InternshipDataService();

        // Retrieve intenshipIDs by userID
        List<String[]> rel = reader.ReadAll(REL_CSV);
        List<String[]> userRule = new ArrayList<>();
        userRule.add(new String[] {"StudentID", student.getId()});
        List<String[]> filtered = CSVFilter.moreFilter(rel, userRule);

        List<String[]> printing = new ArrayList<>();
        printing.add(new String[] {"Company Name", "Title", "Level", "Status"});
        for (int i = 1; i < filtered.size(); i++) {
            try {
                Internship internship = dataApp.getInternshipById(filtered.get(i)[1]);
            
            // Append to print out
            String[] entry = new String[] {internship.getCompanyName(), internship.getTitle(), internship.getLevel().getDisplayName(), filtered.get(i)[2]};
            printing.add(entry);
            } catch(IOException e) {
                System.out.println(e);
                break;
            }
        }
        // Print out with CSVBeautify
        CSVBeutify.printData("Your Internship Applications", printing);

        int choice = openMenu();

        switch(choice) {
            case 1:
                StudentAcceptInternship.acceptInternship(student);
            case 9:
                System.out.println("Returning to previous menu...");
                break;
            default:
                System.out.println("Invalid input, returning to main menu...");
                break;
        }
    }
}