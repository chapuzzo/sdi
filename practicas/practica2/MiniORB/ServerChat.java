public class ServerChat {

	String hostc, hostNS;
	int portc, portNS, oid = 0, iid = 0;
	MiniORB orb = null;
	NameService NS = null;

	public static void main(String args[]) {
		ServerChat sc = new ServerChat();

		if (!sc.parseArgs(args)) {
			System.err
					.println("uso:\n\t $ java ServerChat <host> <port> <hostNS> <portNS>");
		} else
			sc.pruebaServerChat();
	}

	public void pruebaServerChat() {
		orb = new MiniORB(hostc, portc, hostNS, portNS);
		orb.serve();

		NS = orb.getNameService();
		try {
			ChatServer CS = new ChatServerClass();
			ChatChannel CC = new ChatChannelClass("cc");
			CS.registerChannel("cc", CC);
			NS.bind("cs", CS);
		} catch (MiniORBException E) {
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
