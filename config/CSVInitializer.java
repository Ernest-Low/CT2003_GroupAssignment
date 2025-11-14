package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import constants.CSVPaths;

public class CSVInitializer {

    // ! Update this if any of the headers change!!!
    // * Same goes if you add more CSV models
    private static final Map<String, String> CSV_HEADERS = Map.of(
            CSVPaths.STUDENTS_CSV, "GUID,ID,name,yearOfStudy,major",
            CSVPaths.CAREER_STAFF_CSV, "GUID,ID,name,staffDepartment",
            CSVPaths.COMPANY_REPS_CSV, "GUID,ID,name,companyName,department,position",
            CSVPaths.CREDENTIALS_CSV, "ID,salt,hash,usertype,AccountStatus",
            CSVPaths.AUTONUMBERS_CSV, "ID,tableIndex,prefix,currentValue,suffix,incrementStep,paddingLength",
            CSVPaths.INTERNSHIPS_CSV,
            "ID,title,companyName,companyID,major,level,counter,slot,openingDate,closingDate,status,description",
            CSVPaths.INTERNSHIP_APPLICATION_CSV, "StudentID,InternshipID,Status");

    public static void initializeCSVs() {
        CSV_HEADERS.forEach((pathStr, headers) -> {
            Path path = Paths.get(pathStr);
            try {
                if (Files.notExists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }
                if (Files.notExists(path)) {
                    Files.writeString(path, headers + System.lineSeparator());
                    System.out.println("Created CSV: " + pathStr);
                }
            } catch (IOException e) {
                System.err.println("Failed to create CSV: " + pathStr);
                e.printStackTrace();
            }
        });
    }
}

// ! Base for AutoNumber CSV should it be missing
// ID,tableIndex,prefix,currentValue,suffix,incrementStep,paddingLength
// 1,STUDENT,U,1000000,T,1,7
// 2,COMPANYREP,C,0,R,1,6
// 3,CAREERSTAFF,C,0,S,1,6
// 4,CREDENTIAL,C,0,L,1,6
// 5,INTERNSHIP,I,0,P,1,6
// 6,INTERNSHIPAPP,I,0,A,1,6