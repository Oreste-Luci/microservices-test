package test.microservices.a.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by olivernoguera on 07/05/2015.
 */
public class MeassureGroups implements Iterable{
    private Map<String,MeassureGroup> meassures;


    public MeassureGroups() {
        this.meassures = new HashMap<String,MeassureGroup>();
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

    public Iterator<String> iterator()
    {
        return meassures.keySet().iterator();
    }


}
