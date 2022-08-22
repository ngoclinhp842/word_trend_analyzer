/* 
File: AscendingDouble.java
Author: Linh Phan (Michelle)
Date: 04/24/2022

The AscendingDouble class which implements a Comparator of type Double with the method compare. 
Please review the Comparator class documentation and its abstract method compare.

How to run: type "javac AscendingDouble.java" in the command line.
            type "java AscendingDouble" in the command line.
*/

import java.util.Comparator;

public class AscendingDouble implements Comparator<Double>{

    @Override
    public int compare(Double o1, Double o2) {
        if (o1 > o2) return 1;
        if (o1 < o2) return -1;
        return 0;
    }
    
}
