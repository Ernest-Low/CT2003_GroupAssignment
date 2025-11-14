package StaffFiles;

import java.io.*;
import java.io.ObjectInputFilter.Status;
import java.util.*;

import javax.print.attribute.standard.PrinterIsAcceptingJobs;

import CSVMethods.*;

//Ethan - This file contain all the method function that staffmain.java has
//Ethan - If you lazy read how it works and want to just use it scroll all the way down
//Ethan - DONT DIRECTLY CALL ANY OF THE STUFF WITH CSV IN THE METHOD NAME

public class staffutil {

    private static Scanner scanner = new Scanner(System.in);

    private static final String COMPANY_CSV = "data\\companyReps_list.csv";
    private static final String INTERNSHIP_CSV = "data\\internship.csv";
    private static final String CREDS_CSV = "data/credentials_list.csv";

    public void allAccount(){
        //switch case 1
        CSVRead CSVRead = new CSVRead();

        List<String[]> showAll = CSVRead.ReadAll(CREDS_CSV);

        CSVBeutify.printData("Display All Accounts", showAll);
    }
    
    public void getPenAccCSV(){ 
        //this used for switch case 2

        List<String[]> PendingAccounts = new ArrayList<>();
        CSVRead CSVRead = new CSVRead();

        List<String[]> allData = CSVRead.ReadAll(CREDS_CSV);
        List<String[]> filters = new ArrayList<>();
        filters.add(new String[] {"status", "PENDING"});

        PendingAccounts = CSVFilter.moreFilter(allData, filters);

        CSVBeutify.printData("Accounts Pending Approval", PendingAccounts);
    }

    public void identifyApprove(int rowChange, String decision){

        List<String[]> PendingAccounts = new ArrayList<>();
        CSVRead CSVRead = new CSVRead();

        List<String[]> allData = CSVRead.ReadAll(CREDS_CSV);
        List<String[]> filters = new ArrayList<>();
        filters.add(new String[] {"status", "PENDING"});

        PendingAccounts = CSVFilter.moreFilter(allData, filters);

        String[] modifyRow = PendingAccounts.get(rowChange);
        String companyID = modifyRow[0];//hard coding this for now in the future maybe ill change
        //System.out.println("debugging first: " + companyID);
        //System.out.println("================================================");

        CSVWrite CSVWrite = new CSVWrite();
        CSVWrite.updateByID(CREDS_CSV, companyID, "ID", "status", decision);

    }

    public void approvalchk(){

        boolean valid = true;

        while(valid){

            System.out.println("================================================");
            System.out.println("Would you like to approve any of these account?");
            System.out.println("1. Choose Account to Approve/Reject");
            System.out.println("2. Back to Dashboard");
            System.out.println("================================================");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
            case 1:
                getPenAccCSV();
                System.out.println("Which Account would you like to Approve/Reject?");
                System.out.println("Please choose by Row number: \n");
                int approveChoice = scanner.nextInt();
                scanner.nextLine();

                if (approveChoice > 0){

                    String admin = adminChoice();
                    if(admin == ""){
                        break;
                    }else {
                        identifyApprove(approveChoice, admin);
                    }
                }else {
                    System.out.println("Please enter a valid input and try again");
                }
                
                break;
            case 2:
                //System.out.println("test23");
                valid = false;
                break;
            default:
                System.out.println("Please enter a valid Input");
            }
        }
    }

    public String adminChoice(){

        System.out.println("What would you like to do?");
        System.out.println("1. Approve :D");
        System.out.println("2. Reject D:");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice){
            case 1:
                return "ACTIVE";
            case 2:
                return "DISABLED";
            default:
                System.out.println("Please provide a valid input and try again");
                break;
        }

        return "";
    }

}
