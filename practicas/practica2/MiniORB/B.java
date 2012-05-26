

// It must be implemented by the corresponding proxy (ProxyB)
// and server (ClassB).

public interface B extends A {
    // Return the result of adding the integer values bound
    // bound to the given string (names)
    public int add (String s1, String s2);

    // Return the result of substracting the integer values bound
    // bound to the given string (names)
    public int sub (String s1, String s2);
}
