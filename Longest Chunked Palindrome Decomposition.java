/*
Return the largest possible k such that there exists a_1, a_2, ..., a_k such that:
    ·Each a_i is a non-empty string;
    ·Their concatenation a_1 + a_2 + ... + a_k is equal to text;
    ·For all 1 <= i <= k,  a_i = a_{k+1 - i}.

Example 1:
Input: text = "ghiabcdefhelloadamhelloabcdefghi"
Output: 7
Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)".

Example 2:
Input: text = "merchant"
Output: 1
Explanation: We can split the string on "(merchant)".

Example 3:
Input: text = "antaprezatepzapreanta"
Output: 11
Explanation: We can split the string on "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)".

Example 4:
Input: text = "aaa"
Output: 3
Explanation: We can split the string on "(a)(a)(a)".

Constraints:
    1. text consists only of lowercase English characters.
    2. 1 <= text.length <= 1000
 */

/**
 * Approach: Iterative Greedy
 * 枚举长度 i（最大只能到 text.length/2），采用贪心的策略：
 * 只要 text.substring(i) 和 text.substring(n - i - 1) 相等，就说明能够组成一对回文。
 * 因此立刻将他们切割出来，结果+2，然后对剩下的字符串继续进行上述处理。
 * 直到字符串为空或者仅身下一个无法被切分的字符串。（这个过程可以使用递归进行处理）
 *
 * 时间复杂度：O(n * len(string))
 * 空间复杂度：O(n)
 */
class Solution {
    public int longestDecomposition(String text) {
        int n = text.length();
        for (int i = 0; i < n / 2; i++) {
            if (text.substring(0, i + 1).equals(text.substring(n - i - 1))) {
                return 2 + longestDecomposition(text.substring(i + 1, n - i - 1));
            }
        }
        return n == 0 ? 0 : 1;
    }
}