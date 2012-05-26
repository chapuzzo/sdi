
public class ProxyChatUser extends Proxy implements ChatUser{

	public ProxyChatUser(ObjectRef oref) {
		super(oref);
	}

	public String getName() {
		return null;
	}

	public void sendMessage(ChatMessage m) {
		
	}

}
