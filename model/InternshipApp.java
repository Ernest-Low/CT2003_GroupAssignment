package model;

import enums.InternshipApplicationStatus;

public class InternshipApp {

    private final String studentID;
    private final String internshipID;
    private InternshipApplicationStatus status;

    public InternshipApp(String studentID, String internshipID, InternshipApplicationStatus status) {
        this.studentID = studentID;
        this.internshipID = internshipID;
        this.status = status;
    }


    public String getStudentID() {
        return this.studentID;
    }

    public String getInternshipID() {
        return this.internshipID;
    }

    public InternshipApplicationStatus getStatus() {
        return this.status;
    }

    public void setStatus(InternshipApplicationStatus status) {
        this.status = status;
    }

}