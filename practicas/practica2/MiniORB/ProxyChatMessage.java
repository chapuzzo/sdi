
public class ProxyChatMessage extends Proxy implements ChatMessage{

	public ProxyChatMessage(ObjectRef oref) {
		super(oref);
	}

	public String getText() {
		ChatMessage CM;
		Invocation invo = oref.newInvocation();
		invo.putInt(Methods.GETTEXT);
		CM = (ChatMessage)invo.getObject();
		invo.waitEnd();
		return CM.getText();
	}

}
