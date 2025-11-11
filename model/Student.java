package model;

import enums.Major;

public class Student extends User {

    protected int yearOfStudy;
    protected Major major;

    public Student(String GUID, String ID, String name, int yearOfStudy, Major major) {
        super(GUID, ID, name);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public Major getMajor() {
        return major;
    }

}