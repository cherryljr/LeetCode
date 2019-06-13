/*
Given a matrix, and a target, return the number of non-empty submatrices that sum to target.
A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.
Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different
if they have some coordinate that is different: for example, if x1 != x1'.

Example 1:
Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
Output: 4
Explanation: The four 1x1 submatrices that only contain 0.

Example 2:
Input: matrix = [[1,-1],[-1,1]], target = 0
Output: 5
Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.

Note:
    1. 1 <= matrix.length <= 300
    2. 1 <= matrix[0].length <= 300
    3. -1000 <= matrix[i] <= 1000
    4. -10^8 <= target <= 10^8
 */

/**
 * Approach: Turn to Subarray Sum Equals Target
 * 这道题目实际上是 Maximum Submatrix 和 Subarray Sum Equals K 这两道问题的整合。
 * 首先利用 preSum 计算出各个子矩阵之和，preSum[i][j] 代表左上角为 matrix[0][0] 右下角为 matrix[i][j] 的子矩阵之和。
 * 然后类似 Maximum Submatrix 将这道问题转成求 Subarray Sum Equals Target 问题。
 * 而对于这个问题，可以采用 PreSum + HashMap 的方法进行解决（参考Subarray Sum Equals K）
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2)
 *
 * References:
 * Maximum Submatrix:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Submatrix.java
 * Subarray Sum Equals K:
 *  https://github.com/cherryljr/LeetCode/blob/master/Subarray%20Sum%20Equals%20K.java
 * Max Sum of Rectangle No Larger Than K:
 *  https://github.com/cherryljr/LeetCode/blob/master/Max%20Sum%20of%20Rectangle%20No%20Larger%20Than%20K.java
 */
class Solution {
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] preSum = new int[rows + 1][cols + 1];
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        int count = 0;
        for (int top = 0; top < rows; top++) {
            for (int bottom = top + 1; bottom <= rows; bottom++) {
                Map<Integer, Integer> map = new HashMap<>();
                // Initialize the value of map
                map.put(0, 1);
                for (int k = 1; k <= cols; k++) {
                    int sum = preSum[bottom][k] - preSum[top][k];
                    count += map.getOrDefault(sum - target, 0);
                    map.put(sum, map.getOrDefault(sum, 0) + 1);
                }
            }
        }
        return count;
    }
}
