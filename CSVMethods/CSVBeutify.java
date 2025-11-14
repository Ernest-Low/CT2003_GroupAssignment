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

    public static void BeutifyNewFilter(String title, List<?> objects, String sortByCol, String... fieldNames){

        if (objects.isEmpty()){
            System.out.println("No Data was parsed");
            return;
        }

        List<String[]> newData = new ArrayList<>();
        newData.add(fieldNames); //now the first row in the list is the new headers

        int sortColumnIndex = -1;
        for (int i = 0; i < fieldNames.length; i++){
             if (fieldNames[i].equalsIgnoreCase(sortByCol)){
                 sortColumnIndex = i;
                 break;
             }
         }

        for (Object obj : objects){

            String[] data = new String[fieldNames.length];//I want to create a data array based on no. of headers

            for (int i = 0; i < fieldNames.length; i++){
                data[i] = genericGetter(obj, fieldNames[i]);
            }
            newData.add(data);
        }

         int finalCol = sortColumnIndex != -1 ? sortColumnIndex : 0;
         //find the first data to the last and sort, between 2 var a , b. If sortColumnIndex not found then it will just sort by first Col
         newData.subList(1, newData.size()).sort((a, b) -> a[finalCol].compareToIgnoreCase(b[finalCol]));

        outTable(title, newData);
    }

    private static String genericGetter(Object obj, String fieldName){

        try {
            String getter = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            //^ this is used to create the getter without specifying what the getter is so it can be used anywhere
            //this part below is to find the public getfieldName() method !! important get method must be public
            java.lang.reflect.Method method = obj.getClass().getMethod(getter);
            Object value = method.invoke(obj);

            return value == null ? "" : value.toString(); //basically if value is null return "" if not convert the thing to String and return
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "*Not Found*";
        }

    }

    //by right no need to use, but you can try if u want?
    private static void outTable(String title, List<String[]> data){

        System.out.println("==================================\n" + title);
        System.out.println("==================================");
        String[] hValue = data.get(0);
        System.out.print("# ");
        for (String head : hValue){
            //first part is lean left + 15 char, second part is if header > 12 take the first 12 char and put ... at the back;
            String displayHead = head.length() > 18 ? head.substring(0,15) + "..." : head;
            System.out.printf("%-18s", displayHead);
        }
        System.out.println();
        System.out.println("-".repeat(hValue.length * 20 + 3));

        for (int i = 1; i < data.size(); i++){
            System.out.print(i + ". ");
            for (String val : data.get(i)){
                String displayHead = val.length() > 18 ? val.substring(0,15) + "..." : val;
                System.out.printf("%-18s", displayHead);
            }
            System.out.println();
        }
    }    
}
