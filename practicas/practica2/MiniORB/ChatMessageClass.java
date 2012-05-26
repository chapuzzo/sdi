

public class ChatMessageClass implements ChatMessage{
    String message;

    public ChatMessageClass(String message){
        this.message = message;
    }

    public String getText(){
        return this.message;
    }

}
