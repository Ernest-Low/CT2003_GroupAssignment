package model;

import enums.UserType;
import enums.AccountStatus;

public class Credential {
    private final String id;
    private final String salt;
    private final String hash;
    private final UserType userType;
    private final AccountStatus accountStatus;

    public Credential(String id, String salt, String hash, UserType userType, AccountStatus accountStatus) {
        this.id = id;
        this.salt = salt;
        this.hash = hash;
        this.userType = userType;
        this.accountStatus = accountStatus;
    }

    public String getId() {
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

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}