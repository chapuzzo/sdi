public interface ChatChannel extends java.rmi.Remote{
	public void joinUser(ChatUser u) throws java.rmi.RemoteException;

	public void leaveUser(ChatUser u) throws java.rmi.RemoteException;

	public void sendMessage(ChatMessage m) throws java.rmi.RemoteException;

	public String getName() throws java.rmi.RemoteException;
	
	public String[] getUserList() throws java.rmi.RemoteException;
}
