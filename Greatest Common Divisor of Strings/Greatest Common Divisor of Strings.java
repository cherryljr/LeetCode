/*
For strings S and T, we say "T divides S" if and only if S = T + ... + T  (T concatenated with itself 1 or more times)
Return the largest string X such that X divides str1 and X divides str2.

Example 1:
Input: str1 = "ABCABC", str2 = "ABC"
Output: "ABC"

Example 2:
Input: str1 = "ABABAB", str2 = "ABAB"
Output: "AB"

Example 3:
Input: str1 = "LEET", str2 = "CODE"
Output: ""

Note:
    1. 1 <= str1.length <= 1000
    2. 1 <= str2.length <= 1000
    3. str1[i] and str2[i] are English uppercase letters.
 */

/**
 * Approach: Imitate GCD Algorithm (Recursion)
 * 该解法直接模拟 GCD 的求法即可，步骤如下：
 *  1.如果较长的字符串是以较短的字符串为开头（startsWith），那么我们就从较长字符串中去除掉较短字符串的这部分；
 *  2.重复上述步骤，直到较短的字符串为空，此时说明较长的字符串就是我们要找的 GCD String。
 *  3.如果较长的字符串不是以较短的字符串作为开头，那么说明二者不存在 GCD String，返回 "" 即可。
 *
 * 时间复杂度：
 *  介于 Java 中字符串操作的特性，由于本解法递归使用了 startsWith 和 substring 函数。
 *  因此在最坏情况下（共同序列只有一个字符），时间复杂度为：1 + 2 + 3 + ... + n = n * (n + 1) / 2，即 O(n^2)
 * 空间复杂度：O(n^2)，原因同时间复杂度分析。
 */
class Solution {
    public String gcdOfStrings(String str1, String str2) {
        if (str1.length() < str2.length()) {
            // make sure str1 is not shorter than str2
            return gcdOfStrings(str2, str1);
        } else if (!str1.startsWith(str2)) {
            // if shorter string is not common prefix, the answer not exists.
            return "";
        } else if (str2.isEmpty()) {
            // gcd string found
            return str1;
        } else {
            // cut off the common prefix part of str1
            return gcdOfStrings(str1.substring(str2.length()), str2);
        }
    }
}