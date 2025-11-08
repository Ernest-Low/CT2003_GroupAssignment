package config;

import central.Central;
import repositories.AutoNumberRepo;
import repositories.InternshipRepo;
import repositories.impl.AutoNumberRepoImpl;
import repositories.impl.InternshipRepoImpl;
import services.AutoNumberService;
import services.InternshipService;

public class AppContext {

    private final AutoNumberRepo autoNumberRepo;
    private final AutoNumberService autoNumberService;
    private final InternshipRepo internshipRepo;
    private final InternshipService internshipService;
    private final Central central;

    public AppContext(){
        this.autoNumberRepo = new AutoNumberRepoImpl();
        this.autoNumberService = new AutoNumberService(autoNumberRepo);
        this.internshipRepo = new InternshipRepoImpl();
        this.internshipService = new InternshipService(internshipRepo);
        this.central = new Central(autoNumberService, internshipService);
    }

    public Central getCentral(){
        return this.central;
    }
    
}
