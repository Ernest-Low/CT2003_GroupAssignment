package repositories.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.CSVPaths;
import model.CareerStaff;
import repositories.CareerStaffRepo;

public class CareerStaffRepoImpl implements CareerStaffRepo {

    @Override
    public CareerStaff findFirstByColumn(String value, int columnNo) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String recordId = values[columnNo];
                if (recordId.equals(value)) {
                    // String GUID, String ID, String name, String staffDepartment
                    return new CareerStaff(
                            values[0],
                            values[1], // String
                            values[2],
                            values[3]

                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CareerStaff careerStaff) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.CAREER_STAFF_CSV, true))) {
            String line = String.join(",",
                    careerStaff.getGUID(), // String
                    careerStaff.getId(),
                    careerStaff.getName(),
                    careerStaff.getStaffDepartment());
            bw.newLine();
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CareerStaff careerStaff) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recordId = parts[0];
                if (recordId.equals(careerStaff.getGUID())) {
                    line = String.join(",",
                            careerStaff.getGUID(), // String
                            careerStaff.getId(),
                            careerStaff.getName(),
                            careerStaff.getStaffDepartment());
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.CAREER_STAFF_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String GUID) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recordId = parts[0];
                if (!recordId.equals(GUID)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.CAREER_STAFF_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CareerStaff> findAll() {
        List<CareerStaff> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CareerStaff careerStaff = new CareerStaff(
                        values[0],
                        values[1],
                        values[2],
                        values[3]);
                list.add(careerStaff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
