package StaffFiles;

//import generic
import java.util.List;
import java.util.Scanner;

//import files
import model.*;

public class staffmain {

    private static Scanner scanner = new Scanner(System.in);
    private static staffutil staffutil = new staffutil();

    private CareerStaff CareerStaff;

    //Use this for staff entry code
    public staffmain(CareerStaff CareerStaff){
        this.CareerStaff = CareerStaff;
    }

    public void staffEntry(){
        
        boolean dashboard = true; //boolean better than while choice > 0?

        while(dashboard){

            System.out.println("\nYou are currently logged in as: " + CareerStaff.getName());
            System.out.println("1. View All Accounts");
            System.out.println("2. Approve/Reject Account Creation");
            System.out.println("3. Approve new Internship");
            System.out.println("4. Password Change"); //Need your help here mabel tyty
            System.out.println("5. Log out");
            System.out.println ("Choose an Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    viewAllAcc();
                    break;
                case 2:
                    viewPenAcc();
                    break;
                case 3:
                    viewPenInt();
                    break;
                case 4:
                    PassChng();//Should I include this?
                case 5:
                    System.out.println("Logging out now...");
                    System.out.println("Thank you for using our Services!");
                    dashboard = false;
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }

        }


    }

    private static void viewAllAcc(){

        staffutil.allAccount();

    }

    private static void viewPenAcc(){

        staffutil.getPenAccCSV();
        staffutil.approvalchk();

    }

    private static void viewPenInt(){

    }

    private static void PassChng(){
        
    }
    

}

