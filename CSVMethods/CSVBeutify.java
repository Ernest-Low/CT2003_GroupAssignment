package CSVMethods;

//imports goes here
import java.util.*;

//This class file is not to be used directly, its mainly for CSV file to be printed out in a nice format

public class CSVBeutify {

    public static void printData(String Name, List<String[]> table){

        if (table.isEmpty()){

            return;
        }

        System.out.println("=======================================================================");
        System.out.println("=======================" + Name + "=====================");
        System.out.println("=======================================================================");

        int column = table.get(0).length;
        int[] colWidth = new int[column];

        for (String[] row : table){

            for (int i = 0; i < column; i++){
                String value;
                
                if (row[i] == null){
                    value = "";
                }else {
                    value = row[i];
                }
                int curWidth = colWidth[i];
                int valLength = value.length();

                if (valLength > curWidth){
                    colWidth[i] = valLength;
                }else {
                    colWidth[i] = curWidth;
                }
            }
        }

        for (int i = 0; i < column; i++){
            colWidth[i] = Math.min(colWidth[i] +3, 40);
        }

        String[] header = table.get(0);
        System.out.printf("%-5s", "#");
        //System.out.println("Column check" + Arrays.toString(colWidth));//for col check

        for (int y = 0; y < column; y++){
            System.out.printf("%-" + colWidth[y] + "s", header[y]);
        }

        int totalWidth = 5;
        for (int w: colWidth){
            totalWidth += w;
        }
        System.out.println();
        System.out.println("-".repeat(35));

        for (int r = 1; r < table.size(); r++){

            String[] rowData = table.get(r);
            System.out.printf("%-5d", r);

            for (int x = 0; x < column; x++){
                String val = rowData[x];
                
                if (val == null){
                    val = "";
                }

                if (val.length() > colWidth[x] - 3){
                    int cutOff = colWidth[x] - 3;
                    val = val.substring(0, cutOff) + "...";
                }

                System.out.printf("%-" + colWidth[x] + "s", val);
            }
            System.out.println();

        }

    }


    
}
