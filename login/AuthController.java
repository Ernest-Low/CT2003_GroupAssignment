package login;

import java.util.Scanner;

import dto.LoginInfo;

public class AuthController {

    private final Scanner sc = new Scanner(System.in);

    public LoginInfo openMenu() {
        int choice;

		System.out.println("Perform the following methods:");
		System.out.println("(1) Login");
        System.out.println("(2) Create Company Representative Account");
		System.out.println("(3) Exit/n");
            
		choice = sc.nextInt();
            
        sc.nextLine();
			
		switch (choice) {
			case 1:
                LoginController loginController = new LoginController();
                LoginInfo account = loginController.login();
                if (account != null) {
                    return account;
                }
				return null;
            case 2:
                CreateAccountController createAccountController = new CreateAccountController();
                createAccountController.createAccount();
                return null;
			case 3: System.out.println("Program terminating ....");
		}
        return null;
    }

}
