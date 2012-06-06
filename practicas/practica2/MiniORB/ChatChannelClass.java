import java.util.*;

public class ChatChannelClass implements ChatChannel {

	private String name;
	// private ArrayList<ChatUser> users = new ArrayList<ChatUser>();
	private Hashtable<String, ChatUser> userstable = new Hashtable<String, ChatUser>();

	public ChatChannelClass(String name) {
		this.name = name;
	}

	public void joinUser(ChatUser u) {
		if (!userstable.containsKey(u.getName())) {
			userstable.put(u.getName(), u);
			for (ChatUser cu : userstable.values()) {
				cu.sendMessage(new ChatMessageClass("-> user " + u.getName()
						+ " joins #" + getName()));
				System.out.println(cu);
			}
		}

	}

	public void leaveUser(ChatUser u) {
		if (userstable.containsKey(u.getName())) {
			userstable.remove(u.getName());
			broadcastLeaving(u.getName());
		}
	}

	public void sendMessage(ChatMessage m) {
		Vector<String> fallidos = new Vector<String>();		
		for (String uname : userstable.keySet()) {
			try {
				userstable.get(uname).sendMessage(m);
			} catch (Exception E) {
				fallidos.add(uname);
			}
		}
		for (String uname : fallidos) {
			try {
				leaveUser(userstable.get(uname));
			} catch (Exception E) {
				System.out.println("realmente " + uname + " se ha ido , lo borro");
				userstable.remove(uname);
				broadcastLeaving(uname);
			}
		}
	}

	private void broadcastLeaving(String uname) {
		sendMessage(new ChatMessageClass("-> user " + uname
				+ " leaves #" + getName()));
	}

	public String getName() {
		return this.name;
	}
}
