package model;

import enums.UserType;

public class Credential {
    private final String id;
    private final String salt;
    private final String hash;
    private final UserType userType;

    public Credential(String id, String salt, String hash, UserType userType) {
        this.id = id;
        this.salt = salt;
        this.hash = hash;
        this.userType = userType;
    }

    public String getid() {
        return id;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }

    public UserType getUserType() {
        return userType;
    }
}