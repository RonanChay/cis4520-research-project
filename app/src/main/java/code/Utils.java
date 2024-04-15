package code;

import java.security.SecureRandom;
import java.util.HexFormat;

public class Utils {
    /**
     * Helper method - Converts a byte[] to a hex string
     * @param bytesHash Input byte array
     * @return Hex string representation of byte array
     */
    public static String convertBytesToHex(byte[] bytesHash) {
        return HexFormat.of().formatHex(bytesHash);
    }

    /**
     * Helper method - Generates random 16B salt as byte[]
     * @return Random 16B salt as byte[]
     */
    public static byte[] generateSalt16Bytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }
}
