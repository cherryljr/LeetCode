/*
Given a non-empty string check if it can be constructed by taking a substring of it
and appending multiple copies of the substring together.
You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.

Example 1:
Input: "abab"
Output: True
Explanation: It's the substring "ab" twice.

Example 2:
Input: "aba"
Output: False

Example 3:
Input: "abcabcabcabc"
Output: True
Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
 */

/**
 * Approach 1: KMP
 * 求一个字符串能否由一个 subString 重复表示而成。
 * 如果字符串 str 能够被多个 substring 表示而成，那么说明 str.length % substring.length == 0.
 * 因此我们假设字符串 str 能够被一个 substring 表示而成，并且该 substring 为 sp.
 * 那么 str 可以被划分成左右两个部分：s1 + str-sp.
 * 又因为 sp 为 str 的最小表示部分，则说明：右部分 str-sp 也可以被 sp 所表示.
 * 因此我们可以求出 str 的最长相同前后缀，然后用 str - 前后缀的长度。
 * 对于 str 的最长相同前后缀只需要求出 终止位置的 next 值即可。
 * 这样便能够得到 sp.
 * 然后我们只需要检查 sp.length != str.length && str.length % sp.length == 0
 * 符合条件说明 str 能够由重复的 sp 表示，否则不能。
 * 注：要记得判断 str.length != sp.length.因为如果 二者长度相等，说明 相同前后缀长度 为 0.
 * 此时 str 是无法被 sp 所表示的。
 *
 * 时间复杂度为：O(n) 因为 KMP 算法的 next 数组计算的时间复杂度为 O(n)。
 */
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() <= 1) {
            return false;
        }

        int len = s.length();
        char[] arr = s.toCharArray();
        int lenSub = len - endNextLength(arr);

        return ((lenSub != len) && (len % lenSub == 0)) ? true :false;
    }

    private int endNextLength(char[] arr) {
        int[] next = new int[arr.length + 1];
        next[0] = -1;
        // next[1] = 0; 0的赋值在初始化时已经完成，因此该步骤可以省略
        int pos = 2, cn = 0;

        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }

        return next[next.length - 1];
    }
}

/**
 * Approach 2: Copy and Remove
 * 具体做法：
 *  令S1 = S + S（其中输入字符串中的S）
 *  删除 S1 的 第一个 和 最后一个 字符，生成字符串 S2。
 *  如果 S 存在于 S2 中，则返回true否则为false。
 *
 * 做法分析：
 *  假设我们的字符串可以被一个 子串 重复表示。
 *  则我们可以将输入字符串记为：S = SpSp (至少重复两次)
 *  然后我们拷贝一份 S,并将其和原来的字符串拼接起来得到 S2 = SpSpSpSp.
 *  然后我们去掉 S2 的 第一个 和 最后一个 字符。
 *  这样 S2 = SxSpSpSy.
 *  这时我们发现，如果 S 可以被一个 子串重复表示，则能够在 S2 中找到 S，否则不能。
 *
 * 更多详细讨论参见：
 * https://leetcode.com/problems/repeated-substring-pattern/discuss/94334/Easy-python-solution-with-explaination
 */
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() <= 1) {
            return false;
        }

        StringBuilder s1 = new StringBuilder(s).append(s);
        String s2 = s1.deleteCharAt(0).deleteCharAt(s1.length() - 1).toString();
        return s2.indexOf(s) == -1 ? false : true;
    }
}