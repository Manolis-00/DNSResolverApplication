import dns.OctetHelper;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.Assert.*;

/**
 * Unit test for the {@link OctetHelper}
 */
public class OctetHelperTest {

    @Test
    public void testGenerate16BitIdentifier() {
        Integer result = OctetHelper.generate16BitIdentifier();
        assertNotNull(result);
    }

    @Test
    public void testFormatWithLeadingZeros() {
        String result = OctetHelper.formatWithLeadingZeros(1, 10);
        assertEquals("0000000001", result);
    }

    @Test
    public void testIntegerToHexWithLeadingZeros() {
        String result = OctetHelper.integerToHexWithLeadingZeros(1, 10);
        assertEquals("00000000000000000001", result);
    }

    @Test
    public void testStringToHex() {
        String result = OctetHelper.stringToHex("input");
        assertEquals("696E707574", result);
    }

    @Test
    public void testHexToString() {
        String result = OctetHelper.hexToString("696E707574");
        assertEquals("input", result);
    }

    @Test
    public void testHexToInteger() {
        Integer result = OctetHelper.hexToInteger("10");
        assertEquals(Optional.of(16).get(), result);
    }

    @Test
    public void testHexStringToByteArray() {
        byte[] result = OctetHelper.hexStringToByteArray("03");
        assertNotNull(result);
    }

    @Test
    public void testByteArrayToHexString() {
        String result = OctetHelper.byteArrayToHexString(new byte[]{(byte) 0b11}, 1);
        assertEquals("03", result);
    }

    @Test
    public void testIsValidIPAddress_True() {
        assertTrue(OctetHelper.isValidIPAddress("1.1.1.1"));
    }

    @Test
    public void testIsValidIPAddress_False() {
        assertFalse(OctetHelper.isValidIPAddress("301.302.303.304"));
    }
}
