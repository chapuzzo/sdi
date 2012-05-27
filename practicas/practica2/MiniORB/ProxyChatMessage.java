public class ProxyChatMessage extends Proxy implements ChatMessage{

	public ProxyChatMessage(ObjectRef oref) {
		super(oref);
	}

	public String getText() {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETTEXT);
		ChatMessage CM = (ChatMessage)invo.getObject();
		invo.waitEnd();
		return CM.getText();
	}

	public void setText(String text) {
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.SETTEXT);
		invo.putString(text);
		invo.waitEnd();
		return;
		
	}
	
	

}
