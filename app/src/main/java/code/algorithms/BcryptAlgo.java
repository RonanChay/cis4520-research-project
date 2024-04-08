package code.algorithms;

import org.bouncycastle.crypto.generators.BCrypt;

import java.util.Scanner;

import static code.Utils.convertBytesToHex;
import static code.Utils.generateSalt16Bytes;

public class BcryptAlgo implements Algorithm {

    private String plaintextPassword = ""; // Plain text password input (up to 72 bytes)

    private int workFactor = 0; // Work factor parameter input (4..31 inclusive)
    private byte[] salt; // Random 16-byte salt used for password
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter work factor (4 to 31 inclusive): ");
        workFactor = scanner.nextInt();
    }

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

    @Override
    public byte[] hashPassword() {
        byte[] byteInputPassword = BCrypt.passwordToByteArray(plaintextPassword.toCharArray());
        salt = generateSalt16Bytes();
        return BCrypt.generate(byteInputPassword, salt, workFactor);
    }

    public String getSaltAsHex() {
        return convertBytesToHex(salt);
    }
}
