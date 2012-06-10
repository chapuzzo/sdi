import java.rmi.server.*;
import java.rmi.registry.*;

public class ServidorRMI {

	public static void main(String[] args) {
		String ns_host = args[0];
		int ns_port = new Integer(args[1]);

		try {
			ChatService CS = (ChatService) UnicastRemoteObject.exportObject(
					new ChatServiceClass(), 0);
			Registry reg = LocateRegistry.getRegistry(ns_host, ns_port);
			reg.bind("cs", CS);
			//ChatService pCS = (ChatService) reg.lookup("cs");

			ChatChannel CC = new ChatChannelClass("cc");
			ChatChannel pCC = (ChatChannel) UnicastRemoteObject.exportObject(CC,0);
			
			CS.registerChannel("cc", pCC);

			ChatUser espia = new ChatUserClass("espía");
			ChatUser pEspia = (ChatUser) UnicastRemoteObject.exportObject(espia,0);
			CS.registerUser(espia.getName(), pEspia);
			CC.joinUser(pEspia);

			/*while (true) {
				Thread.sleep(30000);
				CC.sendMessage(new ChatMessageClass("test"));
			}*/

		} catch (Exception e) {
			System.out.println("err " + e);
		}

		System.out.println("Server running...");
	}
}
