/*
We have a sorted set of digits D, a non-empty subset of {'1','2','3','4','5','6','7','8','9'}. (Note that '0' is not included.)
Now, we write numbers using these digits, using each digit as many times as we want.
For example, if D = {'1','3','5'}, we may write numbers such as '13', '551', '1351315'.

Return the number of positive integers that can be written (using the digits of D) that are less than or equal to N.

Example 1:
Input: D = ["1","3","5","7"], N = 100
Output: 20
Explanation:
The 20 numbers that can be written are:
1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.

Example 2:
Input: D = ["1","4","9"], N = 1000000000
Output: 29523
Explanation:
We can write 3 one digit numbers, 9 two digit numbers, 27 three digit numbers,
81 four digit numbers, 243 five digit numbers, 729 six digit numbers,
2187 seven digit numbers, 6561 eight digit numbers, and 19683 nine digit numbers.
In total, this is 29523 integers that can be written using the digits of D.

Note:
D is a subset of digits '1'-'9' in sorted order.
1 <= N <= 10^9
 */

/**
 * Approach: Mathematics
 * 根据数据规模可得，时间复杂度应该在 logn 级别。
 * 因此我们应该根据位数对可能性进行分析，而不是一个个数进行遍历的 Brute Force.
 * 根据数学分析，我们从高位开始看，
 * 首先我们可以加上出去最高位的所有可能性。因为这些数字必定是小于 N 的。
 * eg. N = 52525, 那么我们可以加上所有的：
 *  X, XX, XXX, XXXX 这些数，每个位上数的可能性均为 D.length.
 *  因此综合为 sum(D.length^i) 1<=i<=len-1
 * 然后我们再从最高位开始分析：
 *  如果该位上的数 > D[i]，那么就意味着可以组成以 D[i] 为高位的，总共有 D.length^(n-i-1) 的个数比 N 要小。
 *  依次类推下去；
 *  如果该位上的数 = D[i]，那么无法确定，我们需要进行向后判断，即判断较低位上的数。其实就是一个子问题罢了。
 *
 * 时间复杂度：O(log10(N))
 * 空间复杂度：O(1)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=d2O_jwPxroc
 */
class Solution {
    public int atMostNGivenDigitSet(String[] D, int N) {
        char[] s = String.valueOf(N).toCharArray();
        int len = s.length;
        int rst = 0;
        // 加上所有小于 ?XXX... 的数字个数。即不考虑最高位。
        for (int i = 1; i < len; i++) {
            rst += (int)Math.pow(D.length, i);
        }

        for (int i = 0; i < len; i++) {
            boolean prefix = false;
            for (String num : D) {
                if (s[i] > num.charAt(0)) {
                    rst += (int)Math.pow(D.length, len - i  - 1);
                } else if (s[i] == num.charAt(0)) {
                    // check the next digit
                    prefix = true;
                    break;
                }
            }

            if (!prefix) {
                return rst;
            }
        }

        // 如果运行到这说明 N 正好可以被 D 中的数字组合而成，因此需要 +1
        return rst + 1;
    }
}