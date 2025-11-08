package repositories;

import enums.TableIndex;
import model.AutoNumber;

public interface AutoNumberRepo {
    AutoNumber findById(int id);

    AutoNumber findByTableIndex (TableIndex tableIndex);

    void save(AutoNumber autoNumber);

    void update(AutoNumber autoNumber);

}
