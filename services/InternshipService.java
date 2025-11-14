package services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import dtos.InternshipFilter;
import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;
import model.Internship;
import repositories.InternshipRepo;

public class InternshipService {

    private final InternshipRepo internshipRepo;

    public InternshipService(InternshipRepo internshipRepo) {
        this.internshipRepo = internshipRepo;
    }

    public Internship getInternship(String ID) {
        return internshipRepo.findById(ID);
    }

    public List<Internship> getInternships(String value, int columnNo) {
        return internshipRepo.findAllByColumn(value, columnNo);
    }

    public void createInternship(String ID, String title, String companyName, String companyID, Major major,
            InternshipLevel level, int slots,
            LocalDate openingDate, LocalDate closingDate, String description) {
        Internship internship = new Internship(ID, title, companyName, companyID, major, level, 0, slots, openingDate,
                closingDate, InternshipStatus.PENDING, description);
        internshipRepo.save(internship);
    }

    // * Company Rep can only update their own internship
    public void updateInternship(Internship internship, String repID) {
        Internship target = internshipRepo.findById(internship.getID());
        if (target.getCompanyID().equals(repID)) {
            internshipRepo.update(internship);
            System.out.println("Successfully updated");
        } else {
            System.out.println("You can't update an internship that's not yours!");
        }

    }

    // * Company Rep can only delete their own internship
    public void deleteInternship(String internshipID, String repID) {
        Internship target = internshipRepo.findById(internshipID);
        if (target.getCompanyID().equals(repID)) {
            internshipRepo.delete(internshipID);
            System.out.println("Successfully deleted");
            // TODO: Cancel all the pending internship request / offers too, handle the students that were confirmed (too bad gotta apply again)
        } else {
            System.out.println("You can't delete an internship that's not yours!");
        }
    }

    public List<Internship> filterInternships(InternshipFilter filter) {
        return internshipRepo.findAll().stream()
                .filter(internship -> filter.getCompanyNames() == null
                        || filter.getCompanyNames().contains(internship.getCompanyName()))
                .filter(internship -> filter.getMajors() == null || filter.getMajors().contains(internship.getMajor()))
                .filter(internship -> filter.getLevels() == null || filter.getLevels().contains(internship.getLevel()))
                .filter(internship -> filter.getStatuses() == null
                        || filter.getStatuses().contains(internship.getStatus()))
                .filter(internship -> filter.getClosingDateBefore() == null
                        || (internship.getClosingDate() != null
                                && !internship.getClosingDate().isAfter(filter.getClosingDateBefore())))
                .filter(internship -> filter.getClosingDateAfter() == null
                        || (internship.getClosingDate() != null
                                && !internship.getClosingDate().isBefore(filter.getClosingDateAfter())))
                .sorted(Comparator.comparing(Internship::getTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

}
