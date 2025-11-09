package services;

import enums.TableIndex;
import model.AutoNumber;
import repositories.AutoNumberRepo;

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
        AutoNumber autoNumber = autoNumberRepository.findByTableIndex(tableindex);
        int nextValue = autoNumber.getCurrentValue() + autoNumber.getIncrementStep();
        autoNumber.setCurrentValue(nextValue);
        autoNumberRepository.update(autoNumber);
        return autoNumber.getPrefix()
                + String.format("%0" + autoNumber.getPaddingLength() + "d", nextValue)
                + autoNumber.getSuffix();
    }

}
