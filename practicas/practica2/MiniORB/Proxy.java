
// This class must be extended by all the proxies

public class Proxy {
    ObjectRef oref;

    public Proxy (ObjectRef oref) {
        this.oref = oref;
    }

    public String toString(){
        return oref.toString();
    }
}
