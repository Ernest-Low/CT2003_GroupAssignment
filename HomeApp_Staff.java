import user.Account;
import user.StaffProfile;

public class HomeApp_Staff {

	public void homeStaff_Menu(Account loginAccount, StaffProfile loginProfile) {
        System.out.println("Redirected to Staff Home Page...");
        System.out.println("Welcome, " + loginAccount.getName() + " (" + loginAccount.getRole() + ")");

        // if you want to get the details of the account, you can get the general ones from loginAccount
        // else, for role specific data, refer to loginProfile
	}

}