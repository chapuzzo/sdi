import java.util.*;

public class ChatServiceClass implements ChatService {

	Hashtable<String, ChatChannel> channels;
	Hashtable<String, ChatUser> users;

	public ChatServiceClass() {
		channels = new Hashtable<String, ChatChannel>();
		users = new Hashtable<String, ChatUser>();
	}

	public void registerUser(String name, ChatUser u) {
		users.put(name, u);
	}
	
	public void deleteUser(String name){
		users.remove(name);
	}

	public void registerChannel(String name, ChatChannel c) {
		channels.put(name, c);
	}

	public ChatUser getUser(String name) {
		return users.get(name);
	}

	public ChatChannel getChannel(String name) {
		return channels.get(name);
	}

	public String[] getChatChannelList() {
		Vector<String> res = new Vector<String>();
		for (ChatChannel CC: channels.values()){
			res.add(CC.getName());
		}
		return (String[])res.toArray(new String[]{});
	}

}
