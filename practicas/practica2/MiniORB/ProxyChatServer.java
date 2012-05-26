
public class ProxyChatServer extends Proxy implements ChatServer{

	public ProxyChatServer(ObjectRef oref) {
		super(oref);
	}

	public void registerUser(String name, ChatUser u) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.REGISTERUSER);
		invo.putString(name);
		invo.putObject(u);
		invo.waitEnd();
		return;
	}

	public void registerChannel(String name, ChatChannel c) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.REGISTERCHANNEL);
		invo.putString(name);
		invo.putObject(c);
		invo.waitEnd();
		return;		
	}

	public ChatUser getUser(String name) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETUSER);
		invo.putString(name);
		ChatUser u = (ChatUser)invo.getObject();
		return u;
	}

	public ChatChannel getChannel(String name) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETCHANNEL);
		invo.putString(name);
		ChatChannel c = (ChatChannel)invo.getObject();
		return c;
	}

}
