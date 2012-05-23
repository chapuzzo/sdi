import java.util.*;

public class NameServiceClass implements NameService{

    private Hashtable<String,Object> table = new Hashtable<String, Object>();

    public Object resolve(String s){
        //~ System.out.println("resolviendo " + s);
        return (Object)table.get(s);
    }

    public void bind(String s, Object o){
        //~ System.out.println("binding " + s);
        table.put(s, (Object)o);
    }


}

