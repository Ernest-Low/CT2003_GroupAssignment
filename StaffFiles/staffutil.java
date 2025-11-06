package StaffFiles;

import java.io.*;
import java.io.ObjectInputFilter.Status;
import java.util.*;

import javax.print.attribute.standard.PrinterIsAcceptingJobs;

//Ethan - This file contain all the method function that staffmain.java has
//Ethan - If you lazy read how it works and want to just use it scroll all the way down
//Ethan - DONT DIRECTLY CALL ANY OF THE STUFF WITH CSV IN THE METHOD NAME

public class staffutil {

    private static final String COMPANY_CSV = "data\\companyReps_list.csv";
    private static final String INTERNSHIP_CSV = "data\\internship.csv";

    public List<String[]> getAll(){
        List<String[]> allAccounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(COMPANY_CSV))){
            String line;
            String[] headers = br.readLine().split(",");
            allAccounts.add(headers);

            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                allAccounts.add(data);
            }

        } catch (IOException e){
            System.out.println("ERROR: not able to load file" + e.getMessage());
        }
        return allAccounts;
    }
    
    public List<String[]> getPenAccCSV(){ //this used for switch case 2
        List<String[]> PendingAccounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(COMPANY_CSV))){
            String line;
            String[] headers = br.readLine().split(",");
            int StatusIndex = FindColumnCSV(headers, "STATUS"); //go to find column method below

            //if the status is found it will be = the column number minus 1
            if (StatusIndex == -1){
                System.out.println("Error: Data Column not found");
                return PendingAccounts;
            }

            while ((line = br.readLine()) != null){
                String [] data = line.split(",");
                if (data.length > StatusIndex && "FALSE".equalsIgnoreCase(data[StatusIndex].trim())){
                    PendingAccounts.add(data);
                }
            }
        } catch (IOException e){
            System.out.println("Error reading account data, " + e.getMessage() );
        }

        return PendingAccounts;
    }

    public List<String[]> getInternCSV(){ //A copy pasted version of getAccount but variables changed

        List<String[]> PendingInternship = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(INTERNSHIP_CSV))){
            String line;
            String[] headers = br.readLine().split(",");
            int IntIndex = FindColumnCSV(headers, "Status");

            if (IntIndex == -1){
                System.out.println("Error: Data Column not Found");
                return PendingInternship;
            }

            while ((line = br.readLine()) != null){
                String[] data = line.split(",");
                if (data.length > IntIndex && "Pending".equalsIgnoreCase(data[IntIndex].trim())){
                    PendingInternship.add(data);
                }
            }
        }catch (IOException e){
            System.out.println("Error reading data from file" + e.getMessage());
        }

        return PendingInternship;

    }

    //Used in all csv methods that will look for a specific column
    private int FindColumnCSV(String[] headers, String columnName){

        //when i = 4 the status will match and return i = 4
        for (int i = 0; i < headers.length; i++){
            if (headers[i].trim().equalsIgnoreCase(columnName)){
                return i;
            }
        }
        return -1;//if the specificed column is not found return this 
    }

    //Used this to update the csv from pending -> approve or reject
    private boolean StatusUpdateCSV(String filename, String ID, String newStatus, String ColumnName){

        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String header = br.readLine();
            lines.add(header); //should looks something like headers = ["id", "Name", ...] after the next split line
            String[] headers = header.split(","); //just note that this here is headers != header

            //just an error exception here to find the column in excel
            int statusFind = FindColumnCSV(headers, "status");
            int idFind = FindColumnCSV(headers, ColumnName);

            //if either of the column dont exist return error
            if (statusFind == -1 || idFind == -1 ){
                System.out.println("Error 404: Column Not found in " + filename);
                return false;
            }

            String line; //just another note here line != lines
            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                if (data.length > idFind && ID.equals(data[idFind].trim())){
                    if (data.length > statusFind){
                        data[statusFind] = newStatus;
                        line = String.join(",", data);
 
                    }

                    found = true;
                }
                lines.add(line);
            }
        }catch (IOException e){
            System.out.println("Error found while processing: " + e.getMessage());
            return false;
        }

        if (found){
            return writeCSV(filename, lines);//idk if should use the CSV method so i just used what i had for now
        }

        System.out.println("Item with the following ID: " +ID+ "not found, please try again");
        return false;

    }

    //every function with CSV in the name will use this
    private boolean writeCSV(String filename, List<String> lines){

        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))){
            for (String line: lines){
                pw.println(line);
            }
            if (pw.checkError()){ //its here because by right pw doesnt throw a IO Exception 
            System.out.println("Error occured while writing to file");
            return false;
        }

        return true; //if no error uploading
        } catch (IOException e){
            System.out.println("This error should not happen: " + e.getMessage());
            return false;
        }
    }

    //ANYTHING BELOW THIS LINE ARE DIRECT METHODS, ONLY CALL THESE METHODS DIRECTLY

    public boolean AccStatusUpdate(String acc_ID, String newStatus){ //use this from main to update account pending -> approve/reject
        
        return StatusUpdateCSV(COMPANY_CSV, acc_ID, newStatus, "Status");

    }

    public boolean IntStatusUpdate(String int_ID, String newStatus){

        return StatusUpdateCSV(INTERNSHIP_CSV, int_ID, newStatus, "Status");

    }

    public void displayPenAcc(){
        List<String[]> AccountsPending = getPenAccCSV();
        //Refer to line 37 getPenAccCSV() for how it works

        if (AccountsPending.isEmpty() == true){
            System.out.println("No accounts found or error loading csv");
        }else{
            System.out.println("These are the Pending Accounts:...\n");
            //displayAccount(AccountsPending);//this one is to be implemented
        }

    }

    public void displayInt(){

        List<String[]> Internship = getInternCSV();
        //Refer to line 64 getInternCSV() for how it works please modify status field accordingly

        if (Internship.isEmpty() == true){
            System.out.println("No Pending Internship found or error loading CSV");
        }else {
            System.out.println("These are the Internship pending your approval:...\n");
            //displayIntern(Internship);//not implemented yet. Should be a nice table
        }

    }
}
