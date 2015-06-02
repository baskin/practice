package com.garg.concepts.algos;

/*
 * Given a MxN matrix filled with 0 and 1
 * find largest rectangle filled with 1's 
 */
public class ComputeFill {
    public static void precompute(int arr[][], int pre[][]) {
        /*
         * Precompute the matrix pre such that
         * pre[i][j] is sum of all elements in rectangle
         * with top left as arr[0][0] and bottom right as 
         * arr[i][j]
         */

        int cols = arr[0].length;
        int rows = arr.length;

        int row_sum = 0;
        for (int i = 0; i < rows; i++) {
            row_sum = 0;
            for (int j = 0; j < cols; j++) {
                int fill = arr[i][j];
                row_sum += fill;
                if (i > 0) {
                    pre[i][j] = row_sum + pre[i - 1][j];
                } else {
                    pre[i][j] = row_sum;
                }
            }
        }
    }

    public static void displayMatrix(int arr[][]) {
        int cols = arr[0].length;
        int rows = arr.length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println("");

        }
    }

    public static int rectSum(int arr[][], int top_row, int left_col,
            int bottom_row, int right_col) {

        /*
         * Given precompute matrix this method can quickly
         * determine sum of elements in a rectangle specified by
         * given co-ordinates
         * 
         * Say original matrix was orig an precompute matrix was arr
         * the to find sum of elements in rectangle orig[top][left]
         * orig[bottom][right] in time efficient way we use following
         * relation between precomputed matrix and original matrix
         * 
         * SUM(orig[top][left], orig[bottom][right]) =
         *    arr[bottom][right] - arr[bottom][left -1]
         *    - arr[top-1][right] + arr[top -1][left -1]
         * 
         */
        int sum = 0;
        if ((bottom_row >= 0) && (right_col >= 0)) {
            sum += arr[bottom_row][right_col];
        }
        if ((top_row > 0) && (right_col >= 0)) {
            sum -= arr[top_row - 1][right_col];
        }
        if ((left_col > 0) && (bottom_row >= 0)) {
            sum -= arr[bottom_row][left_col - 1];
        }
        if ((left_col > 0) && (top_row > 0)) {
            sum += arr[top_row - 1][left_col - 1];
        }
        return sum;

    }

    public static void solve(int arr[][]) {
        int preCompute[][] = new int[arr.length][arr[0].length];
        //first get precompute matrix, used to find sum of elements in
        //any given rectangle of arr in time efficient manner
        precompute(arr, preCompute);
        System.out.println("\nMatrix");
        displayMatrix(arr);
        System.out.println("\nPrecompute Matrix");
        displayMatrix(preCompute);
        int max_area = 0;
        int indices[] = new int[4];
        // 0 top, 1 left, 2 bottom, 3 right
        for (int i = arr.length; i >= 1; i--) {
            for (int j = arr[0].length; j >= 1; j--) {
                /*
                 * For all possible rectangle dimensions
                 * search for largest solid filled rectangle 
                 * of area more than current maximum max_area 
                 */
                max_area = search(preCompute, i, j, indices, max_area);
            }
        }

        System.out.println("Largest Fill (" + indices[0] + "," + indices[1]
                + ")" + " (" + indices[2] + "," + indices[3] + ")");

    }

    private static int search(int[][] arr, int row_len, int col_len,
            int[] indices, int max) {
        //Basic cases where it is not possible to have a rectangle of 
        // given dimensions and have its area better than max
        if (row_len <= 0) {
            return max;
        }
        if (col_len <= 0) {
            return max;
        }
        if (max >= row_len * col_len) {
            return max;
        }
        // System.out.println(" Try " + row_len + " x " + col_len + " Max " +
        // max);
        int total_rows = arr.length;
        int total_cols = arr[0].length;
        //Now go over all rectangle of given dimension
        for (int i = 0; i <= total_rows - row_len; i++) {
            int bottom_row = i + row_len - 1;
            for (int j = 0; j <= total_cols - col_len; j++) {
                int right_col = j + col_len - 1;
                // System.out.println("Top left " + i + "," + j);
                if (row_len * col_len == rectSum(arr, i, j, bottom_row,
                        right_col)) {
                    // found a rect row_len * col_len in size which is
                    // completely filled
                    // with 1
                    indices[0] = i;
                    indices[1] = j;
                    indices[2] = bottom_row;
                    indices[3] = right_col;
                    System.out.print("Found " + row_len * col_len);
                    System.out.println(" at " + i + "," + j + " : "
                            + bottom_row + "," + right_col);
                    return row_len * col_len;
                }

            }

        }
        return max;

    }

    public static void main(String[] args) {
        int matrix[][] = { { 0, 0, 0, 0, 1, 1, 1, 1, 0 },
                { 0, 0, 0, 0, 1, 1, 1, 0, 1 }, { 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 1, 1, 1, 0, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 1, 0, 0, 1, 1, 0, 1, 0 }, { 0, 1, 0, 0, 1, 1, 1, 1, 0 },
                { 0, 0, 0, 0, 1, 0, 1, 1, 0 }, { 1, 0, 1, 1, 1, 1, 1, 1, 0 },
                { 1, 0, 1, 1, 1, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1, 1, 1, 1, 0 } };
        solve(matrix);
    }

}
