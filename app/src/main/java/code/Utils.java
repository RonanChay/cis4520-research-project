package code;

import java.security.SecureRandom;

public class Utils {
    public static String convertBytesToHex(byte[] bytesHash) {
        // Convert to hex
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytesHash) {
            hexString.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return hexString.toString();
    }

    public static byte[] generateSalt16Bytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }
}
