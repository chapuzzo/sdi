public class SkeletonChatServer implements Skeleton {

    private String iid = "ChatServer";

    public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) {
        ChatServer CS = (ChatServer)obj;

        int methodNumber = parseIn.getInt();
        switch (methodNumber) {
        case Methods.REGISTERUSER: {
            String name;
            ChatUser u;
            name = parseIn.getString();
            u = (ChatUser)parseIn.getObject();
            CS.registerUser(name, u);
            break;
        }
        case Methods.REGISTERCHANNEL: {
            String name;
            ChatChannel c;
            name = parseIn.getString();
            c = (ChatChannel)parseIn.getObject();
            CS.registerChannel(name, c);
            break;
        }
        case Methods.GETUSER: {
            String name;
            Object o;
            name = parseIn.getString();
            o = CS.getUser(name);
            parseOut.putObject(o);
            break;
        }
        case Methods.GETCHANNEL: {
            String name;
            Object o;
            name = parseIn.getString();
            o = CS.getChannel(name);
            parseOut.putObject(o);
            break;
        }
        default:
            break;
        }
    }

    public String getIid() {
        return iid;
    }

    public Proxy createProxy(ObjectRef oref) {
        return new ProxyChatServer(oref);
    }

}
