
public class ServerChat {
	
	public static void main(String[] args) {
		String host, NShost;
	    int port, NSport;
		MiniORB orb = null;
		
		if (args.length != 4) {
            System.err.println("Invalid arguments");
            System.err.println();
            System.err.println("java ServerChat <host> <port>  <NShost> <NSport>");
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
        
        NameService NS = orb.getNameService();
        
        ChatServer CS = new ChatServerClass();
        //SkeletonChatServer SkCS = new SkeletonChatServer();
        
        ChatChannel CC = new ChatChannelClass("prueba");
        //SkeletonChatChannel SkCC = new SkeletonChatChannel();
        
        //ChatUser CU = new ChatUserClass("luis");
        //SkeletonChatUser SkCU = new SkeletonChatUser();
        
        ChatMessage CM = new ChatMessageClass("x-x-x");
        //SkeletonChatMessage SkCM = new SkeletonChatMessage();
        
        //orb.addObject(CS, SkCS);
        //orb.addObject(CC, SkCC);
       // orb.addObject(CU, SkCU);
       // orb.addObject(CM, SkCM);
        
        NS.bind("cs1", CS);
        NS.bind("cc1", CC);
        NS.bind("cm1", CM);
        
        
        //CS.registerChannel("cc1", CC);
        
        //CC.joinUser(CU);
        //CC.sendMessage(CM);
        
        //CS.registerChannel(CC.getName(), CC);

	}

}
