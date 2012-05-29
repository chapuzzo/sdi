
public class SkeletonChatChannel implements Skeleton {

    private String iid = "ChatChannel";

    public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) {
        ChatChannel CC = (ChatChannel)obj;
        int methodNumber = parseIn.getInt();
        switch(methodNumber){
        case Methods.JOINUSER: {
            ChatUser user;
            user = (ChatUser)parseIn.getObject();
            CC.joinUser(user);
            break;
        }
        case Methods.LEAVEUSER: {
            ChatUser user;
            user = (ChatUser)parseIn.getObject();
            CC.leaveUser(user);
            break;
        }
        case Methods.SENDMESSAGE: {
            ChatMessage message;
            message = (ChatMessage)parseIn.getObject();
            CC.sendMessage(message);
            break;
        }
        case Methods.GETNAME: {
            String name = CC.getName();
            parseOut.putString(name);
            break;
        }
        }

    }

    public String getIid() {
        return iid;
    }

    public Proxy createProxy(ObjectRef oref) {
        return new ProxyChatChannel(oref);
    }

}
