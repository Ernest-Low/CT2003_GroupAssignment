package internship_rs;

import CSVMethods.CSVRead;
import CSVMethods.CSVWrite;
import enums.InternshipApplicationStatus;
import enums.InternshipStatus;
import model.Internship;
import model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// this class is for the student to apply for internships
public class InternshipApplicationService {

    private static final int MAX_APPLICATIONS = 3;
    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";

    // this gets all the internships from the csv
    public List<Internship> getAvailableInternships() throws IOException {
        InternshipDataService dataService = new InternshipDataService();
        return dataService.readInternships();
    }

    // new method for zhisheng to get all approved offers for a student
    public List<Internship> getApprovedOffers(Student student) throws IOException {
        List<Internship> approved_internships = new ArrayList<>();
        
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

        InternshipDataService dataService = new InternshipDataService();
        List<Internship> allInternships = dataService.readInternships(); // get all internship details

        // go through all applications
        for (int i = 1; i < allApplications.size(); i++) {
            String[] application = allApplications.get(i);
            String studentId = application[0];
            String internshipId = application[1];
            String status = application[2];

            // if its for this student and the status is approved
            if (studentId.equals(student.getId()) && status.equals("APPROVED")) {
                // find the full internship details
                for (Internship internship : allInternships) {
                    if (internship.getID().equals(internshipId)) {
                        approved_internships.add(internship);
                        break;
                    }
                }
            }
        }
        return approved_internships;
    }

    // zhisheng can use this to accept internship
    // this is for the student to accept an offer
    public boolean acceptInternshipOffer(Student student, Internship internship) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
        boolean already_accepted = false;
        boolean offer_is_approved = false;
        int recordIndex = -1;

        // check if student already accepted something else
        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            if (row[0].equals(student.getId())) {
                if (row[2].equals(InternshipApplicationStatus.ACCEPTED.toString())) {
                    already_accepted = true;
                    break;
                }
            }
        }

        if (already_accepted) {
            System.out.println("Not allowed as you have accepted a internship offer");
            return false;
        }

        // now check if the offer they want to accept is approved
        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            if (row[0].equals(student.getId()) && row[1].equals(internship.getID())) {
                if (row[2].equals(InternshipApplicationStatus.APPROVED.toString())) {
                    offer_is_approved = true;
                    recordIndex = i; // save the line number
                }
                break;
            }
        }

        if (offer_is_approved) {
            // update the status to ACCEPTED
            allApplications.get(recordIndex)[2] = "ACCEPTED";

            // write it back to the file using the public method
            CSVWrite csvWriter = new CSVWrite();
            csvWriter.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);

            // update the main internship list
            InternshipDataService dataService = new InternshipDataService();
            dataService.incrementInternshipCounter(internship.getID());

            System.out.println("Offer for '" + internship.getTitle() + "' accepted successfully!");
            return true;
        } else {
            System.out.println("Offer acceptance failed: The offer is not in an approved state or does not exist.");
            return false;
        }
    }

    // this is for applying to an internship
    public boolean applyForInternship(Student student, Internship internship) {
        CSVRead csvReader = new CSVRead();
        
        // Read all existing application records.
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

        int applicationCount = 0;
        // Skip header row (i=1) and iterate through records.
        for (int i = 1; i < allApplications.size(); i++) {
            String[] record = allApplications.get(i);
            String recordStudentId = record[0];
            String recordInternshipId = record[1];

            // Check if the record belongs to the current student.
            if (recordStudentId.equals(student.getId())) {
                // Validation 1: Check for duplicate application.
                if (recordInternshipId.equals(internship.getID())) {
                    System.out.println("Application failed: You have already applied for this internship.");
                    return false; // Application is a duplicate.
                }
                applicationCount++;
            }
        }

        // Validation 2: Check if student has reached the application limit.
        if (applicationCount >= MAX_APPLICATIONS) {
            System.out.println("Application failed: You have reached the maximum of " + MAX_APPLICATIONS + " applications.");
            return false; // Exceeded application limit.
        }

        // If all validations pass, create a new application record.
        String[] newApplicationData = {
            student.getId(),
            internship.getID(),
            InternshipStatus.PENDING.toString()
        };

        // Add the new application to the list in memory
        allApplications.add(newApplicationData);

        // Now, write the entire updated list back to the CSV file.
        CSVWrite csvWriter = new CSVWrite();
        csvWriter.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);

        System.out.println("Application submitted successfully!");
        System.out.println("Internship application process finished.");
        return true;
    }
}