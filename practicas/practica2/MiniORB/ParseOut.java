
import java.io.*;

public class ParseOut {
    // OutputStream to write data to
    private OutputStream os;

    public ParseOut (OutputStream os) {
        this.os = os;
    }

    public void putInt (int v) {
        //int r = 0;
        byte [] b = new byte[4];

        for (int i = 0; i < 4; i++) {
            b[3-i] = (byte) (v % 256);
            v = v / 256;
        }
        try {
            os.write(b);
        } catch (IOException ioe) {

        }
    }

    public void putLong (long v) {
        putInt((int) v);
    }

    public void putBool (boolean v) {
        try {
            if (v) {
                // Not equal to 0 is true
                os.write(1);
            } else {
                // 0 is false
                os.write(0);
            }
        } catch (IOException ioe) {

        }
    }

    public void putString (String v) {
        byte [] b = v.getBytes();
        // First save its length
        putInt(b.length);

        try {
            os.write(b);
        } catch (IOException ioe) {
        }
    }

   public void putObject (Object obj) {
       //System.out.print ("pO: ");
       Proxy px = null;
       //* //System.out.println("pO->"+obj.getClass().getInterfaces()[0].getName());
        if (obj == null ) {
            px = new Proxy(new ObjectRef("", 0, 0, ""));
        }
        else
        if (obj instanceof Proxy) {
            //* //System.out.println("YA es un proxy");
            px = (Proxy) obj;
            //System.out.println(px);
            //~ putObjectRef(px.oref);
        }
        else {
            try{
                Class<?>[] v =  obj.getClass().getInterfaces();
                for (int x = 0; x<v.length; x++){
                    Class<?> i = v[x];
                    String SkeletonName= "Skeleton" + i.getName();
                    Class<?> cls = Class.forName(SkeletonName);
                    //~ System.out.println(SkeletonName);
                    MiniORB orb = MiniORB.getOrb();
                    Skeleton sk = (Skeleton) cls.newInstance();
                    px = orb.addObject (obj, sk);
                    //~ System.out.println("Putting Object: " + px.oref);
                    //~ putObjectRef(px.oref);
                    //* //System.out.println("NO era un proxy");
                    //System.out.println(px);
                }
            }
            catch(Exception E){
                System.out.println("Ha sucedido un error en putObject!! " + E);
            }
        }
        putObjectRef(px.oref);
        //System.out.println(px);
        //~ System.out.println ("Object put");
    }

    public void putObjectRef(ObjectRef oref) {
        try {
            // Write the address of the ORB that stores the object
            putString(oref.getHost());
            //System.out.println("Put Host");
            // Write the port of the ORB that stores the object
            putInt(oref.getPort());
            //System.out.println("Put Port");
            // Write the object id of the object
            putInt(oref.getOid());
            //System.out.println("Put Oid");
            // Write the interface id of the object
            putString(oref.getIid());
            //System.out.println("Put Iid");
        } catch (Exception ioe) {
            System.out.println("ieeep!! ALTO AHI!!");
            ioe.printStackTrace();
        }
    }
}
