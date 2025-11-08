package services;

import java.time.LocalDate;

import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;
import model.Internship;
import repositories.InternshipRepo;

// import enums.Major;

public class InternshipService {

    private final InternshipRepo internshipRepo;

    public InternshipService(InternshipRepo internshipRepo) {
        this.internshipRepo = internshipRepo;
    }

    public void createInternship(String ID, String title, String companyName, String companyID, Major major,
            InternshipLevel level,
            LocalDate openingDate, LocalDate closingDate, String description) {
        Internship internship = new Internship(ID, title, companyName, companyID, major, level, 0, openingDate,
                closingDate, InternshipStatus.PENDING, description);
        internshipRepo.save(internship);
    }

    // ? Retrieve autonumber, create ID
    // * Or just count entries in csv and use that..
    // TODO: Utilize autonumber to get ID to create

    // public void incrementCounter() {
    // this.counter = this.counter + 1;
    // // Write to csv here
    // }

    // public void updateCompany(String companyID) {
    //     // Take in companyID, read from CSV and update this
    //     // ! Ensure the companyID exists!
    //     // Remember to write to csv too
    // }

    // public void updateMajor(Major major) {
    //     this.major = major;
    //     // Remember to write to csv too
    // }

    // continue with other editable values

}
