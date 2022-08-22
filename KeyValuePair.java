/* File: KeyValuePair.java
Author: Linh Phan (Michelle)
Date: 04/16/2022

A KeyValuePair class that is to be used by the binary search tree.
two generic types: one for the key and one for the value.

How to run: type "javac KeyValuePair.java" in the command line.
            type "java KeyValuePair" in the command line.
 */

public class KeyValuePair<Key,Value>{
    // The class should a field of type Key to contain a key and field of type Value to contain a value.
    private Key key;
    private Value value;

    // the constructor initializing the key and value fields.
    public KeyValuePair( Key k, Value v ) {
        key = k;
        value = v;
    }
    
    // returns the key.
    public Key getKey() {
        return key;
    }
    
    // returns the value.
    public Value getValue() {
        return value;
    }
    
    // sets the value.
    public void setValue( Value v ) {
        value = v;
    }
    
    // returns a String containing both the key and value.
    public String toString() {
        String str = "";
        str += key + ":" + value;
        return str;
    }

    // Write a main test function for the class and ensure each of the methods works properly.
    public static void main(String[] args){
        KeyValuePair<Integer, String> test = new KeyValuePair<Integer, String>(1, "Michelle");
        System.out.println("Key: " + test.getKey());
        System.out.println("Value: " + test.getValue());
        System.out.println(test);
    }
}