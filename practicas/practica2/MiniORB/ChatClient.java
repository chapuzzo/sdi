
public class ChatClient {

	
	 String hostc, hostNS;
	    int portc, portNS, oid = 0, iid = 0;
	    MiniORB orb = null;
	    NameService NS = null;

	    public static void main (String args[]) {
	        ChatClient c = new ChatClient();

	        if (! c.parseArgs(args)){
	            System.err.println(
	                "uso:\n\t $ java ChatClient <hostc> <portc> <hostNS> <portNS> //[oid] [iid]"
	                );
	        }
	        else
	            c.pruebaChat();
	    }


	    public void pruebaChat(){
	        orb = new MiniORB(hostc,portc,hostNS,portNS);
	        orb.serve();

	        NS = orb.getNameService();
	        try{
	        	//ChatServer CS = (ChatServer)NS.resolve("cs1");
	        	ChatChannel CC = (ChatChannel)NS.resolve("cc1");
	        	ChatUser CU = new ChatUserClass("luis");
	        	ChatMessage CM = (ChatMessage)NS.resolve("cm1");
	        	
	        	NS.bind("u1", CU);
	        	//NS.bind("cm1", CM);
	        	
	        	ChatUser pCU = (ChatUser)NS.resolve("u1");
	        	//ChatMessage pCM = (ChatMessage)NS.resolve("cm1");
	        	CC.joinUser(pCU);
	        	CC.sendMessage(CM);
	        }
	        catch(Exception E){
	            E.printStackTrace();
	        }


	    }

	    public boolean parseArgs(String args[]){

	        if (args.length < 4){
	            System.err.println("Invalid argument number: " + args.length);
	            return false;
	        }

	        hostc = args[0];

	        try {
	            portc = (new Integer(args[1])).intValue();
	        } catch (Exception e) {
	            System.err.println("Invalid port number: " + args[1]);
	            return false;
	        }

	        hostNS = args[2];

	        try {
	            portNS = (new Integer(args[3])).intValue();
	        } catch (Exception e) {
	            System.err.println("Invalid port number: " + args[3]);
	            return false;
	        }

	        if (args.length > 4)
	        try {
	            oid = (new Integer(args[4])).intValue();
	        } catch (Exception e) {
	            System.err.println("Invalid oid number: " + args[4]);
	            return false;
	        }

	        if (args.length > 5)
	        try {
	            iid = (new Integer(args[5])).intValue();
	        } catch (Exception e) {
	            System.err.println("Invalid iid number: " + args[5]);
	            return false;
	        }

	        return true;

	    }
}
