package main;

import java.io.*;
import java.util.ArrayList;

public class ParseFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList newWordList = new ArrayList();

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("newword.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				if (strLine.indexOf("/") < 0 && strLine.indexOf("[")<0 && strLine.length()>1) {
					newWordList.add(strLine);
				}
			}
			// Close the input stream
			in.close();

			FileOutputStream out = new FileOutputStream("newoutput.txt");
			for (int i = 0; i < newWordList.size(); i++) {
				out.write(newWordList.get(i).toString().getBytes());
				out.write("\r\n".getBytes());
			}

			// Close the input stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

}
