package model;

import java.time.LocalDate;

import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;

public class Internship {

    private final String ID;
    private String title;
    private String companyName; // Company name based on ID within company table
    private String companyID; // Company Rep's GUID
    private Major major; // Prefered Major
    private InternshipLevel level; // BASIC, INTERMEDIATE, ADVANCED
    private int counter; // Count of internships confirmed
    private LocalDate openingDate;
    private LocalDate closingDate;
    private InternshipStatus status; // PENDING, APPROVED, REJECTED, FILLED
    private String description;

    public Internship(String ID, String title, String companyName, String companyID, Major major, InternshipLevel level,
            int counter,
            LocalDate openingDate, LocalDate closingDate, InternshipStatus status, String description) {
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
        this.description = description;
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

    public LocalDate getOpeningDate() {
        return this.openingDate;
    }

    public LocalDate getClosingDate() {
        return this.closingDate;
    }

    public InternshipStatus getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public void setLevel(InternshipLevel level) {
        this.level = level;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public void setStatus(InternshipStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}