
public class ProxyChatUser extends Proxy implements ChatUser{

    public ProxyChatUser(ObjectRef oref) {
        super(oref);
    }

	public String getName() {
        Invocation invo = oref.newInvocation();
        invo.putInt(Methods.GETNAME);
        String name = invo.getString();
        int retVal = invo.getInt();
        if (retVal != 0){
        	MiniORBException e = invo.getException();
        	throw e;
        }
        return name;
    }

    public void sendMessage(ChatMessage m) {
        Invocation invo = oref.newInvocation();
        invo.putInt(Methods.SENDMESSAGE);
        invo.putObject(m);
        invo.waitEnd();
        int retVal = invo.getInt();
        if (retVal != 0){
        	MiniORBException e = invo.getException();
        	throw e;
        }
        return;
    }

}
