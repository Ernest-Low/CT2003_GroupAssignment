package login;

import java.util.Scanner;

public class CreateAccountController {

    private static final Scanner sc = new Scanner(System.in);

    public void createAccount() {
        boolean createAccount_success = false;
        boolean createProfile_success = false;

        // vaidation - email format
        // have to be unique value
        System.out.println("");
        System.out.println("Please note that all fields are compulsory");
        System.out.println("");
        System.out.println("Username (Company Email Address):");
        String input_username = sc.nextLine();

        System.out.println("");
        System.out.println("Password Requirements:");
        System.out.println("1. Between 8-15 Characters");
        System.out.println("2. At least 1 number");
        System.out.println("3. At least 1 uppercase character");
        System.out.println("4. At least 1 lowecase character");
        System.out.println("5. At least 1 special character (!, @, #, $, %)");
        System.out.println("");
        System.out.println("Password:");
        String input_password = sc.nextLine();
        createAccount_success = AuthService.createAccount(input_username, input_password);
        
        if (createAccount_success) {
            while (!createProfile_success) {
                System.out.println("");
                System.out.println("Set up your profile...");
                System.out.println("");
                System.out.println("Full Name:");
                String input_name = sc.nextLine();
                System.out.println("Company Name:");
                String input_companyName = sc.nextLine();
                System.out.println("Department:");
                String input_department = sc.nextLine();
                System.out.println("Position:");
                String input_position = sc.nextLine();
                createProfile_success = AuthService.createProfile(input_username, input_name, input_companyName, input_department, input_position);
            }
        }

        if ((createAccount_success) && (createProfile_success)) {
            System.out.println("[SUCCESS] Created Account Successfully");
            System.out.println("Please note: Your account has been created and is now pending approval");
        } else {
            System.out.println("[ERROR] Account Creation Failed");
            // delete records if created alr
        }
    }
}
