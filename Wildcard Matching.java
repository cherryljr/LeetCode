/*
Implement wildcard pattern matching with support for '?' and '*'.

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "*") → true
isMatch("aa", "a*") → true
isMatch("ab", "?*") → true
isMatch("aab", "c*a*b") → false
 */

/**
 * Approach 1: Recursion (Time Limit Exceeded)
 * Regular Expression Matching 的改版
 * 如果掌握了正则表达式那道题目的话，这道题目相对来说还会更加简单一下。
 * 做法基本相同。
 * 这里我们其实可以直接写出 DP 做法了，但是为了大家的理解与分析方便，
 * 这边仍然给出了 递归版本 的答案。
 */
class Solution {
    public boolean isMatch(String s, String p) {
        return process(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    private boolean process(char[] s, char[] p, int i, int j) {
        // 当 p 中没有剩余字符可以匹配的时候，如果 s 中还有剩余字符，则直接 return false
        if (j == p.length) {
            return i == s.length;
        }
        // 当 s 中没有剩余字符可以匹配的时候，如果 p 中还有剩余字符的话，需要检查是不是都是 '*'
        // 不是的话，则无法完成匹配
        if (i == s.length) {
            for (; j < p.length; j++) {
                if (p[j] != '*') {
                    return false;
                }
            }
            return true;
        }

        // 单字符匹配
        if (p[j] == '?' || s[i] == p[j]) {
            return process(s, p, i + 1, j + 1);
        } 
        // '*'为空 或者 匹配多个字符
        else if (p[j] == '*') {
            return (process(s, p, i, j + 1) || process(s, p, i + 1, j));
        } 
        // 无法匹配
        else {
            return false;
        }
    }
}

/**
 * Approach 2: DP
 * 由 递归版本 可得：
 * dp[i][j] 表示 s[i...s.length-1] 与 p[j...p.length-1] 是否能够匹配
 * dp[i][j] 的状态依赖于 dp[i+1][j], dp[i][j+1], dp[i+1][j+1] 这三个状态
 * 即该矩阵是 从下到上，从右到左 进行填充的，最终需要的状态为 dp[0][0]
 * 对此我们需要对 dp 矩阵进行一个初始化,使得我们可以根据初始化之后的 base case 推测出矩阵中的其他状态。
 * 这里需要初始化的状态为：最后一行以及最后一列
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
        for (int j = plen - 1; j >= 0; j--) {
            if (pArray[j] == '*') {
                dp[slen][j] = true;
            } else {
                break;
            }
        }

        // Function
        for (int i = slen - 1; i >= 0; i--) {
            for (int j = plen - 1; j >= 0; j--) {
                if (pArray[j] == sArray[i] || pArray[j] == '?') {
                    dp[i][j] = dp[i + 1][j + 1];
                } else if (pArray[j] == '*') {
                    dp[i][j] = dp[i + 1][j] || dp[i][j + 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[0][0];
    }
}