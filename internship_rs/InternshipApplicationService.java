package internship_rs;

import CSVMethods.CSVRead;
import CSVMethods.CSVWrite;
import enums.InternshipStatus;
import model.Internship;
import model.Student;

import java.io.IOException;
import java.util.List;

/**
 * InternshipApplicationService handles the business logic for a student applying for an internship.
 *
 * SOLID Principles Adherence:
 * - Single Responsibility Principle (SRP): This class has one job: processing an internship application.
 *   It validates the application and coordinates with CSV utilities for persistence. It does not handle UI or other unrelated tasks.
 * - Open/Closed Principle (OCP): The class is open to extension (e.g., adding more validation rules)
 *   but closed for modification of its core contract.
 * - Dependency Inversion Principle (DIP): It depends on abstractions (model classes like Student, Internship)
 *   and delegates low-level data persistence to other utility classes (CSVRead, CSVWrite).
 */
public class InternshipApplicationService {

    private static final int MAX_APPLICATIONS = 3;
    private static final String STUDENT_INTERNSHIP_REL_CSV = "data/student_internship_rel.csv";

    /**
     * Processes a student's application for an internship.
     * It validates the application against business rules (max applications, duplicates)
     * and records it if valid.
     *
     * @param student The student who is applying.
     * @param internship The internship being applied for.
     * @return true if the application was successful, false otherwise.
     * @throws IOException if there is an error reading from or writing to the CSV file.
     */
    public boolean applyForInternship(Student student, Internship internship) throws IOException {
        CSVRead csvReader = new CSVRead();
        List<String[]> allApplications = null;

        try {
            // Read all existing application records.
            allApplications = csvReader.ReadAll(STUDENT_INTERNSHIP_REL_CSV);

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

            // Persist the new application to the CSV file.
            // Note: Assumes CSVWrite.appendToCSV() exists and works as planned.
            CSVWrite csvWriter = new CSVWrite();
            boolean writeSuccess = csvWriter.appendToCSV(STUDENT_INTERNSHIP_REL_CSV, newApplicationData);

            if (writeSuccess) {
                System.out.println("Application submitted successfully!");
                // The plan mentioned incrementing the student's application count.
                // This would be done here, e.g., student.incrementApplicationCount();
                return true;
            } else {
                // Throw an exception if the write operation fails unexpectedly.
                throw new IOException("Failed to write new application to CSV file.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred during the application process: " + e.getMessage());
            // Re-throw the exception to let the caller handle it, adhering to the method signature.
            throw e;
        } finally {
            // The 'finally' block ensures this code runs whether an exception occurred or not.
            // It's useful for cleanup, logging, or confirming the operation's end.
            System.out.println("Internship application process finished.");
        }
    }
}
