package com.garg.websites.codeeval;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * http://www.hackerearth.com/may-easy-challenge-14/algorithm/little-deepak-and-game-of-thrones/
 */
public class got_cartographer
{
	public static void main(String[] args)
	{
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new InputStreamReader(System.in));
			String line = fr.readLine();
			// The first line contains an integer, tc, denoting the number
			// of test cases. After that, one line with an integer m,
			// denoting the number of maps. m lines with four integers. The
			// first two integers, x1, y1, will be depicting the coordinates
			// of bottom-left (SW) corner, the next two integers, x2, y2,
			// will be depicting the coordinates of the top-right (NE)
			// corner.
			int tc = Integer.parseInt(line);
			for (int i = 0; i < tc; i++) {
				
				List<Rectangle> rectangles = new ArrayList<Rectangle>(); 
				int m = Integer.parseInt(fr.readLine());
				for (int j = 0; j < m; j++) {
					String[] tokens = fr.readLine().split(" ");
					Rectangle r = new Rectangle();
					r.height = Integer.parseInt(tokens[0]);
					r.width = Integer.parseInt(tokens[1]);
					r.x = Integer.parseInt(tokens[2]);
					r.y = Integer.parseInt(tokens[3]);
					rectangles.add(r);
				}
				System.out.println(findArea(rectangles));
			}
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (fr != null) {
				try {
					fr.close();
				} 
				catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private static int findArea(List<Rectangle> rectangles) 
	{
		// Sort by x1 (leftmost of rectangle)
		Collections.sort(rectangles, new Comparator<Rectangle>() {
			public int compare(Rectangle o1, Rectangle o2) 
			{
				return Integer.valueOf(o1.height).compareTo(o2.height);
			}
		});
		
		// Now sweep a line parallel to the y axis starting x = 0.
		// Keep a track of all intersecting rectangles using a Heap.
		// It will be min Heap with key being right-most of rectangle.
		// As we sweep past a rectangle (sweep line passes ahead of right-most of rectangle)
		// we remove it from the heap.
		// We just accumulate the area under each unit of the sweep line.
		int sum = 0;
		PriorityQueue<Rectangle> heap = new PriorityQueue<Rectangle>(11, new Comparator<Rectangle>() {
			public int compare(Rectangle o1, Rectangle o2) {
				return Integer.valueOf(o1.x).compareTo(o2.x);
			}
		});
		
		int r = 0;
		for (int sweepX = rectangles.get(0).height + 1; sweepX <= rectangles.get(rectangles.size() - 1).x; sweepX++) {
			// See if any rect needs to be removed.
			while (!heap.isEmpty() && heap.peek().x < sweepX) {
				heap.poll();
			}
			
			// See if any rect needs to be added.
			while (r < rectangles.size() && sweepX > rectangles.get(r).height) {
				heap.offer(rectangles.get(r));
				r++;
			}
			
			sum += findSegmentArea(heap);
		}
		return sum;
	}

	private static int findSegmentArea(PriorityQueue<Rectangle> heap) 
	{
		Rectangle[] rr = new Rectangle[heap.size()];
		List<Rectangle> rs = Arrays.asList(heap.toArray(rr));
		Collections.sort(rs, new Comparator<Rectangle>() {
			public int compare(Rectangle o1, Rectangle o2) 
			{
				return Integer.valueOf(o1.width).compareTo(o2.width);
			}
		});
		int sum = 0;
		int sb = rs.get(0).width; // segment beginning
		int se = rs.get(0).y; // segment end
		for (int i = 1; i < rs.size(); i++) {
			int nsb = rs.get(i).width; // next segment beginning
			int nse = rs.get(i).y; // next segment end
			if (nsb <= se) {
				// merge segments
				if (nse > se) {
					se = nse;
				}
			}
			else {
				// new segment begins
				sum += se - sb;
				sb = nsb;
				se = nse;
			}
		}
		return sum + se - sb;
	}
}
