/*
Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

For example, given the following matrix:
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Return 4.
*/

/**
 * Approach 1: Turn to histogram
 * We can solve this problem like Largest Rectangle in Histogram.
 * This method will cost O(mn) time and O(mn) space (O(n) space after optimized).
 * If you want to know how this method run clearly, you can move to Maximal Rectangle:
 * https://github.com/cherryljr/LeetCode/blob/master/Maximal%20Rectangle.java
 */
class Solution {
    /*
     * @param matrix: a matrix of '0' and '1'
     * @return: an integer
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] heights = new int[rows][cols];

        // calculate the height array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[i][j] = i == 0 ? 1 : heights[i - 1][j] + 1;
                } else {
                    heights[i][j] = 0;
                }
            }
        }

        // calculate the max square row by row
        int maxArea = 0;
        for (int i = 0; i < rows; i++) {
            maxArea = Math.max(maxArea, maxAreaInHist(heights[i]));
        }

        return maxArea;
    }

    public int maxAreaInHist(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;

        for (int i = 0; i <= heights.length; i++) {
            int curr = i == heights.length ? -1 : heights[i];
            while (!stack.isEmpty() && curr < heights[stack.peek()]) {
                int h = heights[stack.pop()];
                int w = stack.isEmpty() ? i : (i - stack.peek() - 1);
                int len = w > h ? h : w;	// get the width of square
                max = Math.max(max, len * len);
            }
            stack.push(i);
        }

        return max;
    }
}

/**
 * Approach 2: Matrix DP
 * Algorithm
 * We initialize another matrix (dp) with the same dimensions as the original one initialized with all 0’s.
 * dp(i,j) represents the side length of the maximum square
 * whose bottom right corner is the cell with index (i,j) in the original matrix.
 * Starting from index (0,0), for every 1 found in the original matrix,
 * we update the value of the current element as:
 *      dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
 *      (if you can't understand this expression, you can draw a draft. It will help you)
 * We also remember the size of the largest square found so far.
 * In this way, we traverse the original matrix once and find out the required maximum size.
 * This gives the side length of the square (say maxlen). The required result is the area maxlen^2.
 *
 * Complexity Analysis
 *  Time complexity : O(mn). Single pass.
 *  Space complexity : O(mn). Another matrix of same size is used for dp.
 * 
 * Reference:
 * https://leetcode.com/articles/maximal-square/
 */
class Solution {
    /*
     * @param matrix: a matrix of '0' and '1'
     * @return: an integer
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows + 1][cols + 1];
        int maxlen = 0;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    maxlen = Math.max(dp[i][j], maxlen);
                } else {
                    dp[i][j] = 0;	
                }
            }
        }

        return maxlen * maxlen;
    }
}

/**
 * Approach 3: Matrix DP (Optimized by Sliding Array)
 * In the previous approach for calculating dp of ith row we are using only the previous element and the (i−1)​th row.
 * Therefore, we don't need 2D dp matrix as 1D dp array will be sufficient for this.
 * So we just need to keep (i-1)th row and the previous element (the left one).
 * With the help of Sliding Array, we could save a lot of extra space by make a little change on Approach 2.
 * It's very easy to code and understand.
 * The Function could be optimized as:
 *      if matrix[i-1][j-1] == '1'
 *      dp[i%2][j] = Math.min(dp[(i-1)%2][j-1], Math.min(dp[(i-1)%2][j], dp[i%2][j-1])) + 1;
 *      else
 *      dp[i%2][j] = 0 (reset as 0)
 *
 * Complexity Analysis
 *  Time complexity : O(mn). Single pass.
 *  Space complexity : O(n). Another matrix of 2n size is used for dp.
 *  
 * Note: In fact, the O(2n) extra space could be optimized to O(n).
 * Because we can use a element "prev" to save the previous element instead of an array.
 * You can get it in Approach 4.
 */
class Solution {
    /*
     * @param matrix: a matrix of '0' and '1'
     * @return: an integer
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[2][cols + 1];
        int maxlen = 0;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i & 1][j] = Math.min(dp[(i-1) & 1][j-1],
                            Math.min(dp[(i-1) & 1][j], dp[i & 1][j-1])) + 1;
                    maxlen = Math.max(dp[i & 1][j], maxlen);
                } else {
                    dp[i & 1][j] = 0;
                }
            }
        }

        return maxlen * maxlen;
    }
}

/**
 * Approach 4: Matrix DP (Optimized)
 * Algorithm
 * In the previous approach for calculating dp of ith row we are using only the previous element and the (i−1)​th row.
 * Therefore, we don't need 2D dp matrix as 1D dp array will be sufficient for this.
 *
 * Initially the dp array contains all 0's.
 * As we scan the elements of the original matrix across a row, we keep on updating the dp array as per the equation
 *  dp[j] = Math.min(dp[j-1], dp[j], prev) where prev refers to the old dp[j−1].
 * For every row, we repeat the same process and update in the same dp array.
 *
 * Complexity Analysis
 *  Time complexity  : O(mn). Single pass.
 *  Space complexity : O(n).  Another array which stores elements in a row is used for dp.
 *
 * If you still can't understand it clearly. See the picture here:
 * https://leetcode.com/articles/maximal-square/
 */
class Solution {
    /*
     * @param matrix: a matrix of '0' and '1'
     * @return: an integer
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] dp = new int[cols + 1];
        int maxlen = 0;
        int prev = 0;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(dp[j], Math.min(dp[j - 1], prev)) + 1;
                    maxlen = Math.max(dp[j], maxlen);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }

        return maxlen * maxlen;
    }
}
