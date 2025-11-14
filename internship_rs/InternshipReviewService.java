package internship_rs;

import java.time.LocalDate;

import CSVMethods.CSVRead;
import CSVMethods.CSVFilter;
import CSVMethods.CSVWrite;
import enums.InternshipApplicationStatus;
import model.Internship;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// this class is for the company rep to review applications
public class InternshipReviewService {

    private static final int APPROVAL_LIMIT = 10;
    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";

    // gets all applicants for one internship
    public List<String[]> getApplicantsForInternship(String internshipId) {
        CSVRead csvReader = new CSVRead();
        
        // 1. Read all data from the relationship CSV.
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

        // 2. Define the filter rule to get rows for the specific internship.
        List<String[]> filterRules = new ArrayList<>();
        filterRules.add(new String[]{"InternshipID", internshipId});

        // 3. Use CSVFilter to get only the relevant applications.
        List<String[]> filteredApplicants = CSVFilter.moreFilter(allApplications, filterRules);

        // The header row is included by moreFilter, which is good for display.
        return filteredApplicants;
    }

    // for company rep to approve or reject
    public boolean updateApplicationStatus(String studentId, String internshipId, InternshipApplicationStatus newStatus)
            throws IOException, ApprovalLimitExceededException {
        
        InternshipDataService dataService = new InternshipDataService();
        Internship internship = dataService.getInternshipById(internshipId);

        if (internship != null) {
            // Check if the closing date has passed.
            LocalDate today = LocalDate.now();
            if (today.isAfter(internship.getClosingDate())) {
                System.out.println("The application deadline for this internship has passed. All pending applications will be rejected.");
                rejectAllPendingApplications(internshipId);
                return false;
            } else {
                // deadline is ok, continue
                CSVRead csvReader = new CSVRead();
                List<String[]> allData = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
                boolean recordFound = false;

                // if trying to approve, check the limit first
                if (newStatus == InternshipApplicationStatus.SUCCESSFUL) {
                    int approvedCount = 0;
                    for(int i = 1; i < allData.size(); i++) { // Skip header
                        String[] row = allData.get(i);
                        if (row[1].equals(internshipId) && row[2].equals(InternshipApplicationStatus.SUCCESSFUL.toString())) {
                            approvedCount++;
                        }
                    }

                    if (approvedCount >= APPROVAL_LIMIT) {
                        throw new ApprovalLimitExceededException(
                            "Approval failed: Internship has reached its capacity of " + APPROVAL_LIMIT + " successful applicants."
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
                    CSVWrite.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allData);
                    System.out.println("Status updated successfully.");
                    return true;
                } else {
                    System.out.println("Update failed: Application for student " + studentId + " and internship " + internshipId + " not found.");
                    return false;
                }
            }
        } else {
            System.out.println("Error: Internship with ID " + internshipId + " not found.");
            return false;
        }
    }

    // new feature for company rep to reject everyone who is pending
    public void rejectAllApplicantsForInternship(String internshipId) throws IOException {
        System.out.println("ok, trying to reject all pending applicants for internship " + internshipId);
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
        boolean changesMade = false;

        // go thru all the applications
        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            
            String currentInternshipId = row[1];
            String currentStatus = row[2];

            // check if its the right internship and if the status is pending
            if (currentInternshipId.equals(internshipId) && currentStatus.equals(InternshipApplicationStatus.PENDING.toString())) {
                row[2] = InternshipApplicationStatus.UNSUCCESSFUL.toString(); // change to unsuccessful
                changesMade = true;
            }
        }

        if (changesMade) {
            CSVWrite.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);
            System.out.println("All pending application for this internship are rejected.");
        } else {
            System.out.println("No pending application to reject.");
        }
    }

    // reject all pending applications for an internship (when deadline passes)
    private void rejectAllPendingApplications(String internshipId) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
        boolean changesMade = false;

        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            // Check if it's the correct internship and the status is PENDING
            if (row[1].equals(internshipId) && row[2].equals(InternshipApplicationStatus.PENDING.toString())) {
                row[2] = InternshipApplicationStatus.UNSUCCESSFUL.toString();
                changesMade = true;
            }
        }

        if (changesMade) {
            CSVWrite.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);
            System.out.println("All pending applications for internship " + internshipId + " have been rejected.");
        }
    }
}
