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
public class SHA512Algo implements Algorithm {
    private String plaintextPassword = "";  // Plain text password input
    private int workFactor = 0;   // Work factor parameter input

    // Getters + Setters
    public String getPlaintextPassword() {
        return plaintextPassword;
    }
    public void setPlaintextPassword(String plaintextPassword) {
        this.plaintextPassword = plaintextPassword;
    }
    public int getWorkFactor() {
        return workFactor;
    }
    public void setWorkFactor(int workFactor) {
        this.workFactor = workFactor;
    }

    // Prompts user for plain text password and work factor parameter
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter work factor: ");
        workFactor = scanner.nextInt();
    }

    /**
     * Hashes the password based on the work factor parameter
     * Number of iterations = 2^(work factor)
     * @return byte[] of final output password hash
     */
    @Override
    public byte[] hashPassword() {
        byte[] finalHash = generateHash(plaintextPassword.getBytes(StandardCharsets.UTF_8));
        // total num iterations = 2^(workFactor)
        for (int i = 0; i < 1L << workFactor - 1; i++) {
            finalHash = generateHash(finalHash);
        }
        return finalHash;
    }

    /**
     * Performs one SHA-512 hashing operation on the plaintext password
     * @param passwordToHash Password to be hashed
     * @return byte[] of hash of password
     */
    private byte[] generateHash(byte[] passwordToHash) {
        byte[] generatedPasswordHash = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            generatedPasswordHash = md.digest(passwordToHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error occurred when generating hash: " + e.getMessage());
        }
        return generatedPasswordHash;
    }
}
