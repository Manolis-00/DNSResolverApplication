package dns;

public class DnsQuestion {

    private String name;

    private Integer type;

    private Integer clazz;

    public DnsQuestion(String name) {
        this(name, QuestionType.ALL);
    }

    public DnsQuestion(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public DnsQuestion(String name, Integer type, Integer clazz) {
        this.name = name;
        this.type = type;
        this.clazz = clazz;
    }

    public DnsQuestion(String name, QuestionType questionType) {
    }

    public DnsQuestion(OctetReader reader) {

    }
}
