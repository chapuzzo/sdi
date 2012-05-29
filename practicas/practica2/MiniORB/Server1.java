// This class is a "server" that creates a ClassA object and registers it
// in a MiniORB.

//import java.net.*;
//import java.io.*;

public class Server1 {
    public static void main (String [] args) {
        String host, NShost;
        int port, NSport;
        MiniORB orb = null;

        if (args.length != 4) {
            System.err.println("Invalid arguments");
            System.err.println();
            System.err.println("java Server1 <host> <port>  <NShost> <NSport>");
            return;
        }

        // Get the address (e.g IP address or host name)
        host = args[0];

        // Get the port number for the MiniORB
        try {
            port = (new Integer(args[1])).intValue();
        } catch (Exception e) {
            System.err.println("Invalid port number: " + args[1]);
            return;
        }

         // Get the address (e.g IP address or host name)
        NShost = args[2];

        // Get the port number for the MiniORB
        try {
            NSport = (new Integer(args[3])).intValue();
        } catch (Exception e) {
            System.err.println("Invalid port number: " + args[3]);
            return;
        }


        // Create and start the MiniORB
        orb = new MiniORB(host, port, NShost, NSport);
        orb.serve();

        NameService ns = orb.getNameService();

        // Create "remote" objects and their corresponding skeletons
        ClassA objA = new ClassA();
        //SkeletonA skA = new SkeletonA();
        //~ ClassB objB = new ClassB();
        //~ SkeletonB skB = new SkeletonB();
        // ...

        // Register the object (and its skeleton) in the ORB
        // TO-DO Which are its object id and interface id?
        //orb.addObject(objA, skA);
        //B b = (B) orb.addObject(objB, skB);


        ns.bind("ejemploA",objA);
        //~ ns.bind("ejemploB",objB );
        //A pA = (A)ns.resolve("ejemploA");
        //orb.addObject(pA,skA);


    }
}
