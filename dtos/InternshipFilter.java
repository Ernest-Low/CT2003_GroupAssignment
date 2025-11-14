package dtos;

import java.time.LocalDate;
import java.util.Set;

import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;

public class InternshipFilter {

    private Set<String> companyNames = null;
    private Set<Major> majors = null;
    private Set<InternshipLevel> levels = null;
    private Set<InternshipStatus> statuses = null;
    private LocalDate closingDateBefore = null;
    private LocalDate closingDateAfter = null;

    public Set<String> getCompanyNames() {
        return this.companyNames;
    }

    public Set<Major> getMajors() {
        return this.majors;
    }

    public Set<InternshipLevel> getLevels() {
        return this.levels;
    }

    public Set<InternshipStatus> getStatuses() {
        return this.statuses;
    }

    public LocalDate getClosingDateBefore() {
        return this.closingDateBefore;
    }

    public LocalDate getClosingDateAfter() {
        return this.closingDateAfter;
    }

    public void setCompanyNames(Set<String> companyNames) {
        this.companyNames = companyNames;
    }

    public void setMajors(Set<Major> majors) {
        this.majors = majors;
    }

    public void setLevels(Set<InternshipLevel> levels) {
        this.levels = levels;
    }

    public void setStatuses(Set<InternshipStatus> statuses) {
        this.statuses = statuses;
    }

    public void setClosingDateBefore(LocalDate closingDateBefore) {
        this.closingDateBefore = closingDateBefore;
    }

    public void setClosingDateAfter(LocalDate closingDateAfter) {
        this.closingDateAfter = closingDateAfter;
    }

}
