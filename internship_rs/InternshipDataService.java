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

    // reads all internships from the csv
    public List<Internship> readInternships() throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> records = csvReader.ReadAll(INTERNSHIPS_CSV);
        List<Internship> internships = new ArrayList<>();

        // Start from 1 to skip the header row.
        for (int i = 1; i < records.size(); i++) {
            String[] record = records.get(i);

            // CSV columns: ID,title,companyName,companyID,major,level,counter,slots,openingDate,closingDate,status,description
            Internship internship = new Internship(
                    record[0],                                  // ID
                    record[1],                                  // title
                    record[2],                                  // companyName
                    record[3],                                  // companyID
                    Major.valueOf(record[4].toUpperCase()),     // major
                    InternshipLevel.valueOf(record[5].toUpperCase()), // level
                    Integer.parseInt(record[6]),                // counter
                    Integer.parseInt(record[7]),                // slots
                    LocalDate.parse(record[8]),                    // openingDate
                    LocalDate.parse(record[9]),                    // closingDate
                    InternshipStatus.valueOf(record[10].toUpperCase()), // status
                    record[11]                                  // description
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
                // CSV columns: ID,title,companyName,companyID,major,level,counter,slots,openingDate,closingDate,status,description
                return new Internship(
                        record[0],
                        record[1],
                        record[2],
                        record[3],
                        Major.valueOf(record[4].toUpperCase()),
                        InternshipLevel.valueOf(record[5].toUpperCase()),
                        Integer.parseInt(record[6]),
                        Integer.parseInt(record[7]),
                        LocalDate.parse(record[8]),
                        LocalDate.parse(record[9]),
                        InternshipStatus.valueOf(record[10].toUpperCase()),
                        record[11]
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
                // Columns: 6=counter, 7=slots, 10=status
                int currentCounter = Integer.parseInt(row[6]);
                int slots = Integer.parseInt(row[7]);
                int newCounter = currentCounter + 1;
                row[6] = String.valueOf(newCounter); // Update counter

                if (newCounter == slots) {
                    row[10] = InternshipStatus.FILLED.toString(); // Update status
                }
                recordFound = true;
                break;
            }
        }

        if (recordFound) {
            CSVWrite.writeToCSV(INTERNSHIPS_CSV, allInternships);
        } else {
            // This case should ideally not happen if called after a successful offer acceptance.
            System.err.println("Warning: Could not find internship with ID " + internshipId + " to increment counter.");
        }
    }
}
