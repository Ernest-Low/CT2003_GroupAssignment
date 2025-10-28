package user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHash {

    private static final String hashAlgo = "SHA-256";

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, String salt) {
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

    public static boolean verifyPassword(String password, String salt, String hash) {
        String newHash = hashPassword(password, salt);
        return newHash.equals(hash);
    }
}