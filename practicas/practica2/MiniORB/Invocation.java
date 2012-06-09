// The putXYZ methods are used to send parameters (marshalling).
// The send method must be used to end the sending.

// The getXYZ methods are used to receive results (unmarshalling).
// The waitEnd method must be used to end the reception.

public interface Invocation {
	public void putInt(int i);

	public void putLong(long i);

	public void putBool(boolean b);

	public void putString(String s);

	public void putObject(Object obj);
	
	public void putList(Object[] list);
	
	public void putStringList(String[] list);
	
	public void send();

	public int getInt();

	public long getLong();

	public boolean getBool();

	public String getString();

	public Object getObject();
	
	public Object[] getList();
	
	public String[] getStringList();

	public MiniORBException getException();

	public void waitEnd();

}
