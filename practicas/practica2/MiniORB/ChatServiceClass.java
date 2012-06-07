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

	public void registerChannel(String name, ChatChannel c) {
		channels.put(name, c);
	}

	public ChatUser getUser(String name) {
		return users.get(name);
	}

	public ChatChannel getChannel(String name) {
		return channels.get(name);
	}

}
