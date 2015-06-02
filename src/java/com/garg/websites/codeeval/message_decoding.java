package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StreamCorruptedException;

/**
 * http://codeeval.com/open_challenges/36/
 */
public class message_decoding
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		PushbackReader br = null;
		try {
			br = new PushbackReader(new BufferedReader(new FileReader(file)));
			String msg;
			while ((msg = decode(br)) != null) {
				System.out.println(msg);
			}
		}
		finally {
			if (br != null) {
				br.close();
			}
		}
	}

	private static void readIgnoreCR(Reader r, char[] cb) throws IOException
	{
		int c = 0;
		for (int i = 0; i < cb.length; i++) {
			while ((c = r.read()) == '\r' || (c == '\n'))
				;
			cb[i] = (char) c;
		}
	}

	private static String decode(PushbackReader pbr) throws IOException
	{
		int c = pbr.read();
		if (c == -1) {
			// marks the end of all data sets.
			return null;
		}

		pbr.unread(c);
		StringBuilder sb = new StringBuilder();
		while (true) {
			c = pbr.read();
			if (c == '0' || c =='1') {
				break;
			}
			sb.append((char) c);
		}
		
		pbr.unread(c);
		String header = sb.toString();

		// First, populate the codes.
		// codes has 8 rows, 1 row each for code lengths 0,1,2...7
		// codes[i] will have all binary codes of length i (except all ones).
		// e.g. codes[2] = {00, 01, 10}
		// Thus, length (codes[i]) = (1 << i) - 1
		char[][] codes = new char[8][];
		codes[0] = new char[0]; // Dummy row for length 1.

		int headerIndex = 0;
		for (int i = 1; i < codes.length && headerIndex < header.length(); i++) {
			codes[i] = new char[(1 << i) - 1];
			for (int j = 0; j < codes[i].length
				&& headerIndex < header.length(); j++) 
			{
				codes[i][j] = header.charAt(headerIndex);
				headerIndex++;
			}
		}

		sb = new StringBuilder();
		// Now, process the message, one char at a time.
		int length = -1;
		// Each code segment in a msg begins with the 3 char length.
		// msg ends with segment length 0.
		while ((length = readAsBinary(pbr, 3)) != 0) {
			int code = -1;
			while ((code = readAsBinary(pbr, length)) != (1 << length) - 1) {
				if (length > codes.length - 1 || codes[length] == null
						|| code > codes[length].length - 1
						|| codes[length][code] == '\0') 
				{
					throw new StreamCorruptedException(
						"No encoding found for code "
							+ Integer.toBinaryString(code) + " of length " + length);
				}
				char decrypted = codes[length][code];
				sb.append(decrypted);
			}
		}
		pbr.read();
		return sb.toString();
	}

	/**
	 * Read len characters from the given stream, interpret them as a binary
	 * sequence and return the resulting integer value.
	 */
	private static int readAsBinary(Reader r, int len) throws IOException
	{
		char[] cb = new char[len];
		readIgnoreCR(r, cb);
		// Now interpret the cb as a binary number.
		int val = 0;
		for (char c : cb) {
			int n = c - '0';
			if (n != 0 && n != 1) {
				throw new StreamCorruptedException(
						"Message should have only 0's and 1's. Found "
								+ (char) n);
			}
			val = (val << 1) + n;
		}
		return val;
	}
}
