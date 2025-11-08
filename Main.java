// Main entry point, rename to the app or something when decide on one

import config.AppContext;

// ? Alternative way to run:
// * mvn clean compile
// * mvn exec:java

public class Main {

    public static void main(String[] args) {
        // Do whatever needs to be done at app launch first
        // Maybe stuff like starting resources
        // Could do a csv file check, like make a folder called "startup" or something and throw methods in

        // Repos placed into Repositories here (and central call)
        AppContext context = new AppContext();

        context.getCentral().centralController();

    }

}
