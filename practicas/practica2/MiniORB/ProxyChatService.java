public class ProxyChatService extends Proxy implements ChatService {

	public ProxyChatService(ObjectRef oref) {
		super(oref);
	}

	public void registerUser(String name, ChatUser u) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.REGISTERUSER);
		invo.putString(name);
		invo.putObject(u);
		invo.send();
		invo.waitEnd();
		return;
	}

	public void registerChannel(String name, ChatChannel c) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.REGISTERCHANNEL);
		invo.putString(name);
		invo.putObject(c);
		invo.send();
		invo.waitEnd();
		return;
	}

	public ChatUser getUser(String name) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETUSER);
		invo.putString(name);
		invo.send();
		ChatUser u = (ChatUser) invo.getObject();
		invo.waitEnd();
		return u;
	}

	public ChatChannel getChannel(String name) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETCHANNEL);
		invo.putString(name);
		invo.send();
		ChatChannel c = (ChatChannel) invo.getObject();
		invo.waitEnd();
		return c;
	}

	public String[] getChatChannelList() {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETCHANNELLIST);
		invo.send();
		String[] res = invo.getStringList();
		invo.waitEnd();
		return res;

	}

	public void deleteUser(String name) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.DELETEUSER);
		invo.putString(name);
		invo.send();
		invo.waitEnd();
		return;

	}

}
