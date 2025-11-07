package companyRep;

import java.util.Scanner;

public class CRepEditProfile {

    private static Scanner sc;

    private static int openMenu() {
        System.out.println("Please select an action");
        System.out.println("1: Edit email");
        System.out.println("2: Edit password");
        System.out.println("9: Return");
        int num = Integer.parseInt(sc.next());

        return num;
    }

    public static void CRepEditProfileController(Scanner scanner) {
        sc = scanner;

        System.out.println("Editing Profile...");
        int choice = 0;
        while (choice != 9) {
            try {
                choice = openMenu();
            } catch (NumberFormatException e) {
                System.out.println("Not a valid input");
            }

            switch (choice) {
                case 1 -> System.out.println("1"); // Call edit email
                case 2 -> System.out.println("2"); // Call edit password
                case 9 -> System.out.println(""); // Exit back to menu
                default -> System.out.println("Fail");
            }
        }
        return;
    }

}
