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

    public InternshipFilterMain(CompanyRep companyRep, Scanner sc) {
        // * Company Rep: Can only see Internships that are of their own (companyNames)
        this.sc = sc;
        internshipFilter = new InternshipFilter();
        userType = UserType.COMPANYREP;

        Set<String> companyNames = new HashSet<>();
        companyNames.add(companyRep.getCompanyName());
        this.internshipFilter.setCompanyNames(companyNames);
    }

    public InternshipFilterMain(Student student, Scanner sc) {
        // * Students: Can only see Internships that are of their Major, and within level
        this.sc = sc;
        internshipFilter = new InternshipFilter();
        userType = UserType.STUDENT;
    }

    public InternshipFilterMain(CareerStaff careerStaff, Scanner sc) {
        // * Career Staff: Should only see Internships that are status = PENDING?
        this.sc = sc;
        internshipFilter = new InternshipFilter();
        userType = UserType.CAREERSTAFF;
    }

    private String openMenu() {
        System.out.println("Please select value to filter by.");
        System.out.println("1: Filter by Majors");
        System.out.println("2: Filter by Levels");
        System.out.println("3: Filter by Statuses");
        System.out.println("4: Filter by Opening Date");
        System.out.println("5: Filter by Closing Date");
        if (userType != UserType.COMPANYREP) {
            System.out.println("6: Filter by Company Name");
        }

        System.out.println("X: Return");
        String input = sc.nextLine();

        return input;
    }

    public InternshipFilter getInternshipFilter() {
        return this.internshipFilter;
    }

    public InternshipFilter InternshipFilterController() {
        String choice = "";
        choice = openMenu();
        while (!choice.equalsIgnoreCase("x")) {
            try {
                choice = openMenu();
                switch (choice) {
                    case "1" -> InternshipFilterMajor.InternshipFilterMajorController(internshipFilter);
                    case "2" -> System.out.println("2");
                    case "x", "X" -> System.out.println("Returning..."); // Exit back to menu
                    default -> System.out.println("Not a valid input. Try again.");
                }
            }
            catch (NoSuchElementException e) {
                System.out.println("Not a valid input. Try Again");
            }
        }
        return internshipFilter;
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