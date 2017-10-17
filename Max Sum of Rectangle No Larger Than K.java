The naive solution is brute-force, which is O((mn)^2). 
In order to be more efficient, I tried something similar to Kadane's algorithm. 
(A algorithm to solve question -- Maximum Subarray: https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java)
In fact, Kadane's algorithm couldn't be used in this question directly. It will use Prefix Sum.

There are two differences between this question and 最大矩阵和(in NowCoder):
1. This question is a matrix, it means that it's 2D array question. 
But in 2D matrix, we can sum up all values from row i to row j and create a 1D array to use 1D array solution.
Here's the easily understanding video link for the problem "find the max sum rectangle in 2D array":
https://www.youtube.com/watch?v=yCQN096CwWM
Maximum of Submatrix Blog:
http://blog.csdn.net/u013309870/article/details/70145481

2. Here we have upper bound restriction K. 
Once you are clear how to solve the above problem, 
the next step is to find the max sum no more than K in an array. 
This can be done within O(nlogn), and you can refer to this article:
http://blog.csdn.net/u010900754/article/details/60457594

Time Complexity is O[min(m,n)^2 * max(m,n) * log(max(m,n))]
Space Complexity is O(max(m, n)).

/*
Given a non-empty 2D matrix matrix and an integer k, 
find the max sum of a rectangle in the matrix such that its sum is no larger than k.

Example:
Given matrix = [
  [1,  0, 1], 
  [0, -2, 3]
]
k = 2

The answer is 2. 
Because the sum of rectangle [[0, 1], [-2, 3]] is 2 and 2 is the max number no larger than k (k = 2).

Note:
The rectangle inside the matrix must have an area > 0.
What if the number of rows is much larger than the number of columns?
*/

class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return -1;
        }
        
        int rst = Integer.MIN_VALUE;
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            int[] arr = new int[col];
            // sum up all values from row i to row j
            for (int j = i; j < row; j++) {
                // Traverse every column and sum up
                for (int c = 0; c < col; c++) {
                    arr[c] += matrix[j][c];
                }
                // use 1D array soluiton to find the maxSubarry which is no larger than k
                rst = Math.max(rst, maxSumNoLargerThan(arr, k));
            }
        }
        
        return rst;
    }
    
	public int maxSumNoLargerThan(int[] arr, int k) {
		int sum = 0;
        int max = Integer.MIN_VALUE;
		TreeSet<Integer> set = new TreeSet<>();
		
        set.add(0);
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
            // use TreeSet to binary search previous sum to get possible result 
			Integer min = set.ceiling(sum - k);
			if (min != null) {
                // because min is least element greater than or equal to sum-k
                // so sum-min must be smaller than or equal to k
                max = Math.max(max, sum - min);
            }
			set.add(sum);
		}
         
		return max;
	}
}
