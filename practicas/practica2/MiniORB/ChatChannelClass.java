import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatChannelClass implements ChatChannel {

	private String name;
	private Hashtable<String, ChatUser> userstable = new Hashtable<String, ChatUser>();

	public ChatChannelClass(String name) {
		this.name = name;
	}

	public void joinUser(ChatUser u) throws java.rmi.RemoteException {
		if (!userstable.containsKey(u.getName())) {
			userstable.put(u.getName(), u);
			broadcastJoining(u.getName());
		}
	}

	public void leaveUser(ChatUser u) throws RemoteException {
		if (userstable.containsKey(u.getName())) {
			userstable.remove(u.getName());
			broadcastLeaving(u.getName());
		}
	}

	public void sendMessage(ChatMessage m) throws RemoteException {
		Vector<String> fallidos = new Vector<String>();
		for (String uname : userstable.keySet()) {
			try {
				userstable.get(uname).sendMessage(m);
			} catch (Exception E) {
				fallidos.add(uname);
				System.out.println("no se lo he podido enviar a " + uname);
			}
		}
		for (String uname : fallidos) {
			try {
				leaveUser(userstable.get(uname));
			} catch (Exception E) {
				System.out.println("realmente " + uname
						+ " se ha ido , lo borro");
				userstable.remove(uname);
				broadcastLeaving(uname);
			}
		}
	}

	private void broadcastJoining(String uname) throws RemoteException {
		ChatMessage CM = new ChatMessageClass("-> user " + uname + " joins #"
				+ getName());
		ChatMessage pCM = (ChatMessage) UnicastRemoteObject.exportObject(CM, 0);
		sendMessage(pCM);
	}

	private void broadcastLeaving(String uname) throws RemoteException {
		ChatMessage CM = new ChatMessageClass("-> user " + uname + " leaves #"
				+ getName());
		ChatMessage pCM = (ChatMessage) UnicastRemoteObject.exportObject(CM, 0);
		sendMessage(pCM);
	}

	public String getName() {
		return this.name;
	}

	public String[] getUserList() throws RemoteException {
		Vector<String> res = new Vector<String>();
		for (ChatUser CU : userstable.values()) {
			res.add(CU.getName());
		}
		return res.toArray(new String[] {});

	}
}
