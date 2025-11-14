package StaffFiles;

//generic import here
import java.util.*;

//import goes here
import model.*;
import CSVMethods.CSVBeutify;
import login.*;
import CSVMethods.CSVFilter;
import dtos.LoginInfo;


public class staffmainnew {

    private CareerStaff staff;
    private staffutilnew staffutil;

    //staff information is passed into here from Login Info
    //@param staff, will be the staff that logged in, information is passed from central and used in class file
    public staffmainnew (CareerStaff staff){
        this.staff = staff;
        this.staffutil = new staffutilnew();
    }

    //main entry point for staff menu, will handle the logi flow of the interface and ensure that user choises are passed to the right uttility method
    //@Param, loginInfo is passed from login page so that it will be able to handle the change password data and updated in login function
    //this ensure that only the required dependcies are given to the class
    public void staffEntry(LoginInfo loginInfo){
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

                    CSVBeutify.BeutifyNewFilter("All Accounts", Allaccount,"accountStatus","id","userType", "accountStatus");
                    break;
                case 2:
                    //view pending accounts
                    List <String[]> ruleFilter1 = new ArrayList<>();
                    ruleFilter1.add(new String[] {"accountStatus", "PENDING"});
                    ruleFilter1.add(new String[] {"userType", "COMPANYREP"});

                    staffutil.updateStatACC(ruleFilter1,"All Pending Accounts");
                    break;
                case 3:
                    List <String[]> ruleFilter2 = new ArrayList<>();
                    ruleFilter2.add(new String[] {"status", "PENDING"});

                    staffutil.updateStatINT(ruleFilter2, "All Pending Internship");

                    break;
                case 4:
                    UpdatePasswordController updatePasswordController = new UpdatePasswordController();
                    updatePasswordController.updatePassword(loginInfo.getID());
                    break;
                case 5:
                    dashboard = false;
                    System.out.println("Now Logging out...\nHope to see you again!");
                    break;
                default:
                    System.out.println("Please enter a valid Input");
            }

        }
    }
    
    //reusable UI to ensure that the code is reusable
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
