import dns.OctetWriter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link OctetWriter}
 */
public class OctetWriterTest {

    /**
     * Declaration of the stringbuilder
     */
    StringBuilder stringBuilder;
    /**
     * Declaration of the OctetWriter.
     */
    OctetWriter octetWriter;

    /**
     * Initialization of the global variables
     */
    @Before
    public void init() {
        stringBuilder = new StringBuilder();
        octetWriter = new OctetWriter(stringBuilder);
    }

    /**
     * Unit test for the {@link OctetWriter#appendQuestionName(String)} method
     */
    @Test
    public void testAppendQuestionName() {
        assertEquals(octetWriter, octetWriter.appendQuestionName("Name"));
    }

    /**
     * Unit test for the {@link OctetWriter#append(String)} method
     */
    @Test
    public void testAppend() {
        assertEquals(octetWriter, octetWriter.append("name"));
    }

    /**
     * Unit test for the {@link OctetWriter#appendInteger16(Integer)} method
     */
    @Test
    public void testAppendInteger16() {
        assertEquals(octetWriter, octetWriter.appendInteger16(5));
    }

    /**
     * Unit test for the {@link OctetWriter#appendInteger(Integer)} method
     */
    @Test
    public void testAppendInteger() {
        assertEquals(octetWriter, octetWriter.appendInteger(15));
    }
}
