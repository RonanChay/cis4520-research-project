package code.algorithms;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static code.Utils.convertBytesToHex;
import static code.Utils.generateSalt16Bytes;

public class Argon2Algo implements Algorithm {
    private String plaintextPassword = "";
    int numCycles = 2;
    int memLimit = 66536;
    int hashLength = 32;
    int numThreads = 1;
    byte[] salt;
    @Override
    public void getInputParams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password to hash: ");
        plaintextPassword = scanner.nextLine().strip();
        System.out.println("Enter number of iterations: ");
        numCycles = scanner.nextInt();
        System.out.println("Enter number of threads to use: ");
        numThreads = scanner.nextInt();
    }

    @Override
    public byte[] hashPassword() {
        salt = generateSalt16Bytes();

        Argon2Parameters.Builder argon2Builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(numCycles)
                .withMemoryAsKB(memLimit)
                .withParallelism(numThreads)
                .withSalt(salt);

        Argon2BytesGenerator argon2 = new Argon2BytesGenerator();
        argon2.init(argon2Builder.build());
        byte[] hashedPassword = new byte[hashLength];
        argon2.generateBytes(plaintextPassword.getBytes(StandardCharsets.UTF_8), hashedPassword, 0, hashedPassword.length);

        return hashedPassword;
    }

    public String getSaltAsHex() {
        return convertBytesToHex(salt);
    }
}
