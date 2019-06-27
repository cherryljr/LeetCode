/*
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
 */

/**
 * Approach 1: DP (Similar to LCS)
 * 这个问题可以从数据规模推测出答案的时间复杂度在 O(n^2) 级别。
 * 对此很容易就能够想到与此题类似的 LCS 问题，采用 DP 做法对此进行解决。
 *
 * 题意要求一个字符串，使得 str1 和 str2 均为它的 SubSequence。
 * 对此我们需要将 str1 和 str2 的公共子序列部分进行合并，然后添加上其各自所具备的剩余字符串，就能够得到答案。
 * 因为要求得到的字符串长度最小，这就是的要求 公共子序列部分 最大，即求解 最长公共子序列问题（LCS）。
 * 最后答案Supersequence的长度为：len(str1) + len(str2) - len(LCS)
 *
 * 设 dp[i][j] 表示 str1[0~i] 和 str2[0~j] 部分的最长公共子序列。
 * 则最终的 LCS 结果就是：dp[str1.length()][str2.length()]
 * 然后遍历 str1 和 str2，如果当前位置上的字符与 LCS 当前位置上的字符相等，则将其添加到结果中，两个字符串中的指针向后移动一位；
 * 否则将字符串中各自所独有的字符添加到结果中（同样对应的指针向后移动一位），直到两个字符串中的字符相等。
 * 最后将 str1 和 str2 中剩余的元素添加到结果中，就是我们需要的答案。
 *
 * 时间复杂度：O(m*n*k)
 * 空间复杂度：O(m*n*k)
 *
 * Reference：
 *  https://github.com/cherryljr/LintCode/blob/master/Longest%20Common%20Subsequence.java
 */
class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        String lcs = longestCommonSubSeq(str1, str2);
        StringBuilder ans = new StringBuilder();
        int p1 = 0, p2 = 0;
        for (int i = 0; i < lcs.length(); i++) {
            while (p1 < str1.length() && str1.charAt(p1) != lcs.charAt(i)) {
                ans.append(str1.charAt(p1++));
            }
            while (p2 < str2.length() && str2.charAt(p2) != lcs.charAt(i)) {
                ans.append(str2.charAt(p2++));
            }
            ans.append(lcs.charAt(i));
            p1++;
            p2++;
        }
        ans.append(str1.substring(p1)).append(str2.substring(p2));
        return ans.toString();
    }

    private String longestCommonSubSeq(String str1, String str2) {
        String[][] dp = new String[str1.length() + 1][str2.length() + 1];
        for (String[] strs : dp) {
            Arrays.fill(strs, "");
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + str1.charAt(i - 1);
                } else {
                    dp[i][j] = dp[i - 1][j].length() > dp[i][j - 1].length() ? dp[i - 1][j] : dp[i][j - 1];
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }
}

/**
 * Approach 2: LCS (Using int[][] instead of String[][])
 * 在 Approach 1 中，因为我们需要知道 LCS，对此我们将具体的 LCS 记录在了 dp[i][j] 中。
 * 但是在 Java 中对字符串的处理时间复杂度是线性的，因此我们考虑能否跟求LCS一样，只当前存储最大的长度。
 * 毫无疑问答案是可以的，通过对 DP 递推过程的研究，我们可以逆序遍历这两个字符串：
 *  当 str1[i] == str2[j] 时，当前字符为 LCS 中的一员，将其添加到结果中后，两个字符串中的指针都需要向前移动一位
 *  当 str1[i] != str2[j] 时，则可以看 dp[i][j] 的值是来自于 dp[i-1][j] 还是 dp[i][j-1] 从而确定当前应该选择的字符。
 *  如果来自于 dp[i-1][j] 根据 dp[][] 所代表的含义，str1[i] 不是 LCS 的一员，因此我们应该将 str1[i] 加入到结果中。
 *  对于 dp[i][j-1] 分析同理可得。当 dp[i-1][j] == dp[i][j-1] 时，可以选择二者中的任意一个（都是合法的结果，取一种即可）。
 * 最后再把剩余的字符添加进去即可。答案就是 ans.reverse()
 *
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 *
 * Reference:
 *  https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-1092-shortest-common-supersequence/
 */
class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        int[][] dp = longestCommonSubSeq(str1, str2);
        StringBuilder ans = new StringBuilder();
        int p1 = str1.length(), p2 = str2.length();
        while (p1 > 0 || p2 > 0) {
            if (p1 == 0) {
                ans.append(str2.charAt(--p2));
            } else if (p2 == 0) {
                ans.append(str1.charAt(--p1));
            } else if (str1.charAt(p1 - 1) == str2.charAt(p2 - 1)) {
                ans.append(str1.charAt(--p1));
                --p2;
            } else {
                ans.append(dp[p1][p2] == dp[p1 - 1][p2] ? str1.charAt(--p1) : str2.charAt(--p2));
            }
        }
        return ans.reverse().toString();
    }

    private int[][] longestCommonSubSeq(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + str1.charAt(i - 1);
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp;
    }
}
}