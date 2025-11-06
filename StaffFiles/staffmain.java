package StaffFiles;
import java.util.List;
import java.util.Scanner;

public class staffmain {

    private static Scanner scanner = new Scanner(System.in);
    private static staffutil staffutil = new staffutil();
    private static String cur_Staff;
    private static String cur_Name;
    private static String cur_Role;
    private static String cur_Email;

    //Use this for staff entry code
    public static void StaffEntry(String StaffID, String Name, String Role, String Email){

        cur_Staff = StaffID;
        cur_Name = Name;
        cur_Role = Role;
        cur_Email = Email;
        System.out.println("Welcome, " + cur_Name);
        //Dashbaord code here will update later
    }

    public static void staffStart(){
        
        boolean dashboard = true; //boolean better than while choice > 0?

        while(dashboard){

            System.out.println("\nYou are currently logged in as: " + cur_Name);
            System.out.println("1. View All Accounts");
            System.out.println("2. View Pending Accounts");
            System.out.println("3. View Pending Internships");
            System.out.println("4. Password Change"); //Do I add this here? i forgot
            System.out.println("5. Log Out");
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
                    System.out.println("Logging out now");
                    dashboard = false;
                    break;
                default:
                    System.out.println("Please enter a valid option");
            }

        }


    }

    private static void viewAllAcc(){

    }

    private static void viewPenAcc(){

    }

    private static void viewPenInt(){

    }

    private static void PassChng(){
        
    }
    

}

