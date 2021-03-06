import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Class for solution.
 */
public final class Solution {
    /**
     * Constructs the object.
     */
    private Solution() {
    }
    // Don't modify this method.
    /**
     * {main method that drives the program}.
     *
     * @param      args  The arguments
     */
    public static void main(final String[] args) {
        Scanner scan = new Scanner(System.in);
        String cases = scan.nextLine();
        switch (cases) {
        case "loadDictionary":
            // input000.txt and output000.txt
            BinarySearchST<String, Integer> hash = loadDictionary("/Files/t9.csv");
            while (scan.hasNextLine()) {
                String key = scan.nextLine();
                System.out.println(hash.get(key));
            }
            break;
        case "getAllPrefixes":
            // input001.txt and output001.txt
            T9 t9 = new T9(loadDictionary("/Files/t9.csv"));
            while (scan.hasNextLine()) {
                String prefix = scan.nextLine();
                for (String each : t9.getAllWords(prefix)) {
                    System.out.println(each);
                }
            }
            break;
        case "potentialWords":
            // input002.txt and output002.txt
            t9 = new T9(loadDictionary("/Files/t9.csv"));
            int count = 0;
            while (scan.hasNextLine()) {
                String t9Signature = scan.nextLine();
                for (String each : t9.potentialWords(t9Signature)) {
                    count++;
                    System.out.println(each);
                }
            }
            if (count == 0) {
                System.out.println("No valid words found.");
            }
            break;
        case "topK":
            // input003.txt and output003.txt
            t9 = new T9(loadDictionary("/Files/t9.csv"));
            Bag<String> bag = new Bag<String>();
            int k = Integer.parseInt(scan.nextLine());
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                bag.add(line);
            }
            for (String each : t9.getSuggestions(bag, k)) {
                System.out.println(each);
            }
            break;
        case "t9Signature":
            // input004.txt and output004.txt
            t9 = new T9(loadDictionary("/Files/t9.csv"));
            bag = new Bag<String>();
            k = Integer.parseInt(scan.nextLine());
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                for (String each : t9.t9(line, k)) {
                    System.out.println(each);
                }
            }
            break;
        default:
            break;
        }
    }
    // Don't modify this method.
    /**
     * { function to read file}.
     *
     * @param      file  The file
     *
     * @return     {String type}.
     */
    public static String[] toReadFile(final String file) {
        In in = new In(file);
        return in.readAllStrings();
    }
    /**
     * Loads a dictionary.
     *
     * @param      file  The file
     *
     * @return     {BinarySearchST object}.
     */
    public static BinarySearchST<String, Integer> 
    loadDictionary(final String file) {
        BinarySearchST<String, Integer>  
        st = new BinarySearchST<String, Integer>();
        // your code goes here
        String[] dict = toReadFile(file);
        for (int i = 0; i < dict.length; i++) {
            String word = dict[i].toLowerCase();
            if(st.contains(word )) {
                st.put(word, st.get(word) + 1);
            } else {
                st.put(word, 1);
            }
        }
        return st;
    }
}
/**
 * Class for t 9.
 */
class T9 {
    TST<Integer> tst;
    /**
     * Constructs the object.
     *
     * @param      st    {object of BinarySearchST}.
     */
    public T9(final BinarySearchST<String, Integer> st) {
        // your code goes here
        tst = new TST<>();
        for (String word : st.keys()) {
            tst.put(word, st.get(word));
        }
    }
    // get all the prefixes that match with given prefix.
    /**
     * Gets all words.
     *
     * @param      prefix  The prefix
     *
     * @return     All words.
     */
    public Iterable<String> getAllWords(final String prefix) {
        // your code goes here
        return tst.keysWithPrefix(prefix);
    }
    /**
     * {function to iterate through words}.
     *
     * @param      t9Signature  The t 9 signature
     *
     * @return     {null type}.
     */
    public Iterable<String> potentialWords(final String t9Signature) {
        // your code goes here
        int m = 0;
        while (m < 3) {
            for (String each : tst.keys()) {
                System.out.println(each);
                m++;
            }
        }
        return null;
    }
    // return all possibilities(words), find top k with highest frequency.
    /**
     * Gets the suggestions.
     *
     * @param      words  The words
     * @param      k      {top integer prefixes}.
     *
     * @return     The suggestions.
     */
    public Iterable<String> 
    getSuggestions(final Iterable<String> words, int k) {
        // your code goes here
        ArrayList<String> list = new ArrayList<String>();
        MaxPQ<Integer> max = new MaxPQ<>();
        for (String each : words) {
            max.insert(tst.get(each));
        }
        int m = 0;
        while (m < k) {
            int l = max.delMax();
            for (String each : words) {
                if (l == tst.get(each)) {
                    list.add(each);
                }
            }
            m++;
        }
        Collections.sort(list);
        return list;
    }
    // final output
    // Don't modify this method.
    /**
     * { function t9 }.
     *
     * @param      t9Signature  The t 9 signature
     * @param      k            {frequencies}.
     *
     * @return     {returns to the fucntion getSuggestions()}.
     */
    public Iterable<String> t9(final String t9Signature, final int k) {
        return getSuggestions(potentialWords(t9Signature), k);
    }
}
