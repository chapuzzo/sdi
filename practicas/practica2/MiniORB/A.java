

// It must be implemented by the corresponding proxy (ProxyA)
// and server (ClassA).

public interface A {
	// Save an integer, bound to the given name (string)
	public void save (String s, int i);
	// Load a save integer, by means of its name
	public int load (String s);
}
