package dto;

import enums.UserType;

public class LoginInfo {
    private final String ID;
    private final UserType userType;

    public LoginInfo(String ID, UserType userType) {
        this.ID = ID;
        this.userType = userType;
    }

    public String getID() {
        return ID;
    }

    public UserType getUserType() {
        return userType;
    }

}
