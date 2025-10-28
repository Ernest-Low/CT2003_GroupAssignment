import user.Account;
import user.CompanyRepProfile;

public class HomeApp_CompanyRep {

	public void homeCompanyRep_Menu(Account loginAccount, CompanyRepProfile loginProfile) {
                System.out.println("Redirected to Company Rep Home Page...");
                System.out.println("Welcome, " + loginAccount.getName() + " (" + loginAccount.getRole() + ")");
	}

        // if you want to get the details of the account, you can get the general ones from loginAccount
        // else, for role specific data, refer to loginProfile

}