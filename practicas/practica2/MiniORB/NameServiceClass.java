import java.util.*;

public class NameServiceClass implements NameService{

    private Hashtable<String,Object> table = new Hashtable<String, Object>();

    public Object resolve(String s){
        Object o = table.get(s);
        return o;
    }

    public void bind(String s, Object o){
        table.put(s, (Object)o);
    }


}

