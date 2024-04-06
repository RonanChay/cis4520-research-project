package code.algorithms;

/**
 * Interface to be implemented by password hashing algorithms if needed
 */
public interface Algorithm {
    void getInputParams();
    String hashPassword();
}
