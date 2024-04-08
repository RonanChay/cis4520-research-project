package code.algorithms;

/**
 * Interface to be implemented by password hashing algorithms
 */
public interface Algorithm {
    /**
     * For Manual one-time test - prompt user for parameter values for an algorithm
     */
    void getInputParams();

    /**
     * Hash the password using the set parameter values
     * @return Hash of the plaintext password
     */
    byte[] hashPassword();
}
