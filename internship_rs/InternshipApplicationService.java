package internship_rs;

import CSVMethods.CSVRead;
import CSVMethods.CSVWrite;
import enums.InternshipStatus;
import model.Internship;
import model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;List;
import java.util.List;import java.util.List;

// this class is for the student to apply for internshipsor internships
public class InternshipApplicationService {public class InternshipApplicationService {

    private static final int MAX_APPLICATIONS = 3;
    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";

    // this gets all the internships from the csv
    public List<Internship> getAvailableInternships() throws IOException { {
        InternshipDataService dataService = new InternshipDataService();ew InternshipDataService();
        return dataService.readInternships();   return dataService.readInternships();
    }    }

    // new method for zhisheng to get all approved offers for a student
    public List<Internship> getApprovedOffers(Student student) throws IOException {ers(Student student) throws IOException {
        List<Internship> approved_internships = new ArrayList<>();
        
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);lications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

        InternshipDataService dataService = new InternshipDataService();ataService();
        List<Internship> allInternships = dataService.readInternships(); // get all internship detailsInternships(); // get all internship details

        // go through all applications
        for (int i = 1; i < allApplications.size(); i++) {
            String[] application = allApplications.get(i);ations.get(i);
            String studentId = application[0];Id = application[0];
            String internshipId = application[1];g internshipId = application[1];
            String status = application[2];tring status = application[2];

            // if its for this student and the status is approved            // if its for this student and the status is approved
            if (studentId.equals(student.getId()) && status.equals("APPROVED")) {s(student.getId()) && status.equals("APPROVED")) {
                // find the full internship details
                for (Internship internship : allInternships) {rnship internship : allInternships) {
                    if (internship.getID().equals(internshipId)) {           if (internship.getID().equals(internshipId)) {
                        approved_internships.add(internship);                        approved_internships.add(internship);
                        break;
                    }
                }
            }
        }
        return approved_internships;
    }

    // zhisheng can use this to accept internshipe this to accept internship
    // this is for the student to accept an offers for the student to accept an offer
    public boolean acceptInternshipOffer(Student student, Internship internship) throws IOException {c boolean acceptInternshipOffer(Student student, Internship internship) throws IOException {
        CSVRead csvReader = new CSVRead();        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);ations = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
        boolean already_accepted = false;
        boolean offer_is_approved = false;
        int recordIndex = -1;        int recordIndex = -1;

        // check if student already accepted something else
        for (int i = 1; i < allApplications.size(); i++) {        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);(i);
            if (row[0].equals(student.getId())) {
                if (row[2].equals(InternshipStatus.ACCEPTED.toString())) {) {
                    already_accepted = true;                    already_accepted = true;
                    break;
                }
            }
        }

        if (already_accepted) {f (already_accepted) {
            System.out.println("Not allowed as you have accepted a internship offer");       System.out.println("Not allowed as you have accepted a internship offer");
            return false;            return false;
        }

        // now check if the offer they want to accept is approvedt to accept is approved
        for (int i = 1; i < allApplications.size(); i++) {ze(); i++) {
            String[] row = allApplications.get(i);            String[] row = allApplications.get(i);
            if (row[0].equals(student.getId()) && row[1].equals(internship.getID())) {f (row[0].equals(student.getId()) && row[1].equals(internship.getID())) {
                if (row[2].equals(InternshipStatus.APPROVED.toString())) {PROVED.toString())) {
                    offer_is_approved = true;
                    recordIndex = i; // save the line number                    recordIndex = i; // save the line number
                }
                break;
            }
        }

        if (offer_is_approved) {
            // update the status to ACCEPTED            // update the status to ACCEPTED
            allApplications.get(recordIndex)[2] = "ACCEPTED";

            // write it back to the file
            writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);

            // update the main internship list
            InternshipDataService dataService = new InternshipDataService();pDataService dataService = new InternshipDataService();
            dataService.incrementInternshipCounter(internship.getID());shipCounter(internship.getID());

            System.out.println("Offer for '" + internship.getTitle() + "' accepted successfully!");ystem.out.println("Offer for '" + internship.getTitle() + "' accepted successfully!");
            return true;            return true;
        } else {
            System.out.println("Offer acceptance failed: The offer is not in an approved state or does not exist.");: The offer is not in an approved state or does not exist.");
            return false;
        }
    }

