package code;

import code.algorithms.Argon2idAlgo;
import code.algorithms.BcryptAlgo;
import code.algorithms.SHA512Algo;

import java.io.IOException;
import java.util.Scanner;

import static code.Utils.convertBytesToHex;

/**
 * Main class - Calls each algorithm and records + outputs metrics
 */
public class ResearchProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice; // User input for chosen menu option

        // Menu options
        System.out.println("\n============ CIS 4520 Research Project - Password Hashing Algorithms ============");
        System.out.println("1. Generate parameter-cost comparison data for SHA-512");
        System.out.println("2. Generate parameter-cost comparison data for BCrypt");
        System.out.println("3. Generate parameter-cost comparison data for Argon2id");
        System.out.println("4. Manual input for one-time test of each algorithm");
        System.out.println("Select an option (1-4): ");
        try {
            choice = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println("Error occurred when entering choice. Ensure that a valid integer was input");
            return;
        }

        if (choice >= 1 && choice <= 3) {
            // Generate cost data for specified algorithm
            ResultGenerator resultGenerator = new ResultGenerator();
            try {
                switch (choice) {
                    case 1:
                        resultGenerator.generateSHA512Results();
                        break;
                    case 2:
                        resultGenerator.generateBCryptResults();
                        break;
                    case 3:
                        resultGenerator.generateArgon2idResults();
                        break;
                }
            } catch (IOException ex) {
                System.out.println("Error occurred when writing to csv file: " + ex.getMessage());
            }
        } else if (choice == 4) {
            // Manual one-time algorithm tests
            long startTime, endTime, duration;
            byte[] hashedPassword;

            // SHA-512 Analysis
            SHA512Algo sha512 = new SHA512Algo();

            System.out.println("\n======== Analyzing SHA-512 Algorithm ========");
            sha512.getInputParams();
            System.out.println("\nHashing now...");

            startTime = System.currentTimeMillis();
            hashedPassword = sha512.hashPassword();
            endTime = System.currentTimeMillis();
            duration = endTime - startTime;

            System.out.println("\nResult:");
            System.out.println("Hashed Password: " + convertBytesToHex(hashedPassword));
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
            System.out.println("Salt: " + bcrypt.getSaltAsHex());
            System.out.println("Hashed Password: " + convertBytesToHex(hashedPassword));
            System.out.println("Time Taken: " + duration + "ms");

            // Argon2id Analysis - uses default parameters for memory limit, hash length, and number of threads
            Argon2idAlgo argon2id = new Argon2idAlgo();
            System.out.println("\n======== Analyzing Argon2id Algorithm ========");
            System.out.println("Default parameter values:");
            System.out.printf("Memory limit = %d    Hash length = %d    Number of threads = %d%n",
                    argon2id.getMemLimit(),
                    argon2id.getHashLength(),
                    argon2id.getNumThreads()
            );
            argon2id.getInputParams();
            System.out.println("\nHashing now...");

            startTime = System.currentTimeMillis();
            hashedPassword = argon2id.hashPassword();
            endTime = System.currentTimeMillis();
            duration = endTime - startTime;

            System.out.println("\nResult:");
            System.out.println("Salt: " + argon2id.getSaltAsHex());
            System.out.println("Hashed Password: " + convertBytesToHex(hashedPassword));
            System.out.println("Time Taken: " + duration + "ms");
        } else {
            // Invalid option number input
            System.out.println("Invalid option. Ensure a number (1-4) corresponding to the desired option is input");
        }

        System.out.println("\n\nExiting program...\n");
    }
}
