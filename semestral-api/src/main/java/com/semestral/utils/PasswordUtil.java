package com.semestral.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {

    /**
     * Generating salt using Java class SecureRandom.
     * Inspiration: <a href="https://www.baeldung.com/java-password-hashing">...</a>
     * @return salt
     */
    // Generate a random salt
    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        return salt;
    }

    /**
     * Function for hashing the password.
     * Hashing provided password with the salt generated to obtain hashedPassword.
     * Inspiration from: <a href="https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash">...</a>
     * @param password
     * @param salt
     * @return
     */
    // Hash the password using SHA-256 and the provided salt
    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Tries to hash the entered password with the salt already existing in the database.
     * If this result is the same as the hashed password stored in the database,
     * it means the password entered was correct, therefore the verify process
     * is valid
     * @param enteredPassword
     * @param storedHashedPassword
     * @param salt
     * @return boolean validLogin
     */
    // Verify the entered password against the stored hashed password and salt
    public static boolean verifyPassword(String enteredPassword, String storedHashedPassword, byte[] salt) {
        String hashedEnteredPassword = hashPassword(enteredPassword, salt);
        return hashedEnteredPassword != null && hashedEnteredPassword.equals(storedHashedPassword);
    }

    /**
     * Function for coverting string into the hex format
     * Inspiration from: <a href="https://107.180.101.230/faq/java/java-convert-a-byte-array-to-a-hex-string.html">...</a>
     * @param bytes
     * @return
     */
    // Convert byte array to hex string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();

        for (byte aByte : bytes) {
            hexStringBuilder.append(String.format("%02x", aByte));
        }

        return hexStringBuilder.toString();
    }
}
