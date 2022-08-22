/*
File: Hashmap.java
Author: Linh Phan (Michelle)
Date: 04/18/2022

A Hashmap class that implements MapSet. Using chaning (a data structure like 
a linked list at each entry in the hash table so multiple entries can be stored at each position.)
Hash function is the built-in hash function in java for the String type 
(lookup Java String hashCode) to generate an index given a String. 

The Hashmap keySet, values, and entrySet methods do not need to return the data in any particular order. 
The keySet and values methods should return it in the same ordering.

how to run: type the following in the terminal:
            javac Hashmap.java
            java Hashmap
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Hashmap<K,V> implements MapSet<K,V> {
    // initial size of the hash table
    private int CAPACITY;
    // number of item in the hashtable
    private int size;
    // The Comparator needs to be able to compare two things of type K
    private Comparator<K> comp;
    // Hash table
    private LinkedList<KeyValuePair<K,V>>[] hashTable;
    // number of bucket that is loaded
    private int loadedPos;

    // Hashmap constructor that starts with default size hash table
    public Hashmap(Comparator<K> incomp) {
        CAPACITY = 31;
        comp = incomp;
        hashTable = new LinkedList[CAPACITY];
        size = 0;
        loadedPos = 0;
    }

    // Hashmap constructor that starts with the suggecsted capacity hash table
    public Hashmap(Comparator<K> incomp, int capacity ) {
        CAPACITY = capacity;
        comp = incomp;
        hashTable = new LinkedList[CAPACITY];
        loadedPos = 0;
        size = 0;
    }
    
    @Override
    // adds or updates a key-value pair. 
    // If the key is not already in the map, it should add a new node with the given key-value pair. 
    // The the key is already in the map, it should replace the existing value with the given value.
    // The function implements chaining: a data structure like a linked list at each entry 
    // in the hash table so multiple entries can be stored at each position.
    public V put(K new_key, V new_value) {
        // check if the table need to be resize
        if (1.0 * size/CAPACITY > 0.5){
            expand();
        }

        // get the index key to insert the value
        int index = Math.abs(new_key.hashCode()) % CAPACITY;

        // a pair to store new key and value
        KeyValuePair<K,V> pair = new KeyValuePair<K,V>(new_key, new_value);

        // if the position is empty, put a new list inside
        if (hashTable[index] == null){
            // create a linked list for the each position
            LinkedList<KeyValuePair<K,V>> list = new LinkedList<>();
            hashTable[index] = list;
            list.add(pair);
            size++;
            loadedPos++;
        }
        else {
            // loop through each item within the LinkedList in position index in hashTable
            for (KeyValuePair<K,V> item : hashTable[index]){
                // if we found a duplicate key, replace the value with new_value
                if (comp.compare(item.getKey(), new_key) == 0){
                    V oldValue = item.getValue();
                    item.setValue(new_value);
                    return oldValue;
                }
            }

            // add new pair to the LinkedList
            hashTable[index].add(pair);
            size++;
            
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    // returns the value associated with the given key or null if the key does not exist.
    public V get(K key) {
        // get the index key to insert the value
        int index = Math.abs(key.hashCode()) % CAPACITY;

        // if the index position is not null
        // loop through each item in the LinkedList to find key
        if (hashTable[index] != null){
            for (KeyValuePair<K,V> pair : hashTable[index]){
                if (comp.compare(pair.getKey(), key) == 0){
                    return pair.getValue();
                }
            }   
        }
        return null;
    }

    @Override
    // returns an ArrayList that contains all of the keys in the map. No order is specified for the keys.
    public ArrayList<K> keySet() {
        ArrayList<K> keySet = new ArrayList<>();

        // loop through each bucket in the hash table
        for (int i = 0; i < CAPACITY; i++){
            // loop through each item in each LinkedList in each bucket
            if (hashTable[i] != null){
                for (KeyValuePair<K,V> pair : hashTable[i]){
                    // add all keys to keySet
                    keySet.add(pair.getKey());
                }
            }
        }
        return keySet;
    }

    @Override
    // returns an ArrayList that contains all of the values in the map. 
    // Their order should match the order returned by keySet.
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();

        // loop through each bucket in the hash table
        for (int i = 0; i < CAPACITY; i++){
            // loop through each item in each LinkedList in each bucket
            if (hashTable[i] != null){
                for (KeyValuePair<K,V> pair : hashTable[i]){
                    // add all values to values list
                    values.add(pair.getValue());
                }
            }
        }
        return values;
    }

    @Override
    // returns an ArrayList of KeyValuePair objects.
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K,V>> list = new ArrayList<>();
        ArrayList<K> keys = keySet();

        // loop through the keys list
        // add each key to the KeyValuePair list
        for (K cur_key: keys){
            list.add(new KeyValuePair<K,V>(cur_key, null));
        }

        // loop through the KeyValuePair list
        // find the value with each key
        // set the value of each item in the list with the found value
        for (KeyValuePair<K,V> pair: list){
            V value = get(pair.getKey());
            pair.setValue(value);
        }

        keys.clear();
        return list;
    }

    @Override
    // Returns the number of key-value pairs in the map.
    public int size() {
        return size;
    }

    @Override
    // clears the map and sets it to the empty map.
    public void clear() {
        hashTable = new LinkedList[CAPACITY];
        size = 0;
        loadedPos = 0;
    }
    
    // repand the hashtable
    // When the average number of entries per location goes above some specified value, 
    // then increase the size of the table.
    public int expand(){
        // entrySet() to get a list of every entrySet
        ArrayList<KeyValuePair<K,V>> entrySet = entrySet();

        // find the new CAPACITT
        CAPACITY = nextPrime(CAPACITY*2);

        // create a new table
        hashTable = new LinkedList[CAPACITY];

        // reset size
        size = 0;
        loadedPos = 0;

        // for every pair with the entrySet, hash it into the table
        for (KeyValuePair<K,V> pair: entrySet){
            put(pair.getKey(), pair.getValue());
        }

        entrySet.clear();

        return CAPACITY;
    }

    // source: https://stackoverflow.com/questions/47407251/optimal-way-to-find-next-prime-number-java
    // find the neatest prime number of input
    public int nextPrime(int input){
        int counter;
        input++;
        while(true){
          int l = (int) Math.sqrt(input);
          counter = 0;
          for(int i = 2; i <= l; i ++){
            if(input % i == 0)  counter++;
          }
          if(counter == 0)
            return input;
          else{
            input++;
            continue;
          }
        }
    }


    // return a string to print the data in the hash table
    public String toString(){
        String str = "";
        // loop through every bucket in the hash table
        for (int i = 0; i < CAPACITY; i++){
            // if the bucket is not empty
            if (hashTable[i] != null){
                // store the index and the pair 
                str += "index " + String.valueOf(i) + ": ";
                for (KeyValuePair<K,V> pair: hashTable[i]){
                    str += pair.getKey() + ": " + pair.getValue() + "; ";
                }
                str += "\n";
            }
        }
        return str;
    }

    public int collisions(){
        return size - loadedPos;
    }

    public int getCapacity(){
        return CAPACITY;
    }

    public static void main( String[] argv ){
        Hashmap<String, Integer> hashmap = new Hashmap<>(new AscendingString(), 5);

        hashmap.put( "twenty", 20 );
        hashmap.put( "ten", 10 );
        hashmap.put( "eleven", 11 );
        hashmap.put( "five", 5 );
        hashmap.put( "six", 6 );
        hashmap.put( "one", 1 );

        // System.out.println( "eleven: " + hashmap.get( "eleven" ) );
        // System.out.println( "twenty: " + hashmap.get( "twenty" ) );
        // System.out.println( "six: " + hashmap.get( "six" ) );

        // // TEST: containsKey() method
        // System.out.println("Should be false: " + hashmap.containsKey("a"));
        // System.out.println("Should be true: " + hashmap.containsKey("six"));
        // System.out.println("Should be true: " + hashmap.containsKey("eleven"));
        // System.out.println("Should be true: " + hashmap.containsKey("one"));

        // // TEST: keySet() method
        // System.out.println("Key Set: " + hashmap.keySet());

        // // TEST: values() method
        // System.out.println("Values Set: " + hashmap.values());

        // // TEST: entrySet() method
        // System.out.println("Entry Set: " + hashmap.entrySet());

        // // TEST: size() method
        // System.out.println("Size: " + hashmap.size());

        // // TEST: adding duplicate key
        // hashmap.put("twenty", 2);
        // System.out.println("Entry Set: " + hashmap.entrySet());

        // TEST: clear() method
        // hashmap.clear();
        // System.out.println("Below should all be null.");
        // System.out.println( "eleven: " + hashmap.get( "eleven" ) );
        // System.out.println( "twenty: " + hashmap.get( "twenty" ) );
        // System.out.println( "six: " + hashmap.get( "six" ) );

        System.out.println("hash map:");
        System.out.println(hashmap);
        System.out.println("expand the hash map: " + hashmap.expand());
        System.out.println("hash map:");
        System.out.println(hashmap);

        // System.out.println("New Capacity: " + hashmap.expand());
        // System.out.println("New Capacity: " + hashmap.expand());
        System.out.println(hashmap.CAPACITY);
    }
}
