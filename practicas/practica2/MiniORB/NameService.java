
public interface NameService {
    // Save an integer, bound to the given name (string)
    public void bind (String s, Object o);
    // Load a save integer, by means of its name
    public Object resolve (String s);
}
