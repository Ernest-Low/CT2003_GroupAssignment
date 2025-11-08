package repositories.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.CSVPaths;
import enums.TableIndex;
import model.AutoNumber;
import repositories.AutoNumberRepo;

public class AutoNumberRepoImpl implements AutoNumberRepo {

    @Override
    public AutoNumber findById(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.AUTONUMBERS_CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int recordId = Integer.parseInt(values[0]);
                if (recordId == id) {
                    // int ID, String tableName, String prefix, int currentValue, String suffix, int incrementStep 
                    return new AutoNumber(recordId, TableIndex.valueOf(values[1]), values[2],
                            Integer.parseInt(values[3]), values[4], Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AutoNumber findByTableIndex(TableIndex tableIndex) {
        System.out.println("Entered autonumber findByTableIndex");
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.AUTONUMBERS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("Values: " + values);
                TableIndex recordTableIndex = TableIndex.valueOf(values[1]);
                if (recordTableIndex == tableIndex) {
                    // int ID, TableIndex tableIndex, String prefix, int currentValue, String suffix, int incrementStep 
                    return new AutoNumber(Integer.parseInt(values[0]), TableIndex.valueOf(values[1]), values[2],
                            Integer.parseInt(values[3]), values[4], Integer.parseInt(values[5]),
                            Integer.parseInt(values[6]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(AutoNumber autoNumber) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.AUTONUMBERS_CSV, true))) {
            String line = String.join(",", String.valueOf(autoNumber.getID()), autoNumber.getTableIndex().name(),
                    autoNumber.getPrefix(), String.valueOf(autoNumber.getCurrentValue()), autoNumber.getSuffix(),
                    String.valueOf(autoNumber.getIncrementStep()));
            bw.newLine();
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AutoNumber autoNumber) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.AUTONUMBERS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int recordId = Integer.parseInt(parts[0]);
                if (recordId == autoNumber.getID()) {
                    line = String.join(",", String.valueOf(autoNumber.getID()), autoNumber.getTableIndex().name(),
                            autoNumber.getPrefix(), String.valueOf(autoNumber.getCurrentValue()),
                            autoNumber.getSuffix(), String.valueOf(autoNumber.getIncrementStep()));
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.AUTONUMBERS_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}