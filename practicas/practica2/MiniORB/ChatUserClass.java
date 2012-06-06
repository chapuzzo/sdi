public class ChatUserClass implements ChatUser {
	String name;
	//String prompt = "Escribe tu mensaje -> ";
	ChatClient cc;

	public ChatUserClass(String name) {
		this.name = name;
		//System.out.print(prompt);
	}
	
	public ChatUserClass(String name, ChatClient cc) {
		this(name);
		this.cc = cc;
		cc.prompt();
	}

	public String getName() {
		return this.name;
	}

	public void sendMessage(ChatMessage m) {
		
		int i = 80; while (i-- > 0) System.out.print("\b \b");
		
		System.out.println(/*"\r" +*/"'"+ m.getText()+"'");
		cc.prompt();
		cc.printBuffer();
	}
}
