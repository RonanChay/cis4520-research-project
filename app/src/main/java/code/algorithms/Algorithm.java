package code.algorithms;

/**
 * Interface to be implemented by password hashing algorithms if needed
 */
public interface Algorithm {
    public void getInputParams();
    public String hashPassword();
}
