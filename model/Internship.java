package model;

import java.sql.Date;

import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;

// ? Writing to csv shouldn't be called within the model
// ? Get the app to do it instead

public class Internship {

    private final String ID;
    private String title;
    private String companyName; // Company name based on ID within company table
    private String companyID; // Company's ID
    private Major major; // Prefered Major
    private InternshipLevel level; // BASIC, INTERMEDIATE, ADVANCED
    private int counter; // Count of internships confirmed
    private Date openingDate;
    private Date closingDate;
    private InternshipStatus status; // PENDING, APPROVED, REJECTED, FILLED

    public Internship(String ID, String title, String companyName, String companyID, Major major, InternshipLevel level,
            int counter,
            Date openingDate, Date closingDate, InternshipStatus status) {

        // ? Retrieve autonumber, create ID
        // * Or just count entries in csv and use that..
        // TODO: This becoming a POJO, move to service logic

        this.ID = ID;
        this.title = title;
        this.companyName = companyName;
        this.companyID = companyID;
        this.major = major;
        this.level = level;
        this.counter = counter;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.status = status;
    }

    public String getID() {
        return this.ID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyID() {
        return this.companyID;
    }

    public Major getMajor() {
        return this.major;
    }

    public InternshipLevel getLevel() {
        return this.level;
    }

    public int getCounter() {
        return this.counter;
    }

    public void incrementCounter() {
        this.counter = this.counter + 1;
        // Write to csv here
    }

    public Date getOpeningDate() {
        return this.openingDate;
    }

    public Date getClosingDate() {
        return this.closingDate;
    }

    public InternshipStatus getStatus() {
        return this.status;
    }

    public void updateTitle(String title) {
        this.title = title;
        // Remember to write to csv too
    }

    public void updateCompany(String companyID) {
        // Take in companyID, read from CSV and update this
        // ! Ensure the companyID exists!
        // Remember to write to csv too
    }

    public void updateMajor(Major major) {
        this.major = major;
        // Remember to write to csv too
    }

    // continue with other editable values

}
