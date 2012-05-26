

import java.util.*;

public class ClassB implements B  {
   // This table saves integers, bound to strings (names)
   private Hashtable<String,Integer> table = new Hashtable<String, Integer>();

   public int add (String a, String b) {
       return load(a) + load(b);
   }

   public int sub (String a, String b) {
       return load(a) - load(b);
   }

   public void save (String s, int i) {
      table.put(s, new Integer(i));
   }

   public int load (String s) {
      Integer integer = table.get(s);
      int i = integer.intValue();
      return i;
   }

}
