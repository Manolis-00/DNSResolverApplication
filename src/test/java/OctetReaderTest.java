import dns.OctetReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the {@link OctetReader}
 */
public class OctetReaderTest {

    OctetReader stringOctetReader;
    OctetReader integerOctetReader;

    @Before
    public void initialize() {
        stringOctetReader = new OctetReader("octetString");
        integerOctetReader = new OctetReader("88");
    }

    @After
    public void reset() {

    }

    @Test
    public void testReadHexWithParameters() throws IOException {
        Optional<String> result = stringOctetReader.readHex(2);
        assertTrue("octe", result.isPresent());
    }

    @Test
    public void testReadHexWithoutParameters() throws IOException {
        Optional<String> result = stringOctetReader.readHex();
        assertTrue("oc", result.isPresent());
    }

    @Test
    public void testReadByte() throws IOException {
        Optional<Integer> result = integerOctetReader.readByte();
        assertTrue("136", result.isPresent());
    }

    @Test
    public void testReadIpAddress() throws IOException {
        Optional<String> result = integerOctetReader.readIpAddress();
        assertNotNull(result);
    }
}
