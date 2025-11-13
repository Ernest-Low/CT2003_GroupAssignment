package dtos;

import java.util.Set;

import enums.InternshipApplicationStatus;

public class InternshipAppFilter {

    private Set<String> studentIDs = null;
    private Set<String> internshipIDs = null;
    private Set<InternshipApplicationStatus> internshipApplicationStatuses = null;

    public Set<String> getStudentIDs() {
        return this.studentIDs;
    }

    public Set<String> getInternshipIDs() {
        return this.internshipIDs;
    }

    public Set<InternshipApplicationStatus> getInternshipApplicationStatuses() {
        return this.internshipApplicationStatuses;
    }

    public void setStudentIDs(Set<String> studentIDs) {
        this.studentIDs = studentIDs;
    }

    public void setInternshipIDs(Set<String> internshipIDs) {
        this.internshipIDs = internshipIDs;
    }

    public void setInternshipApplicationStatuses(Set<InternshipApplicationStatus> internshipApplicationStatuses) {
        this.internshipApplicationStatuses = internshipApplicationStatuses;
    }

}
