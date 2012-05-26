

public class ChatUserClass implements ChatUser{
    String name;

    public ChatUserClass(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void sendMessage(ChatMessage m){
        System.out.println(m.getText());
    }
}
