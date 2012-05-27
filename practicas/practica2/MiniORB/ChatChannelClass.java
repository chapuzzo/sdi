import java.util.*;

public class ChatChannelClass implements ChatChannel{

    private String name;
    //private ArrayList<ChatUser> users = new ArrayList<ChatUser>();
    private Hashtable<String,ChatUser> userstable = new Hashtable<String, ChatUser>();

    public ChatChannelClass(String name){
        this.name = name;
    }

    public void joinUser(ChatUser u){
    	if (!userstable.containsKey(u.getName())){
    		userstable.put(u.getName(), u);
    		for (ChatUser cu: userstable.values()){
                cu.sendMessage(new ChatMessageClass("-> user " + u.getName() + " joins #" + getName()));
                System.out.println(cu);
            } 
    	}
    	       
    }

    public void leaveUser(ChatUser u){
    	if (userstable.containsKey(u.getName())){
    		userstable.remove(u.getName());
    		for (ChatUser cu: userstable.values()){
			        cu.sendMessage(new ChatMessageClass("-> user " + u.getName() + " leaves #" + getName()));
					System.out.println(cu);
		    }
    	}
    }

    public void sendMessage(ChatMessage m){
    	for (ChatUser cu: userstable.values()){
	        cu.sendMessage(m);
			System.out.println(cu);
    	}
    }    

    public String getName(){
        return this.name;
    }
}
