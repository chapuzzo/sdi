import java.io.*;
import java.lang.reflect.*;

public class ParseIn {
    // InputStream to read data from
    private InputStream is;

    public ParseIn(InputStream is) {
        this.is = is;
    }

    public int getInt() {
        byte[] b = new byte[4];
        int val = 0;

        try {
            is.read(b);
        } catch (IOException ioe) {
            throw new MiniORBException("cannot getInt!!");
        }

        for (int i = 0; i < 4; i++) {
            val <<= 8;
            if (b[0] >= 0 && b[i] < 0)
                val |= (256 + b[i]);
            else
                val |= b[i];
        }

        return val;
    }

    public long getLong() {
        return (long) getInt();
    }

    public boolean getBool() {
        byte b;

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
            throw new MiniORBException("cannot getBool!!");
        }
    }

    public String getString() {
        // First read the length of the string
        int l = getInt();
        byte[] b = new byte[l];

        try {
            // Read the chars of the string
            is.read(b);
        } catch (IOException ioe) {
            throw new MiniORBException("cannot getSring!!");
        }

        return new String(b);
    }

    public Object getObject() {
        ObjectRef or = getObjectRef();


        try {
            if (or.getOid() == 0)
                return null;
            // creación automática de Proxies en base a la clase del objeto
            String ProxyName = "Proxy" + or.getIid();
            Class<ObjectRef> obclass = ObjectRef.class;
            Constructor<?> c = Class.forName(ProxyName).getConstructor(obclass);
            Object o = c.newInstance(or);
            // System.out.println(o);
            return o;
        } catch (Exception E) {
            System.out.println("cannot getObject!!");
            System.out.println(E.getStackTrace());
            return null;
        }
    }

    public ObjectRef getObjectRef() {
        try {
            // The object has 4 fields:
            // 1) Address of the ORB
            // 2) Port of the ORB
            // 3) Object id
            // 4) Interface id
            String host = getString();
            int port = getInt();
            int obId = getInt();
            String iId = getString();

            // Create and return an object reference with those values
            ObjectRef oref = new ObjectRef(host, port, obId, iId);
            return oref;
        } catch (MiniORBException MIOE) {
            System.out.println("cannot getObjectRef!!");
            System.out.println(MIOE.getMessage());
            return null;
        }
    }

    public MiniORBException getException() {
        String message = getString();
        MiniORBException MIOE = new MiniORBException(message);
        return MIOE;
    }
}
