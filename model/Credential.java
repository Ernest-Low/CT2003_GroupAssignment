package model;

public class Credential {
    private final String id;
    private final String salt;
    private final String hash;

    public Credential(String id, String salt, String hash) {
        this.id = id;
        this.salt = salt;
        this.hash = hash;
    }

    public String getid() {
        return id;
    }

    public String getSalt() {
        return salt;
    }

    public String hash() {
        return hash;
    }
}