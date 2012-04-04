
public class ProxyNameService extends Proxy implements NameService{

    public static final int BIND = 1;
    public static final int RESOLVE = 2;

    public ProxyNameService (ObjectRef oref) {
        super (oref);
    }

    public void bind(String s, Object o){
        Invocation invo = oref.newInvocation();
        invo.putInt(BIND);
        invo.putString(s);
        if (o instanceof Proxy)
            invo.putObject(((Proxy)o).oref);
        else
            invo.putObject(o);
        invo.getInt();

        return;

    }

    public Object resolve(String s){
        Invocation invo = oref.newInvocation();
        invo.putInt(RESOLVE);
        invo.putString(s);

        Object ret = invo.getObject();
        invo.getInt();
        if (ret instanceof ObjectRef)
            return new Proxy((ObjectRef)ret);
        else
            return ret;
    }

}
