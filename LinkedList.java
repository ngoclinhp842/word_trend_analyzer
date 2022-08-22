/**
 * File: LinkedList.java
 * Author: Linh Phan (Michelle)
 * Date: 03/16/2022
 * 
 * LinkedList class is a public class that has a generic type specifier. 
 * The type specifier indicates what type of class is stored in the linked list.
 * If no specifier is provided in the declaration, the defualt type is Object.
 * 
 * How to run: enter "javac LinkedList.java"
 *          then enter "java LinkedList"
 * 
 */

import java.util.Iterator;    // defines the Iterator interface
import java.util.ArrayList;   
import java.util.Collections; // contains a shuffle function

public class LinkedList<T> implements Iterable<T>{
    private int size;
	private Node<T> head;

    // constructor that initializes the fields so it is an empty list.
    public LinkedList() {
        size = 0;
        head = null;
    }
    
    // empties the list (resets the fields so it is an empty list).
    public void clear() {
        size = 0;
		head = null;
    }
    
    // returns the size of the list.
    public int size() {
        return size;
    }
   
    // inserts the item at the beginning of the list.
    public void addFirst(T item) {
        // create a new Node with value of item
        Node<T> newNode = new Node<T>(item);
        // set the next of newNode to head 
        newNode.setNext(this.head);
        // replace the head with this new Node
		this.head = newNode;
        // increment the size
		size++;
    }
    
    // appends the item at the end of the list.
    public void addLast(T item) {
        if (size() == 0){
            addFirst(item);
        }
        else {
            Node<T> temp;
            temp = head;
            // find the Node whose next is null (at the end of the list)
            while (temp.next != null) {
                temp = temp.next;
            }

            // create a new Node with item value
            Node<T> newNode = new Node<T>(item);
            temp.setNext(newNode);
            size++;
        }
    }
    
    // inserts the item at the specified poistion in the list.
    public void add(int index, T item) {
        // if the list is empty, addFirst
        if (index == 0){
            addFirst(item);
        }
        else if (index == size){
            addLast(item);
        }
        // if the list is not empty
        else { 
            // create a new Node with item value
            Node<T> newNode = new Node<T>(item);
            Node<T> lastNode = null;
            Node<T> temp = head;

            // find the Node at index - 1 (the previous Node)
            for (int i = 0; i <= index; i++){
                if (i == index - 1){
                    lastNode = temp;
                }
                temp = temp.next;
            }
            // set newNode point to the current Node
            newNode.setNext(lastNode.next);
            // set previous Node point to the newNode
            lastNode.setNext(newNode);
            size++;
        }
    }
    
    // removes the item at the specified position in the list.
    public T remove (int index) {
        // a temp variable
        Node<T> temp = head;

        // find the item at index - 1 (the previous Node)
        for (int i = 0; i < index-1; i++){
            temp = temp.next;
        }

        // if remove the first item
        if (index == 0){
            T head_data = head.data;
            // set head point to 
            head = temp.next;
            size--;
            return head_data;
        }
        else {
            T data = temp.next.data;
            temp.setNext(temp.next.next);
            size--;
            return data;
        }   
    }

    // returns an ArrayList of the list contents in order.
    public ArrayList<T> toArrayList() {
        ArrayList<T> data = new ArrayList<T>();
        Iterator<T> it = iterator();
        while (it.hasNext()){
            data.add(it.next());
        }
        return data;
    }
    
    // returns an ArrayList of the list contents in shuffled order.
    public ArrayList<T> toShuffledList() {
        ArrayList<T> data = toArrayList();
        Collections.shuffle(data);
        return data;
    }
    

    // return a string of Node value
    public String toString(){
        Node<T> current = head;
 		String s = "";
 		while(current != null){
 			s += current.data + ", ";
 			current = current.next;
 		}
 		return s;
    }
    
    private class LLIterator implements Iterator<T>{
        private Node<T> nextNode;

        // the constructor for the LLIterator given the head of a list.
        public LLIterator(Node<T> head){
			nextNode = head;
		}

        /*
        returns the next item in the list, which is the item contained in the current node. 
        The method also needs to move the traversal along to the next node in the list.
        */
		public T next() {
			if (hasNext()){
				T data = nextNode.getThing();
				nextNode = nextNode.getNext();
				return data;
			} else {
				return null;
			}
		}

        // returns true if there are still values to traverse
        // (if the current node reference is not null).
		public boolean hasNext() {
			if (nextNode != null) {
				return true;
			} else {
				return false;
			}
		}

		/* does nothing. Implementing this function is optional for an Iterator.*/
		public void remove() {

		}
    }

    // Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator( this.head );
    }

    private class Node<T> {
        private T data;
	    private Node<T> next;

        // a constructor that initializes next to null and the container field to item.
        public Node(T item) {
            next = null;
            data = item;
        }

        // returns the value of the container field.
        public T getThing() {
            return this.data;
        }

        // sets next to the given node.
        public void setNext(Node<T> n) {
            next = n;
        }
    
        // returns the next field.
        public Node<T> getNext() {
            return next;
        }
    }

    public static void main(String[] args){
        /*
        TEST LAB TASK 4: methods in LinkedList class
        */
        LinkedList<Integer> intLinked = new LinkedList<Integer>();

        // add ten numbers to the list, in order
		for(int i=0;i<5;i++) {
            intLinked.addFirst(i);
        }
        System.out.println("Size: " + intLinked.size() + " This should be 5.");
        System.out.println(intLinked);

        intLinked.clear();
        System.out.println("Size after clear: " + intLinked.size());

        intLinked.addLast(19);
        System.out.println("Add 19 at the end: " + intLinked);

        intLinked.addLast(34);
        System.out.println("Add 34 at the end: " + intLinked);

        // add ten numbers to the list, in order
		for(int i=0;i<5;i++) {
            intLinked.addFirst(i);
        }
        System.out.println("Add [0,5] to the head: " + intLinked);
        
        intLinked.add(1, 19);
        System.out.println("Add 19 at index 1: " + intLinked);

        intLinked.remove(3);
        System.out.println("Remove item at index 3: " + intLinked);
    }
    
}
