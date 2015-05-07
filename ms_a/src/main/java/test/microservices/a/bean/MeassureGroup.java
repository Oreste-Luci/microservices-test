package test.microservices.a.bean;

/**
 * Created by olivernoguera on 05/05/2015.
 */
public class MeassureGroup {

    private long avgTimeTaken;
    private long minTimeTaken = Long.MAX_VALUE;
    private long maxTimeTaken = Long.MIN_VALUE;

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
                " avgTimeTaken=" + avgTimeTaken +
                ", minTimeTaken=" + minTimeTaken +
                ", maxTimeTaken=" + maxTimeTaken +
                '}';
    }
}
