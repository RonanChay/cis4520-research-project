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
    private int workFunction = 0;   // Work function parameter input

    // Prompts user for plain text password and work function parameter
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter work function: ");
        workFunction = scanner.nextInt();
    }

    public String getPlaintextPassword() {
        return plaintextPassword;
    }
    public void setPlaintextPassword(String plaintextPassword) {
        this.plaintextPassword = plaintextPassword;
    }
    public int getWorkFunction() {
        return workFunction;
    }
    public void setWorkFunction(int workFunction) {
        this.workFunction = workFunction;
    }

    /**
     * Hashes the password based on the work function parameter
     * Number of iterations = 2^(work function)
     * @return String final output password hash
     */
    @Override
    public byte[] hashPassword() {
        byte[] finalHash = generateHash(plaintextPassword.getBytes(StandardCharsets.UTF_8));
        // total num iterations = 2^(workFunction)
        for (int i = 0; i < 1L << workFunction - 1; i++) {
            finalHash = generateHash(finalHash);
        }
        return finalHash;
    }

    /**
     * Performs one SHA-512 hashing operation on the password given
     * @param passwordToHash Password to be hashed
     * @return String hash of password
     */
    private byte[] generateHash(byte[] passwordToHash) {
        byte[] generatedPasswordHash = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            generatedPasswordHash = md.digest(passwordToHash);
        } catch (NoSuchAlgorithmException e) {
            // TODO: Better error handling
            e.printStackTrace();
        }
        return generatedPasswordHash;
    }
}
