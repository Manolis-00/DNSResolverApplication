package dns;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Random;

/**
 * A helper class, that makes the usage of octets easier.
 */
public class OctetHelper {
    /**
     * A pseudo random number.
     */
    private static final Random random = new SecureRandom();

    /**
     * Generates a 16-bit identifier based on a random number.
     * @return
     */
    public static Integer generate16BitIdentifier() {
        return random.nextInt(1 << 16);
    }

    /**
     * It formats the number and adds zeros in the beginning.
     * @param number
     * @param width
     * @return
     */
    public static String formatWithLeadingZeros(Integer number, Integer width) {
        return String.format("%0" + width +"d", number);
    }

    /**
     * It transforms {@link Integer} to hexadecimal and adds zeros in the start.
     * @param number
     * @param width
     * @return
     */
    public static String integerToHexWithLeadingZeros(Integer number, Integer width) {
        return String.format("%0" + width * 2 + "X", number);
    }

    /**
     * Transforms a {@link String} to the hexadecimal equivalent.
     * @param input
     * @return
     */
    public static String stringToHex(String input) {
        StringBuilder hexStringBuilder = new StringBuilder();

        byte[] bytes = input.getBytes();
        for (byte b:bytes) {
            String hex = String.format("%02X", b).toUpperCase();
            hexStringBuilder.append(hex);
        }

        return hexStringBuilder.toString();
    }

    /**
     * It transfors a hexadecimal to the {@link String} equivalent
     * @param hex
     * @return
     */
    public static String hexToString(String hex) {          //todo if the number is not even, there might be issues
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < hex.length(); i += 2) {
            String pair = hex.substring(i, i+2);            //gets a pair of the hex at all times.
            int charCode = Integer.parseInt(pair, 16); //radix = 16 for the corresponding numbering system
            stringBuilder.append((char) charCode);
        }

        return stringBuilder.toString();
    }

    /**
     * Transformation of a hexadecimal to the {@link Integer} equivalent
     * @param hex
     * @return
     */
    public static Integer hexToInteger(String hex) {
        return Integer.parseInt(hex, 16);
    }

    /**
     * Transformation of a hexadecimal in {@link String} datatype to an array of {@link byte}s.
     * @param hex
     * @return
     */
    public static byte[] hexStringToByteArray(String hex) {
        int length = hex.length();
        byte[] data = new byte[length/2];
        for (int i = 0; i < length; i += 2) {
            data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) +
                    Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Trnasformation of an array of bytes to a hexadecimal number in the form of a {@link String}
     * @param data
     * @param length
     * @return
     */
    public static String byteArrayToHexString(byte[] data, Integer length) {
        StringBuilder hex = new StringBuilder(length*2);
        for (int i = 0; i < length; i++) {
            hex.append(String.format("%02X", data[i]));
        }
        return hex.toString();
    }

    /**
     * Checks if the input is a valid IP address.
     * @param ipAddress
     * @return
     */
    public static Boolean isValidIPAddress(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
