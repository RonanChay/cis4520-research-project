package code;

import code.algorithms.SHA512;

/**
 * Main class - Calls each algorithm and records+outputs metrics
 */
public class ResearchProject {
    public static void main(String[] args) {
        SHA512 sha512 = new SHA512();

        // SHA-512 Analysis
        System.out.println("\n======== Analyzing SHA-512 Algorithm ========");
        sha512.getInputParams();
        System.out.println("\nHashing now...");

        long startTime = System.currentTimeMillis();
        String hashedPassword = sha512.hashPassword();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("\nResult:");
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("Time Taken: " + duration + "ms");
    }
}
