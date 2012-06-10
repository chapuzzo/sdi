// This interface must be implemented by all the skeletons

public interface Skeleton {
	public void upcall(ParseIn parseIn, ParseOut parseOut, Object obj) throws java.rmi.RemoteException;

	public String getIid();

	public Proxy createProxy(ObjectRef oref);
}
