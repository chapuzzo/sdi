public class ChatServer {

	String hostc, hostNS;
	int portc, portNS, oid = 0, iid = 0;
	MiniORB orb = null;
	NameService NS = null;

	public static void main(String args[]) {
		ChatServer sc = new ChatServer();

		if (!sc.parseArgs(args)) {
			System.err
					.println("uso:\n\t $ java ServerChat <host> <port> <hostNS> <portNS>");
		} else
			sc.pruebaChatServer();
	}

	public void pruebaChatServer() {
		orb = new MiniORB(hostc, portc, hostNS, portNS);
		orb.serve();

		NS = orb.getNameService();
		try {
			ChatService CS = new ChatServiceClass();
			ChatChannel CC = new ChatChannelClass("cc");
			CS.registerChannel("cc", CC);
			NS.bind("cs", CS);
			/*ChatUser espia = new ChatUserClass("espía");
			CS.registerUser(espia.getName(), espia);
			CC.joinUser(espia);*/
			while (true) {
				Thread.sleep(15000);
				CC.sendMessage(new ChatMessageClass("test"));
			}
		} catch (Exception E) {
			E.printStackTrace();
		}
	}

	public boolean parseArgs(String args[]) {

		if (args.length < 4) {
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

		return true;

	}

}
