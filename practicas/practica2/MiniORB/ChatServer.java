

public interface ChatServer{
    public void registerUser(String name, ChatUser u);
    public void registerChannel(String name, ChatChannel c);
    public ChatUser getUser(String name);
    public ChatChannel getChannel(String name);
    //~ listChannel array en el marshall/unmarshall
    //~ que pasa si no encuentra un objeto -> soporte a objetos nulos!

}
