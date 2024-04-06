package code.algorithms;

import org.bouncycastle.crypto.generators.BCrypt;

import java.security.SecureRandom;
import java.util.Scanner;

import static code.Utils.convertBytesToHex;

public class BcryptAlgo implements Algorithm {

    private String plaintextPassword = ""; // Plain text password input (up to 72 bytes)

    private int workFunction = 0; // Work function parameter input (4..31 inclusive)
    private byte[] salt; // Random 16-byte salt used for password
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter work function (4 to 31 inclusive): ");
        workFunction = scanner.nextInt();
    }

    @Override
    public byte[] hashPassword() {
        byte[] byteInputPassword = BCrypt.passwordToByteArray(plaintextPassword.toCharArray());
        salt = generateSalt16Bytes();
        return BCrypt.generate(byteInputPassword, salt, workFunction);
    }

    public String getSaltHex() {
        return convertBytesToHex(salt);
    }

    private byte[] generateSalt16Bytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }
}
