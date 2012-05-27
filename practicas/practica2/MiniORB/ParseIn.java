
import java.io.*;
import java.lang.reflect.*;

public class ParseIn {
    // InputStream to read data from
    private InputStream is;

    public ParseIn (InputStream is) {
        this.is = is;
    }

    public int getInt () {
        byte [] b = new byte[4];
        int val = 0;

        try {
            is.read(b);
        } catch (IOException ioe) {
            return -1;
        }

        for (int i = 0; i < 4; i++) {
            val <<= 8;
            if ( b[0] >= 0 && b[i] < 0 )
                val |= ( 256 + b[i] );
            else
                val |= b[i];
        }

        return val;
    }

    public long getLong () {
        return (long) getInt();
    }

    public boolean getBool () {
            byte  b;

        try {
            b = (byte) is.read();

            if (b == 0) {
                // 0 is false
                return false;
            } else {
                // Other value is true
                return true;
            }
        } catch (IOException ioe) {
            return false;
        }
    }

    public String getString () {
        // First read the length of the string
        int l = getInt();
        byte [] b = new byte[l];

        try {
            // Read the chars of the string
            is.read(b);
        } catch (IOException ioe) {
        }

        return new String(b);
    }

    public Object getObject () {
       //System.out.println ("Ojooooo!! : has de implmementar estoooo");
       //System.out.print ("gO: ");
       ObjectRef or = getObjectRef();
       if (or.getIid() == 0 && or.getOid() == 0) return null;
       //System.out.println ("gotObjectRef: " + or);
       try { //creacion automática de Proxies en base a la clase del objeto
            String ProxyName = "Proxy" + or.getInterfaceName();
            //System.out.println ("ProxyName: " + ProxyName);
            Constructor<?>[] c = Class.forName(ProxyName).getConstructors();
            Object o = c[0].newInstance(or);
            //System.out.println("gO->"+o.getClass().getInterfaces()[0].getName());
            //System.out.println(o);
            return o;
       }
       catch (Exception E){
           System.out.println ("El experimento ha salido mal :( pasamos al método clasico)");
       }
       switch (or.getIid()) {
            case 1:  // "A"
                //~ System.out.println ("recibiendo A");
                return new ProxyA(or);
            case 2: // B
                //~ System.out.println ("recibiendo B");
                return new ProxyB(or);
            case 3: // NameService
                //~ System.out.println ("recibiendo NS");
                return new ProxyNameService(or);
            default:
                System.out.println ("mal!!");
       }
       return null;
    }

    public ObjectRef getObjectRef () {
        // The object has 4 fields:
        // 1) Address of the ORB
        // 2) Port of the ORB
        // 3) Object id
        // 4) Interface id
        String host = getString();
        //System.out.println ("gotHost: " + host);
        int port = getInt();
        //System.out.println ("gotPort: " + port);
        int obId = getInt();
        //System.out.println ("gotOid: " + obId);
        int iId = getInt();
        //System.out.println ("gotIid: " + iId);
        String cls = getString();
        //System.out.println ("gotIname: " + cls);

        // Create and return an object reference with those values
        ObjectRef oref = new ObjectRef(host,port,obId,iId,cls);
        return oref;
    }
}
