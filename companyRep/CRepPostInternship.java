package companyRep;

import java.time.LocalDate;
import java.util.Scanner;

import config.Services;
import enums.InternshipLevel;
import enums.Major;
import enums.TableIndex;
import model.CompanyRep;

public class CRepPostInternship {

    private final Services services;
    private CompanyRep companyRep;
    private final Scanner sc;

    private String title;
    private Major major;
    private InternshipLevel level;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private String description;

    public CRepPostInternship(Services services, CompanyRep companyRep, Scanner sc) {
        this.services = services;
        this.companyRep = companyRep;
        this.sc = sc;
    }

    private void PostInternshipMenu() {
        // TODO: Ask questions, securely take inputs, set to variables
        title = sc.nextLine();
        major = Major.valueOf(sc.nextLine());
        level = InternshipLevel.valueOf(sc.nextLine());
        openingDate = LocalDate.parse(sc.nextLine());
        closingDate = LocalDate.parse(sc.nextLine());
        description = sc.nextLine();
    }

    public void CRepPostInternshipController() {

        System.out.println("Posting new Internship Opportunity...");
        PostInternshipMenu();

        String nextId = services.autoNumberService.generateNextId(TableIndex.INTERNSHIP);

        System.out.println("Got autonumber ID: " + nextId);
        // * Company name / company ID should come from companyRep, these 2 variables hardcode is temp
        String companyName = this.companyRep.getCompanyName();
        String companyID = this.companyRep.getId(); // "C000001R";
        // ! Mockup
        services.internshipService.createInternship(nextId, title,
                companyName,
                companyID, major, level, openingDate,
                closingDate, description);
        // services.internshipService.createInternship(nextId, "Novalink Electrical Engineer Internship Programme",
        //         companyName,
        //         companyID, Major.ELECTRICAL_ENGINEERING, InternshipLevel.BASIC, LocalDate.now(),
        //         LocalDate.now().plusDays(60), "Great internship opportunity");
        System.out.println("Made internship!");
        return;
    }

}
