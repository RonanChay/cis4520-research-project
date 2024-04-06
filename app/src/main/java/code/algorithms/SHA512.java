package code.algorithms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * SHA512 Class
 * Provides the necessary methods to input password and parameter
 * and perform SHA-512 hash
 */
public class SHA512 implements Algorithm {
    // Plain text password input
    private String plaintextPassword = "";
    // Work function parameter input
    private int workFunction = 0;

    // Prompts user for plain text password and work function parameter
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter work function: ");
        workFunction = scanner.nextInt();
    }

    /**
     * Hashes the password based on the work function parameter
     * Number of iterations = 2^(work function)
     * @return String final output password hash
     */
    @Override
    public String hashPassword() {
        String finalHash = plaintextPassword;
        for (int i = 0; i < 1L << workFunction; i++) {
            finalHash = generateHash(finalHash);
        }
        return finalHash;
    }

    /**
     * Performs one SHA-512 hashing operation on the password given
     * @param passwordToHash Password to be hashed
     * @return String hash of password
     */
    private String generateHash(String passwordToHash) {
        String generatedPasswordHash = "-";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytesHash = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            // Convert to hex
            StringBuilder hexString = new StringBuilder();
            for (byte aByte : bytesHash) {
                hexString.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPasswordHash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO: Better error handling
            e.printStackTrace();
        }
        return generatedPasswordHash;
    }
}
