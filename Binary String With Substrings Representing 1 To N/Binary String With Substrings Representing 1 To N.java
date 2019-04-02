/*
Given a binary string S (a string consisting only of '0' and '1's) and a positive integer N,
return true if and only if for every integer X from 1 to N, the binary representation of X is a substring of S.

Example 1:
Input: S = "0110", N = 3
Output: true

Example 2:
Input: S = "0110", N = 4
Output: false

Note:
    1. 1 <= S.length <= 1000
    2. 1 <= N <= 10^9
 */

/**
 * Approach: Mathematics
 * 这道题目我们可以发现 S.length 只有 1000.
 * 按照这个字符串长度是根本没有办法表示 10^9 以内所有这么多数的全部二进制的。
 * 因此，我们可以想到，这道题目的数据规模实际上并没有题目所给的这么大。
 * 其上界应该是 S 而并不是 N.
 *
 * 对于字符串 S 而言，其最多只有 S.length^2 个不同的 substring。
 * 因此其实际上的时间复杂度上限只有到 O(S.length^2) 级别而已。
 * 然后我们将 N 由大到小进行 遍历，（由大到小是因为更大数的二进制值更加难以被表示，如果不符合可以更早退出循环）
 * 同时，我们知道 num 的二进制字符串肯定是可以被包含在 2 * num 的二进制字符串中的
 * （原因很简单：num << 1 == num * 2.所以 2*num的二进制字符串 就是 num二进制字符串的左移一位罢了，后面多了个0）
 * 所以我们实际上遍历的范围只是（N, N/2) 之间
 *
 * 时间复杂度：O(S.length * 2) == O(S) (详细分析将 References)
 * 空间复杂度：O(bin(N)) (因为每次需要生成二进制字符串)
 *
 * References:
 *  https://leetcode.com/problems/binary-string-with-substrings-representing-1-to-n/discuss/260847/JavaC%2B%2BPython-O(S2)
 */
class Solution {
    public boolean queryString(String S, int N) {
       for (int i = N; i > N >> 1; i--) {
           String str = Integer.toBinaryString(i);
           if (!S.contains(str)) {
               return false;
           }
        }
        return true;
    }
}