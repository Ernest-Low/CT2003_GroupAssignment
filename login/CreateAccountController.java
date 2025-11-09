package login;

import java.util.Scanner;

public class CreateAccountController {

    private final Scanner sc = new Scanner(System.in);

    public void createAccount() {

        System.out.println("Username:");
        String input_username = sc.nextLine();
        System.out.println("Password:");
        String input_password = sc.nextLine();
        boolean success = AuthService.createAccount(input_username, input_password);
        if (success) {
            System.out.println("[SUCCESS] Created Account Successfully");
            System.out.println("Please note: Your account has been created and is now pending approval");
        } else {
            System.out.println("[ERROR] Account Creation Failed");
        }
    }
}
