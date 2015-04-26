package test.microservices.b.bean;

/**
 * Created by Oreste Luci on 22/04/2015.
 */
public class Message {

    private final long id;
    private final String content;

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
