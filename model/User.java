package model;

public class User {
    protected final String ID;
    protected String name;

    public User(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getId() {
        return ID;
    }

    public String getName() {
        return name;
    }

}