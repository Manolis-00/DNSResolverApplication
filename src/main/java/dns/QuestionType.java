package dns;

import java.util.Arrays;

/**
 * {@link Enum} that defines the type of the Question.
 */
public enum QuestionType {

    A(1),
    NS(2),
    CNAME(5),
    SOA(6),
    WKS(11),
    PTR(12),
    HINFO(13),
    MX(15),
    TXT(16),
    AAAA(28),
    ALL(255);

    /**
     * The value that corresponds to the {@link QuestionType}
     */
    private final Integer value;

    /**
     * Constructor
     * @param value
     */
    QuestionType(Integer value) {
        this.value = value;
    }

    /**
     * Getter
     * @return
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Get the {@link String} equivalent of the {@link Integer} value.
     * @param value
     * @return
     */
    public static String stringValueOf(Integer value) {
        for (QuestionType questionType : QuestionType.values()) {
            if (questionType.value.equals(value)) {
                return questionType.toString();
            }
        }
        return String.format("Unknown<%s>", value);
    }

    /**
     * Get the {@link QuestionType} equivalent of the {@link Integer} value.
     * @param value
     * @return
     */
    public static QuestionType valueOf(Integer value) {
        for (QuestionType questionType : QuestionType.values()) {
            if (questionType.value.equals(value)) {
                return questionType;
            }
        }
        throw new IllegalArgumentException("No matching value for " + value);
    }

    /**
     * Checks if the {@link Integer} parameter is valid.
     * @param value
     * @return
     */
    public static boolean isValid(final Integer value) {
        return Arrays.stream(QuestionType.values()).
                anyMatch(questionType -> questionType.value.equals(value));
    }
}
