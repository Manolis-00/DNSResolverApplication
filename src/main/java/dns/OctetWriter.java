package dns;

import java.util.Arrays;

/**
 * The OctetWriter class.
 */
public class OctetWriter {

    /**
     * The builder field.
     */
    private final StringBuilder builder;

    /**
     * Single argument constructor
     * @param builder
     */
    public OctetWriter(StringBuilder builder) {
        this.builder = builder;
    }

    /**
     * Appends the QuestionName
     * @param name
     * @return
     */
    public OctetWriter appendQuestionName(String name) {
        String[] list = Arrays.stream(name.split("\\.")).map(
                (x) -> OctetHelper.integerToHexWithLeadingZeros(x.length(), 1)
                + OctetHelper.stringToHex(x)).toArray(String[]::new);
        String encodedMessage = String.join("", list) + OctetHelper.integerToHexWithLeadingZeros(0, 1);
        this.append(encodedMessage);
        return this;
    }

    /**
     * Appends a String to the OctetWriter
     * @param text
     * @return
     */
    public OctetWriter append(String text) {
        this.builder.append(text);
        return this;
    }

    /**
     * Appends an integer to a hex number
     * @param integer
     * @return
     */
    public OctetWriter appendInteger16(Integer integer) {
        return this.append(OctetHelper.integerToHexWithLeadingZeros(integer, 2));
    }

    /**
     * Appends an integer.
     * @param integer
     * @return
     */
    public OctetWriter appendInteger(Integer integer) {
        integer = integer & 0xFF;
        return this.append(OctetHelper.integerToHexWithLeadingZeros(integer, 1));
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
