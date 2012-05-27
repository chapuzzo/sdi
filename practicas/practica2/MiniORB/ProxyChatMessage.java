public class ProxyChatMessage extends Proxy implements ChatMessage{

	public ProxyChatMessage(ObjectRef oref) {
		super(oref);
	}

	public String getText() {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETTEXT);
		String text = invo.getString();
		invo.waitEnd();
		return text;
	}

	public void setText(String text) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.SETTEXT);
		invo.putString(text);
		invo.waitEnd();
		return;
		
	}
	
	

}
