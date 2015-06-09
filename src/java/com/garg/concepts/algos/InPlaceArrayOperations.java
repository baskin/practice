package com.garg.concepts.algos;

public class InPlaceArrayOperations
{
	public static void reverse(int[] a, int start, int end)
	{
		while (start < end) {
			int temp = a[start];
			a[start] = a[end];
			a[end] = temp;

			start++;
			end--;
		}
	}

	// i -> (i + k) mod sz 
	/**
	 * A = PQ (P, Q are subsegments), Find QP.
	 * r(A) = r(Q)r(P)
	 * 
	 * QP = r(r(QP)
	 *    = r(r(P)r(Q))
	 */
	public static void rotateCyclic(int[] a, int k)
	{
		// Left rotate
		reverse(a, 0, k - 1);
		reverse(a, k, a.length - 1);
		reverse(a, 0, a.length - 1);
	}

	/**
	 * array     : 1 2 3 4 5 6 7 8
	 * outshuffle: 1 5 2 6 3 7 4 8 (outer boundaries remain same)
	 * inshuffle : 5 1 6 2 7 3 8 4
	 */
	public static void outshuffle(int[] a)
	{
	    if (a.length % 2 != 0) {
	        throw new IllegalArgumentException();
	    }
	    // create position array (similar to rank array)
	    int[] pos = new int[a.length];
	    for (int i = 0; i < pos.length; i++) {
	        pos[i] = i * 2 % (pos.length - 1);
	    }
	    pos[a.length - 1] = a.length - 1;
	    rearrange(a, pos);
	}
	
	/**
	 * array     : 1 2 3 4 5 6 7 8
     * inshuffle : 5 1 6 2 7 3 8 4
	 */
	public static void inshuffle(int[] a)
    {
        if (a.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        // create position array (similar to rank array)
        int[] pos = new int[a.length];
        for (int i = 0; i < pos.length; i++) {
            if (2 * i + 1 < pos.length) {
                pos[i] = i * 2 + 1;
            }
            else {
                pos[i] = (i * 2) % (pos.length);
            }
        }
        rearrange(a, pos);
    }

	/**
	 * Rearrange the given array a according to the positions array pos.
	 */
    private static void rearrange(int[] a, int[] pos)
    {
        for (int i = 0; i < pos.length; i++) {
            while (i != pos[i]) {
                // This while loop completes one cycle in the array
                Util.swap(a, i, pos[i]);
                Util.swap(pos, i, pos[i]);
            }
        }
    }

	public static void main(String[] args)
	{
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8};
		rotateCyclic(a, 2);
		Util.print(a);
		
		int[] b = { 1, 2, 3, 4, 5, 6, 7, 8};
		outshuffle(b);
		Util.print(b);
		
		int[] c = { 1, 2, 3, 4, 5, 6, 7, 8};
		inshuffle(c);
		Util.print(c);
	}
}
