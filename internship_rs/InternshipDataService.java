package internship_rs;

import CSVMethods.CSVRead;
import CSVMethods.CSVWrite;
import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;
import model.Internship;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// this class is for getting data about internships
public class InternshipDataService {

    private static final String INTERNSHIPS_CSV = "data/internships.csv";
    private static final int INTERNSHIP_CAPACITY = 10; // Based on APPROVAL_LIMIT

    // reads all internships from the csv
    public List<Internship> readInternships() throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> records = csvReader.ReadAll(INTERNSHIPS_CSV);
        List<Internship> internships = new ArrayList<>();

        // Start from 1 to skip the header row.
        for (int i = 1; i < records.size(); i++) {
            String[] record = records.get(i);

            // CSV columns: ID,title,companyName,companyID,major,level,counter,openingDate,closingDate,status,description
            Internship internship = new Internship(
                    record[0],                                  // ID
                    record[1],                                  // title
                    record[2],                                  // companyName
                    record[3],                                  // companyID
                    Major.valueOf(record[4].toUpperCase()),     // major
                    InternshipLevel.valueOf(record[5].toUpperCase()), // level
                    Integer.parseInt(record[6]),                // counter
                    LocalDate.parse(record[7]),                    // openingDate
                    LocalDate.parse(record[8]),                    // closingDate
                    InternshipStatus.valueOf(record[9].toUpperCase()), // status
                    record[10]                                  // description
            );
            internships.add(internship);
        }
        return internships;
    }

    // get one internship by its id
    public Internship getInternshipById(String internshipId) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> records = csvReader.ReadAll(INTERNSHIPS_CSV);

        // Start from 1 to skip the header row.
        for (int i = 1; i < records.size(); i++) {
            String[] record = records.get(i);
            if (record[0].equals(internshipId)) {
                // CSV columns: ID,title,companyName,companyID,major,level,counter,openingDate,closingDate,status,description
                return new Internship(
                        record[0],
                        record[1],
                        record[2],
                        record[3],
                        Major.valueOf(record[4].toUpperCase()),
                        InternshipLevel.valueOf(record[5].toUpperCase()),
                        Integer.parseInt(record[6]),
                        LocalDate.parse(record[7]),
                        LocalDate.parse(record[8]),
                        InternshipStatus.valueOf(record[9].toUpperCase()),
                        record[10]
                );
            }
        }
        return null; // Return null if no internship with the given ID is found.
    }

    // add 1 to the counter for an internship
    public void incrementInternshipCounter(String internshipId) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> allInternships = csvReader.ReadAll(INTERNSHIPS_CSV);
        boolean recordFound = false;

        // Find the internship, update its counter, and check if it's now filled.
        for (int i = 1; i < allInternships.size(); i++) {
            String[] row = allInternships.get(i);
            if (row[0].equals(internshipId)) {
                // Columns: 6=counter, 9=status
                int currentCounter = Integer.parseInt(row[6]);
                int newCounter = currentCounter + 1;
                row[6] = String.valueOf(newCounter); // Update counter

                if (newCounter >= INTERNSHIP_CAPACITY) {
                    row[9] = InternshipStatus.FILLED.toString(); // Update status
                }
                recordFound = true;
                break;
            }
        }

        if (recordFound) {
            CSVWrite csvWriter = new CSVWrite();
            csvWriter.writeToCSV(INTERNSHIPS_CSV, allInternships);
        } else {
            // This case should ideally not happen if called after a successful offer acceptance.
            System.err.println("Warning: Could not find internship with ID " + internshipId + " to increment counter.");
        }
    }
}
