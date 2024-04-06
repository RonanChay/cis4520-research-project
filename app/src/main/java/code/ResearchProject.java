package code;

import code.algorithms.Argon2Algo;
import code.algorithms.BcryptAlgo;
import code.algorithms.SHA512Algo;

import java.util.Scanner;

import static code.Utils.convertBytesToHex;

/**
 * Main class - Calls each algorithm and records + outputs metrics
 */
public class ResearchProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n============ CIS 4520 Research Project - Password Hashing Algorithms ============");
        System.out.println("1. Automatic input for parameter comparison");
        System.out.println("2. Manual input for one-time test");
        System.out.println("Select an option (enter the number): ");
        int choice = Integer.parseInt(scanner.nextLine().strip());

        if (choice == 1) {
            // auto loop though all possible parameter combinations

        } else if (choice == 2) {
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

            // BCrypt Analysis
            Argon2Algo argon2 = new Argon2Algo();
            System.out.println("\n======== Analyzing Argon2 Algorithm ========");
            argon2.getInputParams();
            System.out.println("\nHashing now...");

            startTime = System.currentTimeMillis();
            hashedPassword = argon2.hashPassword();
            endTime = System.currentTimeMillis();
            duration = endTime - startTime;

            System.out.println("\nResult:");
            System.out.println("Salt: " + argon2.getSaltAsHex());
            System.out.println("Hashed Password: " + convertBytesToHex(hashedPassword));
            System.out.println("Time Taken: " + duration + "ms");
        } else {
            System.out.println("Invalid option");
        }

        System.out.println("\n\nExiting program...\n");
    }
}