    // this is for applying to an internship
    public boolean applyForInternship(Student student, Internship internship) throws IOException {nt student, Internship internship) throws IOException {
        CSVRead csvReader = new CSVRead();CSVRead();
        List<String[]> allApplications = null;ons = null;

        try {
            // Read all existing application records.            // Read all existing application records.
            allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);ERNSHIP_REL_CSV);

            int applicationCount = 0;
            // Skip header row (i=1) and iterate through records.
            for (int i = 1; i < allApplications.size(); i++) {            for (int i = 1; i < allApplications.size(); i++) {
                String[] record = allApplications.get(i); = allApplications.get(i);
                String recordStudentId = record[0];
                String recordInternshipId = record[1];dInternshipId = record[1];

                // Check if the record belongs to the current student.
                if (recordStudentId.equals(student.getId())) {
                    // Validation 1: Check for duplicate application.       // Validation 1: Check for duplicate application.
                    if (recordInternshipId.equals(internship.getID())) {                    if (recordInternshipId.equals(internship.getID())) {
                        System.out.println("Application failed: You have already applied for this internship.");t.println("Application failed: You have already applied for this internship.");
                        return false; // Application is a duplicate.
                    }
                    applicationCount++;applicationCount++;
                }
            }

            // Validation 2: Check if student has reached the application limit.   // Validation 2: Check if student has reached the application limit.
            if (applicationCount >= MAX_APPLICATIONS) {       if (applicationCount >= MAX_APPLICATIONS) {
                System.out.println("Application failed: You have reached the maximum of " + MAX_APPLICATIONS + " applications.");                System.out.println("Application failed: You have reached the maximum of " + MAX_APPLICATIONS + " applications.");
                return false; // Exceeded application limit.xceeded application limit.
            }

            // If all validations pass, create a new application record.eate a new application record.
            String[] newApplicationData = {
                student.getId(),(),
                internship.getID(),   internship.getID(),
                InternshipStatus.PENDING.toString()       InternshipStatus.PENDING.toString()
            };       };

            // Persist the new application to the CSV file.            // Persist the new application to the CSV file.


































}    }        }            }                bw.newLine();                bw.write(String.join(",", row));            for (String[] row : allData) {        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) { // 'false' for overwrite    private void writeToCSV(String filename, List<String[]> allData) throws IOException {    // this writes to the csv file    }        }            System.out.println("Internship application process finished.");            // This block runs whether an exception occurred or not.        } finally {            throw e;            // Re-throw the exception to let the caller handle it.            System.err.println("An error occurred during the application process: " + e.getMessage());        } catch (IOException e) {            }                throw new IOException("Failed to write new application to CSV file.");                // Throw an exception if the write operation fails unexpectedly.            } else {                return true;                System.out.println("Application submitted successfully!");            if (writeSuccess) {            boolean writeSuccess = csvWriter.appendToCSV(STUDENT_INTERNSHIP_REL_CSV, newApplicationData);            // this appendToCSV method needs to be created in CSVWrite.java            CSVWrite csvWriter = new CSVWrite();            CSVWrite csvWriter = new CSVWrite();
            // this appendToCSV method needs to be created in CSVWrite.java
            boolean writeSuccess = csvWriter.appendToCSV(STUDENT_INTERNSHIP_REL_CSV, newApplicationData);

            if (writeSuccess) {
                System.out.println("Application submitted successfully!");
                return true;
            } else {
                // Throw an exception if the write operation fails unexpectedly.
                throw new IOException("Failed to write new application to CSV file.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred during the application process: " + e.getMessage());
            // Re-throw the exception to let the caller handle it.
            throw e;
        } finally {
            // This block runs whether an exception occurred or not.
            System.out.println("Internship application process finished.");
        }
    }

    // this writes to the csv file
    private void writeToCSV(String filename, List<String[]> allData) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) { // 'false' for overwrite
            for (String[] row : allData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}