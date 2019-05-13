/*
We write the integers of A and B (in the order they are given) on two separate horizontal lines.
Now, we may draw a straight line connecting two numbers A[i] and B[j] as long as A[i] == B[j],
and the line we draw does not intersect any other connecting (non-horizontal) line.

Return the maximum number of connecting lines we can draw in this way.

Example 1:
https://leetcode.com/problems/uncrossed-lines/
Input: A = [1,4,2], B = [1,2,4]
Output: 2
Explanation: We can draw 2 uncrossed lines as in the diagram.
We cannot draw 3 uncrossed lines, because the line from A[1]=4 to B[2]=4 will intersect the line from A[2]=2 to B[1]=2.

Example 2:
Input: A = [2,5,1,2,5], B = [10,5,2,1,5,2]
Output: 3

Example 3:
Input: A = [1,3,7,1,7,5], B = [1,9,2,5,1]
Output: 2

Note:
    1. 1 <= A.length <= 500
    2. 1 <= B.length <= 500
    3. 1 <= A[i], B[i] <= 2000
 */

/**
 * Approach: Sequence DP (Same as Longest Common SubSequence)
 * 这道问题其实是 LCS 问题的一个包装。
 * 题目给了两个数组 A[], B[]，要求我们将两个数组中相等的元素相连，并且要求连线不得相交。
 * 我们可以发现 A[], B[] 中相连的元素必定是 A[], B[] 的子序列。（这样就能够保证连线不会相交）
 * 因此问题到这里就转换成了求数组 A[], B[] 的最长公共子序列。
 * 典型的 DP 问题了...这里就不再赘述，不清楚的可以参考下方给出的链接。
 *
 * 时间复杂度：O(M * N)
 * 空间复杂度：O(M * N)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Longest%20Common%20Subsequence.java
 */
class Solution {
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[A.length][B.length];
    }
}