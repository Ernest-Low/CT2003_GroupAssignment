// Main entry point, rename to the app or something when decide on one

import config.AppContext;

// * 1: To run: Ensure there is a bin folder
// * 2: javac -d bin $(find . -name "*.java")
// * 3: java -cp bin Main

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
