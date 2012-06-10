import java.rmi.Remote;

public interface ChatService extends Remote{
	public void registerUser(String name, ChatUser u) throws java.rmi.RemoteException;

	public void registerChannel(String name, ChatChannel c) throws java.rmi.RemoteException;

	public ChatUser getUser(String name) throws java.rmi.RemoteException;

	public ChatChannel getChannel(String name) throws java.rmi.RemoteException;
	
	public String[] getChatChannelList() throws java.rmi.RemoteException;
	
	public void deleteUser(String name) throws java.rmi.RemoteException; /* añadido por mi */

}
