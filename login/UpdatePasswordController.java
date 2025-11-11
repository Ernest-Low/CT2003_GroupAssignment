package login;

import java.util.Scanner;
public class UpdatePasswordController {

    private static final Scanner sc = new Scanner(System.in);
    
    public void updatePassword(String userID) {
        while (true) { 
            System.out.println("");
            System.out.println("Password Requirements:");
            System.out.println("1. Between 8-15 Characters");
            System.out.println("2. At least 1 number");
            System.out.println("3. At least 1 uppercase character");
            System.out.println("4. At least 1 lowecase character");
            System.out.println("5. At least 1 special character (!, @, #, $, %)");
            System.out.println("");

            System.out.println("New Password:");
            String new_password = sc.nextLine();

            boolean success = AuthService.updatePassword(userID, new_password);

            // password validation
            if (success) {
                System.out.println("");
                System.out.println("[SUCCESS] Password was succesfully changed");
            } else {
                System.out.println("");
                System.out.println("[ERROR] Password change unsuccessful");
            }
        }
    }
}
