
public class ProxyNameService extends Proxy implements NameService{
    public ProxyNameService (ObjectRef oref) {
        super (oref);
    }

    public void bind(String s, Object o){
        Invocation invo = oref.newInvocation();
        invo.putInt(1);
        invo.putString(s);

        invo.putObject(o);
        invo.getInt();

        return;

    }

    public Object resolve(String s){
        Invocation invo = oref.newInvocation();
        invo.putInt(2);
        invo.putString(s);

        Object ret = invo.getObject();
        invo.getInt();

        return ret;
    }

}
