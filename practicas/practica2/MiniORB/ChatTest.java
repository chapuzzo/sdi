/* -*- encoding: utf-8 -*- */
public class ChatTest {

    public static void main(String[] args) {
        ChatTest test = new ChatTest();
        test.go();
    }

    public void go() {

        ChatServiceClass CS = new ChatServiceClass();
        ChatChannelClass CC = new ChatChannelClass("unico"); // único, ya que de
                                                                // momento sólo
                                                                // hay un canal
        ChatUserClass CU = new ChatUserClass("luis");
        ChatUserClass CP = new ChatUserClass("pepe");

        CS.registerChannel(CC.getName(), CC);
        CS.registerUser(CU.getName(), CU);
        CS.registerUser(CP.getName(), CP);

        CC.joinUser(CU);
        CC.joinUser(CP);
        CC.sendMessage(new ChatMessageClass(CU.getName() + "> " + "hola"));
        CC.leaveUser(CU);

    }

}
