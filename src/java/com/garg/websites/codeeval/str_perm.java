
package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class str_perm
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				List<String> perms = perm(line);
				Collections.sort(perms);
				boolean delim = false;
				for (String p : perms) {
					if (delim) {
						System.out.print(",");
					}
					delim = true;
					System.out.print(p);
				}
				System.out.println();
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static List<String> perm(String str)
	{
		if (str.length() == 0) {
			return Collections.singletonList(str);
		}
		List<String> perms = perm(str.substring(1));
		char c = str.charAt(0);
		List<String> result = new ArrayList<String>();
		for (String p : perms) {
			result.addAll(insert(p, c));
		}
		return result;
	}

	private static List<String> insert(String p, char c)
	{
		List<String> result = new ArrayList<String>();
		for (int i = 0; i <= p.length(); i++) {
			StringBuilder sb = new StringBuilder(p);
			sb.insert(i, c);
			result.add(sb.toString());
		}
		return result;
	}
}