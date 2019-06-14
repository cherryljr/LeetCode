/*
Given a non-empty 2D matrix matrix and an integer k,
find the max sum of a rectangle in the matrix such that its sum is no larger than k.

Example:
Input: matrix = [[1,0,1],[0,-2,3]], k = 2
Output: 2
Explanation:
Because the sum of rectangle [[0, 1], [-2, 3]] is 2,
and 2 is the max number no larger than k (k = 2).

Note:
    1. The rectangle inside the matrix must have an area > 0.
    2. What if the number of rows is much larger than the number of columns?
*/

/**
 * Approach: Maximum Submatrix + TreeSet
 * The naive solution is brute-force, which is O((mn)^2).
 * In order to be more efficient, I tried something similar to Kadane's algorithm.
 * A algorithm to solve question -- Maximum Subarray:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java
 * In fact, Kadane's algorithm couldn't be used in this question directly. It will use Prefix Sum.
 * There are two differences between this question and 最大矩阵和(in NowCoder):
 *
 * 1. This question is a matrix, it means that it's 2D array question.
 * But in 2D matrix, we can sum up all values from row i to row j and create a 1D array to use 1D array solution.
 * Here's the easily understanding video link for the problem "find the max sum rectangle in 2D array":
 *  https://www.youtube.com/watch?v=yCQN096CwWM
 * Maximum of Submatrix in LintCode:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Submatrix.java
 *
 * 2. Here we have upper bound restriction K.
 * Once you are clear how to solve the above problem,
 * the next step is to find the max sum no more than K in an array.
 * This can be done within O(nlogn), and you can refer to this article:
 *  http://blog.csdn.net/u010900754/article/details/60457594
 *
 * Time Complexity is O(m^2 * nlogn)
 * Space Complexity is O(m*n).
 */
class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] preSum = new int[rows + 1][cols + 1];
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        int ans = Integer.MIN_VALUE;
        for (int top = 0; top < rows; top++) {
            for (int down = top + 1; down <= rows; down++) {
                // use 1D array soluiton to find the maxSubarry which is no larger than k
                ans = Math.max(ans, maxSumNoLargerThanK(preSum, top, down, k));
            }
        }
        return ans;
    }

    private int maxSumNoLargerThanK(int[][] preSum, int top, int down, int k) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(0);
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < preSum[top].length; i++) {
            int sum = preSum[down][i] - preSum[top][i];
            // use TreeSet previous sum to get possible result (binary search, logn)
            Integer min = treeSet.ceiling(sum - k);
            if (min != null) {
                // because min is least element greater than or equal to sum-k
                // so sum-min must be smaller than or equal to k
                max = Math.max(max, sum - min);
            }
            treeSet.add(sum);
        }
        return max;
    }
}