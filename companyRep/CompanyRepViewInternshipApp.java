package companyRep;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import config.Services;
import dtos.InternshipAppFilter;
import enums.InternshipApplicationStatus;
import model.CompanyRep;
import model.Internship;
import model.InternshipApp;
import model.Student;

public class CompanyRepViewInternshipApp {

    private final Services services;
    private final CompanyRep companyRep;
    private final InternshipAppFilter internshipAppFilter;
    private List<Internship> internships;
    private List<InternshipApp> internshipApps;
    private List<Student> students;

    public CompanyRepViewInternshipApp(Services services, CompanyRep companyRep) {
        this.services = services;
        this.companyRep = companyRep;
        this.internshipAppFilter = new InternshipAppFilter();

    }

    // private void populateMap() {

    // }

    public void viewInternshipAppController() {

        while (true) {

            // Get Internships belonging to company rep
            this.internships = this.services.internshipService.getInternships(companyRep.getGUID(), 3); // Internship companyID is GUID of Company Rep

            // Use Internships to find InternshipApps, populate internshipApps
            for (Internship internship : internships) {
                Set<String> internshipIDs = Set.of(internship.getID());
                Set<InternshipApplicationStatus> internshipAppStatuses = Set.of(InternshipApplicationStatus.PENDING);
                internshipAppFilter.setInternshipApplicationStatuses(internshipAppStatuses);
                internshipAppFilter.setInternshipIDs(internshipIDs);
                List<InternshipApp> newInternshipApps = this.services.internshipAppService
                        .filterInternshipApps(internshipAppFilter);
                for (InternshipApp internshipApp : newInternshipApps) {
                    this.internshipApps.add(internshipApp);
                }
            }

            // Now to find the students using InternshipApps
            // StudentIDs set
            Set<String> studentIDs = Set.of();
            for (InternshipApp internshipApp : this.internshipApps) {
                studentIDs.add(internshipApp.getStudentID());
            }
            // Use the studentIDs set to get corresponding students
            for (String studentID : studentIDs) {
                Student student = services.studentService.getStudentByID(studentID);
                students.add(student);
            }

            // Create map
            Map<Integer, String> menuMap = new LinkedHashMap<>();
            int idx = 1;
            for (InternshipApp internshipApp : internshipApps) { // For every app, make a nice string
                Internship internship = internships.stream()
                        .filter(item -> item.getID() == internshipApp.getInternshipID()).findFirst().orElse(null);
                Student student = students.stream().filter(item -> item.getId() == internshipApp.getStudentID())
                        .findFirst().orElse(null);
                String description = internship.getTitle() + " -- " + student.getName() + "(" + student.getYearOfStudy() // Internship name -- Student Name (Year of Study) - Major
                        + ")" + " - " + student.getMajor();
                menuMap.put(idx++, description);
            }

        }

    }

}
