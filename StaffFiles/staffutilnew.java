package StaffFiles;

//general imports
import java.util.List;

//file imports
import CSVMethods.*;

public class staffutilnew {

    private static final String CREDS_CSV = "data/credentials_list.csv";


    public void getAllAcc(){
        //switch case 1
        CSVRead CSVRead = new CSVRead();

        List<String[]> showAll = CSVRead.ReadAll(CREDS_CSV);

        CSVBeutify.printData("Display All Accounts", showAll);
    }

    public void getPenCSV(){
        
    }
    
}
