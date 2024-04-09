package code.algorithms;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static code.Utils.convertBytesToHex;
import static code.Utils.generateSalt16Bytes;

public class Argon2idAlgo implements Algorithm {
    private String plaintextPassword = "";
    int numIterations = 1;
    int memLimit = 10240;   // in KiB
    int hashLength = 32;    // in Bytes
    int numThreads = 1;
    byte[] salt;
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter number of iterations: ");
        numIterations = scanner.nextInt();
    }

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

    @Override
    public byte[] hashPassword() {
        salt = generateSalt16Bytes();

        Argon2Parameters.Builder argon2Params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_10)
                .withIterations(numIterations)
                .withMemoryAsKB(memLimit)
                .withParallelism(numThreads)
                .withSalt(salt);

        Argon2BytesGenerator argon2id = new Argon2BytesGenerator();
        argon2id.init(argon2Params.build());
        byte[] hashedPassword = new byte[hashLength];
        argon2id.generateBytes(plaintextPassword.getBytes(StandardCharsets.UTF_8), hashedPassword, 0, hashedPassword.length);

        return hashedPassword;
    }

    public String getSaltAsHex() {
        return convertBytesToHex(salt);
    }
}
