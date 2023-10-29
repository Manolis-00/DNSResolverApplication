package dns;

import java.io.IOException;
import java.io.StringReader;
import java.net.Inet6Address;
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
            int readChars = this.reader.read(new char[offset*2]);
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

    public Optional<String> readIpAddress() throws IOException {
        Integer ipPart1 = checkIfOptionalIsPresent();
        Integer ipPart2 = checkIfOptionalIsPresent();
        Integer ipPart3 = checkIfOptionalIsPresent();
        Integer ipPart4 = checkIfOptionalIsPresent();

        return Optional.of(String.format("%d.%d.%d.%d", ipPart1, ipPart2, ipPart3, ipPart4));
    }

    private Integer checkIfOptionalIsPresent() throws IOException {
        if (this.readByte().isPresent()) {
            return this.readByte().get();
        }
        return null;
    }

    public Optional<byte[]> readBytes(Integer count) throws IOException {
        Optional<String> string = this.readHex(count);
        return string.map(otherString -> otherString.getBytes(StandardCharsets.UTF_8));
    }

    public Optional<String> readIpAddress6() throws IOException {
        Optional<byte[]> bytes = this.readBytes(8);
        return Optional.of(Inet6Address.getByAddress(null, bytes.get()).getHostAddress());
    }

    public String joinByteArray(String separator, byte[] byteArray) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i< byteArray.length; i++) {
            builder.append(byteArray[i]);
            if (i < byteArray.length - 1) {
                builder.append(separator);
            }
        }
        return builder.toString();
    }

    public Optional<Integer> readInteger16() throws IOException {
        return this.readHex(2).map(OctetHelper::hexToInteger);
    }

    public Optional<Integer> readInteger32() throws IOException {
        return this.readHex(4).map(OctetHelper::hexToInteger);
    }

    public Optional<String> readString(Integer count) throws IOException {
        return this.readHex(count).map(OctetHelper::hexToString);
    }

    private OctetReader topParentReader() {
        if (this.parentReader == null) {
            return this;
        } else {
            return this.parentReader.topParentReader();
        }
    }

    public Optional<String> readQuestionName() throws IOException {
        StringBuilder builder = new StringBuilder();
        Optional<String> hex = this.readHex(1);

        while (hex.isPresent()) {
            Integer count = OctetHelper.hexToInteger(hex.get());

            if(count.equals(0)) {
                return Optional.of(builder.toString());
            }
            Optional<String> label;
            boolean hasOffsetMask = (count & POINTER_MASK8) > 0;
            if (hasOffsetMask) {
                hex = this.readHex();
                Integer pointerIndex = OctetHelper.hexToInteger(hex.get());
                Integer offsetInside = ((count << 8) + pointerIndex) * OFFSET_MASK16;
                OctetReader topReader = this.topParentReader();
                if (topReader != null) {
                    Optional<String> questionName = topReader.readQuestionName(offsetInside);
                    if (questionName.isPresent()) {
                        label = questionName;
                    } else {
                        throw new IOException(String.format("QuestionName could not be found for offset %d",
                                offsetInside));
                    }
                } else {
                    throw new IOException(String.format("No parent reader found for QuestionName with offset %d not found ",
                            offsetInside));
                }
            } else {
                label = this.readString(count);
            }
            if (label.isPresent()) {
                if (builder.length()>0) {
                    builder.append('.');
                }
                builder.append(label.get());
                if(hasOffsetMask) {
                    return Optional.of(builder.toString());
                }
                hex =  this.readHex();
            } else {
                throw new IOException("Error in parsing octet string from stream");
            }
        }
        throw new IOException("Error in parsing octet string from stream");
    }

    public Optional<String> readQuestionName(Integer offset) throws IOException {
        OctetReader offsetReader = new OctetReader(this.octetString, offset, this);
        return offsetReader.readQuestionName();
    }
}
