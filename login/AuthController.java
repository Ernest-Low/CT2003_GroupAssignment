package login;

import java.util.Scanner;

import dtos.LoginInfo;

public class AuthController {

    private final Scanner sc = new Scanner(System.in);

    public LoginInfo openMenu() {

        while (true) {
            String choice = "";
            System.out.println("\nWelcome to the Internship Placement Management System:");
            System.out.println("(1) Login");
            System.out.println("(2) Create Company Representative Account");
            System.out.println("(3) Exit");

            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    LoginController loginController = new LoginController();
                    LoginInfo account = loginController.login();
                    if (account != null) {
                        return account;
                    }
                    break;
                case "2":
                    CreateAccountController createAccountController = new CreateAccountController();
                    createAccountController.createAccount();
                    // return null;
                    break;
                case "3":
                    System.out.println("\nProgram terminating ....");
                    return null;
                default:
                    System.out.println("\nNot a valid input. Try again.");
            }
        }
    }

}
