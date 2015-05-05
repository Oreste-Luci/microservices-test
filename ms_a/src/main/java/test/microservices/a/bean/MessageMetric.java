package test.microservices.a.bean;

/**
 * Created by olivernoguera on 05/05/2015.
 */
public class MessageMetric {
    private Message message;
    private long generatingTime;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getGeneratingTime() {
        return generatingTime;
    }

    public void setGeneratingTime(long generatingTime) {
        this.generatingTime = generatingTime;
    }
}
