package StaffFiles;

import java.util.ArrayList;
//general imports
import java.util.List;

//file imports
import CSVMethods.*;
import enums.AccountStatus;
import enums.UserType;
import model.CareerStaff;
import model.Credential;

public class staffutilnew {

    private static final String CREDS_CSV = "data/credentials_list.csv";

    //the original stuff
    public void getAllAcc(){
        //switch case 1
        CSVRead CSVRead = new CSVRead();

        List<String[]> showAll = CSVRead.ReadAll(CREDS_CSV);

        CSVBeutify.printData("Display All Accounts", showAll);
    }

    public void displayObjWFil(String title, List<Object> objects, String... fields){

        CSVBeutify2.BeutifyNewFilter(title, objects, fields);

    }

    //new CSV -> Object for display all account
    public List<?> mapToAllAccount(){

        List<Credential> allCreds = new ArrayList<>();
        CSVRead CSVRead = new CSVRead();

        List<String[]> allData = CSVRead.ReadAll(CREDS_CSV);

        if (allData.isEmpty()){
            System.out.println("Backend Error read from file");
            return null;
        }

        for (int i = 1; i <allData.size(); i ++){

            String[] rowData = allData.get(i);

            UserType userType = UserType.valueOf(rowData[3].toUpperCase());
            AccountStatus accountStatus = AccountStatus.valueOf(rowData[4].toUpperCase());
            Credential Credential = new Credential(rowData[0], rowData[1], rowData[2], userType, accountStatus);

            allCreds.add(Credential);
        } return allCreds;
    }

    public void getPenCSV(){

    }
    
}
