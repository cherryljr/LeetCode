/*
You have d dice, and each die has f faces numbered 1, 2, ..., f.
Return the number of possible ways (out of fd total ways) modulo 10^9 + 7 to roll the dice 
so the sum of the face up numbers equals target.

Example 1:
Input: d = 1, f = 6, target = 3
Output: 1
Explanation: 
You throw one die with 6 faces.  There is only one way to get a sum of 3.

Example 2:
Input: d = 2, f = 6, target = 7
Output: 6
Explanation: 
You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
1+6, 2+5, 3+4, 4+3, 5+2, 6+1.

Example 3:
Input: d = 2, f = 5, target = 10
Output: 1
Explanation: 
You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.

Example 4:
Input: d = 1, f = 2, target = 3
Output: 0
Explanation: 
You throw one die with 2 faces.  There is no way to get a sum of 3.

Example 5:
Input: d = 30, f = 30, target = 500
Output: 222616187
Explanation: 
The answer must be returned modulo 10^9 + 7.

Constraints:
    1. 1 <= d, f <= 30
    2. 1 <= target <= 1000
 */

/**
 * Approach 1: DFS + Memory Search
 * 使用记忆化搜索即可轻松解决，如果把 Map 化成 数组 来存储状态的话速度会快上不少。
 * 
 * 时间复杂度：O(d * f * target)
 * 空间复杂度：O(d * target)
 */
class Solution {
    private static final int MOD = 1000000007;
    private Map<String, Integer> mem = new HashMap<>();

    public int numRollsToTarget(int d, int f, int target) {
        if (d == 0 && target == 0) {
            return 1;
        }
        if (d <= 0 || target <= 0) {
            return 0;
        }
        String key = d + "_" + target;
        if (mem.containsKey(key)) {
            return mem.get(key);
        }

        int ans = 0;
        for (int i = 1; i <= f && i <= target; i++) {
            ans = (ans + numRollsToTarget(d - 1, f, target - i)) % MOD;
        }
        mem.put(key, ans);
        return ans;
    }
}

/**
 * Approach 2: DP
 * 比较明显的一道 DP 问题，有f个面的d个骰子的组合总共有 d^f 个。
 * 如果采用 dfs 一一进行枚举的话，肯定会爆掉的。
 * 但是我们可以发现这是一个无后效性问题，因此可以采用 DP 来解决。
 * DP 的思路也非常简单直接，跟背包问题一样，装就行了...
 * 同时在这过程中，我们可以根据 j 的大小进行适当的剪枝。
 *
 * 时间复杂度：O(d * f * target)
 * 空间复杂度：O(d * target)
 */
class Solution {
    public int numRollsToTarget(int d, int f, int target) {
        int[][] dp = new int[d + 1][target + 1];
        dp[0][0] = 1;

        int MOD = 1000000007;
        // How many possibility can i dices sum up to j;
        for (int i = 1; i <= d; i++) {
            for (int j = 1; j <= target; j++) {
                // If j is larger than largest possible sum of i dices, there is no possible ways
                if (j > i * f) break;
                // k mustn't be larger than f and j
                for (int k = 1; k <= f && k <= j; k++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
                }
            }
        }
        return dp[d][target];
    }
}