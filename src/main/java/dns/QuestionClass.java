package dns;

/**
 * {@link Enum} that defines the class of the Question.
 */
public enum QuestionClass {

    INTERNET(1),
    CSNET(2),
    CHAOS(3),
    HESIOD(4),
    ANY(255);

    /**
     * The value that corresponds to the {@link QuestionClass}
     */
    private final int value;

    /**
     * Constructor
     * @param value
     */
    QuestionClass(int value) {
        this.value = value;
    }

    /**
     * Getter
     * @return
     */
    public int getValue() {
        return value;
    }
}
