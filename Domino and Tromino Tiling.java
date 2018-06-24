/*
We have two types of tiles: a 2x1 domino shape, and an "L" tromino shape. These shapes may be rotated.
    XX  <- domino
    XX  <- "L" tromino
    X
Given N, how many ways are there to tile a 2 x N board? Return your answer modulo 10^9 + 7.

(In a tiling, every square must be covered by a tile. Two tilings are different
if and only if there are two 4-directionally adjacent cells on the board
such that exactly one of the tilings has both squares occupied by a tile.)

Example:
Input: 3
Output: 5
Explanation:
The five different ways are listed below, different letters indicates different tiles:
    XYZ XXZ XYY XXY XYY
    XYZ YYZ XZZ XYY XXY

Note:
N  will be in range [1, 1000].
 */

/**
 * Approach: Sequence DP
 * 这类题刚刚开始看并没有什么想法，需要写出初始的几个状态，然后试着递推看看。
 * 通常就能发现规律，然后根据这个写出 递推方程（动态规划方程）即可。
 *
 * dp[i][0]:表示到 ith 列，全部完全铺满的方案个数
 * dp[i][1]:表示到 ith 列，剩余 左上角/右小角 一个格子未铺满的方案个数。
 * 则当前状态依赖于：
 *  dp[i-2][0]: 还差 两列 完全铺满，则再加两个横条便能够得到当前状态;
 *  dp[i-1][0]: 还差 一列 完全铺满，则再加一个竖条便能够得到当前状态；
 *  2 * dp[i-1][1]: 还差一个 L 完全铺满（缺的位置可以是 右上角 或者是 右下角），
 *  则再加一个 L 便可以得到当前状态。
 *
 * 类似的问题还有：
 * Dyeing Problem:
 *  https://github.com/cherryljr/LintCode/blob/master/Dyeing%20Problem.java
 * Find the Derangement of An Array:
 *  https://github.com/cherryljr/LintCode/blob/master/Find%20the%20Derangement%20of%20An%20Array.java
 *
 * 参考资料：https://www.youtube.com/watch?v=S-fUTfqrdq8
 */
class Solution {
    private static final int MOD = 1000000007;

    public int numTilings(int N) {
        long[][] dp = new long[N + 1][2];
        dp[0][0] = 1;
        dp[1][0] = 1;
        for (int i = 2; i <= N; i++) {
        	// 因为这边使用了 long 所以在相加时不担心溢出，如果是 int 的话，运算过程中也需要取模才行
            dp[i][0] = (dp[i - 2][0] + dp[i - 1][0] + 2 * dp[i - 1][1]) % MOD;
            dp[i][1] = (dp[i - 2][0] + dp[i - 1][1]) % MOD;
        }
        return (int)dp[N][0];
    }
}