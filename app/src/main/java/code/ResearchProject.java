package code;

import code.algorithms.BcryptAlgo;
import code.algorithms.SHA512;

/**
 * Main class - Calls each algorithm and records+outputs metrics
 */
public class ResearchProject {
    public static void main(String[] args) {
        long startTime, endTime, duration;
        String hashedPassword;

        // SHA-512 Analysis
        SHA512 sha512 = new SHA512();

        System.out.println("\n======== Analyzing SHA-512 Algorithm ========");
        sha512.getInputParams();
        System.out.println("\nHashing now...");

        startTime = System.currentTimeMillis();
        hashedPassword = sha512.hashPassword();
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;

        System.out.println("\nResult:");
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("Time Taken: " + duration + "ms");

        // BCrypt Analysis
        BcryptAlgo bcrypt = new BcryptAlgo();
        System.out.println("\n======== Analyzing BCrypt Algorithm ========");
        bcrypt.getInputParams();
        System.out.println("\nHashing now...");

        startTime = System.currentTimeMillis();
        hashedPassword = bcrypt.hashPassword();
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;

        System.out.println("\nResult:");
        System.out.println("Salt: " + bcrypt.getSaltHex());
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("Time Taken: " + duration + "ms");
    }
}
