package model;

import enums.TableIndex;

// So uh, retrieve the row with the ID for the CSV type
// Eg: Student = ID 1? TBC
// Make an instance of this class (correct ID, and call getNextNumber)
// Then call increment when that's saving
public class AutoNumber {

    private final int ID;
    private final TableIndex tableIndex;
    private final String prefix;
    private int currentValue;
    private final String suffix;
    private final int incrementStep; // Usually 1
    private final int paddingLength;

    public AutoNumber(int recordId, TableIndex tableIndex, String prefix, int currentValue, String suffix, int incrementStep,
            int paddingLength) {
        this.ID = recordId;
        this.tableIndex = tableIndex;
        this.prefix = prefix;
        this.currentValue = currentValue;
        this.suffix = suffix;
        this.incrementStep = incrementStep;
        this.paddingLength = paddingLength;
    }

    public int getID() {
        return this.ID;
    }

    public TableIndex getTableIndex() {
        return this.tableIndex;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public int getCurrentValue() {
        return this.currentValue;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public int getIncrementStep() {
        return this.incrementStep;
    }

    public int getPaddingLength() {
        return this.paddingLength;
    }

    public void setCurrentValue(int value) {
        this.currentValue = value;
    }

}
