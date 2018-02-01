package substring;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Donny Rozendal
 * This program analyzes the KMP substring algorithm and the Boyer-Moore
 * algorithm. The code of both algorithms is altered slightly to calculate how
 * many comparisons are made, to eventually discover which algorithm is more
 * efficient and by how much.
 */
public class Substring {

    public static void main(String[] args) {
        String pattern = "bier";
        String book = readBook();
        
        KMP kmp = new KMP(pattern);
        int counter = 0;
        int start = 0;
        int offset = kmp.search(book, start);
        while (offset != book.length()) {
            counter++;
            start = offset;
            offset = kmp.search(book, start + pattern.length() + 1);
        }
        System.out.println("The specified word is found " + counter + " times.");
        System.out.println("There were " + KMP.charComparisons + " comparisons.");
    }
    
    public static String readBook() {
        File file = new File("/home/terazeus/Dropbox/College/Jaar 2/Sorting and searching/Week 6/mei_herman_gorter.txt");
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
    
}
