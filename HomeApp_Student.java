import user.Account;
import user.StudentProfile;

public class HomeApp_Student {
        
        public void homeStudent_Menu(Account loginAccount, StudentProfile loginProfile) {
                System.out.println("Redirected to Student Home Page...");
                System.out.println("Welcome, " + loginAccount.getName() + " (" + loginAccount.getRole() + ")");
	}

        // if you want to get the details of the account, you can get the general ones from loginAccount
        // else, for role specific data, refer to loginProfile

}