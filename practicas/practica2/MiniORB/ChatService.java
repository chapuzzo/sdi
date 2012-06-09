
public interface ChatService {
	public void registerUser(String name, ChatUser u);

	public void registerChannel(String name, ChatChannel c);

	public ChatUser getUser(String name);

	public ChatChannel getChannel(String name);
	
	public String[] getChatChannelList();
	
	public void deleteUser(String name); /* añadido por mi */

}
