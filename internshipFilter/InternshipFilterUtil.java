package internshipFilter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InternshipFilterUtil {
    public static boolean addOrRemove(boolean addFilter, Scanner sc) {
        String choice = "";
        while (true) {
            try {
                System.out.println("1: Add filter");
                System.out.println("2: Remove filter");
                System.out.println("X: Back");
                System.out.print("Enter choice: ");
                choice = sc.nextLine().toLowerCase();
                switch (choice) {
                    case "1":
                        return true; // Add
                    case "2":
                        return false; // Remove
                    case "x":
                        return addFilter; // Exit
                    default:
                        System.out.println("Not a valid input. Try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Input was closed. Returning...");
                return addFilter;
            }
        }
    }
}
