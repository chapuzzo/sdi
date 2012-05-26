

// This interface must be implemented by all the skeletons

public interface Skeleton {     
	public void upcall (ParseIn parseIn, ParseOut parseOut, Object obj);
	public int getIid ();
	public Proxy createProxy (ObjectRef oref);
}
