/*
 * This is a utility tool to process the 'newword.txt' file from Hanvon electronic dictionary.
 * This tool will remove all explanations and other lines. Only the new words will be kept.
 */

package main;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHanvonFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList newWordList = new ArrayList();
		ArrayList filteredWordList = new ArrayList();

		try {

			FileInputStream fstream = new FileInputStream("newword.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Lines contains special characters will be skipped
				if (strLine.indexOf('/') > 0 || strLine.indexOf('�') > 0
						|| strLine.indexOf(';') > 0 || strLine.indexOf('#') > 0
						|| strLine.indexOf(']') > 0)
					continue;
				// Replacing all non-alphanumeric characters with empty strings
				strLine = strLine.replaceAll("[^A-Za-z- ]", "");
				// strLine = strLine.replaceAll(" ", "");
				// Replacing multiple spaces with single space
				// strLine = strLine.replaceAll("( )+", " ");

				if (strLine.length() > 1) {
					System.out.println(strLine + ": " + strLine.length());
					newWordList.add(strLine.trim());
				} else {
					filteredWordList.add(strLine.trim());
				}

			}
			// Close the input stream
			in.close();

			FileOutputStream out = new FileOutputStream("newoutput11.txt");
			for (int i = 0; i < newWordList.size(); i++) {
				out.write(newWordList.get(i).toString().getBytes());
				out.write("\r\n".getBytes());
			}

			// Close the output stream
			out.close();

			// FileOutputStream fileredOut = new
			// FileOutputStream("fileredOut.txt");
			// for (int i = 0; i < filteredWordList.size(); i++) {
			// fileredOut.write(filteredWordList.get(i).toString().getBytes());
			// fileredOut.write("\r\n".getBytes());
			// }
			//
			// fileredOut.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public static boolean isLetterDigitOrChinese(String str) {
		// String regex = "^[a-zA-Z- ]+$";
		String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
		return str.matches(regex);
	}

	public static boolean englishOnly(String str) {
		String regex = "^[a-zA-Z- ]+$";
		return str.matches(regex);
	}

	static Pattern p = Pattern.compile("\\W");

	public static boolean containsSpecialChars(String string) {
		Matcher m = p.matcher(string);
		return m.find();
	}

}
