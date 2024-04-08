package code.algorithms;

import org.bouncycastle.crypto.generators.BCrypt;

import java.util.Scanner;

import static code.Utils.convertBytesToHex;
import static code.Utils.generateSalt16Bytes;

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

    @Override
    public byte[] hashPassword() {
        byte[] byteInputPassword = BCrypt.passwordToByteArray(plaintextPassword.toCharArray());
        salt = generateSalt16Bytes();
        return BCrypt.generate(byteInputPassword, salt, workFunction);
    }

    public String getSaltAsHex() {
        return convertBytesToHex(salt);
    }
}
