package substring;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Donny Rozendal
 * This program analyzes the KMP substring algorithm and the Boyer-Moore
 * algorithm. The code of both algorithms is altered slightly to calculate how
 * many comparisons are made, to eventually discover which algorithm is more
 * efficient and by how much.
 */
public class Substring {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("oog");
        words.add("golven");
        words.add("bloemen");
        words.add("lijstervink");
        words.add("poster");
        words.add("haar");
        words.add("een");
        words.add("zonnelicht");
        words.add("wonderbaar");
        words.add("bier");
        
        for (String word : words) {
            searchKMP(word);
            searchBoyerMoore(word);
        }
    }
    
    public static String readBook() {
        File file = new File("mei_herman_gorter.txt");
        BufferedInputStream bis = null;
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            while (bis.available() > 0) {
                sb.append((char)bis.read());
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe);
        } catch (IOException ioe) {
            System.out.println("I/O Exception: " + ioe);
        }
        finally {
            try {
                if (bis != null && fis != null) {
                    fis.close();
                    bis.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error in stream close: " + ioe);
            }
        }
        return sb.toString();
    }
    
    public static void searchKMP(String pattern) {
        String book = readBook();
        KMP kmp = new KMP(pattern);
        KMP.charComparisons = 0;
        int counter = 0;
        int start = 0;
        int offset = kmp.search(book, start);
        while (offset != book.length()) {
            counter++;
            start = offset;
            offset = kmp.search(book, start + pattern.length() + 1);
        }
        System.out.println(pattern);
        System.out.println("The specified word is found " + counter + " times.");
        System.out.println("KMP: " + KMP.charComparisons + " comparisons.\n");
    }
    
    public static void searchBoyerMoore(String pattern) {
        String book = readBook();
        BoyerMoore bm = new BoyerMoore(pattern);
        BoyerMoore.charComparisons = 0;
        int counter = 0;
        int start = 0;
        int offset = bm.search(book, start);
        while (offset != book.length()) {
            counter++;
            start = offset;
            offset = bm.search(book, start + pattern.length() + 1);
        }
        System.out.println(pattern);
        System.out.println("The specified word is found " + counter + " times.");
        System.out.println("Boyer-Moore: " + BoyerMoore.charComparisons + " comparisons.\n");
    }
    
}
