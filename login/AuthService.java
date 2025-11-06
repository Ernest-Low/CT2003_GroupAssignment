package login;

import java.util.List;

import model.Credential;
import csvmethods.CSVMethods;
import dto.LoginInfo;
import enums.UserType;

public class AuthService {
    protected static LoginInfo login(String input_username, String input_password) {
        // check if username exists in csv
        boolean found = false;
        List<Credential> credentials = CSVMethods.readCredentials();
        for (int i=0; i<credentials.size(); i++) {
            Credential current = credentials.get(i);
            if (current.getid().equals(input_username)) {
                boolean passwordMatch = PasswordUtil.verifyPassword(input_password, current.getSalt(), current.getHash());
                if (passwordMatch) {
                    System.out.println("[SUCCESS] Login Success");
                    return new LoginInfo(input_username, current.getUserType());
                } else {
                    System.out.println("[ERROR] Password is incorrect");
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("[ERROR] Username does not exist");
        }
        return null;
    }

    protected static void updatePassword(String new_password) {
        List<String> hashedPassword = PasswordUtil.hashNewPassword(new_password);
        // store hashed password into csv
        // String salt = hashedPassword.get(0);
        // String hash = hashedPassword.get(1);
    }

    protected static void createAccount(String username, String password, String role) {
        List<String> hashedPassword = PasswordUtil.hashNewPassword(password);
        UserType usertype = null;
        switch (role) {
            case "A":
                usertype = UserType.STUDENT;
                break;
            case "B":
                usertype = UserType.CAREERSTAFF;
                break;
            case "C":
                usertype = UserType.COMPANYREP;
                break;
        }
        Credential newAccount = new Credential(username, hashedPassword.get(0), hashedPassword.get(1), usertype);
        // store new credentials into csv
    }
}
