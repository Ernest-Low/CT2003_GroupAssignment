package internship_rs;

import CSVMethods.CSVRead;
import CSVMethods.CSVWrite;
import dtos.InternshipFilter;
import enums.InternshipApplicationStatus;
import enums.InternshipStatus;
import model.Internship;
import model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



// this class is for the student to apply for internships 
// this class handles the application logic, restrictions and validations of the code. 
public class InternshipApplicationService {

    private static final int MAX_APPLICATIONS = 3;
    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";

    public List<Internship> getFilteredInternships(Student student, InternshipFilter filter) throws IOException {
        InternshipDataService dataService = new InternshipDataService();
        List<Internship> allInternships = dataService.readInternships();
        
        // Create an empty list to hold the internships we find.
        List<Internship> filteredList = new ArrayList<>();

        // Go through each internship one by one.
        for (Internship internship : allInternships) {
            
            // Check 1: Is the internship open for applications? (Must be APPROVED or PENDING)
            boolean isOpen = internship.getStatus() == InternshipStatus.APPROVED || internship.getStatus() == InternshipStatus.PENDING;

            // Check 2: Does the internship's major match the student's major?
            boolean isCorrectMajor = internship.getMajor() == student.getMajor();

            // Check 3: Is the internship's level one of the levels the student wants to see?
            boolean isDesiredLevel = filter.getLevels().isEmpty() || filter.getLevels().contains(internship.getLevel());

            // Check 4: Closing date filter
            boolean afterOk = filter.getClosingDateAfter() == null || !internship.getClosingDate().isBefore(filter.getClosingDateAfter());
            boolean beforeOk = filter.getClosingDateBefore() == null || !internship.getClosingDate().isAfter(filter.getClosingDateBefore());

            // If all checks are true, add the internship to our list.
            if (isOpen && isCorrectMajor && isDesiredLevel && afterOk && beforeOk) {
                filteredList.add(internship);
            }
        }

        // Return the final list of matching internships.
        return filteredList;
    }

    // new method for zhisheng to get all approved offers for a student
    public List<Internship> getApprovedOffers(Student student) throws IOException {
        List<Internship> successful_internships = new ArrayList<>();
        
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

            // if its for this student and the status is successful
            if (studentId.equals(student.getId()) && status.equalsIgnoreCase(InternshipApplicationStatus.SUCCESSFUL.toString())) {
                // find the full internship details
                for (Internship internship : allInternships) {
                    if (internship.getID().equals(internshipId)) {
                        successful_internships.add(internship);
                        break;
                    }
                }
            }
        }
        return successful_internships;
    }

    // zhisheng can use this to accept internship
    // this is for the student to accept an offer
    public boolean acceptInternshipOffer(Student student, Internship internship) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);
        boolean alreadyAccepted = false;
        boolean offerIsSuccessful = false;
        int recordIndex = -1;

        // check if student already accepted something else
        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            if (row[0].equals(student.getId())) {
                if (row[2].equalsIgnoreCase(InternshipApplicationStatus.ACCEPTED.toString())) {
                    alreadyAccepted = true;
                    break;
                }
            }
        }

        if (alreadyAccepted) {
            System.out.println("Not allowed as you have already accepted an internship offer.");
            return false;
        }

        // now check if the offer they want to accept is successful
        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            if (row[0].equals(student.getId()) && row[1].equals(internship.getID())) {
                if (row[2].equalsIgnoreCase(InternshipApplicationStatus.SUCCESSFUL.toString())) {
                    offerIsSuccessful = true;
                    recordIndex = i; // save the line number
                }
                // Removed break to correctly check all applications for the student
            }
        }

        if (offerIsSuccessful) {
            // update the status to ACCEPTED
            allApplications.get(recordIndex)[2] = InternshipApplicationStatus.ACCEPTED.toString();

            // Perform post-acceptance tasks (e.g., withdraw other applications)
            // We pass the modified list to avoid re-reading the file
            processPostAcceptanceTasks(student, allApplications);

            // write it back to the file using the public method
            CSVWrite.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);

            // update the main internship list
            InternshipDataService dataService = new InternshipDataService();
            dataService.incrementInternshipCounter(internship.getID());

            System.out.println("Offer for '" + internship.getTitle() + "' accepted successfully!");
            System.out.println("Your other pending and successful applications have been withdrawn.");
            return true;
        } else {
            System.out.println("Offer acceptance failed: The offer is not in a successful state or does not exist.");
            return false;
        }
    }


    private void processPostAcceptanceTasks(Student student, List<String[]> allApplications) {
        // Iterate through all applications and withdraw any others from the same student
        for (int i = 1; i < allApplications.size(); i++) {
            String[] row = allApplications.get(i);
            String studentId = row[0];
            String status = row[2];

            // Check if it's an application from the same student
            if (studentId.equals(student.getId())) {
                // Check if the status is PENDING or SUCCESSFUL (but not the one just ACCEPTED)
                boolean isPending = status.equalsIgnoreCase(InternshipApplicationStatus.PENDING.toString());
                boolean isSuccessful = status.equalsIgnoreCase(InternshipApplicationStatus.SUCCESSFUL.toString());

                if (isPending || isSuccessful) {
                    row[2] = InternshipApplicationStatus.UNSUCCESSFUL.toString(); // Set to UNSUCCESSFUL
                }
            }
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
            InternshipApplicationStatus.PENDING.toString()
        };

        // Add the new application to the list in memory
        allApplications.add(newApplicationData);

        // Now, write the entire updated list back to the CSV file.
        CSVWrite.writeToCSV(STUDENT_INTERNSHIP_REL_CSV, allApplications);

        System.out.println("Application submitted successfully!");
        System.out.println("Internship application process finished.");
        return true;
    }

    public List<Internship> getAvailableInternshipsForStudent(Student student) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableInternshipsForStudent'");
    }
}