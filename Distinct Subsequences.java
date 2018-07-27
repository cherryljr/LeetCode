/*
Given a string S and a string T, count the number of distinct subsequences of S which equals T.
A subsequence of a string is a new string which is formed from the original string by deleting some (can be none)
of the characters without disturbing the relative positions of the remaining characters.
(ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

Example 1:
Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^

Example 2:
Input: S = "babgbag", T = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^
 */

/**
 * Approach: Sequence DP
 * 字符串问题的典型DP。
 * 状态定义:
 *  dp[i][j]: S的前i个字符中有多少个 T的前j个字符 的 SubSequences
 * 状态转移方程：
 *  当 S[i] == T[j] 时
 *  我们可以取 S[i] 也可以不取 S[i]
 *      dp[i][j] = dp[i - 1][j - 1](取) + dp[i - 1][j](不取);
 *  当 S[i] != T[j] 时
 *  我们只能不取 S[i]，即只使用 s的前i-1个元素(不使用当前元素s[i]) 去构成 t的前j个元素
 *      dp[i][j] = dp[i - 1][j];
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=mPqqXh8XvWY
 */
class Solution {
    public int numDistinct(String s, String t) {
        int sLen = s.length(), tLen = t.length();
        int[][] dp = new int[sLen + 1][tLen + 1];
        
        // Initialize
        // 当 t 为空时，s只有一个方案（什么都不取）
        for (int i = 0; i <= sLen; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= sLen; i++) {
            for (int j = 1; j <= tLen; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // 取s[i] 或者 不取s[i] 均可（因此将其方法总数相加）
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    // 不相等的情况下，只能不取s[i]，即使用 s的前i-1个元素 去构成 t的前j个元素
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[sLen][tLen];
    }
}