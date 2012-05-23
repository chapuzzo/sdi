// This class is used to do the unmarshalling of data.
// It reads data from an InputStream (of some socket).

import java.io.*;
//import java.net.*;

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
       System.out.println ("getObject en ParseIn");
       ObjectRef or = getObjectRef();
       System.out.println ("gotObjectRef: " + or);
       //getInt();
       //Object p = new Proxy(or);
       switch (or.iid) {
           case 1:  // "A"
                return new ProxyA(or);
            case 2: // B
                return new ProxyB(or);
            case 3: // NameService
                return new ProxyNameService(or);
            default:
                System.out.println ("mal!!");
       }
       
       
       System.out.println("getting proxy: " + p + " on object: " + or);
       return p;
    }

    public ObjectRef getObjectRef () {
        // The object has 4 fields:
        // 1) Address of the ORB
        // 2) Port of the ORB
        // 3) Object id
        // 4) Interface id
        String host = getString();
        int port = getInt();
        int obId = getInt();
        int iId = getInt();

        // Create and return an object reference with those values
        ObjectRef oref = new ObjectRef(host,port,obId,iId);
        return oref;
    }
}
