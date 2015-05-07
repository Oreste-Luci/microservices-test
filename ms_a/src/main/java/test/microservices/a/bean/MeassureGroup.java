package test.microservices.a.bean;

/**
 * Created by olivernoguera on 05/05/2015.
 */
public class MeassureGroup {
    private long calls;
    private long lines;
    private long avgTimeTaken;
    private long minTimeTaken = 10000000000L;
    private long maxTimeTaken = 0L;

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

    public long getAvgTimeTaken() {
        return avgTimeTaken;
    }

    public void setAvgTimeTaken(long avgTimeTaken) {
        this.avgTimeTaken = avgTimeTaken;
    }

    public long getMinTimeTaken() {
        return minTimeTaken;
    }

    public void setMinTimeTaken(long minTimeTaken) {
        this.minTimeTaken = minTimeTaken;
    }

    public long getMaxTimeTaken() {
        return maxTimeTaken;
    }

    public void setMaxTimeTaken(long maxTimeTaken) {
        this.maxTimeTaken = maxTimeTaken;
    }


    public void addTimeMinMax(final long time)
    {
        if( this.getMinTimeTaken() > time)
        {
            this.setMinTimeTaken(time);
        }

        if( this.getMaxTimeTaken() < time)
        {
            this.setMaxTimeTaken(time);
        }
    }


    @Override
    public String toString() {
        return "MeassureGroup{" +
                "calls=" + calls +
                ", lines=" + lines +
                ", avgTimeTaken=" + avgTimeTaken +
                ", minTimeTaken=" + minTimeTaken +
                ", maxTimeTaken=" + maxTimeTaken +
                '}';
    }
}
