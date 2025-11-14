// Main entry point, rename to the app or something when decide on one

import config.AppContext;
import config.CSVInitializer;

// * 1: To run: Ensure there is a bin folder
// * 2: javac -d bin $(find . -name "*.java")
// * 3: java -cp bin Main

public class Main {

    public static void main(String[] args) {

        // If CSVs arent in folder, create them
        CSVInitializer.initializeCSVs();

        // Repos placed into Repositories here (and central call)
        AppContext context = new AppContext();

        context.getCentral().centralController();
    }

}
