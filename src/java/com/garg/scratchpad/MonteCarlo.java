
package com.garg.scratchpad;

public class MonteCarlo
{
	/**
	 * Generate random points in a space of square. Count the number of points
	 * falling into an inscribed circle in the square, and PI can be derived
	 * from the number inside of the circle
	 */
	public static float estimatePI()
	{
		int total    = 0;
		int inscribed = 0;
		for (int n = 0; n < 1e5; n++) {
			// locate random points in quadrant (0,5) (0,5).
			// Count number of points with distance <= 5 from centre (0,0)
			int x = (int) (Math.random() * 6);
			int y = (int) (Math.random() * 6);
			total ++;
			if (x*x + y*y <= 25) {
				inscribed++;
			}
		}
		// PI = 4* inscribed / total
		return (4.0f * inscribed / total);
	}
	
	public static void main(String[] args)
	{
		System.out.println(estimatePI());
	}
}
