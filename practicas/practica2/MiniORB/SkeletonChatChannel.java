
public class SkeletonChatChannel implements Skeleton {
	
	private int iid = 5;

	public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) {
		ChatChannel CC = (ChatChannel)obj;
		int methodNumber = parseIn.getInt();
		switch(methodNumber){
		case Methods.JOINUSER: {
			System.out.println("joiningUser (sk)");
			ChatUser user;
			user = (ChatUser)parseIn.getObject();
			System.out.println("joiningUser (sk) 2");
			CC.joinUser(user);
			System.out.println("jointUser (sk)");
			break;
		}
		case Methods.LEAVEUSER: {
			ChatUser user;
			user = (ChatUser)parseIn.getObject();
			CC.leaveUser(user);
			break;
		}
		case Methods.SENDMESSAGE: {
			System.out.println("sendingMessage (sk)");
			ChatMessage message;
			message = (ChatMessage)parseIn.getObject();
			CC.sendMessage(message);
			System.out.println("sentMessage (sk)");
			break;
		}
		case Methods.GETNAME: {
			String name = CC.getName();
			parseOut.putString(name);
			break;
		}
		}

	}

	public int getIid() {
		return iid;
	}

	public Proxy createProxy(ObjectRef oref) {
		return new ProxyChatChannel(oref);
	}

}
