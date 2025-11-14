package internshipFilter;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import dtos.InternshipFilter;
import enums.UserType;
import model.CareerStaff;
import model.CompanyRep;
import model.Student;

public class InternshipFilterMain {

    private final InternshipFilter internshipFilter;
    private final Scanner sc;
    private final UserType userType;

    private final InternshipFilterMajor internshipFilterMajor;
    private final InternshipFilterLevel internshipFilterLevel;
    private final InternshipFilterStatus internshipFilterStatus;
    private final InternshipFilterClosingDate internshipFilterClosingDate;

    public InternshipFilterMain(CompanyRep companyRep) {
        // * Company Rep: Can only see Internships that are of their own (companyNames)
        this.sc = new Scanner(System.in);
        internshipFilter = new InternshipFilter();
        userType = UserType.COMPANYREP;

        this.internshipFilterMajor = new InternshipFilterMajor(internshipFilter, sc);
        this.internshipFilterLevel = new InternshipFilterLevel(internshipFilter, sc);
        this.internshipFilterStatus = new InternshipFilterStatus(internshipFilter, sc);
        this.internshipFilterClosingDate = new InternshipFilterClosingDate(internshipFilter, sc);

        Set<String> companyNames = new HashSet<>();
        companyNames.add(companyRep.getCompanyName());
        this.internshipFilter.setCompanyNames(companyNames);
    }

    public InternshipFilterMain(Student student) {
        // * Students: Can only see Internships that are of their Major, and within level
        this.sc = new Scanner(System.in);
        internshipFilter = new InternshipFilter();
        userType = UserType.STUDENT;

        this.internshipFilterMajor = new InternshipFilterMajor(internshipFilter, sc);
        this.internshipFilterLevel = new InternshipFilterLevel(internshipFilter, sc);
        this.internshipFilterStatus = new InternshipFilterStatus(internshipFilter, sc);
        this.internshipFilterClosingDate = new InternshipFilterClosingDate(internshipFilter, sc);
    }

    public InternshipFilterMain(CareerStaff careerStaff) {
        // * Career Staff: Should only see Internships that are status = PENDING?
        this.sc = new Scanner(System.in);
        internshipFilter = new InternshipFilter();
        userType = UserType.CAREERSTAFF;

        this.internshipFilterMajor = new InternshipFilterMajor(internshipFilter, sc);
        this.internshipFilterLevel = new InternshipFilterLevel(internshipFilter, sc);
        this.internshipFilterStatus = new InternshipFilterStatus(internshipFilter, sc);
        this.internshipFilterClosingDate = new InternshipFilterClosingDate(internshipFilter, sc);
    }

    private void openMenu() {
        System.out.println("\nPlease select value to filter by.");
        System.out.println("1: Filter by Majors");
        System.out.println("2: Filter by Levels");
        System.out.println("3: Filter by Statuses");
        System.out.println("4: Filter by Closing Date");
        if (userType != UserType.COMPANYREP) {
            System.out.println("5: Filter by Company Name");
        }
        System.out.println("X: Return");
    }

    public InternshipFilter getInternshipFilter() {
        return this.internshipFilter;
    }

    public InternshipFilter InternshipFilterController() {
        String choice = "";
        while (true) {
            try {
                openMenu();
                choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        internshipFilterMajor.InternshipFilterMajorController();
                        break;
                    case "2":
                        internshipFilterLevel.InternshipFilterLevelController();
                        break;
                    case "3":
                        internshipFilterStatus.InternshipFilterStatusController();
                        break;
                    case "4":
                        internshipFilterClosingDate.InternshipFilterClosingDateController();
                        break;
                    case "x", "X": // Exit back to menu
                        return internshipFilter;
                    default:
                        System.out.println("Not a valid input. Try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Not a valid input. Try Again");
            }
        }
    }
}
// private Set<String> companyNames = null;
// private Set<Major> majors = null;
// private Set<InternshipLevel> levels = null;
// private Set<InternshipStatus> statuses = null;
// private LocalDate closingDateBefore = null;
// private LocalDate closingDateAfter = null;
// private LocalDate openingDateBefore = null;
// private LocalDate openingDateAfter = null;