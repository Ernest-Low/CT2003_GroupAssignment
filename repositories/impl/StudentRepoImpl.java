package repositories.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.CSVPaths;
import enums.Major;
import model.Student;
import repositories.StudentRepo;

public class StudentRepoImpl implements StudentRepo {

    @Override
    public Student findFirstByColumn(String value, int columnNo) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String recordId = values[columnNo];
                if (recordId.equals(value)) {
                    // String GUID, String ID, String name, int yearOfStudy, Major major
                    return new Student(
                            recordId,
                            values[1], // String
                            values[2],
                            Integer.parseInt(values[3]), // Int
                            Major.valueOf(values[4])); // Major
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVPaths.CAREER_STAFF_CSV, true))) {
            String line = String.join(",",
                    student.getGUID(), // String
                    student.getId(),
                    student.getName(),
                    String.valueOf(student.getYearOfStudy()), // int
                    student.getMajor().name() // Major
            );
            bw.newLine();
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student student) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String recordId = parts[0];
                if (recordId.equals(student.getGUID())) {
                    line = String.join(",",
                            student.getGUID(), // String
                            student.getId(),
                            student.getName(),
                            String.valueOf(student.getYearOfStudy()), // int
                            student.getMajor().name() // Major

                    );
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
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSVPaths.CAREER_STAFF_CSV))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Student student = new Student(
                        values[0],
                        values[1],
                        values[2],
                        Integer.parseInt(values[3]), // Int
                        Major.valueOf(values[4]) // Major
                );
                list.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
