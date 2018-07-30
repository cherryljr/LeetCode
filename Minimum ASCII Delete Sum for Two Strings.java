/*
Given two strings s1, s2, find the lowest ASCII sum of deleted characters to make two strings equal.

Example 1:
Input: s1 = "sea", s2 = "eat"
Output: 231
Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
Deleting "t" from "eat" adds 116 to the sum.
At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.

Example 2:
Input: s1 = "delete", s2 = "leet"
Output: 403
Explanation: Deleting "dee" from "delete" to turn the string into "let",
adds 100[d]+101[e]+101[e] to the sum.  Deleting "e" from "leet" adds 101[e] to the sum.
At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.

Note:
0 < s1.length, s2.length <= 1000.
All elements of each string will have an ASCII value in [97, 122].
 */

/**
 * Approach: Sequence DP
 * 这道题目属于 Edit Distance 的弱化版本。
 * 状态定义 dp[i][j] : 字符串A的前i个 和 字符串B的前j个 将他们delete成相同的最小代价
 * 因此当 s1[i] == s2[j] 时，无需付出删除的代价；
 * 如果不同的话，那么在 删除s1[i] 和 删除s2[j] 这两个方案中选择代价最小的方案。
 * 最后答案为 dp[s1.length()][s2.length()]
 *
 * 时间复杂度：O(MN)
 * 空间复杂度：O(MN)
 *
 * 类似问题：
 * Edit Distance:
 *  https://github.com/cherryljr/LintCode/blob/master/Edit%20Distance.java
 * Distinct Subsequences:
 *  
 */
class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] += dp[0][i - 1] + s2.charAt(i - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1),    // delete s1[i]
                            dp[i][j - 1] + s2.charAt(j - 1));               // delete s2[j]
                }
            }
        }
        return dp[m][n];
    }
}