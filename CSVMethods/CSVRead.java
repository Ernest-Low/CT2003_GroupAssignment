package CSVmethods;//this is for file path

//Please put any required import below here
import java.io.*;
import java.util.*;


//Ethan Note: This CSV File is for Read from CSV commands. Use case will be given at the top of each method

public class CSVRead {

    //Example input -> CSVRead.ReadAll(COMPANY_CSV); //All you need is just specify the CSV to read from

    public List<String[]> ReadAll(String csvFile){

        List<String[]> getAll = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            
            String line;
            String [] headers = br.readLine().split(",");
            getAll.add(headers);

            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                getAll.add(data);
            }

        }catch (IOException e){

            System.out.print("Error read file: " + e.getMessage());

        }
        return getAll; 
        //expected output is in the form of an array. Will make a code to beutify later
        //[StaffID, Name, Role, Department, Email]
        //[sng001, Dr. Sng Hui Lin, Career Center Staff, CCDS, sng001@ntu.edu.sg]
    };

    //this is used if u only want to print out certain columns example u want to exclude salt and hash
    //and also please dont change the expected output data type it cant be in array it needs to be a list with a bunch of array
    //Usage -> CSVRead.ReadByColumn(STUDENT.CSV, [ID,Name,Major])
    public static List<String[]> ReadByColumn(String csvFile, String[] filHeaders){
        
        List<String[]> wantedHead = new ArrayList<>(); // just note that this var will contain the output u want at the end
        List<Integer> colIndex = new ArrayList<>(); //this will store the col index explanation below

        //code explanation starts here -> i need to know the column index example staffID = position 0

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            
            String line;

            if ((line = br.readLine()) != null){ //this will take the header ONLY and split it with ","
                String[] allHead = line.split(",");

                for (int index = 0; index < filHeaders.length; index++ ){//indentify header position based on index

                    String filtered = filHeaders[index];

                        for (int i = 0; i < allHead.length; i++){

                            if (filtered.equalsIgnoreCase(allHead[i].trim())){
                                
                                colIndex.add(i); //this is supposed to add the position-1 to the col index (note it starts from position 0)
                                break;
                            }
                        }
                    }

                    String[] newHead = new String[colIndex.size()]; //should be = filHeaders length

                    for (int x = 0; x < colIndex.size(); x++){

                        newHead[x] = allHead[colIndex.get(x)]; //inside here colIndex will be like [0,3,5] so when u .get(1) = allHead[3]

                    }wantedHead.add(newHead); //adds the new headers into the expected return
                }

                while ((line = br.readLine()) != null){
                    
                    String[] AllData = line.split(",");
                    String[] afterFilter = new String[colIndex.size()];

                    for (int y = 0; y < colIndex.size(); y++){
                        int ogPos = colIndex.get(y);

                        if (ogPos < AllData.length){
                            afterFilter[y] = AllData[ogPos];
                        }
                        else{
                            afterFilter[y] = " ";
                        }
                    }
                    wantedHead.add(afterFilter);
                }
            }   catch (IOException e){
                System.out.println("Error filtering CSV File: " +e.getMessage());
            }
            return wantedHead;
            //Expected output will be something like this
            //[[StaffID,Name,Role],[ID001,Ethan,CCDS],[ID002,Ernest,CCDS]] basically an array in an array
        }
    
}