import java.util.*;

public class NameServiceClass implements NameService{

    private Hashtable<String,Object> table = new Hashtable<String, Object>();

    public Object resolve(String s){
        Object o = table.get(s);
        System.out.print("resolviendo " + s + " que es de tipo:");
        System.out.println(o.getClass().getName());
        return o;
    }

    public void bind(String s, Object o){
        System.out.print("binding " + s+ " que es de tipo:");
        System.out.println(o.getClass().getName());
        table.put(s, (Object)o);
    }


}

