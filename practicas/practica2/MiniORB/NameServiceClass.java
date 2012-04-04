import java.util.*;

public class NameServiceClass implements NameService{

    private Hashtable<String,Object> table = new Hashtable<String, Object>();

    public Object resolve(String s){
        System.out.println("resolviendo" + s);
        return table.get(s);
    }

    public void bind(String s, Object o){
        table.put(s, o);
    }


}

