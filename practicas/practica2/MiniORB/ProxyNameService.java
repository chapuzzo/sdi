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
        invo.putObject(o);
        invo.waitEnd();
        return;

    }

    public Object resolve(String s){
        Invocation invo = oref.newInvocation();
        invo.putInt(RESOLVE);
        invo.putString(s);
        Object o = invo.getObject();
        invo.waitEnd();
        return o;
    }

}
