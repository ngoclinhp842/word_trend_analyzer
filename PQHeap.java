import java.util.Comparator;

/**
 * File: PQMap.java
Author: Linh Phan (Michelle)
Date: 04/24/2022

 * The priority queue will use a heap and a comparator. 
 * Whenever a value is removed from the priority queue, it should have the highest priority, 
 * as determined by the Comparator. The Comparator should work just like the Comparator 
 * objects from the past two projects.
 * 
 * how to run: type the following in the terminal:
            javac PQMap.java
            java PQMap
 */

public class PQHeap<T>{
    // the heap data. the Heap needs only a single generic type
    private T[] heap;
    private Comparator<T> comp;
    // number of elements in the heap
    private int size;
    private int CAPACITY;

    // a constructor that initializes the empty heap, 
    // sets the size to zero, and stores the comparator.
    public PQHeap(Comparator<T> comparator){
        CAPACITY = 31;
        heap = (T[]) new Object[CAPACITY];
        size = 0; 
        comp = comparator;
    }

    // returns the number of elements in the heap.
    public int size(){
        return size;
    }

    // double the size of the heap
    private void resize(){
        // increase the capacity
        CAPACITY = CAPACITY * 2;

        // store all elements in the heap
        T[] newHeap = (T[]) new Object[CAPACITY];
        for (int i = 0; i < size; i++){
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    // return the index of the parent of the child at index i
    private int parent(int i){
        return (i - 1) / 2;
    }

    // return the index of the leftChild of parent at index i
    private int leftChild(int i ){
        return ((2 * i) + 1);
    }

    // return the index of the rightChild of parent at index i
    private int rightChild(int i ){
        return ((2 * i) + 2);
    }

    // adds the value to the heap and increments the size. 
    public void add(T obj){
        // if the heap is full, double the size
        if (size == CAPACITY){
            resize();
        }

        // add the new object to the last available bucket
        heap[size] = obj;

        // store the current index of the object
        int cur_index = size;
        size++;

        while (cur_index != 0){
            // compare the current object with its parent
            int parentID = parent(cur_index);
            // if the parent is smaller than the current object
            // shift their positions
            if (comp.compare(heap[parentID], heap[cur_index]) < 0){
                shift(parentID, cur_index);
            }
            // set the current index to its parent's index
            cur_index = parentID;
        }
        
    }

    // removes and returns the highest priority element from the heap.
    public T remove(){
        // if the heap is empty, return null
        if (size == 0){
            return null;
        }

        // store the first element to return
        T element = heap[0];
        
        // move the last element into index 0, current element is at index 0
        size--;
        heap[0] = heap[size];
        int cur_index = 0;

        // set the last element to null
        heap[size] = null;

        for (int i = 0; i < Math.log(size); i++){
            // find the index of the current element of left child 
            int leftChildID = leftChild(cur_index);
            int rightChildID = rightChild(cur_index);

            // shift the largest child into the current element position
            // if the current node have two child
            if (heap[leftChildID] != null && heap[rightChildID] != null){
                // if the right child is smaller than the left, shift the left up
                if (comp.compare(heap[rightChildID], heap[leftChildID]) <= 0){
                    // if the left child is bigger than the current element, shift the left child up
                    if (comp.compare(heap[cur_index], heap[leftChildID]) <= 0){
                        shift(cur_index, leftChildID);
                        // update the current index
                        cur_index = leftChildID;
                    }
                    else {
                        break;
                    }
                }
                else{
                    // if the right child is bigger than the left child
                    if (comp.compare(heap[cur_index], heap[rightChildID]) <= 0){
                        // if the right child is bigger than the current element, shift the right child up
                        shift(cur_index, rightChildID);
                        // update the current index
                        cur_index = rightChildID;
                    }
                    else {
                        break;
                    }
                }
            }
            // if the current node only have left child
            if (heap[leftChildID] != null && heap[rightChildID] == null){
                // if left child is bigger than the current element, shift the left child up
                if (comp.compare(heap[cur_index], heap[leftChildID]) <= 0){
                    shift(cur_index, leftChildID);
                    // update the current index
                    cur_index = leftChildID;
                }
                else {
                    break;
                }
            }
            // if the current node only have right child
            else if (heap[leftChildID] == null && heap[rightChildID] != null){
                // if the right child is bigger than the current element, shift the right child up
                if (comp.compare(heap[cur_index], heap[rightChildID]) <= 0){
                    shift(cur_index, rightChildID);
                    // update the current index
                    cur_index = rightChildID;
                }
                else {
                    break;
                }
            }
        }
        return element;
    }

    // shift the data in 2 indexes parent and child
    // the order of the two parameters do not matter
    private void shift(int parent, int child){
        T old_parent = heap[parent];
        heap[parent] = heap[child];
        heap[child] = old_parent;
    }

    // return the String to print out the heap nicely 
    public String toString(){
        String str = "";
        for (int i = 0; i < size; i++){
            str += heap[i] + "\n";
        }
        return str;
    }

    public static void main( String[] argv ){
        PQHeap<String> heap = new PQHeap<>(new AscendingString());
        heap.add("a");
        heap.add("c");
        heap.add("x");
        heap.add("d");
        heap.add("b");

        System.out.println(heap);
        heap.remove();
        System.out.println(heap);

    }
}