package login;

import java.util.List;
import java.util.regex.Pattern;

import model.Credential;
import CSVmethods.CSVMethods;
import dto.LoginInfo;
import enums.AccountStatus;
import enums.UserType;

public class AuthService {
    protected static LoginInfo login(String input_username, String input_password) {
        // check if username exists in csv
        List<Credential> credentials = CSVMethods.readCredentials();
        for (int i=0; i<credentials.size(); i++) {
            Credential current = credentials.get(i);
            if (current.getAccountStatus() == AccountStatus.ACTIVE) {
                if (current.getid().equals(input_username)) {
                    boolean passwordMatch = PasswordUtil.verifyPassword(input_password, current.getSalt(), current.getHash());
                    if (passwordMatch) {
                        return new LoginInfo(input_username, current.getUserType());
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }

    protected static boolean updatePassword(String new_password) {
        if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d!@#$%]{8,15}$", new_password)) {
            List<String> hashedPassword = PasswordUtil.hashNewPassword(new_password);
            // store hashed password into csv
            // String salt = hashedPassword.get(0);
            // String hash = hashedPassword.get(1);
            return true;
        } else {
            System.out.println("[ERROR] New Password does not match requirements");
            return false;
        }
    }

    protected static boolean createAccount(String username, String password) {
        List<String> hashedPassword = PasswordUtil.hashNewPassword(password);
        Credential newAccount = new Credential(username, hashedPassword.get(0), hashedPassword.get(1), UserType.COMPANYREP, AccountStatus.PENDING);
        // store new credentials into csv
        return true;
    }
}
