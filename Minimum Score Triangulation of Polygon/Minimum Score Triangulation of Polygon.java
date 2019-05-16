/*
Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise order.
Suppose you triangulate the polygon into N-2 triangles.
For each triangle, the value of that triangle is the product of the labels of the vertices,
and the total score of the triangulation is the sum of these values over all N-2 triangles in the triangulation.

Return the smallest possible total score that you can achieve with some triangulation of the polygon.

Example 1:
Input: [1,2,3]
Output: 6
Explanation: The polygon is already triangulated, and the score of the only triangle is 6.

Example 2:
Input: [3,7,4,5]
Output: 144
Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The minimum score is 144.

Example 3:
Input: [1,3,1,4,1,5]
Output: 13
Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.

Note:
    1. 3 <= A.length <= 50
    2. 1 <= A[i] <= 100
 */

/**
 * Approach: Interval DP
 * 这道问题与 Burst Balloons 这个问题很像。属于 区间DP 问题。
 * 题目要求将一个拥有 N 条边的多边形切割成 N-2 个三角形，使得各个三角形三个 points 之积的总和最小。
 * 看到题目的数据范围为 50，因此想到解法的时间复杂度应该不大于 O(n^4)，
 * 并且本题中对于某一个特定的范围，最终的结果是确定的。
 * 这就使得我们往 DP 的方向取考虑，因为需要对结果进行分段式的区间分析，所以自然想到了使用 Interval DP 来解决。
 *  dp[i][j] 表示由 i~j 这些点所组成的多边形被分割成三角形后最小的值。
 * 因此状态转移方程为（k是 i~j 中的一个分割点）：
 *  dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j] + A[i] * A[k] * A[j])  (i < k < j)
 *  对于这个过程如果不清楚的话可以参考下方给出的链接，里面有很详细的图文解析。
 * 最终结果为：dp[0][A.length - 1]
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2)
 *
 * References:
 *  https://leetcode.com/problems/minimum-score-triangulation-of-polygon/discuss/286753/C%2B%2B-with-picture
 *  https://github.com/cherryljr/LeetCode/blob/master/Burst%20Balloons.java
 */
class Solution {
    public int minScoreTriangulation(int[] A) {
        int n = A.length;
        int[][] dp = new int[n][n];
        for (int l = 3; l <= n; l++) {
            for (int start = 0; start + l <= n; start++) {
                int end = start + l - 1;
                dp[start][end] = Integer.MAX_VALUE;
                for (int pivot = start + 1; pivot < end; pivot++) {
                    dp[start][end] = Math.min(dp[start][end], dp[start][pivot] + dp[pivot][end] + A[start] * A[pivot] * A[end]);
                }
            }
        }
        return dp[0][n - 1];
    }
}