public interface ChatUser extends java.rmi.Remote {
	public String getName() throws java.rmi.RemoteException;

	public void sendMessage(ChatMessage m) throws java.rmi.RemoteException;
}
