package main;

import java.io.*;

public class readfile {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println(System.getProperty("os.name"));
		// TODO Auto-generated method stub
		// 1. Buffered input file
		try {
			// 1. Buffered input file
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					new FileInputStream("output.txt")));
			String s, s2 = new String();
			while ((s = in.readLine()) != null){
				s2 += s + "\n";
//				if (s.length()<=20)
//					System.out.println(s);
			}
			in.close();

		} catch (EOFException e) {
			System.out.println("End of stream encountered");
		}
	}

}
