package StaffFiles;

//generic import here
import java.util.*;

//import goes here
import model.*;

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

            int choice = getUserInput();
            switch(choice){
                case 1:
                    staffutil.getAllAcc();
                    break;
                case 2:
                    staffutil.getPenCSV();
                    break;
                case 3:
                    staffutil.getPenCSV();
                    break;
                case 4:
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
        System.out.println("5. Log Out? \n Enter choice here: ");

    }

    private int getUserInput(){

        try {
            return scanner.nextInt();
        }catch (Exception e){
            scanner.nextLine();
            return -1;
        }
    }
}
