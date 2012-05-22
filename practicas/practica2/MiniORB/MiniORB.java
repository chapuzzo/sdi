import java.util.*;
import java.net.*;
import java.io.*;

public class MiniORB implements Runnable {
    // Table of exported objects: pairs (oid, object)
        private Hashtable<Integer,Object> objects;
    // Table of interfaces (skeletons): pairs (iid, skeleton)
        private Hashtable<Integer,Skeleton> skeletons;
    // Object counter
    // (used as objectId of the next object to create)
    private int objCount;

    // Address of this ORB
    private String host;
    // Port of this ORB
    private int port;

    private String NShost;
    private int NSport;

    private static MiniORB orb;

    public MiniORB (String host, int port) {
        objects = new Hashtable<Integer,Object>();
        skeletons = new Hashtable<Integer,Skeleton>();
        objCount = 0;
        this.host = host;
        this.port = port;
        orb = this;
    }

    public MiniORB(String host, int port, String NShost, int NSport){
        this(host, port);
        this.NShost = NShost;
        this.NSport = NSport;
    }

    public String getHost () {
        return host;
    }
    public int getPort () {
        return port;
    }

    // Registers an object and its skeleton
    // synchronized guarantees mutual exclusion
    public synchronized Proxy addObject (Object obj, Skeleton sk) {
        objCount++;

        // Add the object to the table of objects
        objects.put(new Integer(objCount), obj);
        // Add the skeleton to the table of skeletons
        skeletons.put(new Integer(sk.getIid()), sk);

        // Create an object reference for the object
        ObjectRef oref = new ObjectRef(host, port, objCount, sk.getIid());
        // Create a proxy for the object
        Proxy proxy = sk.createProxy(oref);

        // Return the proxy for the object
        return proxy;
    }

    // Returns the object bound to the given object identifier
    public synchronized Object getObject (int oid) {
        return objects.get(new Integer(oid));
    }

    // Returns the interface (skeleton) bound to the given interface id.
    public synchronized Skeleton getInterface (int iid) {
            return skeletons.get(new Integer(iid));
    }

    public synchronized NameService getNameService(){
        ObjectRef oref = new ObjectRef(NShost, NSport, 1, 3);
        return new ProxyNameService(oref);
    }

    // Starts the server
    public void serve () {
        // Create a new thread and start it
        // See the run() method!
        new Thread(this).start();
    }

    public void run () {
        // Main socket to receive requests
        ServerSocket serverSocket = null;
        // Client socket
        Socket clientSocket;
        // A worker to handle requests from clients
        Worker worker;
        Thread thread;

        // Create the main socket
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("I can't start a ServerSocket in port number " + port);
            e.printStackTrace();
            return;
        }

        // Wait for requests from clients
        while (true) {
            try {
                // Accept a new request from a client
                clientSocket = serverSocket.accept();
                // Create a new Worker
                worker = new Worker(clientSocket);
                // Create a new thread with the worker
                thread = new Thread(worker);
                // Start the worker
                thread.start();
            } catch (IOException e) {
                System.out.println("I can't handle a request");
                e.printStackTrace();
            }
        }
    }

    // Handles a request from a client
    // The ParseIn object is used to read the arguments
    // The ParseOut object is used to write the result
    public void demultiplexer(ParseIn pin, ParseOut pou) {
        int oid;
        int iid;
        Object obj;
        Skeleton sk;

        // Read the identifier of the object to call
        oid = pin.getInt();
        // Read the identifier of its interface (skeleton)
        iid = pin.getInt();

        // Look for the object in the table of objects
        // The object must have been registered previously!
        obj = getObject(oid);
        // Look for the skeleton in the table of skeletons
        sk = getInterface(iid);

        System.out.println ("oid: " + oid + ", iid: " + iid + ", obj: " + obj + ", sk: " + sk);
        // The skeleton knows how to attend the request
        sk.upcall(pin, pou, obj);

        // Send something (e.g. a "0"),
        // so there is always some data sent as a result
        pou.putInt(0);
    }

    // Inner class
    class Worker implements Runnable {
        // Client socket
        private Socket clientSocket;

        Worker (Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run () {
            try {
                ParseIn parseIn = new ParseIn(clientSocket.getInputStream());
                System.out.println("pin creado");
                ParseOut parseOut = new ParseOut(clientSocket.getOutputStream());
                System.out.println("pou creado");

                // Attend this request
                demultiplexer(parseIn, parseOut);
            } catch (Exception e) {
                System.out.println("I cant' handle a request");
                e.printStackTrace();
            }
        }
    }

    public static MiniORB getOrb(){
        return orb;
    }
}
