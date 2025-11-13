package repositories.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.CSVPaths;
import model.CompanyRep;
import repositories.CompanyRepRepo;

public class CompanyRepRepoImpl implements CompanyRepRepo {

    @Override
    public CompanyRep findFirstByColumn(String value, int columnNo) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.COMPANY_REPS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String recordId = values[columnNo];
                if (recordId.equals(value)) {
                    // String GUID, String ID, String name, String companyName, String department, String position
                    return new CompanyRep(
                            recordId,
                            values[1], // String
                            values[2],
                            values[3],
                            values[4],
                            values[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CompanyRep companyRep) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.COMPANY_REPS_CSV, true))) {
            String line = String.join(",",
                    companyRep.getGUID(), // String
                    companyRep.getId(),
                    companyRep.getName(),
                    companyRep.getCompanyName(),
                    companyRep.getDepartment(),
                    companyRep.getDepartment());
            bw.newLine();
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CompanyRep companyRep) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.COMPANY_REPS_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recordId = parts[0];
                if (recordId.equals(companyRep.getGUID())) {
                    line = String.join(",",
                            companyRep.getGUID(), // String
                            companyRep.getId(),
                            companyRep.getName(),
                            companyRep.getCompanyName(),
                            companyRep.getDepartment(),
                            companyRep.getDepartment());
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.COMPANY_REPS_CSV))) {
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
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.COMPANY_REPS_CSV))) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.COMPANY_REPS_CSV))) {
            for (String headers : lines) {
                bw.write(headers);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CompanyRep> findAll() {
        List<CompanyRep> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.COMPANY_REPS_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CompanyRep companyRep = new CompanyRep(
                        values[0],
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        values[5]);
                list.add(companyRep);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
