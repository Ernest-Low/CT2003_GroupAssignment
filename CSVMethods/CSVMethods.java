package CSVMethods;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import enums.Major;
import enums.UserType;
import model.Student;
import model.CompanyRep;
import model.Credential;
import model.CareerStaff;

public class CSVMethods {

    private static final String COMMA_DELIMITER = ",";
    private static final String STUDENT_CSV_FILE = "data/students_list.csv";
    private static final String STAFF_CSV_FILE = "data/careerStaff_list.csv";
    private static final String COMPANYREP_CSV_FILE = "data/companyReps_list.csv";
    private static final String CREDENTIALS_CSV_FILE = "data/credentials_list.csv";

    // String ID, String name, int yearOfStudy, Major major
    public static List<Student> readStudents() {
        List<List<String>> records = new ArrayList<>();
        records = pullFromCSV(STUDENT_CSV_FILE);
        records.remove(0);
        List<Student> students = records.stream()
                .map(list -> new Student(
                        list.get(0),
                        list.get(1),
                        Integer.parseInt(list.get(2)),
                        Major.valueOf(list.get(3))))
                .collect(Collectors.toList());
        return students;
    }

    // String ID, String name, String companyName, String department, String
    // position
    public static List<CompanyRep> readCompanyRep() {
        List<List<String>> records = new ArrayList<>();
        records = pullFromCSV(COMPANYREP_CSV_FILE);
        records.remove(0);
        List<CompanyRep> companyReps = records.stream()
                .map(list -> new CompanyRep(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4)))
                .collect(Collectors.toList());
        return companyReps;
    }

    // String id, String salt, String hash
    public static List<Credential> readCredentials() {
        List<List<String>> records = new ArrayList<>();
        records = pullFromCSV(CREDENTIALS_CSV_FILE);
        List<Credential> credentials = records.stream()
                .map(list -> new Credential(
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        UserType.valueOf(list.get(3))))

                .collect(Collectors.toList());
        return credentials;
    }

    // String ID, String name, String staffDepartment
    public static List<CareerStaff> readCareerStaff() {
        List<List<String>> records = new ArrayList<>();
        records = pullFromCSV(STAFF_CSV_FILE);
        List<CareerStaff> careerStaff = records.stream()
                .map(list -> new CareerStaff(
                        list.get(0),
                        list.get(1),
                        list.get(2)))
                .collect(Collectors.toList());
        return careerStaff;
    }

    public static List<List<String>> pullFromCSV(String filepath) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
            System.out.println("Properly returning records");
            return records;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Error happened and it's probably empty records");
        return records; // This be empty if it fails tho, so handle on the other side
    }

    // public static void main(String[] args) {
    // try {
    // List<Student> students = readStudents();
    // System.out.println("ID: " + students.get(0).getId());
    // System.out.println("Name: " + students.get(0).getName());
    // System.out.println("YearOfStudy: " + students.get(0).getYearOfStudy());
    // System.out.println("Major: " + students.get(0).getMajor());
    // } catch (Exception e) {
    // System.out.println("Error: " + e);
    // }

    // }
}