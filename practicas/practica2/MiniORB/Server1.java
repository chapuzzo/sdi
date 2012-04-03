// This class is a "server" that creates a ClassA object and registers it
// in a MiniORB.

import java.net.*;
import java.io.*;

public class Server1 {                                
	public static void main (String [] args) {
		String host;
		int port;
		MiniORB orb = null;

		if (args.length != 2) {
			System.err.println("Invalid arguments");
			System.err.println();
			System.err.println("java Server1 <host> <port>");
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

		// Create and start the MiniORB
		orb = new MiniORB(host, port);
		orb.serve();

		// Create "remote" objects and their corresponding skeletons
		ClassA objA = new ClassA();
		SkeletonA skA = new SkeletonA();
		// ClassB b = new ClassB();
		// ...

		// Register the object (and its skeleton) in the ORB
		// TO-DO Which are its object id and interface id?
		orb.addObject(objA, skA);
		// orb.addObject(objB, skB);
	}
}
