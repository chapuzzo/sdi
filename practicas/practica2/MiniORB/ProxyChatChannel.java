
public class ProxyChatChannel extends Proxy implements ChatChannel{

	public ProxyChatChannel(ObjectRef oref) {
		super(oref);
	}

	public void joinUser(ChatUser u) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.JOINUSER);
		invo.putObject(u);
		invo.waitEnd();
		return;
	}

	public void leaveUser(ChatUser u) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.LEAVEUSER);
		invo.putObject(u);
		invo.waitEnd();
		return;
	}

	public void sendMessage(ChatMessage m) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.SENDMESSAGE);
		invo.putObject(m);
		invo.waitEnd();
		return;
	}

	public String getName() {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETNAME);
		String name = invo.getString();
		invo.waitEnd();	
		return name;
	}

}
