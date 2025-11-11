package repositories.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import constants.CSVPaths;
import enums.InternshipLevel;
import enums.InternshipStatus;
import enums.Major;
import model.Internship;
import repositories.InternshipRepo;

public class InternshipRepoImpl implements InternshipRepo {

    @Override
    public Internship findById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIPS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String recordId = values[0];
                if (recordId.equals(id)) {
                    // String ID, String title, String companyName, String companyID, Major major, InternshipLevel level, int counter, LocalDate openingDate, LocalDate closingDate,
                    // InternshipStatus status, String description
                    return new Internship(
                            recordId,
                            values[1], // String
                            values[2],
                            values[3],
                            Major.valueOf(values[4]), // Enum
                            InternshipLevel.valueOf(values[5]),
                            Integer.parseInt(values[6]), // int
                            LocalDate.parse(values[7]), // LocalDate
                            LocalDate.parse(values[8]),
                            InternshipStatus.valueOf(values[9]),
                            values[10]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Internship internship) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.INTERNSHIPS_CSV, true))) {
            // String ID, String title, String companyName, String companyID, Major major, InternshipLevel level, int counter, LocalDate openingDate, LocalDate closingDate,
            // InternshipStatus status, String description
            String line = String.join(",",
                    internship.getID(), // String
                    internship.getTitle(),
                    internship.getCompanyName(),
                    internship.getCompanyID(),
                    internship.getMajor().name(), // Enum
                    internship.getLevel().name(),
                    String.valueOf(internship.getCounter()), // int
                    internship.getOpeningDate().toString(), // LocalDate
                    internship.getClosingDate().toString(),
                    internship.getStatus().name(),
                    internship.getDescription());
            bw.newLine();
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Internship internship) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIPS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recordId = parts[0];
                if (recordId.equals(internship.getID())) {
                    line = String.join(",",
                            internship.getID(),
                            internship.getTitle(),
                            internship.getCompanyName(),
                            internship.getCompanyID(),
                            internship.getMajor().name(),
                            internship.getLevel().name(),
                            String.valueOf(internship.getCounter()),
                            internship.getOpeningDate().toString(),
                            internship.getClosingDate().toString(),
                            internship.getStatus().name(),
                            internship.getDescription());
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.INTERNSHIPS_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIPS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recordId = parts[0];
                if (!recordId.equals(id)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.INTERNSHIPS_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Internship> findAll() {
        List<Internship> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIPS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Internship internship = new Internship(
                        values[0], // ID
                        values[1],
                        values[2],
                        values[3],
                        Major.valueOf(values[4]),
                        InternshipLevel.valueOf(values[5]),
                        Integer.parseInt(values[6]),
                        LocalDate.parse(values[7]),
                        LocalDate.parse(values[8]),
                        InternshipStatus.valueOf(values[9]),
                        values[10]);
                list.add(internship);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
