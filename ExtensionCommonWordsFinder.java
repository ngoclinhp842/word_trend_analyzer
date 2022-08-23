/*
File: ExtensionCommonWordsFinder.java
Author: Linh Phan (Michelle)
Date: 04/24/2022

The class has a method that reads the word count file and dumps the words straight into a BSTMap

Usage: CommonWordsFinder <N> <WC file 1> <...>
            Reports the N most common words in each provided Word Count file. 
            For WC file 1, download from zip file for this link: 
            https://drive.google.com/file/d/1Byv9eJ2QOgi1ynuwE9auRJ3NK2F0KRHp/view?usp=sharing
            and only enter the year of the file.

Example on how to run: type "javac ExtensionCommonWordsFinder.java" in the command line.
            type "java ExtensionCommonWordsFinder 10 2008 2009 2010 2011 2012 2013 2014 2015" 
            in the command line.
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;



public class ExtensionCommonWordsFinder {
    private BSTMap<Double, String> bstmap;

    public boolean readWordCount( String filename ) throws IOException{
        bstmap = new BSTMap<Double, String>(new AscendingDouble());

        // use the BufferedReader to read in the file one line at a time.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line = bufferedReader.readLine();

        // read in the first line separately, since it contains the total word count. 
        line = bufferedReader.readLine();

        while (line != null){
            // split line into words. The regular expression can be interpreted
            // as split on anything that is not (^) (a-z or A-Z or 0-9 or ').
            String[] words = line.split("[^a-zA-Z0-9'.]");
            
            // call the BSTMap's put method every time you parse a new key-value pair.
            if (words.length == 2){
                bstmap.put(Double.parseDouble(words[1]), words[0]);
            }
            words = null;
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return true;
    }

    // return the String to print out the heap nicely 
    public String toString(){
        return bstmap.toString();
    }

    public ArrayList<KeyValuePair<Double, String>> sort(){
        ArrayList<KeyValuePair<Double, String>> entrySet = bstmap.entrySet();
        entrySet.sort(new AscendingKeyValuePair());
        Collections.reverse(entrySet);
        return entrySet;
    }

    public static void main( String[] argv ) throws FileNotFoundException, IOException{
        Long start = System.currentTimeMillis();
        ExtensionCommonWordsFinder finder = new ExtensionCommonWordsFinder();

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

        ArrayList<KeyValuePair<Double, String>> entrySet;

        // loop through each filename in argv
        for (int i = 1; i < argv.length; i++){
            // Pass through the wordCounter filename, read the wordCount file
            // dump the data in the heap
            finder.readWordCount("wordCounter_" + argv[i] + ".txt");
            entrySet = finder.sort();

            // store the year in column ID
            columnID += argv[i] + ", ";

            // remove n most frequent KeyValuePair in the heap
            // store each KeyValuePair in position [i - 1][j] 
            // i - 1 is the filename index
            // j is ranking of frequency 
            for (int j = 0; j < n; j++){
                freqs[i - 1][j] = entrySet.get(j);
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
