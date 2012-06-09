public interface ChatChannel {
	public void joinUser(ChatUser u);

	public void leaveUser(ChatUser u);

	public void sendMessage(ChatMessage m);

	public String getName();
	
	public String[] getUserList();
}
