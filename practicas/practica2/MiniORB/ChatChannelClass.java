

import java.util.ArrayList;


public class ChatChannelClass implements ChatChannel{

    private String name;
    private ArrayList<ChatUser> users = new ArrayList<ChatUser>();

    public ChatChannelClass(String name){
        this.name = name;
    }

    public void joinUser(ChatUser u){
        users.add(u);
        for (ChatUser cu: users){
            cu.sendMessage(new ChatMessageClass("-> user " + u.getName() + " joins #" + getName()));
        }
    }

    public void leaveUser(ChatUser u){
        users.remove(u);
        for (ChatUser cu: users){
            cu.sendMessage(new ChatMessageClass("-> user " + u.getName() + " leaves #" + getName()));
        }
    }

    public void sendMessage(ChatMessage m){
        for (ChatUser cu: users){
            cu.sendMessage(m);
        }
    }

    public String getName(){
        return this.name;
    }
}
