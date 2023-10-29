import dns.OctetHelper;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Unit test for the {@link OctetHelper}
 */
public class OctetHelperTest {

    @Test
    public void testGenerate16BitIdentifier() {
        Integer result = OctetHelper.generate16BitIdentifier();
        assertNotNull(result);
    }
}
