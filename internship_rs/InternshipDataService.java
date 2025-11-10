package internship_rs;

import CSVMethods.CSVRead;
import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;
import model.Internship;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * This service is responsible for data operations related to internships,
 * such as reading them from the CSV file.
 */
public class InternshipDataService {

    private static final String INTERNSHIPS_CSV = "data/internships.csv";
    private static final int INTERNSHIP_CAPACITY = 10; // Based on APPROVAL_LIMIT

    /**
     * Reads all internship records from the CSV file and converts them into a list of Internship objects.
     *
     * @return A list of all internships.
     * @throws IOException if there is an error reading the file.
     */
    public List<Internship> readInternships() throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> records = csvReader.ReadAll(INTERNSHIPS_CSV);
        List<Internship> internships = new ArrayList<>();

        // Start from 1 to skip the header row.
        for (int i = 1; i < records.size(); i++) {
            try {
                String[] record = records.get(i);

                // CSV columns: ID,title,companyName,companyID,major,level,counter,openingDate,closingDate,status
                Internship internship = new Internship(
                        record[0],                                  // ID
                        record[1],                                  // title
                        record[2],                                  // companyName
                        record[3],                                  // companyID
                        Major.valueOf(record[4].toUpperCase()),     // major
                        InternshipLevel.valueOf(record[5].toUpperCase()), // level
                        Integer.parseInt(record[6]),                // counter
                        Date.valueOf(record[7]),                    // openingDate
                        Date.valueOf(record[8]),                    // closingDate
                        InternshipStatus.valueOf(record[9].toUpperCase()) // status
                );
                internships.add(internship);
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid record at row " + (i + 1) + ": " + e.getMessage());
            }
        }
        return internships;
    }

    /**
     * Reads the internships CSV and returns a single Internship object matching the given ID.
     *
     * @param internshipId The ID of the internship to find.
     * @return The Internship object if found, otherwise null.
     * @throws IOException if there is an error reading the file.
     */
    public Internship getInternshipById(String internshipId) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> records = csvReader.ReadAll(INTERNSHIPS_CSV);

        // Start from 1 to skip the header row.
        for (int i = 1; i < records.size(); i++) {
            String[] record = records.get(i);
            if (record[0].equals(internshipId)) {
                try {
                    // CSV columns: ID,title,companyName,companyID,major,level,counter,openingDate,closingDate,status
                    return new Internship(
                            record[0],
                            record[1],
                            record[2],
                            record[3],
                            Major.valueOf(record[4].toUpperCase()),
                            InternshipLevel.valueOf(record[5].toUpperCase()),
                            Integer.parseInt(record[6]),
                            Date.valueOf(record[7]),
                            Date.valueOf(record[8]),
                            InternshipStatus.valueOf(record[9].toUpperCase())
                    );
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing internship record with ID " + internshipId + ": " + e.getMessage());
                    return null; // Return null if the found record is malformed.
                }
            }
        }
        return null; // Return null if no internship with the given ID is found.
    }

    /**
     * Increments the counter for a given internship and updates its status to FILLED if capacity is reached.
     *
     * @param internshipId The ID of the internship to update.
     * @throws IOException if there is a file I/O error.
     */
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
            writeToCSV(INTERNSHIPS_CSV, allInternships);
        } else {
            // This case should ideally not happen if called after a successful offer acceptance.
            System.err.println("Warning: Could not find internship with ID " + internshipId + " to increment counter.");
        }
    }

    // Private helper to write data, ensuring this service is self-contained.
    private void writeToCSV(String filename, List<String[]> allData) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) { // 'false' for overwrite
            for (String[] row : allData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}
