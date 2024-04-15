package code.algorithms;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static code.Utils.convertBytesToHex;
import static code.Utils.generateSalt16Bytes;

public class Argon2idAlgo implements Algorithm {
    private String plaintextPassword = "";
    int numIterations = 1;  // Number of iterations parameter, default = 1
    int memLimit = 10240;   // Memory limit parameter (in KiB), default = 10MiB
    int hashLength = 32;    // Hash length parameter (in bytes), default = 32B
    int numThreads = 1;     // Number of threads parameter, default = 1
    byte[] salt;            // Random 16B salt

    // Getters + Setters
    public String getPlaintextPassword() {
        return plaintextPassword;
    }
    public void setPlaintextPassword(String plaintextPassword) {
        this.plaintextPassword = plaintextPassword;
    }
    public int getNumIterations() {
        return numIterations;
    }
    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }
    public int getMemLimit() {
        return memLimit;
    }
    public void setMemLimit(int memLimit) {
        this.memLimit = memLimit;
    }
    public int getHashLength() {
        return hashLength;
    }
    public void setHashLength(int hashLength) {
        this.hashLength = hashLength;
    }
    public int getNumThreads() {
        return numThreads;
    }
    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    // Prompts user for plain text password and number of iterations parameter
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter number of iterations: ");
        numIterations = scanner.nextInt();
    }

    // Hash the plaintext password using Argon2idv10 using set parameters and random 16B salt
    @Override
    public byte[] hashPassword() {
        salt = generateSalt16Bytes();

        // Set the parameters
        Argon2Parameters.Builder argon2Params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_10)
                .withIterations(numIterations)
                .withMemoryAsKB(memLimit)
                .withParallelism(numThreads)
                .withSalt(salt);

        Argon2BytesGenerator argon2id = new Argon2BytesGenerator();
        argon2id.init(argon2Params.build());    // Initialise parameters for Argon2id object
        byte[] hashedPassword = new byte[hashLength];
        argon2id.generateBytes(
                plaintextPassword.getBytes(StandardCharsets.UTF_8), // plaintext password as byte[]
                hashedPassword,                                     // byte[] to store output hash
                0,                                                  // offset output by 0 bytes
                hashedPassword.length                               // set hash length as 32B
        );

        return hashedPassword;
    }

    // Getter method for salt, converts byte[] to hex string
    public String getSaltAsHex() {
        return convertBytesToHex(salt);
    }
}
