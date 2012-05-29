
public class SkeletonChatMessage implements Skeleton {

    private String iid = "ChatMessage";

    public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) {
        ChatMessage CM = (ChatMessage)obj;
        int methodNumber = parseIn.getInt();
        switch(methodNumber){
        case Methods.GETTEXT: {
            String message = CM.getText();
            parseOut.putString(message);
            break;
        }
        }
    }

    public String getIid() {
        return iid;
    }

    public Proxy createProxy(ObjectRef oref) {
        return new ProxyChatMessage(oref);
    }

}
