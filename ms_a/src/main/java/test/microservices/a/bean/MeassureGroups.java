package test.microservices.a.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by olivernoguera on 07/05/2015.
 */
public class MeassureGroups implements Iterable{
    private long calls;
    private long lines;
    private Map<String,MeassureGroup> meassures;


    public MeassureGroups() {
        this.meassures = new HashMap<String,MeassureGroup>();
    }

    public MeassureGroups(final long calls, final long lines) {
        this.meassures = new HashMap<String,MeassureGroup>();
        this.calls = calls;
        this.lines = lines;
    }

    public void put(String name, MeassureGroup value)
    {
        meassures.put(name,value);
    }

    public MeassureGroup get(String name)
    {
        return meassures.get(name);
    }

    public int size()
    {
        return meassures.size();
    }

    public Map<String,MeassureGroup> getMap()
    {
        return meassures;
    }

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


    public Iterator<String> iterator()
    {
        return meassures.keySet().iterator();
    }


}
