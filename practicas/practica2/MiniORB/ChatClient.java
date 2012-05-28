import java.io.*;

public class ChatClient {

	String hostc, hostNS;
	int portc, portNS, oid = 0, iid = 0;
	MiniORB orb = null;
	NameService NS = null;

	public static void main(String args[]) {
		ChatClient c = new ChatClient();

		if (!c.parseArgs(args)) {
			System.err
					.println("uso:\n\t $ java ChatClient <host> <port> <hostNS> <portNS>");
		} else
			c.pruebaChatClient();
	}

	public void pruebaChatClient() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = new PrintStream(System.out);

		orb = new MiniORB(hostc, portc, hostNS, portNS);
		orb.serve();

		NS = orb.getNameService();
		try {
			out.println("Escribe un nombre de usuario: ");
			String user = in.readLine();
			ChatUser CU = new ChatUserClass(user);
			ChatServer CS = (ChatServer) NS.resolve("cs");
			if (CS.getUser(CU.getName()) == null)
				CS.registerUser(CU.getName(), CU);
			ChatChannel CC = CS.getChannel("cc");
			CC.joinUser(CU);
			String message = "";
			while (!message.equals("fin")) {
				message = in.readLine();
				CC.sendMessage(new ChatMessageClass(CU.getName() + "> "
						+ message));
			}
			CC.leaveUser(CU);
			System.exit(0);
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



/*private static void setTerminalToCBreak() throws IOException, InterruptedException {

        ttyConfig = stty("-g");

        // set the console to be character-buffered instead of line-buffered
        stty("-icanon min 1");

        // disable character echoing
        stty("-echo");
    }

   
    private static String stty(final String args)
                    throws IOException, InterruptedException {
        String cmd = "stty " + args + " < /dev/tty";

        return exec(new String[] {
                    "sh",
                    "-c",
                    cmd
                });
    }
*/
