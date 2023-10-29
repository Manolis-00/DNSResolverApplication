package dns;

import java.util.ArrayList;
import java.util.List;

public class DnsMessage {

    private Integer id;

    private final int flags = 0;

    private final List<DnsQuestion> questionList = new ArrayList<>();
}
