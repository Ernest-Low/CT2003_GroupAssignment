package config;

import repositories.AutoNumberRepo;
import repositories.InternshipRepo;
import repositories.impl.AutoNumberRepoImpl;
import repositories.impl.InternshipRepoImpl;
import services.AutoNumberService;
import services.InternshipService;

public class Services {

    private final AutoNumberRepo autoNumberRepo;
    public final AutoNumberService autoNumberService;
    private final InternshipRepo internshipRepo;
    public final InternshipService internshipService;

    public Services() {
        this.autoNumberRepo = new AutoNumberRepoImpl();
        this.autoNumberService = new AutoNumberService(autoNumberRepo);
        this.internshipRepo = new InternshipRepoImpl();
        this.internshipService = new InternshipService(internshipRepo);
    }

}
