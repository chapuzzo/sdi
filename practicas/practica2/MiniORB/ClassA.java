// Implementation of the A interface

import java.util.*;

public class ClassA implements A {
   // This table saves integers, bound to strings (names)
   private Hashtable<String,Integer> table = new Hashtable<String, Integer>();
   
   public void save (String s, int i) {
      table.put(s, new Integer(i));
   }
   
   public int load (String s) {
      Integer integer = table.get(s);
      int i = integer.intValue();
      return i;
   }
}
