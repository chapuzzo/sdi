import java.util.ArrayList;

public class ChatChannelClass implements ChatChannel{

    private String name;
    private ArrayList<ChatUser> users = new ArrayList<ChatUser>();

    public ChatChannelClass(String name){
        this.name = name;
    }

    public void joinUser(ChatUser u){
    	System.out.println("CCC: joining user");
        users.add(u);
        //u.sendMessage(new ChatMessageClass("hola"));
        System.out.println("CCC: joint user");
        /*for (ChatUser cu: users){
            cu.sendMessage(new ChatMessageClass("-> user " + u.getName() + " joins #" + getName()));
        }*/
        System.out.println("CCC: bc users");
    }

    public void leaveUser(ChatUser u){
        users.remove(u);
        /*for (ChatUser cu: users){
            cu.sendMessage(new ChatMessageClass("-> user " + u.getName() + " leaves #" + getName()));
        }*/
    }

    public void sendMessage(ChatMessage m){
    	System.out.println("sending message(CC)");
    	for (int i = 0; i < users.size(); i++) {
			users.get(i).sendMessage(m);
		}
        /*for (ChatUser cu: users){
            cu.sendMessage(m);
        }*/
    }

    public String getName(){
        return this.name;
    }
}
