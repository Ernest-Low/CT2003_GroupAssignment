package internshipFilter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InternshipFilterUtil {
    public static void addOrRemove(Boolean addFilter) {
        Scanner sc = new Scanner(System.in);
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
                        sc.close();
                        addFilter = true; // Add
                    case "2":
                        sc.close();
                        addFilter = false; // Remove
                    case "x":
                        return; // Exit
                    default:
                        System.out.println("Not a valid input. Try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Input was closed. Try again.");
                return;
            }
        }
    }
}
