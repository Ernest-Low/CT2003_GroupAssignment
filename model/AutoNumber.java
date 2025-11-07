package model;

// So uh, retrieve the row with the ID for the CSV type
// Eg: Student = ID 1? TBC
// Make an instance of this class (correct ID, and call getNextNumber)
// Then call increment when that's saving
public class AutoNumber {

    private int ID;
    private String prefix;
    private int currentValue;
    private String suffix;
    private int incrementStep; // Usually 1

    public AutoNumber(int ID, String prefix, int currentValue, String suffix, int incrementStep) {
        this.ID = ID;
        this.prefix = prefix;
        this.currentValue = currentValue;
        this.suffix = suffix;
        this.incrementStep = incrementStep;
    }

    public int getID() {
        return this.ID;
    }

    // ! Remember to call this afterwards
    public void increment() {
        this.currentValue += incrementStep;
        // Write to AutoNumber CSV goes here
    }

    // ? Call this to get next ID
    public String getNextNumber() {
        int next = currentValue + incrementStep;
        if (suffix != null) {
            return prefix + next + suffix;
        } else {
            return prefix + next;
        }
    }

}
