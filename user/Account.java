package user;

import java.util.Base64;
import user.PasswordHash;

public class Account {

	private final String userID;
    // if student - U1234567A
    // if company rep - email address
    // if career centre staff - ntu account
    private String name;
    private String hash;
    private String salt;
	private final String role;
    private String email;
    private boolean isLoggedIn;

	public Account(String userID, String salt, String hash, String name, String role, String email) {
		this.userID = userID;
        this.name = name;
        this.salt = salt;
        this.hash = hash;
        this.role = role;
        this.email = email;
        this.isLoggedIn = false;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public String getName() {
		return name;
	}

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean getLoginStatus() {
        return isLoggedIn;
    }

    public void amendName(String name_Amended) {
        name = name_Amended;
    }

    public boolean amendPassword(String password_Amended) {
        try {
            salt = Base64.getEncoder().encodeToString(PasswordHash.generateSalt());
            hash = PasswordHash.hashPassword(password_Amended, salt);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void amendEmail(String email_Amended) {
        email = email_Amended;
    }

    public boolean doesUsernamePasswordMatch(String inputPassword) {
        return PasswordHash.verifyPassword(inputPassword, salt, hash);
    }

    public boolean login(String inputUserID, String inputPassword) {
        if (doesUsernamePasswordMatch(inputPassword) == true) {
            isLoggedIn = true;
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        isLoggedIn = false;
    }

}
