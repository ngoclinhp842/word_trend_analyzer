/* 
File: AscendingKeyValuePair.java
Author: Linh Phan (Michelle)
Date: 04/24/2022

The AscendingKeyValuePair class which implements a Comparator of type KeyValuePair with the method compare. 
Please review the Comparator class documentation and its abstract method compare.

How to run: type "javac AscendingKeyValuePair.java" in the command line.
            type "java AscendingKeyValuePair" in the command line.
*/

import java.util.Comparator;

public class AscendingKeyValuePair implements Comparator<KeyValuePair<Double, String>>{
    @Override
    public int compare(KeyValuePair<Double, String> o1, KeyValuePair<Double, String> o2) {
        if (o1.getKey() > o2.getKey()) return 1;
        else if(o1.getKey().equals(o2.getKey())) return 0;
        else return -1;
    }
    
}
