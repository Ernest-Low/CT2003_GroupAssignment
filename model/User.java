package model;

public class User {
    protected final String GUID;
    protected String ID;
    protected String name;

    public User(String GUID, String ID, String name) {
        this.GUID = GUID;
        this.ID = ID;
        this.name = name;
    }

    public String getGUID() {
        return this.GUID;
    }

    public String getId() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

}