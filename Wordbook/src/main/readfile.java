package main;

import java.io.*;
import java.util.*;

public class readfile {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		
		File currentWords = new File("words.txt");
		
		int count = 0;
		Scanner scanner = new Scanner(currentWords);
		while (scanner.hasNextLine()) {
		    String nextToken = scanner.nextLine();
		    if (nextToken.equalsIgnoreCase("heralded"))
		    count++;
		}
		
		
//		System.out.println(count);
		System.out.println(countWord("heralded", currentWords));
//		Scanner scanner = new Scanner(System.in);
//	    String readString = scanner.nextLine();
//	    while(readString!=null)
//	    {
//	        System.out.println(readString);
//	        if(readString.equals(""))
//	            System.out.println("Read Enter Key.");
//	        if(scanner.hasNextLine())
//	            readString = scanner.nextLine();
//	        else
//	            readString = null;
//	    }
	}

	static int countWord(String word, File file) {

		int count = 0;
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String nextToken = scanner.nextLine();
				if (nextToken.equalsIgnoreCase(word))
					count++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
