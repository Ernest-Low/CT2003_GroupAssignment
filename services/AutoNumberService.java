package services;

import enums.TableIndex;
import model.AutoNumber;
import repositories.AutoNumberRepo;

// TODO: Change AutoNumber into a POJO, move logic here
public class AutoNumberService {

    private final AutoNumberRepo autoNumberRepository;

    public AutoNumberService(AutoNumberRepo autoNumberRepo) {
        this.autoNumberRepository = autoNumberRepo;
    }

    public void createAutoNumberRow(int ID, TableIndex tableIndex, String prefix, int currentValue, String suffix,
            int incrementStep, int paddingLength) {
        AutoNumber autoNumber = new AutoNumber(ID, tableIndex, prefix, currentValue, suffix, incrementStep,
                paddingLength);
        autoNumberRepository.save(autoNumber);
    }

    public String generateNextId(TableIndex tableindex) {
        System.out.println("Entered generateNextId");
        AutoNumber autoNumber = autoNumberRepository.findByTableIndex(tableindex);
        System.out.println("At autonumber ID: " + autoNumber.getID());
        int nextValue = autoNumber.getCurrentValue() + autoNumber.getIncrementStep();
        autoNumber.setCurrentValue(nextValue);
        autoNumberRepository.update(autoNumber);
        return autoNumber.getPrefix()
                + String.format("%0" + autoNumber.getPaddingLength() + "d", nextValue)
                + autoNumber.getSuffix();
    }

    // public int generateNextId(int autoNumberId) {
    //     // Use the autonumber ID to return the row in the autonumber table

    //     return 0; // temp
    // }

    // ? Call this to get next ID
    // public String getNextNumber() {
    //     int next = currentValue + incrementStep;
    //     if (suffix != null) {
    //         return prefix + next + suffix;
    //     } else {
    //         return prefix + next;
    //     }
    // }

}
