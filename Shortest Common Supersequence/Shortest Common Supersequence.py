'''
Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.
If multiple answers exist, you may return any of them.

(A string S is a subsequence of string T if deleting some number of characters from T
(possibly 0, and the characters are chosen anywhere from T) results in the string S.)

Example 1:
Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation:
str1 = "abac" is a substring of "cabac" because we can delete the first "c".
str2 = "cab" is a substring of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.

Note:
    1. 1 <= str1.length, str2.length <= 1000
    2. str1 and str2 consist of lowercase English letters.
'''

# Approach: DP (Similar to LCS)
# 时间复杂度：O(m*n)
# 空间复杂度：O(m*n)
# 解法详解参考同名 java 文件
class Solution:
    def shortestCommonSupersequence(self, str1: str, str2: str) -> str:
        m, n = len(str1), len(str2)
        def longestCommonSubSeq(str1: str, str2: str) -> str:
            dp = [[""] * (n + 1) for _ in range(m + 1)]
            for i in range(1, m + 1):
                for j in range(1, n + 1):
                    if str1[i - 1] == str2[j - 1]:
                        dp[i][j] = dp[i - 1][j - 1] + str1[i - 1]
                    else:
                        dp[i][j] = max(dp[i - 1][j], dp[i][j - 1], key = len)
            return dp[m][n]
        
        ans, p1, p2 = "", 0, 0
        for c in longestCommonSubSeq(str1, str2):
            while p1 < m and str1[p1] != c:
                ans += str1[p1]
                p1 += 1
            while p2 < n and str2[p2] != c:
                ans += str2[p2]
                p2 += 1
            ans += c
            p1, p2 = p1 + 1, p2 + 1
        return ans + str1[p1:] + str2[p2:]