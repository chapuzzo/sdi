public class ProxyChatUser extends Proxy implements ChatUser {

	public ProxyChatUser(ObjectRef oref) {
		super(oref);
	}

	public String getName() {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETNAME);
		invo.send();
		String name = invo.getString();
		invo.waitEnd();
		return name;
	}

	public void sendMessage(ChatMessage m) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.SENDMESSAGE);
		invo.putObject(m);
		invo.send();
		invo.waitEnd();
		return;
	}

}
