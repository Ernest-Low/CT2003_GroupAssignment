package internship_rs;

import CSVMethods.CSVRead;
import CSVMethods.CSVFilter;
import enums.InternshipStatus;
import model.Internship;
import model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//This service handles the business logic for a company rep to review internship applications.
public class InternshipReviewService {

    private static final int APPROVAL_LIMIT = 10;
    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";

    /**
     * Fetches all applications for a specific internship.
     * This method correctly uses CSVRead and CSVFilter to select and return data.
     *
     * @param internshipId The ID of the internship to fetch applicants for.
     * @return A List of String arrays, where each array is a row from the CSV representing an applicant.
     */
    public List<String[]> getApplicantsForInternship(String internshipId) {
        CSVRead csvReader = new CSVRead();
        
        // 1. Read all data from the relationship CSV.
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

        // 2. Define the filter rule to get rows for the specific internship.
        List<String[]> filterRules = new ArrayList<>();
        filterRules.add(new String[]{"InternshipID", internshipId});

        // 3. Use CSVFilter to get only the relevant applications.
        List<String[]> filteredApplicants = CSVFilter.moreFilter(allApplications, filterRules);

        // 4. Return the filtered list to be displayed in the menu.
        // The header row is included by moreFilter, which is good for display.
        return filteredApplicants;
    }

    /**
     * Updates the status of a specific internship application.
     *
     * @param studentId The ID of the student whose application is being updated.
     * @param internshipId The ID of the internship.
     * @param newStatus The new status to set (APPROVED or REJECTED).
     * @return true if the update was successful, false if the application was not found.
     * @throws IOException if there is a file I/O error.
     * @throws ApprovalLimitExceededException if an approval would exceed the internship's capacity.
     */
    public boolean updateApplicationStatus(String studentId, String internshipId, InternshipStatus newStatus)
            throws IOException, ApprovalLimitExceededException {
        
        InternshipDataService dataService = new InternshipDataService();
        Internship internship = dataService.getInternshipById(internshipId);

        if (internship == null) {
            System.out.println("Error: Internship with ID " + internshipId + " not found.");
            return false;
        }

        // Check if the closing date has passed.
        Date today = new Date();
        if (today.after(internship.getClosingDate())) {
            System.out.println("The application deadline for this internship has passed. All pending applications will be rejected.");
            rejectAllPendingApplications(internshipId);
            return false;
        }

        CSVRead csvReader = new CSVRead();
        List<String[]> allData = null;
        boolean recordFound = false;

        try {
            allData = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

            // Validation: Check approval limit only if the new status is APPROVED.
            if (newStatus == InternshipStatus.APPROVED) {
                int approvedCount = 0;
                for(int i = 1; i < allData.size(); i++) { // Skip header
                    String[] row = allData.get(i);
                    if (row[1].equals(internshipId) && row[2].equals(InternshipStatus.APPROVED.toString())) {
                        approvedCount++;
                    }
                }

                if (approvedCount >= APPROVAL_LIMIT) {
                    throw new ApprovalLimitExceededException(
                        "Approval failed: Internship has reached its capacity of " + APPROVAL_LIMIT + " approved applicants."
                    );
                }
            }

            // Find and update the specific record in memory.
            for (int i = 1; i < allData.size(); i++) { // Start from 1 to skip header
                String[] row = allData.get(i);
                if (row[0].equals(studentId) && row[1].equals(internshipId)) {
                    row[2] = newStatus.toString();
                    recordFound = true;
                    break; // Exit loop once the record is found and updated.
                }
            }

            // If the record was found, overwrite the CSV file with the updated data.
            if (recordFound) {
                writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allData);
                System.out.println("Status updated successfully.");
                return true;
            } else {
                System.out.println("Update failed: Application for student " + studentId + " and internship " + internshipId + " not found.");
                return false;
            }

        } catch (IOException e) {
            System.err.println("Error during file operation: " + e.getMessage());
            throw e; // Re-throw exception for the caller to handle.
        } finally {
            System.out.println("Internship review process has concluded.");
        }
    }

    /**
     * Finds all PENDING applications for a given internship and updates their status to REJECTED.
     *
     * @param internshipId The ID of the internship whose applications are to be rejected.
     * @throws IOException if there is a file I/O error.
     */
    private void rejectAllPendingApplications(String internshipId) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
        boolean changesMade = false;

        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            // Check if it's the correct internship and the status is PENDING
            if (row[1].equals(internshipId) && row[2].equals(InternshipStatus.PENDING.toString())) {
                row[2] = InternshipStatus.REJECTED.toString();
                changesMade = true;
            }
        }

        if (changesMade) {
            writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);
            System.out.println("All pending applications for internship " + internshipId + " have been rejected.");
        }
    }

    /**
     * A private helper method to write a list of string arrays to a CSV file, overwriting its contents.
     */
    private void writeToCSV(String filename, List<String[]> allData) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) { // 'false' for overwrite
            for (String[] row : allData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}
