//: StringSortTest.java
//Testing the generic sorting Vector
package WordSort;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class WordSort {
	static class StringCompare implements Compare {
		public boolean lessThan(Object l, Object r) {
			return ((String) l).toLowerCase().compareTo(
					((String) r).toLowerCase()) < 0;
		}

		public boolean lessThanOrEqual(Object l, Object r) {
			return ((String) l).toLowerCase().compareTo(
					((String) r).toLowerCase()) <= 0;
		}
	}

	public static void main(String[] args) {
		SortVector sv = new SortVector(new StringCompare());

		// Open the file which contains the words you want to add
		try {
			FileInputStream fstream = new FileInputStream("Wordbook.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			FileOutputStream sortedWordBook = new FileOutputStream(
					"SortedWordbook.txt");

			String newWord, currentWord;
			while ((newWord = br.readLine()) != null) {
				newWord = newWord.trim();
				sv.addElement(newWord);
			}
			sv.sort();
			Enumeration e = sv.elements();

			int wordCount = 0;
			while (e.hasMoreElements()) {
				currentWord = newWord;
				newWord = ((String) e.nextElement()).toLowerCase();
				if (currentWord != null && currentWord.equals(newWord)) {
					continue;
				}
				System.out.println(newWord);
				sortedWordBook.write(newWord.getBytes());
				sortedWordBook.write("\r\n".getBytes());
				wordCount++;
			}

			String totalWords = "Total Words: " + wordCount;
			sortedWordBook.write(totalWords.getBytes());
			sortedWordBook.write("\r\n".getBytes());
			fstream.close();
			sortedWordBook.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
} // /:~