/* 
File: BSTMap.java
Author: Linh Phan (Michelle)
Date: 04/16/2022

BSTMap creates node-based binary search tree. Each node's data is a KeyValuePair.

How to run: type "javac BSTMap.java" in the command line.
            type "java BSTMap" in the command line.

*/
import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {
	// BSTMap instance variables
    private TNode root;
    private int size;
    private Comparator<K> comp;

	// constructor: takes in a Comparator object
	public BSTMap( Comparator<K> comp ) {
		// initialize fields heres
        root = null;
        this.comp = comp;
        size = 0;
	}

	// adds or updates a key-value pair
	// returns the old value or null if no old value existed
	public V put( K key, V value ) {
        // check for and handle the special case

        // If there is already a pair with new_key in the map, then 
        // update the pair's value to new_value.
        if (root != null){
            // call the root node's put method
            V result = root.put(key, value, comp);
            if (result == null) {
                size++;
            }
            return result;
        }
        // If there is not already a pair with new_key, then
        // add pair with new_key and new_value.
        root = new TNode(key, value);
        size++;
        return null;
    }

    // gets the value at the specified key or null
    public V get( K key ) {
        // check for and handle the special case
        // call the root node's get method
        if (root != null){
            return root.get(key, comp);
        }
        // stub code
        return null;
    }


    // Returns true if the map contains a key-value pair with the given key
    public boolean containsKey(K key) {
        if (root == null){
            return false;
        }
        else if (root.get(key, comp) != null){
            return true;
        }
        return false;
    }

    // Returns an ArrayList of all the keys in the map 
    // in Pre-order (node -> left -> right)
    public ArrayList<K> keySet() {
        // list to store all keys
        ArrayList<K> list = new ArrayList<>();
        return ArrayListKey(root, list);
    }

    // triverse the tree in Pre-order (node -> left -> right)
    // add all the key in the list and return the list
    public ArrayList<K> ArrayListKey(TNode cur, ArrayList<K> list){
        if (cur != null){
            list.add(cur.data.getKey());
            ArrayListKey(cur.left, list);
            ArrayListKey(cur.right, list);
        }
        return list;
    }

    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values() {
        // list to store all keys
        ArrayList<V> values = new ArrayList<>();
        return ArrayListValue(root, values);
    }

    // triverse the tree in Pre-order (node -> left -> right)
    // add all the value in the list and return the list
    public ArrayList<V> ArrayListValue(TNode cur, ArrayList<V> list){
        if (cur != null){
            list.add(cur.data.getValue());
            ArrayListValue(cur.left,list);
            ArrayListValue(cur.right, list);
        }
        return list;
    }

    // return an ArrayList of pairs.
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
        return list;
    }

    // Returns the number of key-value pairs in the map.
    public int size() {
        return size;
    }

    // removes all mappings from this MapSet
    public void clear() {
        root = null;
    }

    // return map data to print in pre-order
    public String toString(){
        return toStringHelper(root, 0, "root");
    }

    // return the tree height 
    public int height(){
        if (root == null){
            return -1;
        }
        return heightHelper(root);
    }

    public int heightHelper(TNode cur){
        // if root == null, the tree does not exist
        if (cur == null){
            return -1;
        }
        
        // find the height of the left and right subtree
        int leftDepth = heightHelper(cur.left);
        int rightDepth = heightHelper(cur.right);

        // compare the heights of two subtree,
        // return the larger height
        if (leftDepth > rightDepth){
            return leftDepth + 1;
        }
        else {
            return rightDepth + 1;
        }
    }

    // return the map data
    // parameters: the current TNode, the current depth of the tree, the direction of the Node
    public String toStringHelper(TNode cur, int depth, String direction){
        String output = "";
        // if the current node is empty, return an empty String
        if (cur == null){
            return "";
        }
        else {
            // Base case: plus the direction, key, and value of the current Node
            output += direction + "\t" + "  ".repeat(depth) + cur.data.getKey() + ": " 
                        + cur.data.getValue() + "\n";
            // recursively add the left Node direction, key and value
            output += toStringHelper(cur.left, depth + 1, "left");
            // recursively add the right Node direction, key and value
            output += toStringHelper(cur.right, depth + 1, "right");
        }
        return output;
    }

    public int collisions(){
        return 0;
    } 

    // You can make this a nested class
    private class TNode {
        // need a KeyValuePair to hold the data at this node
        private KeyValuePair<K,V> data;
        // need fields for the left and right children
        private TNode left;
        private TNode right;

        // constructor, given a key and a value
        public TNode( K k, V v ) {
            // initialize all of the TNode fields
            data = new KeyValuePair<K,V>(k, v);
        }

        // Takes in a key, a value, and a comparator and inserts
        // the TNode in the subtree rooted at this node 

        // Returns the value associated with the key in the subtree
        // rooted at this node or null if the key does not already exist
        public V put( K key, V value, Comparator<K> comp ) {
            // implement the binary search tree put

            // if the key is less than the current key
            if (comp.compare(key, data.getKey()) < 0){
                // update the left of the tree
                if (left == null){
                    left = new TNode(key, value);
                    return null;
                }
                else {
                    return this.left.put(key, value, comp);
                }
            }
            // if the key is more than the current key
            else if (comp.compare(key, data.getKey()) > 0){
                // update the right of the tree
                if (right == null){
                    right = new TNode(key, value);
                    return null;
                }
                else {
                    return this.right.put(key, value, comp);
                }
            } 
            // if the key is the same as the current key
            else {
                V oldValue = data.getValue();
                data.setValue(value);
                return oldValue;
            }
        }

        // Takes in a key and a comparator
        // Returns the value associated with the key or null
        public V get( K key, Comparator<K> comp ) {
            // if the key is less than the current key
            if (comp.compare(key, data.getKey()) < 0){
                if (left == null){
                    return null;
                }
                else {
                    return this.left.get(key, comp);
                }
            }
            // if the key is more than the current key
            else if (comp.compare(key, data.getKey()) > 0){
                if (right == null){
                    return null;
                }
                else {
                    return this.right.get(key, comp);
                }
            }
            // if the key is the same as the current key
            else {
                return data.getValue();
            }
        }
            
    }
    // end of TNode class

    // test function
    public static void main( String[] argv ) {
        // create a BSTMap

        // TEST: put() and get() method
        // BSTMap<Integer, Integer> bst = new BSTMap<Integer, Integer>( new AscendingString() );
        class IntegerAscending implements Comparator<Integer>{
            public int compare(Integer o1, Integer o2){
                return o1-o2;
            }
        }
        BSTMap<Integer, Integer> bst = new BSTMap<Integer, Integer>( new IntegerAscending() );

        // bst.put( "twenty", 20 );
        // bst.put( "ten", 10 );
        // bst.put( "eleven", 11 );
        // bst.put( "five", 5 );
        // bst.put( "six", 6 );
        // bst.put( "one", 1 );

        bst.put( 20, 20 );
        bst.put( 10, 10 );
        bst.put( 11, 11 );
        bst.put( 5, 5 );
        bst.put( 6, 6 );
        bst.put( 1, 1 );

        // System.out.println( "eleven: " + bst.get( "eleven" ) );
        // System.out.println( "twenty: " + bst.get( "twenty" ) );
        // System.out.println( "six: " + bst.get( "six" ) );

        // // TEST: containsKey() method
        // System.out.println("Should be false: " + bst.containsKey("a"));
        // System.out.println("Should be true: " + bst.containsKey("six"));

        // // TEST: keySet() method
        // System.out.println("Key Set: " + bst.keySet());

        // // TEST: values() method
        // System.out.println("Values Set: " + bst.values());

        // // TEST: entrySet() method
        // System.out.println("Entry Set: " + bst.entrySet());

        // // TEST: size() method
        // System.out.println("Size: " + bst.size());

        // TEST: clear() method
        // bst.clear();
        // System.out.println("Below should all be null.");
        // System.out.println( "eleven: " + bst.get( "eleven" ) );
        // System.out.println( "twenty: " + bst.get( "twenty" ) );
        // System.out.println( "six: " + bst.get( "six" ) );
        System.out.println(bst);
        System.out.println("tree height: " + bst.height());
    }

}