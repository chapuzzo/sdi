import java.io.*;
import java.util.Scanner;

public class ChatClient {

	String hostc, hostNS;
	int portc, portNS, oid = 0, iid = 0;
	MiniORB orb = null;
	NameService NS = null;
	public static final String prompt = "Escribe tu mensaje -> ";
	String buffer = "";

	ChatService CS = null;
	ChatChannel CC = null;
	ChatUser CU = null;

	public static void main(String args[]) throws Exception {
		ChatClient c = new ChatClient();

		if (!c.parseArgs(args)) {
			System.err
					.println("uso:\n\t $ java ChatClient <host> <port> <hostNS> <portNS>");
		} else
			c.pruebaChatClient();
	}

	public void pruebaChatClient() throws Exception {
		Scanner in = new Scanner(System.in);
		PrintStream out = new PrintStream(System.out);
		String user;
		orb = new MiniORB(hostc, portc, hostNS, portNS);
		orb.serve();

		NS = orb.getNameService();
		try {
			CS = (ChatService) NS.resolve("cs");
			boolean usuarioDuplicado;
			do {
				out.println("Escribe un nombre de usuario: ");
				user = in.nextLine();
				usuarioDuplicado = CS.getUser(user) != null;
				if (usuarioDuplicado) {
					out.println("Lo siento ese usuario ya existe en este servidor");
				}
			} while (usuarioDuplicado);

			CU = new ChatUserClass(user, this);
			CS.registerUser(CU.getName(), CU);

			setTerminalToCBreak();
			displayWelcome();
			while (true) {
				procesaEntrada();
			}
		} catch (Exception E) {
			E.printStackTrace();			
		} finally {
			restoreTerminal();
		}

	}

	private void procesaEntrada() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		char leido = (char) in.read();
		switch (leido) {
		case 10:
			procesaComandos();
			break;
		case 127:
			if (buffer.length() > 0) {
				buffer = buffer.substring(0, buffer.length() - 1);
			}
			break;
		default:
			buffer += leido;
			break;
		}
		prompt();
		printBuffer();
	}

	private void procesaComandos() {
		if (buffer.equals("/exit")) {
			exit();
		} else if (buffer.startsWith("/join")) {
			joinChannel();
		} else if (buffer.equals("/list")) {
			displayChannels();
		} else if (CC == null) {
			displayWelcome();
		} else if (buffer.equals("/users")) {
			displayUsers();
		} else if (buffer.equals("/leave")) {
			leaveChannel();
		} else {
			sendMessage();
		}
		buffer = "";
	}

	private void sendMessage() {
		CC.sendMessage(new ChatMessageClass(CU.getName() + "> " + buffer));
	}

	private void exit() {
		leaveChannel();
		restoreTerminal();
		System.exit(0);
	}

	private void joinChannel() {
		String channelName = buffer.split(" ")[1];
		leaveChannel();
		this.CC = CS.getChannel(channelName);
		this.CC.joinUser(CU);
	}

	private void leaveChannel() {
		if (CC != null)
			CC.leaveUser(CU);
		CC = null;
	}

	private void displayUsers() {
		String userList = String.format("Los Usuarios conectados a %s son:",
				CC.getName());
		for (String user : CC.getUserList()) {
			userList += "\n\t" + user;
		}
		CU.sendMessage(new ChatMessageClass(userList));
	}

	private void displayWelcome() {
		CU.sendMessage(new ChatMessageClass(
				"No estás en ningún canal,\n\t/list para ver los disponibles\n\t/join <nombreCanal> para unirte a uno"));
	}

	private void displayChannels() {
		String channelList = String
				.format("Los Canales disponibles en este servidor son:");
		for (String channel : CS.getChatChannelList()) {
			channelList += "\n\t" + channel;
		}
		CU.sendMessage(new ChatMessageClass(channelList));
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
		// System.out.println("la configuracion actual de tty es:\n" +
		// ttyConfig);
		// set the console to be character-buffered instead of line-buffered
		stty("-icanon min 1");

		// disable character echoing
		stty("-echo");
		// System.out.println("y ahora:\n" + stty("-g"));
	}

	private static void restoreTerminal() {
		try {
			/*stty(ttyConfig);
			stty("echo");*/
			System.out.println(
			exec(new String[]{"sh", "-c" , "/usr/bin/reset < /dev/tty"})
			);
		} catch (Exception E) {
			System.out.println("WTF!!");
		}
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
		System.out.print(buffer);
	}
}
