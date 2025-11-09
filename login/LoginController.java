package login;

import java.util.Scanner;

import dto.LoginInfo;

public class LoginController {

    private final Scanner sc = new Scanner(System.in);

    public LoginInfo login() {
        System.out.println();
        System.out.println("Username:");
        String input_username = sc.nextLine();
        System.out.println("Password:");
        String input_password = sc.nextLine();
        System.out.println();

        LoginInfo loggedInAccount = AuthService.login(input_username, input_password);
        if (loggedInAccount == null) {
            System.out.println("[ERROR] Login Unsuccessful");
            System.out.println();
            return null;
        } else {
            System.out.println("[SUCCESS] Login Successful");
            System.out.println();
            return loggedInAccount;
        }
    }
}
