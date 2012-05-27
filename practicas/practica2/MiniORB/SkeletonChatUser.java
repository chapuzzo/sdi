
public class SkeletonChatUser implements Skeleton {
	
	private int iid = 6;

	public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) {
		ChatUser CU = (ChatUser)obj;
		int methodNumber = parseIn.getInt();
		switch(methodNumber){
		case Methods.GETNAME: {
			String name = CU.getName();
			parseOut.putString(name);
			break;
		}
		case Methods.SENDMESSAGE: {
			ChatMessage CM = (ChatMessage)parseIn.getObject();
			CU.sendMessage(CM);
			break;
		}
		}
	}

	public int getIid() {
		return iid;
	}

	public Proxy createProxy(ObjectRef oref) {
		return new ProxyChatUser(oref);
	}

}
