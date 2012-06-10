//import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.Scanner;

public class ClienteRMI implements Client {

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
		ClienteRMI c = new ClienteRMI();

		if (!c.parseArgs(args)) {
			System.err.println("uso:\n\t $ java ClienteRMI <hostNS> <portNS>");
		} else
			c.pruebaChatClient();
	}

	public void pruebaChatClient() throws Exception {
		Scanner in = new Scanner(System.in);
		PrintStream out = new PrintStream(System.out);
		String user;

		try {
			Registry reg = LocateRegistry.getRegistry(hostNS, portNS);
			CS = (ChatService) reg.lookup("cs");
			boolean usuarioDuplicado;
			do {
				out.println("Escribe un nombre de usuario: ");
				user = in.nextLine();
				usuarioDuplicado = CS.getUser(user) != null;
				if (usuarioDuplicado) {
					out.println("Lo siento ese usuario ya existe en este servidor");
				}
			} while (usuarioDuplicado);

			CU = (ChatUser) UnicastRemoteObject.exportObject(new ChatUserClass(
					user, this), 0);
			CS.registerUser(user, CU);

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

	private void procesaComandos() throws java.rmi.RemoteException {
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

	private void sendMessage() throws java.rmi.RemoteException {
		CC.sendMessage(
				(ChatMessage)UnicastRemoteObject.exportObject(
						new ChatMessageClass(CU.getName() + "> " + buffer),0)
						);
	}

	private void exit() throws java.rmi.RemoteException {
		leaveChannel();
		CS.deleteUser(CU.getName());
		restoreTerminal();
		System.exit(0);
	}

	private void joinChannel() throws java.rmi.RemoteException {
		String channelName = buffer.split(" ")[1];
		leaveChannel();
		CC = CS.getChannel(channelName);
		if (CC != null) {
			//System.out.println("\n[1]CC=" + CC);
			//CC = (ChatChannel) UnicastRemoteObject.exportObject(CC, 0);
			//System.out.println("[2]CC=" + CC);
			CC.joinUser(CU);
		} else {
			CU.sendMessage((ChatMessage) UnicastRemoteObject.exportObject(
					new ChatMessageClass("Ese canal no existe, elige otro"), 0));
			displayChannels();
		}
	}

	private void leaveChannel() throws java.rmi.RemoteException {
		if (CC != null)
			CC.leaveUser(CU);
		CC = null;
	}

	private void displayUsers() throws java.rmi.RemoteException {
		String userList = String.format("Los Usuarios conectados a %s son:",
				CC.getName());
		for (String user : CC.getUserList()) {
			userList += "\n\t" + user;
		}
		ChatMessage pCM = (ChatMessage) UnicastRemoteObject.exportObject(
				new ChatMessageClass(userList), 0);
		CU.sendMessage(pCM);
	}

	private void displayWelcome() throws java.rmi.RemoteException {
		ChatMessage pCM = (ChatMessage) UnicastRemoteObject
				.exportObject(
						new ChatMessageClass(
								"No estás en ningún canal,\n\t/list para ver los disponibles\n\t/join <nombreCanal> para unirte a uno"),
						0);
		CU.sendMessage(pCM);
	}

	private void displayChannels() throws java.rmi.RemoteException {
		String channelList = String
				.format("Los Canales disponibles en este servidor son:");
		for (String channel : CS.getChatChannelList()) {
			channelList += "\n\t" + channel;
		}
		ChatMessage pCM = (ChatMessage) UnicastRemoteObject.exportObject(
				new ChatMessageClass(channelList), 0);
		CU.sendMessage(pCM);
	}

	public boolean parseArgs(String args[]) {

		if (args.length < 2) {
			System.err.println("Invalid number: " + args.length);
			return false;
		}

		hostNS = args[0];

		try {
			portNS = (new Integer(args[1])).intValue();
		} catch (Exception e) {
			System.err.println("Invalid port number: " + args[1]);
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
			/*
			 * stty(ttyConfig); stty("echo");
			 */
			System.out.println(exec(new String[] { "sh", "-c",
					"/usr/bin/reset < /dev/tty" }));
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
		clearLine();
		System.out.print(prompt);
	}

	public void printBuffer() {
		System.out.print(buffer);
	}

	public void printMessage(String text) {
		clearLine();
		System.out.println(text);
	}

	private void clearLine() {
		int i = 80;
		while (i-- > 0)
			System.out.print("\b \b");
	}
}
