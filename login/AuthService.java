package login;

import java.util.List;
import java.util.regex.Pattern;

import CSVmethods.*;
import dto.LoginInfo;

import enums.AccountStatus;
import enums.UserType;

import model.Credential;


public class AuthService {

    private static final String CREDENTIALS_CSV_FILE = "data/credentials_list.csv";
    private static final String COMPANYREP_CSV_FILE = "data/companyReps_list.csv";

    private static final CSVMethods csvmethods = new CSVMethods();
    private static final CSVRead csvread = new CSVRead();
    private static final CSVWrite csvwrite = new CSVWrite();

    protected static LoginInfo login(String input_username, String input_password) {
        // check if username exists in csv
        // CSVMethods csvmethods = new CSVMethods();
        List<Credential> credentials = csvmethods.readCredentials();
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
            }
        }
        return null;
    }

    protected static boolean updatePassword(String userID, String new_password) {
        if ((userID.isBlank()) || (new_password.isBlank())) {
            System.out.println("[ERROR] Password should not be blank");
            return false;
        }
        if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d!@#$%]{8,15}$", new_password)) {
            List<String> hashedPassword = PasswordUtil.hashNewPassword(new_password);
            // store hashed password into csv
            String salt = hashedPassword.get(0);
            String hash = hashedPassword.get(1);

            // CSVRead csvWrite = new CSVWrite();
            csvwrite.updateByID(CREDENTIALS_CSV_FILE, userID, "ID", "salt", salt);
            csvwrite.updateByID(CREDENTIALS_CSV_FILE, userID, "ID", "hash", hash);
            
            return true;
        } else {
            System.out.println("[ERROR] New Password does not match requirements");
            return false;
        }
    }

    protected static boolean createAccount(String username, String password) {
        if ((username.isBlank()) || (password.isBlank())) {
            System.out.println("[ERROR] Username and Password cannot be blank");
            return false;
        } else if (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", username)) {
            // ensure it's in email format
            System.out.println("[ERROR] Username should be your Company Email");
            return false;
        }
        // check if username is unique
        // CSVRead csvRead = new CSVRead();
        // CSVWrite csvWrite = new CSVWrite();
        String[] colHeader = {"ID"};

        List<String[]> usernameList = csvread.ReadByColumn(COMPANYREP_CSV_FILE, colHeader);
        
        for (String[] arr : usernameList) {
            if ((arr.length > 0) && (arr[0].equals(username))) {
                System.out.println("[ERROR] Account Already Exists");
                return false;
            }
        }

        List<String> hashedPassword = PasswordUtil.hashNewPassword(password);
        List<String[]> crendentialList = csvread.ReadAll(CREDENTIALS_CSV_FILE);
   
        // store new credentials into csv
        crendentialList.add(new String[]{username, hashedPassword.get(0), hashedPassword.get(1), UserType.COMPANYREP.name(), AccountStatus.PENDING.name()});
        // System.out.println(crendentialList);
        csvwrite.writeToCSV(CREDENTIALS_CSV_FILE, crendentialList);
        return true;
    }

    protected static boolean createProfile(String username, String name, String companyName, String department, String position) {
        if ((name.isBlank()) || (companyName.isBlank()) || (department.isBlank()) || (position.isBlank())) {
            System.out.println("[ERROR] Profile fields cannot be blank");
            return false;
        }
        // CSVRead csvRead = new CSVRead();
        // CSVWrite csvWrite = new CSVWrite();

        List<String[]> profileList = csvread.ReadAll(COMPANYREP_CSV_FILE);
        // store new credentials into csv
        profileList.add(new String[]{username, name, companyName, department, position});
        // System.out.println(profileList);
        csvwrite.writeToCSV(COMPANYREP_CSV_FILE, profileList);
        return true;
    }
}
