package CSVMethods;

//generic imports
import java.util.*;

public class CSVBeutify2 {

    //the String... fieldNames is so that you can input as many fields as you need
    //usage is enter a title, the List Object and the fields u want to display 
    //example usage: BeutifyNewFilter("All Accounts", allUsers, "ID", "Name", "Status", "Email") you can keep adding more fields if u want
    //the ... part is supposed to let you keep adding stuff

    public static void BeutifyNewFilter(String title, List<?> objects, String... fieldNames){

        if (objects.isEmpty()){
            System.out.println("No Data was parsed");
            return;
        }

        List<String[]> newData = new ArrayList<>();
        newData.add(fieldNames); //now the first row in the list is the new headers

        for (Object obj : objects){

            String[] data = new String[fieldNames.length];//I want to create a data array based on no. of headers

            for (int i = 0; i < fieldNames.length; i++){
                data[i] = genericGetter(obj, fieldNames[i]);
            }
            newData.add(data);
        }
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
            System.out.printf("%-15s", head.length() > 12 ? head.substring(0,12) + "..." : head + "\n");
        }
        System.out.println("-".repeat(25));

        for (int i = 1; i < data.size(); i++){
            System.out.print(i + ". ");
            for (String val : data.get(i)){
                System.out.printf("%-15s", val.length() > 12 ? val.substring(0,12) + "..." : val + "\n\n");
            }
        }
    }
    
}
