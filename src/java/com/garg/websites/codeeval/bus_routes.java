package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.codeeval.com/open_challenges/134/
 */
public class bus_routes
{
	private static final Pattern SOURCE_DEST_PATTERN = Pattern.compile("(.*)");
	
	private static final Pattern ROUTE_PATTERN = Pattern.compile(".*=\\[(.*)\\]");
	
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String msg;
			while ((msg = br.readLine()) != null) {
				String[] tokens = msg.split(";");
				Matcher m = SOURCE_DEST_PATTERN.matcher(tokens[0]);
				m.find();
				String [] sd = m.group().split(",");
				List<List<Integer>> routes = new ArrayList<List<Integer>>();
				for (int i = 1; i < tokens.length; i++) {
					Matcher rm = ROUTE_PATTERN.matcher(tokens[i]);
					rm.find();
					String [] rds = rm.group().split(",");
					List<Integer> route = new ArrayList<Integer>();
					for (String s : rds) {
						route.add(Integer.parseInt(s));
					}
					routes.add(route);
				}
				System.out.println(minTravelTime(Integer.parseInt(sd[0]), Integer.parseInt(sd[1]), routes));
			}
		}
		finally {
			if (br != null) {
				br.close();
			}
		}
	}

	private static char[] minTravelTime(int source, int dest, List<List<Integer>> routes) 
	{
		return null;
	}
}
