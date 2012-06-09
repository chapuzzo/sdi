public class ProxyChatMessage extends Proxy implements ChatMessage {

	public ProxyChatMessage(ObjectRef oref) {
		super(oref);
	}

	public String getText() {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETTEXT);
		invo.send();
		String text = invo.getString();
		invo.waitEnd();
		return text;
	}
}
