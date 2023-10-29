package dns;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class OctetReader {

    public static final Integer POINTER_MASK16 = 0xC000;
    public static final Integer POINTER_MASK8 = 0xC0;
    public static final Integer OFFSET_MASK16 = 0xFFFF - POINTER_MASK16;
    private final StringReader reader;
    private final String octetString;
    private final OctetReader parentReader;

    public OctetReader(String octetString) {
        this.octetString = octetString;
        this.reader = new StringReader(octetString);
        this.parentReader = null;
    }

    public OctetReader(byte[] octetBytes) {
        this.octetString = new String(octetBytes, StandardCharsets.UTF_8);
        this.reader = new StringReader(octetString);
        this.parentReader = null;
    }

    public OctetReader(byte[] octetBytes, OctetReader parentReader) {
        this.octetString = new String(octetBytes, StandardCharsets.UTF_8);
        this.reader = new StringReader(octetString);
        this.parentReader = parentReader;
    }

    public OctetReader(String octetString, Integer offset, OctetReader parentReader) throws IOException {
        this.octetString = octetString;
        this.reader = new StringReader(octetString);
        this.parentReader = parentReader;

        if (offset > 0) {
            Integer readChars = this.reader.read(new char[offset*2]);
            if (readChars != offset * 2) {
                throw new IOException(String.format("Read only %d instead of %d chars. End of stream",
                        readChars, offset * 2));
            }
            this.reader.mark(0);
        }
    }

    public Optional<String> readHex(Integer count) throws IOException {
        char[] buffer = new char[count*2];
        Integer charsRead = reader.read(buffer);

        if (charsRead.equals(count*2)) {
            return Optional.of(new String(buffer));
        }
        return Optional.empty();
    }

    public Optional<String> readHex() throws IOException {
        return this.readHex(1);
    }

    public Optional<Integer> readByte() throws IOException {
        return this.readHex().map(OctetHelper::hexToInteger);
    }
}
