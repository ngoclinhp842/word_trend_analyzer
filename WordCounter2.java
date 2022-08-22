/*
File: WordCounter2.java
Author: Linh Phan (Michelle)
Date: 04/18/2022

In order to properly compare the times for building a BSTMap versus a Hashmap, 
we need to separate the file I/O from the process of building the data structure. 
Your WordCounter2 should have a field of type MapSet to hold the data structure 
and a field of type int to hold the total word count.

how to run: type the following in the terminal:
            javac WordCounter2.java
            java WordCounter2 <filename>
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class WordCounter2 {
    private MapSet<String, Integer> data;
    // store the type of data structure, "bst" or "hashmap"
    private String data_structure;
    private int totalWordCount;
    private int uniqueWordCount;

    // constructor, where data_structure is either "bst" or "hashmap". 
    // It should create the appropriate data structure and store it in the map field.
    public WordCounter2( String data_structure ){
        this.data_structure = data_structure;
        if (data_structure == "bst"){
            data = new BSTMap<String, Integer>(new AscendingString());
        }
        else if (data_structure == "hashmap"){
            data = new Hashmap<String, Integer>(new AscendingString());
        }
    }

    // given the filename of a text file, read the text file 
    // return an ArrayList list of all the words in the file.
    public ArrayList<String> readWords( String filename ) throws IOException, FileNotFoundException{
        // ArrayList list of all the words in the file
        ArrayList<String> data = new ArrayList<>();

        // use the BufferedReader to read in the file one line at a time.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line = bufferedReader.readLine();

        String[] words;
        String word;
        while (line != null){
            // split line into words. The regular expression can be interpreted
            // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
            words = line.split("[^a-zA-Z0-9']");

            for (int i = 0; i < words.length; i++) {
                word = words[i].trim().toLowerCase();
                word = word.replaceAll("[\uFEFF-\uFFFF]", ""); 

                // Might want to check for a word of length 0 and not process it
                if (words.length != 0 && word.compareTo("") != 0){
                    // increment the total word count
                    totalWordCount++;
                    // add each word in the list data
                    data.add(word);
                }
            }
            // assign to line the result of calling the readLine method of your BufferedReader object.
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return data;
    }


    // given an ArrayList of words, put the words into the map data structure. 
    // Return the time taken in ms. Record the time using System.nanoTime().
    public double buildMap( ArrayList<String> words ){
        double start = System.nanoTime();
        // loop through every word in the parameter list
        for (String word: words){
            // if the word already in the hash table as a key
            if (data.containsKey(word)){
                // increment value at word key position
                data.put(word, data.get(word) + 1);
            }
            else {
                // if word is not a existing key in the hash table
                // set the value at the bucket with word as key to 1
                data.put(word, 1);
                uniqueWordCount++;
            }
        }
        double end = System.nanoTime();
        return end - start;
    }

    // return the total word count from the last time readWords was called.
    public int totalWordCount(){
        return totalWordCount;
    }

    // clear the map data structure.
    public void clearMap(){
        data.clear();
        totalWordCount = 0; 
        uniqueWordCount = 0;
        if (data_structure == "bst"){
            data = new BSTMap<>(new AscendingString());
        }
        else if (data_structure == "hashmap"){
            data = new Hashmap<>(new AscendingString());
        }
    }

    // return the unique word count, which should be the size of the map data structure.
    public int uniqueWordCount(){
        return uniqueWordCount;
    }


    // return the number of times the word occurred in the list of words. 
    // Query the data structure to get the information. 
    // Return 0 if the word does not exist in the data structure.
    public int getCount( String word ){
        if (data.get(word) == null) return 0;
        return data.get(word);
    }

    // return the frequency of the word in the list of words. 
    // Query the data structure to get the word count and 
    // then divide by the total number of words to get the frequency. 
    // Return 0 if the word does not exist in the data structure.
    public double getFrequency( String word ){
        return 1.0 * getCount(word)/totalWordCount;
    }

    // write a word count file given the current set of words in the data structure. 
    // The first line of the file should contain the total number of words. 
    // Each subsequent line should contain a word and its frequency.
    public boolean writeWordCount( String filename ) throws FileNotFoundException, IOException{
        // create a new file to store the result 
        File file = new File(filename);
        // create a FileWriter to write the result
        FileWriter writer = new FileWriter(file);
        // write the total Word Count first 
        writer.write("totalWordCount : " + totalWordCount + "\n");

        // get the list to store every pairs
        ArrayList<KeyValuePair<String, Integer>> pairs = data.entrySet();
        
        // Write the word count file using a pre-order traversal of the tree. 
        for (KeyValuePair<String, Integer> pair: pairs){
            writer.write(pair.getKey() + " " + getFrequency(pair.getKey()) + "\n");
        }
        pairs.clear();
        writer.close();
        return true;
    }

    // read a word count file given the filename. 
    // The function should clear the map and then put all of the words, 
    // with their counts, into the map data structure.
    public boolean readWordCount( String filename ) throws IOException{
        data.clear();
        // use the BufferedReader to read in the file one line at a time.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

        // get total word count
        String line = bufferedReader.readLine();
        String[] words = line.split("[^a-zA-Z0-9'.-]");
        totalWordCount = Integer.parseInt(words[3]);
        
        // read in the first line separately, since it contains the total word count. 
        line = bufferedReader.readLine();

        while (line != null){
            // split line into words. The regular expression can be interpreted
            // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
            words = line.split("[^a-zA-Z0-9'.-]");
            // call the BSTMap's put method every time you parse a new key-value pair.
            if (words.length > 1){
                // convert totalWordCount from int to Double
                double wordCount = totalWordCount * 1.0;
                double value = Double.valueOf(words[1]) * wordCount;
                data.put(words[0], (int) Math.round(value));
            }
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return true;
    }

    public String toString(){
        return data.toString();
    }

    public int collisions(){
        return data.collisions();   
    }

    public int getCapacity(){
        return ((Hashmap<String, Integer>)data).getCapacity();
    }


    public static void main( String[] argv ) throws FileNotFoundException, IOException{
        WordCounter2 test = new WordCounter2("hashmap");
        
        // for every filename typed in the commandline
        for (String filename: argv){
            // clear the initial map
            test.clearMap();
            // read the words from the file into an ArrayList using the readWords method
            ArrayList<String> words = test.readWords(filename);

            // calculate a robust average of the time it takes to build the map
            // loop five times, each time through the loop clearing the map 
            // and then building the map, storing the run times.
            ArrayList<Double> buildMapTime = new ArrayList<>();
            for (int i = 0; i < 5; i++){
                test.clearMap();
                buildMapTime.add(test.buildMap(words));
            }

            // keep track the sum of the initial arraylist of building map time
            double sum_buildMap = 0;
            // sum of the arraylist for time building map after removing the max and min
            double sum_buildMapWithoutMaxMin = 0;
            double min = Double.MAX_VALUE; 
            double max = Double.MIN_VALUE;

            for (Double time: buildMapTime){
                // calculate the sum of time
                sum_buildMap += time;
                // find the min value
                if (time < min){
                    min = time;
                }

                // find the max value
                if (time > max){
                    max = time;
                }
            }
            // System.out.println(buildMapTime);
            double avg_buildMap = sum_buildMap / buildMapTime.size();

            buildMapTime.remove(min);
            buildMapTime.remove(max);

            for (Double time: buildMapTime){
                sum_buildMapWithoutMaxMin += time;
            }
            // System.out.println(buildMapTime);
            double avg_buildMapWithoutMaxMin = sum_buildMapWithoutMaxMin / buildMapTime.size();

            // calculate a robust average by dropping the low and high values
            // and computing the average of the remaining times.
            words.clear();
            buildMapTime.clear();
            System.out.println("Result for " + filename);
            System.out.println("Number of collisions: " + test.collisions());
            System.out.println("Size of the hash table: " + test.getCapacity());
            System.out.println("Average time of building Map: " + avg_buildMap * 0.000001);
            System.out.println("Droping the low and high value. Average time of building Map: " + avg_buildMapWithoutMaxMin * 0.000001);
            System.out.println();
        }
    }
}
