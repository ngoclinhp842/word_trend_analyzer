/* 
File: AscendingString.java
Author: Linh Phan (Michelle)
Date: 04/16/2022

The AscendingString class which implements a Comparator of type String with the method compare. 
Please review the Comparator class documentation and its abstract method compare.

Strings already have a compareTo method which you can use inside AscendingString's compare method.

How to run: type "javac AscendingString.java" in the command line.
            type "java AscendingString" in the command line.
*/

import java.util.Comparator;

/**implements a Comparator of type String with the method compare. 
 * 
 */
public class AscendingString implements Comparator<String>{
    @Override
    // takes in two arguments of type T 
    // return 0 if o1 is equal to o1 
    // negative value if o1 is lexicographically less than o2
    // positive value if o1 is lexicographically greater than o2
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
    
}
