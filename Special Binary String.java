/*
Special binary strings are binary strings with the following two properties:

The number of 0's is equal to the number of 1's.
Every prefix of the binary string has at least as many 1's as 0's.
Given a special string S, a move consists of choosing two consecutive, non-empty, special substrings of S, and swapping them.
(Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.)

At the end of any number of moves, what is the lexicographically largest resulting string possible?

Example 1:
Input: S = "11011000"
Output: "11100100"
Explanation:
The strings "10" [occuring at S[1]] and "1100" [at S[3]] are swapped.
This is the lexicographically largest string possible after some number of swaps.

Note:
    1. S has length at most 50.
    2. S is guaranteed to be a special binary string as defined above.
 */

/**
 * Approach: Recursion (Similar to Valid Parentheses)
 * 这道题给了我们一个特殊的二进制字符串，说是需要满足两个要求
 *  1. '0' 和 '1' 的个数要相等；
 *  2. 任何一个前缀中的 '1' 的个数都要大于等于 '0' 的个数。
 * 对此我们不难发现这其实就是一个括号的字符串表达方式。（'1' 表示左括号，'0' 表示右括号）
 * 题目中的两个限制条件其实就是限定这个括号字符串必须合法，即左右括号的个数必须相同，且左括号的个数随时都要大于等于右括号的个数。
 * 对此，可以参见类似的题目 Valid Parenthesis String。
 *
 * 那么这道题让我们通过交换子字符串，生成字母顺序最大的特殊字符串，注意这里交换的子字符串也必须是特殊字符串，满足题目中给定的两个条件。
 * 换作括号来说就是交换的子括号字符串也必须是合法的。那么我们来想什么样的字符串是字母顺序最大的呢？
 * 根据题目中的例子可以分析得出，应该是 '1' 靠前的越多越好，那么换作括号来说就是括号嵌套多的应该放在前面。
 * 即如果左括号多，就意味着前面的 '1' 就多。所以我们要做的就是将中间的子串分别提取出来，然后排序，再放回即可。
 * 注意在给它们排序之前，其自身的顺序应该已经按字母顺序排好了才行，因此这个特点非常符合递归的思路：
 * 递归到最里层，然后一层一层向外扩展，直至完成所有的排序。
 *
 * 因为我们移动的子字符串必须是合法的，因此我们利用检测括号字符串合法性的一个最常用的方法，就是遇到左括号加1，遇到右括号-1，
 * 这样当 count == 0 的时候，就是一个合法的子字符串了。我们用 begin 来记录这个合法子字符串的起始位置，list ans 来保存这些合法的子字符串。
 * 当将合法子字符串放入 ans 之后，我们需要同样对这个字串自身进行排序，因此我们要对自身调用递归函数，
 * 不过，我们不用对整个子串调用递归，因为字串的起始位置和结束位置是确定的，一定是 '1' 和 '0'，
 * 我们只需对中间的调用递归即可，然后更新下一个遍历的起始位置 begin = end + 1。
 * 当我们将所有排序后的合法字串存入 ans 中后，我们对 ans 进行排序，将字母顺序大的放前面，最后将其连为一个字符串返回即可。
 *
 * 时间复杂度：O(N/k * klogk + N) = O(Nlogk + N)  (k为递归时，分割的 substring 个数)
 * 空间复杂度：O(logn)
 *
 * Reference:
 *  https://leetcode.com/problems/special-binary-string/discuss/113211/JavaC%2B%2BPython-Easy-and-Concise-Recursion
 * Longest Valid Parentheses:
 *  https://github.com/cherryljr/LeetCode/blob/master/Longest%20Valid%20Parentheses.java
 */
class Solution {
    public String makeLargestSpecial(String S) {
        // 因为 S 在递归求解 substring 的过程中会不断减小（被去掉头尾的'1'和'0'），最终变成一个空字符串 ""，所以该递归不会陷入死循环。
        // （这里可以不用对 递归 的边界进行处理，因为 return String.join("", ans) 对于 "" 的处理效果是一样的）
        int begin = 0, count = 0;
        List<String> ans = new ArrayList<>();
        for (int end = 0; end < S.length(); end++) {
            count += S.charAt(end) == '1' ? 1 : -1;
            if (count == 0) {
                ans.add('1' + makeLargestSpecial(S.substring(begin + 1, end)) + '0');
                begin = end + 1;
            }
        }

        Collections.sort(ans, Collections.reverseOrder());
        return String.join("", ans);
    }
}