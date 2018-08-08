/*
There are G people in a gang, and a list of various crimes they could commit.
The i-th crime generates a profit[i] and requires group[i] gang members to participate.
If a gang member participates in one crime, that member can't participate in another crime.
Let's call a profitable scheme any subset of these crimes that generates at least P profit,
and the total number of gang members participating in that subset of crimes is at most G.

How many schemes can be chosen?  Since the answer may be very large, return it modulo 10^9 + 7.

Example 1:
Input: G = 5, P = 3, group = [2,2], profit = [2,3]
Output: 2
Explanation:
To make a profit of at least 3, the gang could either commit crimes 0 and 1, or just crime 1.
In total, there are 2 schemes.

Example 2:
Input: G = 10, P = 5, group = [2,3,5], profit = [6,7,8]
Output: 7
Explanation:
To make a profit of at least 5, the gang could commit any crimes, as long as they commit one.
There are 7 possible schemes: (0), (1), (2), (0,1), (0,2), (1,2), and (0,1,2).

Note:
1 <= G <= 100
0 <= P <= 100
1 <= group[i] <= 100
0 <= profit[i] <= 100
1 <= group.length = profit.length <= 100
 */

/**
 * Approach 1: DP (01 Backpack)
 * 这道题目属于 01背包问题 的变形题。
 * 难度在于由 二维空间(2个限制条件) 上升到了 三维空间(3个限制条件)。
 * 但是实质上就是一个普通的 01背包问题 罢了。
 * 因此不必惧怕，只需要分析好状态信息，然后推出转移方程即可。
 *
 *  状态：dp[i][j][k] 表示前 i 个任务，选取 j 个人员，获得 k 的收益 的方法数。
 *  转移方程：因为对于第 ith 项任务，我们有做和不做两种选择。
 *  因此如果 不做当前任务 的话，方法个数为 dp[i-1][j][k]
 *  如果 做当前任务 的话，方法个数 dp[i-1][j-group[i]][k - profit[i]]
 *  因此总方法数为两项之和。
 *  注意：
 *      1. 在处理 j-group[i] 时需要保证人员数要足够完成第 ith 项任务
 *      2. 在处理 k-profit[i] 时，有可能出现负数情况，这是因为当前的 profit 很可能是 0 或者较小的一个数。
 *      但是当前的任务是可以完成的（可以看作是干一票大的），因此需要与 0 进行取 max 的操作，防止出现负数下标。
 *  最后结果为：不管使用多少人，达到目标获利的方法个数，即 sum(dp[N][i][P]) 0 <= i <= G
 *
 * 时间复杂度：O(NGP) N表示任务个数，G表示总人数，P表示需要达到的目标利润
 * 空间复杂度：O(NGP)
 */
class Solution {
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        final int MOD = 1000000007;
        int tasks = group.length;
        int[][][] dp = new int[tasks + 1][G + 1][P + 1];
        dp[0][0][0] = 1;

        for (int i = 1; i <= tasks; i++) {
            int g = group[i - 1];   // 当前任务需要的任务
            int p = profit[i - 1];  // 当前任务可以获得的利润
            for (int j = 0; j <= G; j++) {
                for (int k = 0; k <= P; k++) {
                    // 不完成当前(第 ith 项)任务
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (j >= g) {
                        // 完成当前(第 ith 项)任务
                        dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j - g][Math.max(0, k - p)]) % MOD;
                    }
                }
            }
        }

        long rst = 0L;
        for (int i = 0; i <= G; i++) {
            rst = (rst + dp[tasks][i][P]) % MOD;
        }
        return (int)rst;
    }
}

/**
 * Approach 2: 01 Backpack (Space Optimized)
 * 与 01背包问题 相同，本题也可以对空间复杂度进行优化。
 * 当前状态仅依赖于 i-1 个任务的状态。
 * 优化的做法与 01背包 的优化做法如出一辙。
 * 不管是从理解的角度看或者是从空间矩阵的依赖递推过程看都可以。
 * （推荐直接从三维空间的递推过程出发直接优化即可~）
 *
 * 时间复杂度：O(NGP) N表示任务个数，G表示总人数，P表示需要达到的目标利润
 * 空间复杂度：O(GP)
 */
class Solution {
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        final int MOD = 1000000007;
        int tasks = group.length;
        int[][] dp = new int[G + 1][P + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= tasks; i++) {
            int g = group[i - 1];
            int p = profit[i - 1];
            // 人数只有大于 g 的时候才能完成当前任务，因此小于 g 时没有必要进行 loop 中的操作
            // 即这里 dp[j][k] 代表的就是上一层的状态，其值就是 dp[i-1][j][k]
            for (int j = G; j >= g; j--) {
                for (int k = P; k >= 0; k--) {
                    dp[j][k] = (dp[j][k] + dp[j - g][Math.max(0, k - p)]) % MOD;
                }
            }
        }

        long rst = 0L;
        for (int i = 0; i <= G; i++) {
            rst = (rst + dp[i][P]) % MOD;
        }
        return (int)rst;
    }
}