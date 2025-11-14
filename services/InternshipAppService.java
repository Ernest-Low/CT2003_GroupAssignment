package services;

import java.util.List;

import dtos.InternshipAppFilter;
import model.InternshipApp;
import repositories.InternshipAppRepo;

public class InternshipAppService {

    private final InternshipAppRepo internshipAppRepo;

    public InternshipAppService(InternshipAppRepo internshipAppRepo) {
        this.internshipAppRepo = internshipAppRepo;
    }

    public List<InternshipApp> getInternshipAppsByColumn(String value, int columnNo) {
        return internshipAppRepo.findAllByColumn(value, columnNo);
    }

    public void updateInternshipApp(InternshipApp internshipApp) {
        internshipAppRepo.update(internshipApp);
    }

    public List<InternshipApp> filterInternshipApps(InternshipAppFilter filter) {
        return internshipAppRepo.findAll().stream()
                .filter(internshipApp -> filter.getStudentIDs() == null
                        || filter.getStudentIDs().contains(internshipApp.getStudentID()))
                .filter(internshipApp -> filter.getInternshipIDs() == null
                        || filter.getInternshipIDs().contains(internshipApp.getInternshipID()))
                .filter(internshipApp -> filter.getInternshipApplicationStatuses() == null
                        || filter.getInternshipApplicationStatuses().contains(internshipApp.getStatus()))
                .toList();
    }

}
