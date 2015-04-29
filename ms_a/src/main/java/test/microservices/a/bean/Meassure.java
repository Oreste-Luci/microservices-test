package test.microservices.a.bean;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Oreste Luci
 */
public class Meassure {

    private long calls;
    private long lines;
    private long timeTaken;

    public long getCalls() {
        return calls;
    }

    public void setCalls(long calls) {
        this.calls = calls;
    }

    public long getLines() {
        return lines;
    }

    public void setLines(long lines) {
        this.lines = lines;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public String getTimeTakenFormated() {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        return nf.format(timeTaken) + " nano seconds.";
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
