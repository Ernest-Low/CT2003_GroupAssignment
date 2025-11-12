package CSVMethods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWrite {

    //ur input CSVFile = File name;userID = the userID;
    //idCol = the Column Name example StaffID; should always be the ID column like student ID or companyID
    //reason its like this is because the ID is the only Unique identifier.
    //updateCol = Status (Should be the column u want to update the name)
    //updatedVal = Approved (whats the new value?)
    //Another thing to note is that it updates the entire file so how it work is
    //1 -> Read the entire file 2-> find tha specific ID and change the value 3-> write back to file

    public void updateByID(String CSVFile, String userID, String idCol, String updateCol, String updatedVal) {

    //there was a try here before but was facing an error so i removed it lemme know if there are any issues without it
            CSVRead CSVRead = new CSVRead();
            List<String[]> allData = CSVRead.ReadAll(CSVFile);

            String[] AllCol = allData.get(0); // get headers first

            int colPos = -1;
            int updateColPos = -1;

            for (int i = 0; i < AllCol.length; i++) {
                if (AllCol[i].equals(idCol)) {
                    colPos = i;
                }
                if (AllCol[i].equals(updateCol)) {
                    updateColPos = i;
                }
            }

            if (colPos == -1 || updateColPos == -1) {
                System.out.println("Error 404, Column was not found or Column does not exist");
                return;
            }

            boolean successful = false;
            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (row[colPos].equals(userID)) {
                    String oldVal = row[updateColPos];
                    row[updateColPos] = updatedVal;
                    successful = true;
                    System.out.println("Changes have been made...\nUpdating to Database...");
                    break;
                }
            }

            if (!successful) {
                System.out.println("Error updating file, please check with system admin");
            } else {
                writeToCSV(CSVFile, allData);
            }
            
        } 
    

    private void writeToCSV(String filename, List<String[]> allData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String[] row : allData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error Writing to File, Please confirm if file Exists");
        }
    }
}