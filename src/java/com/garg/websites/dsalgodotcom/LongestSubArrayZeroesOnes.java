package com.garg.websites.dsalgodotcom;

/**
 * You are given an array of 1's and 0's only. Find the longest subarray which
 * contains equal number of 1's and 0's.
 * 
 * @author bgarg
 */
public class LongestSubArrayZeroesOnes
{

    /**
     * We will keep a running sum of "num of ones minus num of zeros" for each
     * index of the array. For any two indices, if the running sum is equal,
     * that means there are equal number of ones and zeros between these two
     * indices. We will store the running sum in an array such that it acts like
     * a hash map where key is the running sum and value is the leftmost index
     * of that running sum to appear. The running sum can vary from -n to +n. So
     * we need an array of length 2*n+1. So for any index we can check whether
     * this sum has occurred before and if yes what is the left most index for
     * it. We can do this in O(1) time by maintaining the array. So at any index
     * we can find the longest equal subarray till that point. We will compare
     * it with a maximum value and update the maximum accordingly. So the
     * overall complexity for the process is O(n)
     */
    private static void printMaxSubarray(int[] arr)
    {
        int n = arr.length;
        
        // store k -> diffArr[k+n]
        int[] diffArr = new int[2*n + 1];
        for (int i = 0; i < diffArr.length; i++) {
            diffArr[i] = -2;
        }
        // This is necessary! Trick
        // e.g. consider arr {1 0 0} diffArr {1 0 -1}
        // now we know answer is a[0]..a[1], however, since we do not have diffArr storage of count = 0
        // this fails. Hence we store count=0, diffArr[0 + n] = -1. And make sure we include 
        // left index + 1 as the starting point.
        diffArr[0 + n] = -1;

        int maxLength = 0;
        int count = 0;
        int maxLeftIndex = -1, maxRightIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            count += (arr[i] == 1 ? 1 : -1);
            int leftMostIndex = diffArr[count + n];
            if (leftMostIndex == -2) {
                diffArr[count + n] = i;
            }
            else {
                int candidateLength = i - leftMostIndex;
                if (candidateLength > maxLength) {
                    maxLength = candidateLength;
                    maxLeftIndex = leftMostIndex + 1;
                    maxRightIndex = i;
                }
            }
        }
        System.out.println(String.format("a[%d]..a[%d]", maxLeftIndex, maxRightIndex));
    }

    public static void main(String[] args)
    {
        printMaxSubarray(new int[]{1, 0, 0});
        printMaxSubarray(new int[]{1, 0, 1});
        printMaxSubarray(new int[]{1, 0, 0, 0, 0, 1, 1});
        printMaxSubarray(new int[]{1, 1, 0, 0, 0, 0, 1});
        printMaxSubarray(new int[]{ 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0 });
    }

}
