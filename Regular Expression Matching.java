/*
Implement regular expression matching with support for '.' and '*'.
'.' Matches any single character.
'*' Matches zero or more of the preceding element.

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "a*") → true
isMatch("aa", ".*") → true
isMatch("ab", ".*") → true
isMatch("aab", "c*a*b") → true
 */

// 参考资料：https://leetcode.com/articles/regular-expression-matching/

/**
 * Approach 1: Recursion
 * 递归解法，主要是针对 '*' 的情况进行分析。
 * 对于 '.' 而言，其就是一个万能字符，所以还是相对较好处理的。
 * 代码还是很简短的，主要过程参考注释即可。
 * 注：请务必先理解透彻该递归尝试的过程！然后再看 动态规划 的解法。
 */
class Solution {
    public boolean isMatch(String s, String p) {
        return process(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    // 检查 s[i...s.length-1] 与 p[j...p.length-1] 是否匹配
    private boolean process(char[] s, char[] p, int i, int j) {
        // 当 p 中已经没有剩余可以匹配的字符时，查看 s 中是否还有没被匹配的字符
        if (j == p.length) {
            return i == s.length;
        }

        // 看 s 和 p 的开头字符，即 ith 和 jth 位置上的字符 是否匹配
        boolean firstMatch = (i < s.length && (s[i] == p[j] || p[j] == '.'));
        /*
        当 j 不是最后一个字符，并且字符串 p 的 j+1 位置上的字符是 '*'
        那么有两种处理方法：
            1.'*'等于0，因此 j 向后移动两个位置，i保持不变。
            此时 s的ith 和 p的jth 不管是否匹配都可以这么处理(即 firstMatch 不管是什么都可以)。
            2.'*'不为0，此时firstMatch必须为true,否则根本无法匹配。
            但是它要与 s 中多少个字符进行匹配呢？我们并不清楚，
            对此之只能枚举每一种方案，看其中是否有一种能够成立
        注：j 位置上的字符不可能为 '*',因为 '*' 必须与前面一个字符搭配使用，单独是无法使用的，
        每当遇到 j+1 为'*' 的话，要么不跳要么我们就会一次跳过两个字符。（连续出现两个 '*' 是非法的）
         */
        if (j + 1 < p.length && p[j + 1] == '*') {
            return process(s, p, i, j + 2) || (firstMatch && process(s, p, i + 1, j));
        }
        /*
        当 j 是最后一个字符，或者p的 j+1 位置上不为 '*'
        那么就意味着要想 s 和 p 匹配的话 i, j 位置上的字母必须能够匹配。
        然后继续递归检查后面的子串是否能够匹配。
         */
        else {
            return firstMatch && process(s, p, i + 1, j + 1);
        }
    }
}

/**
 * Approach 2: DP
 * 本题直接写出 DP 解法还是较为困难的，庆幸的是我们已经写出了 递归版本 的解法。
 * 因此我们可以参考 递归版本 将其改为 DP 版本。（存在大量重复计算，且无后效性）
 *
 * 由递归版本我们可以得到如下信息：
 * dp[i][j]:表示 s[i...s.length-1] 与 p[j...p.length-1] 是否能够匹配
 * dp[i][j] 依赖于 dp[i+1][j+1], dp[i+1][j], dp[i][j+2] 这三个状态。
 * 即该矩阵是 从下到上，从右到左 进行填充的，最终需要的状态为 dp[0][0]
 * 对此我们需要对 dp 矩阵进行一个初始化,使得我们可以根据初始化之后的 base case 推测出矩阵中的其他状态。
 * 有了以上信息，我们结合 firstMatch 状态即可完成对矩阵中每个位置值的计算。
 * 注意：最后一行和最后一列代表的意思是，该字符串已经没有剩余字符以供匹配的意思
 *
 * 大家可以发现，本题我们不是直接从 DP 的角度去分析，怎么去做，状态转移方程怎么去推。
 * 而是先写出 递归的尝试版本 然后再改写成 DP 版本。这比直接写出 DP 实在简单了不少。
 * 在面对比较难的问题 或者 无法直接写出DP 的话可以使用此方法。（十分推荐）
 */
class Solution {
    public boolean isMatch(String s, String p) {
        char[] sArray = s.toCharArray();
        char[] pArray = p.toCharArray();
        int slen = sArray.length, plen = pArray.length;
        boolean[][] dp = new boolean[slen + 1][plen + 1];

        // Initialize the last column
        dp[slen][plen] = true;
        // Initialize the last row
        for (int j = plen - 2; j >= 0; j = j - 2) {
            if (pArray[j] != '*' && pArray[j + 1] == '*') {
                dp[slen][j] = true;
            } else {
                break;
            }
        }

        for (int i = slen - 1; i >= 0; i--) {
            for (int j = plen - 1; j >= 0; j--) {
                boolean first_match = (sArray[i] == pArray[j] || pArray[j] == '.');
                if (j + 1 < plen && pArray[j + 1] == '*') {
                    dp[i][j] = dp[i][j + 2] || (first_match && dp[i + 1][j]);
                } else {
                    dp[i][j] = first_match && dp[i + 1][j + 1];
                }
            }
        }

        return dp[0][0];
    }
}
