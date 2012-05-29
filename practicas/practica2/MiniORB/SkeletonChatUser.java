public class SkeletonChatUser implements Skeleton {

	private String iid = "ChatUser";

	public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) {
		ChatUser CU = (ChatUser) obj;
		int methodNumber = parseIn.getInt();
		switch (methodNumber) {
		case Methods.GETNAME: {
			String name = CU.getName();
			parseOut.putString(name);
			break;
		}
		case Methods.SENDMESSAGE: {
			ChatMessage CM = (ChatMessage) parseIn.getObject();
			CU.sendMessage(CM);
			break;
		}
		}
	}

	public String getIid() {
		return iid;
	}

	public Proxy createProxy(ObjectRef oref) {
		return new ProxyChatUser(oref);
	}

}
