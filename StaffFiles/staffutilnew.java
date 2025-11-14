package StaffFiles;

import java.time.LocalDate;
import java.util.ArrayList;
//general imports
import java.util.List;
import java.util.Scanner;

//file imports
import CSVMethods.*;
import enums.AccountStatus;
import enums.UserType;
import enums.Major;
import enums.InternshipStatus;
import enums.InternshipLevel;
import model.*;

public class staffutilnew {

    private static final String CREDS_CSV = "data/credentials_list.csv";
    private static final String INTERN_CSV = "data/internships.csv";
    private Scanner scanner = new Scanner(System.in);

    //the original stuff
    public void getAllAcc(){
        //switch case 1
        CSVRead CSVRead = new CSVRead();

        List<String[]> showAll = CSVRead.ReadAll(CREDS_CSV);

        CSVBeutify.printData("Display All Accounts", showAll);
    }

    public void updateStatACC(List <String[]> penFilter, String title){

        List <?> AllaccPending = new ArrayList<>();

        boolean dashboard = true;

        while (dashboard){

            AllaccPending = mapToAllAccount();
            AllaccPending = CSVFilter.filter4Obj(AllaccPending, penFilter, "accountStatus");

            if (AllaccPending.isEmpty()){
                dashboard = false;
                System.out.println("No Pending Account for Approval...");
                return;
            }
            CSVBeutify.BeutifyNewFilter(title, AllaccPending, "accountStatus", "id","userType", "accountStatus");

            uiStatus();
            int choice = getUserInput();

            switch(choice){
                case 1:
                    int rowIndex1 = statDashboard("Approve");
                    String userID1 = finduserID(AllaccPending, rowIndex1);
                    CSVWrite.updateByID(CREDS_CSV, userID1, "ID", "AccountStatus", "ACTIVE");
                    break;
                case 2:
                    int rowIndex2 = statDashboard("Reject");
                    String userID2 = finduserID(AllaccPending, rowIndex2);
                    //System.out.println(userID2);
                    CSVWrite.updateByID(CREDS_CSV, userID2, "ID", "AccountStatus", "REJECTED");
                    break;
                case 3:
                    dashboard = false;
                    break;
                default:
                    System.out.println("Please enter a valid input");
            }
        }
    }

    public void updateStatINT(List <String[]> penFilter, String title){

        List <?> AllIntPending = new ArrayList<>();

        boolean dashboard = true;

        while (dashboard){

            AllIntPending = mapToAllInternship();
            AllIntPending = CSVFilter.filter4Obj(AllIntPending, penFilter, "title");

            if (AllIntPending.isEmpty()){
                dashboard = false;
                System.out.println("No Pending Internship for Approval...");
                return;
            }

            CSVBeutify.BeutifyNewFilter(title, AllIntPending, "title", "ID","title", "companyName", "major", "level", "status", "description");

            uiStatus();
            int choice = getUserInput();

            switch (choice){
                case 1: 
                    int rowIndex1 = statDashboard("Approve");
                    String internID1 = findInternID(AllIntPending, rowIndex1);
                    CSVWrite.updateByID(INTERN_CSV, internID1, "ID", "status", "APPROVED");
                    break;
                case 2:
                    int rowIndex2 = statDashboard("Reject");
                    String internID2 = findInternID(AllIntPending, rowIndex2);
                    CSVWrite.updateByID(INTERN_CSV, internID2, "ID", "status", "REJECTED");
                    break;
                case 3:
                    dashboard = false;
                    break;
                default:
                    System.out.println("Please enter a valid input");
            }

        }

    }

    private int statDashboard(String minor){

        System.out.println("-".repeat(35));
        System.out.println("Pleae choose a row number to " + minor);

        int rowIndex = getUserInput();
        return rowIndex;

    }

    private String finduserID(List<?> object, int rowIndex){

        if(object.isEmpty()){
            System.out.println("There are no Pending accounts");
        }
        if (rowIndex < 0){
            System.out.println("Invalid Input");
        }

        Object obj = object.get(rowIndex -1);

        Credential Creds = (Credential) obj;
        return Creds.getId();

    }

    private String findInternID(List<?> object, int rowIndex){

        if(object.isEmpty()){
            System.out.println("There are no Pending accounts");
        }
        if (rowIndex < 0){
            System.out.println("Invalid Input");
        }

        Object obj = object.get(rowIndex - 1);

        Internship intern = (Internship) obj;
        return intern.getID();

    }

    public List<?> mapToAllAccount(){

        List<Credential> allCreds = new ArrayList<>();
        CSVRead CSVRead = new CSVRead();

        List<String[]> allAcc = CSVRead.ReadAll(CREDS_CSV);

        if (allAcc.isEmpty()){
            System.out.println("Backend Error read from file");
            return null;
        }

        for (int i = 1; i <allAcc.size(); i ++){

            String[] rowData = allAcc.get(i);

            UserType userType = UserType.valueOf(rowData[3].toUpperCase()); //fix eneum problem
            AccountStatus accountStatus = AccountStatus.valueOf(rowData[4].toUpperCase()); //fix enum problem
            Credential Credential = new Credential(rowData[0], rowData[1], rowData[2], userType, accountStatus);

            allCreds.add(Credential);
        } return allCreds;
    }

    public static List<?> mapToAllInternship(){

        List<Internship> Internships = new ArrayList<>(); 
        CSVRead CSVRead = new CSVRead();

        List<String[]> allIntern = CSVRead.ReadAll(INTERN_CSV);

        if (allIntern.isEmpty()){
            System.out.println("BackEnd Error, Error read from file");
            return null;
        }

        for (int i = 1; i < allIntern.size(); i++){
        
            String[] rowData = allIntern.get(i);

            Major Major = enums.Major.valueOf(rowData[4].toUpperCase());
            InternshipLevel InternshipLevel = enums.InternshipLevel.valueOf(rowData[5].toUpperCase());
            int counter = Integer.parseInt(rowData[6]);
            int slots = Integer.parseInt(rowData[7]);
            LocalDate openingDate = LocalDate.parse(rowData[8]);
            LocalDate closingDate = LocalDate.parse(rowData[9]);
            InternshipStatus InternshipStatus = enums.InternshipStatus.valueOf(rowData[10].toUpperCase());

            Internship Internship = new Internship(rowData[0], rowData[1], rowData[2], rowData[3], Major, InternshipLevel, counter, slots, openingDate, closingDate, InternshipStatus, rowData[11]);

            Internships.add(Internship);
        }
            return Internships;
    }

    private void uiStatus(){

        System.out.println("-".repeat(35));
        System.out.println("What would you like to do?");
        System.out.println("1. Approve Request");
        System.out.println("2. Reject Request");
        System.out.println("3. Return to Dashboard?");
        System.out.print(":");

    }

    public int getUserInput(){

        try {
            return scanner.nextInt();
        }catch (Exception e){
            scanner.nextLine();
            return -1;
        }
    }
    
}
