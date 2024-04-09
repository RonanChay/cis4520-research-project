package code;

import java.security.SecureRandom;
import java.util.HexFormat;

public class Utils {
    public static String convertBytesToHex(byte[] bytesHash) {
        return HexFormat.of().formatHex(bytesHash);
    }

    public static byte[] generateSalt16Bytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }
}
