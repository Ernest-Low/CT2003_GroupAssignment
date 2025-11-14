package repositories.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.CSVPaths;
import enums.InternshipApplicationStatus;
import model.InternshipApp;
import repositories.InternshipAppRepo;

public class InternshipAppRepoImpl implements InternshipAppRepo {

    @Override
    public InternshipApp findFirstByColumn(String value, int columnNo) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String recordId = values[columnNo];
                if (recordId.equals(value)) {
                    // String studentID, String internshipID, InternshipApplicationStatus status
                    return new InternshipApp(
                            values[1], // studentID
                            values[2], // internshipID
                            InternshipApplicationStatus.valueOf(values[2]) // InternshipApplicationStatus
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<InternshipApp> findAllByColumn(String value, int columnNo) {
        List<InternshipApp> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String check = values[columnNo];
                if (check.equals(value)) {
                    InternshipApp internshipApp = new InternshipApp(
                            values[1],
                            values[2],
                            InternshipApplicationStatus.valueOf(values[3]));
                    list.add(internshipApp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(InternshipApp internshipApp) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.INTERNSHIP_APPLICATION_CSV, true))) {
            String line = String.join(",",
                    internshipApp.getStudentID(),
                    internshipApp.getInternshipID(),
                    internshipApp.getStatus().name() // InternshipApplicationStatus
            );
            bw.newLine();
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(InternshipApp internshipApp) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String studentID = parts[0];
                String internshipID = parts[1];
                if (studentID.equals(internshipApp.getStudentID())
                        && internshipID.equals(internshipApp.getInternshipID())) {
                    line = String.join(",",
                            internshipApp.getStudentID(),
                            internshipApp.getInternshipID(),
                            internshipApp.getStatus().name() // InternshipApplicationStatus
                    );
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String studentID, String internshipID) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String retrievedStudentID = parts[0];
                String retrievedInternshipID = parts[1];
                if (retrievedStudentID.equals(studentID)
                        && retrievedInternshipID.equals(internshipID)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InternshipApp> findAll() {
        List<InternshipApp> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.INTERNSHIP_APPLICATION_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                InternshipApp internshipApp = new InternshipApp(
                        values[0],
                        values[1],
                        InternshipApplicationStatus.valueOf(values[2]));
                list.add(internshipApp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
