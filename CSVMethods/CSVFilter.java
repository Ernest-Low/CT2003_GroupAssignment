package CSVMethods;

//imports below here please, add if needed
import java.io.*;
import java.util.*;

//Ethan - Please only use this file after you got the output from CSVRead.java
//This file is for only filtering data and does not print any data, Please use readfilter in CSVRead.java.

public class CSVFilter {

    public List<String[]> moreFilter(List<String[]> colTable, List<String[]> userRules){

        if (userRules.isEmpty()){ //this check if the parsing filter is empty.
            return colTable;
        }

        //get the new column headers 
        //Missing feature -> Currently it doesnt first check if the userRules is inside of colTable
        String[] newColumn = colTable.get(0);
        List<String[]> newTable = new ArrayList<>();
        newTable.add(newColumn);

        //idk how to make it better but i just put a loop inside a loop inside a loop
        //need to start from 0 since it is the first set of data 0 = column
        for (int i = 1; i < colTable.size(); i++){
            String[] data = colTable.get(i);
            boolean ifrule = true; //need this to check if user filter = true later, if true will add into return data

            for (int x = 0; 0 < userRules.size(); x++){

                //since rules are given as list need this to get first set of rules 
                //this allows multiple set of rules
                String[] ruleset = userRules.get(x);

                String ColSet = ruleset[0]; //get the column name
                String valueSet = ruleset[1];//get the value example data must be equals to y2

                int existCol = -1;

                for (int y = 0; y < newColumn.length; y++){

                    if (newColumn[y].equals(ColSet)){ //this is to find column index example staff ID index = 0 matches what u want to filter
                        existCol = y;
                        break;
                    }
                }

                //if -1 means column doesnt exist and if column 1 exist and data at that column doesnt match valueset
                //means the its not correct example check if column = student year and value = year2
                //if true just continue dont do anything
                if (existCol != -1 && data[existCol].equals(valueSet)){ 

                }else{
                    ifrule = false;//if false break and restart loop
                    break;
                }

            }

            if (ifrule ==true){
                newTable.add(data);
            }

        }return newTable;


    }
    
}
