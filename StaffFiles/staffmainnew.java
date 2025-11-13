package StaffFiles;

//generic import here
import java.util.*;

//import goes here
import model.*;
import CSVMethods.CSVBeutify2;
import CSVMethods.CSVFilter;


public class staffmainnew {

    private CareerStaff staff;
    private staffutilnew staffutil;
    private Scanner scanner = new Scanner(System.in);

    public staffmainnew (CareerStaff staff){
        this.staff = staff;
        this.staffutil = new staffutilnew();
    }

    public void staffEntry(){
        boolean dashboard = true;

        while(dashboard){

            uiMenu();

            int choice = staffutil.getUserInput();
            switch(choice){
                case 1:
                    //view All Acc
                    List <?> Allaccount = new ArrayList<>();
                    Allaccount = staffutil.mapToAllAccount();
                    //System.out.println(Allaccount);

                    CSVBeutify2.BeutifyNewFilter("All Accounts", Allaccount,"id", "userType", "accountStatus");
                    break;
                case 2:
                    //view pending accounts
                    List <String[]> ruleFilter1 = new ArrayList<>();
                    ruleFilter1.add(new String[] {"accountStatus", "PENDING"});
                    ruleFilter1.add(new String[] {"userType", "CAREERSTAFF"});

                    staffutil.updateStatACC(ruleFilter1,"All Pending Accounts");
                    break;
                case 3:
                    List <String[]> ruleFilter2 = new ArrayList<>();
                    ruleFilter2.add(new String[] {"status", "PENDING"});

                    staffutil.updateStatINT(ruleFilter2, "All Pending Internship");

                    break;
                case 4:
                    //please help me mabel ty ty
                    //changpassword()
                    break;
                case 5:
                    dashboard = false;
                    System.out.println("Now Logging out...\nHope See you again!");
                    break;
                default:
                    System.out.println("Please enter a valid Input");
            }

        }
    }
    
    private void uiMenu(){
        System.out.println("-".repeat(50));
        System.out.println("Welcome to Staff Menu!: " + staff.getName());
        System.out.println("1. View All Accounts");
        System.out.println("2. Review Account Creation");
        System.out.println("3. Review Internship Creation");
        System.out.println("4. Change Account Password?");
        System.out.println("5. Log Out?");
        System.out.print("Option: ");

    }

   
}
