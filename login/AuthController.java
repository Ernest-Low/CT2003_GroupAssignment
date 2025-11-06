package login;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dto.LoginInfo;

public class AuthController {

    private Scanner sc = new Scanner(System.in);

    public LoginInfo openMenu() {
        int choice;

		do {
			System.out.println("Perform the following methods:");
			System.out.println("(1) Login");
            System.out.println("(2) Create Account - WORK IN PROGRESS");
			System.out.println("(3) Exit/n");
            
			choice = sc.nextInt();
            
            sc.nextLine();
			
			switch (choice) {
				case 1:
                    LoginInfo account = login(sc);
                    if (account != null) {
                        return account;
                    }
					break;
                case 2:
                    // work in progress zzz
                    break;
				case 3: System.out.println("Program terminating ....");
			}
		} while ((choice < 3));
        
        return null;
    }

    public void updatePassword() {
        while (true) { 
            System.out.println("Password Requirements:");
            System.out.println("1. Between 8-15 Characters");
            System.out.println("2. At least 1 number");
            System.out.println("3. At least 1 uppercase character");
            System.out.println("4. At least 1 lowecase character");
            System.out.println("5. At least 1 special character (!, @, #, $, %)");

            System.out.println("New Password:");
            String new_password = sc.nextLine();

            // password validation
            if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d!@#$%]{8,15}$", new_password)) {
                AuthService.updatePassword(new_password);
                System.out.println("[SUCCESS] Password was succesfully changed");
                break;
            } else {
                System.out.println("[ERROR] New Password does not match requirements");
            }
        }
    }

    private LoginInfo login(Scanner sc) {
        System.out.println("Username:");
        String input_username = sc.nextLine();
        System.out.println("Password:");
        String input_password = sc.nextLine();

        return AuthService.login(input_username, input_password);
    }

    private void createAccount(Scanner sc) {

        System.out.println("Username:");
        String input_username = sc.nextLine();
        System.out.println("Password:");
        String input_password = sc.nextLine();
        System.out.println("Role - Choose (A) Student (B) Staff (C) Company Representative:");
        String input_role = sc.nextLine();
    }

}
