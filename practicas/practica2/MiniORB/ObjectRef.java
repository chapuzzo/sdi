


import java.net.*;

public class ObjectRef {
    // Address of the ORB in which the referenced object is stored
    private String host;
    // Port of the ORB in which the referenced object is stored
    private int port;
    // Object id of the referenced object
    private int oid;
    // Interface id of the referenced object
    private String iid;

    //private String interfaceName;

    public ObjectRef (String host, int port, int oid, String iid) {
        this.host = host;
        this.port = port;
        this.oid = oid;
        this.iid = iid;
    }

    /*public ObjectRef (String host, int port, int oid, int iid, String interfaceName) {
        this(host,port,oid,iid);
        this.interfaceName = interfaceName;
    }*/


    // Creates a new invocation to the referenced object
    public Invocation newInvocation () {
        Socket clientSocket = null;
        Invocation invocation = null;

        try {
            // Create a client socket against the server socket
            // of the ORB referenced by the stored host and port
            clientSocket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println("I can't create a socket against " +
                host + ":" + port);
            /*e.printStackTrace();
            return null;*/
            throw new MiniORBException("no se puede conectar");
        }

        // Create a new invocation
        invocation = new ObjectInvocation(clientSocket);
        // Write the object id
        invocation.putInt(oid);
        // Write the interface id
        invocation.putString(iid);

        return invocation;
    }

    public String getHost () {
        return host;
    }

    public int getPort () {
        return port;
    }

    public int getOid () {
        return oid;
    }

    public String getIid () {
        return iid;
    }

    public String toString(){
        return "oref: [" + getHost() + ":" + getPort() + "(oid:" + getOid() +
                ", iid:" + getIid() +"]";
    }



    // An ObjectInvocation represents an invocation to a remote object
    class ObjectInvocation implements Invocation {
        ParseIn  parseIn;
        ParseOut parseOut;

        public ObjectInvocation (Socket s) {
            try {
                parseIn = new ParseIn(s.getInputStream());
                parseOut = new ParseOut(s.getOutputStream());
            } catch (Exception e) {
                System.out.println("I can't get the streams");
                e.printStackTrace();
            }
        }

        public void putInt (int i) {parseOut.putInt(i);}
        public void putLong (long i) {parseOut.putLong(i);}
        public void putBool (boolean b) {parseOut.putBool(b);}
        public void putString (String s) {parseOut.putString(s);}
        public void putObject (Object obj) {parseOut.putObject(obj);}

        // send is used to finish sending parameters
        public void send () {parseOut.putInt(0);}

        public int getInt () {return parseIn.getInt();}
        public long getLong () {return parseIn.getLong();}
        public boolean getBool () {return parseIn.getBool();}
        public String getString () {return parseIn.getString();}
        public Object getObject () {return parseIn.getObject();}

        // waitEnd is used to finish receiving parameters
        // It receives the "0" send by the multiplexer method (MiniORB)
        public void waitEnd () {parseIn.getInt();}
    }
}
