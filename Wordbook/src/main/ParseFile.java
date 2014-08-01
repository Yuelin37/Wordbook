package main;

import java.io.*;
import java.util.ArrayList;

public class ParseFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList newWordList = new ArrayList();
		ArrayList filteredWordList = new ArrayList();

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("My Clippings.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Replacing all non-alphanumeric characters with empty strings 
				strLine = strLine.replaceAll("[^A-Za-z]", " ");
				
				// Replacing multiple spaces with single space
				strLine = strLine.replaceAll("( )+", " ");
				
				if (isLetterDigitOrChinese(strLine) && strLine.length()>1 && strLine.length()<41) {
					newWordList.add(strLine.trim());
				}
				else{
					filteredWordList.add(strLine.trim());
				}

			}
			// Close the input stream
			in.close();

			FileOutputStream out = new FileOutputStream("newoutput1.txt");
			for (int i = 0; i < newWordList.size(); i++) {
				out.write(newWordList.get(i).toString().getBytes());
				out.write("\r\n".getBytes());
			}

			// Close the input stream
			out.close();
			
			FileOutputStream fileredOut = new FileOutputStream("fileredOut.txt");
			for (int i = 0; i < filteredWordList.size(); i++) {
				fileredOut.write(filteredWordList.get(i).toString().getBytes());
				fileredOut.write("\r\n".getBytes());
			}

			fileredOut.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public static boolean isLetterDigitOrChinese(String str) {
		String regex = "^[a-z0-9A-Z- ]+$";
		//String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
		return str.matches(regex);
	}

}
