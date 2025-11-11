package student;

import java.util.*;
import java.io.*;

import model.*;
import CSVmethods.*;

import java.sql.Date;

import enums.*;

public class StudentViewInternship {
    public static final String REL_CSV = "data/student_internship_rel.csv";
    public static final String INTERNSHIP_CSV = "data/internships.csv";

    private static Scanner sc = new Scanner(System.in);

    private static int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Accept Successful Internship");
        System.out.println("9: Return");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public static void viewAppliedInternship(Student student) {
        CSVRead reader = new CSVRead();
        // Retrieve intenshipIDs by userID
        List<String[]> rel = reader.ReadAll(REL_CSV);
        List<String[]> userRule = new ArrayList<>();
        userRule.add(new String[] {"StudentID", student.getId()});
        List<String[]> filtered = CSVFilter.moreFilter(rel, userRule);

        // get data from internshipIDs
        List<String[]> internships = reader.ReadAll(INTERNSHIP_CSV);
        List<Internship> successfulInternships = new ArrayList<>();;
        String[] entry;
        for (int i = 1; i < filtered.size(); i++) {
            userRule.clear();
            userRule.add(new String[] {"ID", filtered.get(i)[1]});
            entry = CSVFilter.moreFilter(internships, userRule).get(1);

            // If internship is successful, store it in the successfulInternships list
            if (filtered.get(i)[2].equals("Successful")) {
                successfulInternships.add(new Internship(entry[0],entry[1],entry[2],entry[3], Major.valueOf(entry[4]), InternshipLevel.valueOf(entry[5]), Integer.parseInt(entry[6]), Date.valueOf(entry[7]), Date.valueOf(entry[8]), InternshipStatus.valueOf(entry[9])));
            }
        }

        int choice = openMenu();

        switch(choice) {
            case 1:
                StudentAcceptInternship.acceptInternship(student, successfulInternships);
            case 9:
                System.out.println("Returning to previous menu...");
                break;
            default:
                System.out.println("Invalid input, returning to main menu...");
                break;
        }
    }
}