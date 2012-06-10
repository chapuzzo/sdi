import java.rmi.RemoteException;

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

        try {
			CC.joinUser(CU);
			CC.joinUser(CP);
        } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			CC.sendMessage(new ChatMessageClass(CU.getName() + "> " + "hola"));
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			CC.leaveUser(CU);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
