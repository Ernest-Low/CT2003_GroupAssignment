package config;

import repositories.AutoNumberRepo;
import repositories.CareerStaffRepo;
import repositories.CompanyRepRepo;
import repositories.InternshipAppRepo;
import repositories.InternshipRepo;
import repositories.StudentRepo;
import repositories.impl.AutoNumberRepoImpl;
import repositories.impl.CareerStaffRepoImpl;
import repositories.impl.CompanyRepRepoImpl;
import repositories.impl.InternshipAppRepoImpl;
import repositories.impl.InternshipRepoImpl;
import repositories.impl.StudentRepoImpl;
import services.AutoNumberService;
import services.CareerStaffService;
import services.CompanyRepService;
import services.InternshipAppService;
import services.InternshipService;
import services.StudentService;

public class Services {

    private final AutoNumberRepo autoNumberRepo;
    public final AutoNumberService autoNumberService;
    private final InternshipRepo internshipRepo;
    public final InternshipService internshipService;
    private final CompanyRepRepo companyRepRepo;
    public final CompanyRepService companyRepService;
    private final CareerStaffRepo careerStaffRepo;
    public final CareerStaffService careerStaffService;
    private final StudentRepo studentRepo;
    public final StudentService studentService;
    private final InternshipAppRepo internshipAppRepo;
    public final InternshipAppService internshipAppService;

    public Services() {
        this.autoNumberRepo = new AutoNumberRepoImpl();
        this.autoNumberService = new AutoNumberService(autoNumberRepo);
        this.internshipRepo = new InternshipRepoImpl();
        this.internshipService = new InternshipService(internshipRepo);
        this.companyRepRepo = new CompanyRepRepoImpl();
        this.companyRepService = new CompanyRepService(companyRepRepo);
        this.careerStaffRepo = new CareerStaffRepoImpl();
        this.careerStaffService = new CareerStaffService(careerStaffRepo);
        this.studentRepo = new StudentRepoImpl();
        this.studentService = new StudentService(studentRepo);
        this.internshipAppRepo = new InternshipAppRepoImpl();
        this.internshipAppService = new InternshipAppService(internshipAppRepo);
    }

}
