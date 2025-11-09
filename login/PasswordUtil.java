package login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Arrays;

public class PasswordUtil {

    private static final String hashAlgo = "SHA-256";

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance(hashAlgo);
            msgDigest.update(Base64.getDecoder().decode(salt));
            byte[] hashedBytes = msgDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < 1000; i++) {
                hashedBytes = msgDigest.digest(hashedBytes);
            }
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[ERROR]: Hashing Algorithm not found", e);
        }
    }

    protected static boolean verifyPassword(String password, String salt, String hash) {
        String newHash = hashPassword(password, salt);
        return newHash.equals(hash);
    }

    protected static List<String> hashNewPassword(String password) {
        String salt = Base64.getEncoder().encodeToString(generateSalt());
        String hash = hashPassword(password, salt);
        return Arrays.asList(salt, hash);
    }

}
