public interface NameService {
	// Save an object, bound to the given name (string)
	public void bind(String s, Object o);

	// Load a saved object, by means of its name
	public Object resolve(String s);
}
