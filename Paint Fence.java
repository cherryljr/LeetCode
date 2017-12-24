/*
Description
There is a fence with n posts, each post can be painted with one of the k colors.
You have to paint all the posts such that no more than two adjacent fence posts have the same color.
Return the total number of ways you can paint the fence.

Notice
n and k are non-negative integers.

Example
Given n=3, k=2 return 6

      post 1,   post 2, post 3
way1    0         0       1 
way2    0         1       0
way3    0         1       1
way4    1         0       0
way5    1         0       1
way6    1         1       0

Tags 
Dynamic Programming
*/

/**
 * Approach 1: DP
 * 这种给定一个规则，计算有多少种结果的题目一般都是动态规划，因为我们可以从这个规则中得到递推式。
 * 根据题意，不能有超过连续两根柱子是一个颜色，
 * 也就意味着第三根柱子要么根第一个柱子不是一个颜色，要么跟第二根柱子不是一个颜色。
 * 首先讨论初始化情况（Initialize）：
 * 所有柱子中第一根涂色的方式有k中，第二根涂色的方式则是k*k，因为第二根柱子可以和第一根一样。
 *
 * Function:
 * 如果不是同一个颜色，计算可能性的时候就要去掉之前的颜色，也就是 k-1 种可能性。
 * 假设dp[1]是第一根柱子及之前涂色的可能性数量，dp[2]是第二根柱子及之前涂色的可能性数量，
 * 则dp[3] = (dp[1] + dp[2]) * (k - 1)。
 */
public class Solution {
    /*
     * @param n: non-negative integer, n posts
     * @param k: non-negative integer, k colors
     * @return: an integer, the total number of ways
     */
    public int numWays(int n, int k) {
        int[] dp = {0, k, k * k, 0};
        if (n <= 2) {
            return dp[n];
        }

        for (int i = 2; i < n; i++) {
            dp[3] = (dp[1] + dp[2]) * (k - 1);
            dp[1] = dp[2];
            dp[2] = dp[3];
        }
        return dp[3];
    }
}

/**
 * Approach 2: DP (Optimized)
 * 首先来我们分析一下，如果n=0的话，说明没有需要刷的部分，直接返回0即可;
 * 如果n为1的话，那么有几种颜色，就有几种刷法，所以应该返回k;
 * 其余情况下：
 * 我们可以分两种情况来统计，一种是相邻部分没有相同的，一种相同部分有相同的颜色。
 * 那么对于没有相同的，对于第一个格子，我们有 k 种填法，对于下一个相邻的格子，由于不能相同，所以我们只有 k-1 种填法。
 * 而有相同部分颜色的刷法和上一个格子的不同颜色刷法相同，因为我们下一格的颜色和之前那个格子颜色刷成一样的即可。
 * 最后总共的刷法就是把不同和相同两个刷法加起来即可。
 * 
 * Get more details here:
 * https://discuss.leetcode.com/topic/23426/o-n-time-java-solution-o-1-space
 */
public class Solution {
    /*
     * @param n: non-negative integer, n posts
     * @param k: non-negative integer, k colors
     * @return: an integer, the total number of ways
     */
    public int numWays(int n, int k) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return k;
        }

        int diffColorCounts = k * (k - 1);
        int sameColorCounts = k;
        for (int i = 2; i < n; i++) {
            int temp = diffColorCounts;
            diffColorCounts = (diffColorCounts + sameColorCounts) * (k - 1);
            sameColorCounts = temp;
        }
        return diffColorCounts + sameColorCounts;
    }
}