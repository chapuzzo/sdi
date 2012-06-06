import java.io.*;

public class ChatClient {

	String hostc, hostNS;
	int portc, portNS, oid = 0, iid = 0;
	MiniORB orb = null;
	NameService NS = null;
	String prompt = "Escribe tu mensaje -> ";
	String message = "";
	char leido = 0;

	public static void main(String args[]) throws Exception {
		ChatClient c = new ChatClient();

		if (!c.parseArgs(args)) {
			System.err
					.println("uso:\n\t $ java ChatClient <host> <port> <hostNS> <portNS>");
		} else
			c.pruebaChatClient();
	}

	public void pruebaChatClient() throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = new PrintStream(System.out);
		//
		orb = new MiniORB(hostc, portc, hostNS, portNS);
		orb.serve();

		NS = orb.getNameService();
		try {
			out.println("Escribe un nombre de usuario: ");
			String user = in.readLine();
			ChatUser CU = new ChatUserClass(user, this);
			ChatService CS = (ChatService) NS.resolve("cs");
			if (CS.getUser(CU.getName()) == null)
				CS.registerUser(CU.getName(), CU);
			ChatChannel CC = CS.getChannel("cc");
			CC.joinUser(CU);
			setTerminalToCBreak();
			while (!message.equals("fin")) {
				message = "";

				boolean fin = false;
				while (!fin) {
					leido = (char) in.read();
					switch (leido) {
					case 10:
						fin = true;
						CC.sendMessage(new ChatMessageClass(CU.getName() + "> "
								+ message));
						prompt();
						break;
					case 127:
						message = String.copyValueOf(message.toCharArray(), 0,
								message.length() - 1);
						prompt();
						printBuffer();
						break;
					default:
						message += leido;
						prompt();
						printBuffer();
						break;
					}
				}
			}
			CC.leaveUser(CU);
			restoreTerminal();
			System.exit(0);
		} catch (Exception E) {
			E.printStackTrace();
			restoreTerminal();
		} finally {
			restoreTerminal();
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

	static String ttyConfig;

	private static void setTerminalToCBreak() throws IOException,
			InterruptedException {

		ttyConfig = stty("-g");
		//System.out.println("la configuracion actual de tty es:\n" + ttyConfig);
		// set the console to be character-buffered instead of line-buffered
		stty("-icanon min 1");

		// disable character echoing
		stty("-echo");
		//System.out.println("y ahora:\n" + stty("-g"));
	}

	private static void restoreTerminal() throws IOException,
			InterruptedException {
		stty(ttyConfig);
		stty("echo");
	}

	private static String stty(final String args) throws IOException,
			InterruptedException {
		String cmd = "stty " + args + " < /dev/tty";

		return exec(new String[] { "sh", "-c", cmd });
	}

	private static String exec(String[] cmdarray) {
		byte[] buffer = new byte[120];
		try {
			Runtime.getRuntime().exec(cmdarray).getInputStream().read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return new String(buffer);
	}

	public void prompt() {
		int i = 80;
		while (i-- > 0)
			System.out.print("\b \b");
		System.out.print(prompt);
	}

	public void printBuffer() {
		//if (leido != 0)
			System.out.print(message);
	}
}
