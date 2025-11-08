package companyRep;

import java.time.LocalDate;
import java.util.Scanner;

import enums.InternshipLevel;
import enums.Major;
import enums.TableIndex;
import model.CompanyRep;
import services.AutoNumberService;
import services.InternshipService;

public class CRepPostInternship {

    private final AutoNumberService autoNumberService;
    private final InternshipService internshipService;
    private CompanyRep companyRep;
    private final Scanner sc;

    public CRepPostInternship(AutoNumberService autoNumberService, InternshipService internshipService,
            CompanyRep companyRep, Scanner sc) {
        this.autoNumberService = autoNumberService;
        this.internshipService = internshipService;
        this.companyRep = companyRep;
        this.sc = sc;
    }

    public void CRepPostInternshipController() {

        System.out.println("Posting new Internship Opportunity...");

        System.out.println("Please work 1");
        String nextId = autoNumberService.generateNextId(TableIndex.INTERNSHIP);

        System.out.println("Got autonumber ID: " + nextId);
        // * Company name / company ID should come from companyRep, these 2 variables hardcode is temp
        String companyName = "NovaLink";
        String companyID = "C000001R";
        // ! Mockup
        internshipService.createInternship(nextId, "Novalink Electrical Engineer Internship Programme", companyName,
                companyID, Major.ELECTRICAL_ENGINEERING, InternshipLevel.BASIC, LocalDate.now(),
                LocalDate.now().plusDays(60), "Great internship opportunity");
        System.out.println("Made internship!");
        return;
    }

}
