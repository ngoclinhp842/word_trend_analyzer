import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
File: CommonWordsFinder.java
Author: Linh Phan (Michelle)
Date: 04/24/2022

The class reads the word count file and dumps the words straight into a PQHeap

Usage: CommonWordsFinder <N> <WC file 1> <...>
Reports the N most common words in each provided Word Count file.

How to run: type "javac CommonWordsFinder.java" in the command line.
            type "java CommonWordsFinder 10 2008 2009 2010 2011 2012 2013 2014 2015" in the command line
*/
public class CommonWordsFinder {
    private PQHeap<KeyValuePair<Double, String>> heap;

    // read a word count file given the filename. 
    // The function should clear the map and then put all of the words, 
    // with their counts, into the map data structure.
    public boolean readWordCount( String filename ) throws IOException{

        heap = new PQHeap<KeyValuePair<Double, String>>(new AscendingKeyValuePair());

        // use the BufferedReader to read in the file one line at a time.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line = bufferedReader.readLine();

        // read in the first line separately, since it contains the total word count. 
        line = bufferedReader.readLine();

        KeyValuePair<Double,String> element;

        while (line != null){
            // split line into words. The regular expression can be interpreted
            // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
            String[] words = line.split("[^a-zA-Z0-9'.]");
            
            // call the BSTMap's put method every time you parse a new key-value pair.
            if (words.length == 2){
                // convert the line into an KeyValuePair
                element = new KeyValuePair<Double,String>(Double.valueOf(words[1]), words[0]);
                // add the KeyValuePair to the heap
                heap.add(element);
            }
            words = null;
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return true;
    }

    // return the String to print out the heap nicely 
    public String toString(){
        return heap.toString();
    }

    public KeyValuePair<Double, String> remove(){
        return heap.remove();
    }

    // Test Function
    public static void main( String[] argv ) throws FileNotFoundException, IOException{
        Long start = System.currentTimeMillis();
        // WordCounter2 counter2 = new WordCounter2("hashmap");

        // counter2.buildMap(counter2.readWords("reddit_comments_" + argv[0] + ".txt"));
        // counter2.writeWordCount("wordCounter_" + argv[0] + ".txt");

        CommonWordsFinder finder = new CommonWordsFinder();

        // print out the N most frequent words along with their frequency 
        int n = Integer.parseInt(argv[0]);
        // number of filename
        int m = argv.length - 1;

        // freqs[i][j] is the jth most common word in the ith file
        // Column is the filename
        // Rows is the most frequent words
        KeyValuePair<Double, String>[][] freqs = new KeyValuePair[m][n];
        
        // store the first line, column ID
        String columnID = " , ";

        // loop through each filename in argv
        for (int i = 1; i < argv.length; i++){
            // Pass through the wordCounter filename, read the wordCount file
            // dump the data in the heap
            finder.readWordCount("wordCounter_" + argv[i] + ".txt");
            // store the year in column ID
            columnID += argv[i] + ", ";

            // remove n most frequent KeyValuePair in the heap
            // store each KeyValuePair in position [i - 1][j] 
            // i - 1 is the filename index
            // j is ranking of frequency 
            for (int j = 0; j < n; j++){
                freqs[i - 1][j] = finder.remove();
            }
        }

        // print the columnID first
        System.out.println(columnID);
        
        // loop n times (number of KeyValuePair need to print)
        for (int i = 0; i < n; i++){
            String row = "";
            row += String.valueOf(i + 1) + ", ";

            // loop m times (number of filename)
            for (int j = 0; j < m; j++){
                row += freqs[j][i] + ", ";
            }
            System.out.println(row);
        }

        Long end = System.currentTimeMillis();
        System.out.println("Running time: " + String.valueOf(end-start));

    }
}
