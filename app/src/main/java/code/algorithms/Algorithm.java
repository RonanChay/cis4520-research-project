package code.algorithms;

/**
 * Interface to be implemented by password hashing algorithms
 */
public interface Algorithm {
    void getInputParams();
    byte[] hashPassword();
}
