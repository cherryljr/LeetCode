/*
Given a positive integer K, you need find the smallest positive integer N such that N is divisible by K, and N only contains the digit 1.
Return the length of N.  If there is no such N, return -1.

Example 1:
Input: 1
Output: 1
Explanation: The smallest answer is N = 1, which has length 1.

Example 2:
Input: 2
Output: -1
Explanation: There is no such positive integer N divisible by 2.

Example 3:
Input: 3
Output: 3
Explanation: The smallest answer is N = 111, which has length 3.

Note:
    1. 1 <= K <= 10^5
 */

/**
 * Approach: Pigeonhole Principle + MOD
 * 对于个位上数 2 或者 5 以及他们倍数的数字，其乘积是无法产生 1 的。
 * 因此对于这些数，我们直接 return -1 即可。
 *
 * 首先，我们假设从 1~111...1 (k个1) 中总共 k 个元素，没有存在一个元素能够整除K。
 * 但是我们知道 x%k 的结果只有 0...k-1 总共 k 种可能，现在去除了 0，
 * 也就是说 1~111...1 (k个1) % k 的这些结果里面，必定有一个值是重复的（Pigeonhole Principle）
 * 当我们知道这 k 个数里面存在重复的 reminder 并且没有值为 0 时，我们就可以断定不存在各个位上全为 1 的数能够被 K 整除。
 * 原因如下：
 *  设当前元素为 N，则下一个我们需要遍历的元素为 N*10 + 1。
 *  但是我们发现，题目的数据规模为 10^5，即如果一直这样遍历一下无疑是会越界的。
 *  因此，我们可以选择利用 reminder 来替代这个元素进行计算。
 *  在此之前，我们必须要了解 取余运算 的一个重要操作：
 *      (a + b) %n = [a%n + b%n] %n （对于减法和乘法同理）
 *  则，根据该式可以得出：
 *      Nnext % K  ==  (N*10 + 1) % K  ==  (N*10%K + 1%K) % K  ==  (N%K*10 + 1) % K
 *  因此 Nnext % K 的值是由 N % K 来决定的，并且通过该方法可以避免数值溢出的问题。
 *  有了以上结论，我们就可以推出当 reminder 出现循环的时候，我们已经遍历了所有的余数值，继续遍历 111...1(K+1)时，值就会出现重复了。
 *  但是本题实际上可以证明：
 *      如果 k !=2 && k != 5,则必定有解。
 *      根据Pigeonhole Principle，必定存在 f(N) ≡ f(M)。（假设 N > M）
 *      则必定有 N-M 被 K 整除。N-M == 111..1000...0 (N-M个1，M个0)
 *      因为结尾为 0，这要使得该元素能被 K 整除，K的个位数必定为 2 或者 5.
 *      但是 2，5我们已经在开头判断过了，因此这里必定有解。
 *      
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 补充说明：
 * 本题对于取余部分的操作其实可以进行一下延伸：
 *  设 n2 = n1*a + c 则我们可以推出: n2 % k = (n1%k*a + c) % k  (a !=0，k为正整数，c为常数)
 * 证明：
 *  首先根据定义可知：n1 = ⌊n1/k⌋*k + n1%k
 *  将上式代入 n2 = n1*a + c 可得：
 *  n2 % k = (⌊n1/k⌋*k*a + n1%k*a + c) % k
 *         = (0 + n1%k*a + c) % k
 *         = (n1%k*a + c) % k
 * 则结论得证
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Binary%20Prefix%20Divisible%20By%205/Binary%20Prefix%20Divisible%20By%205.java
 * 
 * 使用到了 Pigeonhole Principle 的类似问题有：
 * Maximum Gap:
 *  https://github.com/cherryljr/LeetCode/blob/master/Maximum%20Gap.java
 */
class Solution {
    public int smallestRepunitDivByK(int K) {
        if (K % 10 == 2 || K % 10 == 5) {
            return -1;
        }

        // 根据鸽巢原理和余数会出现循环，我们只需遍历到 111...1(K个1) 即可确实值是否存在
        for (int N = 1, reminder = 0; N <= K; N++) {
            reminder = (reminder * 10 + 1) % K;
            if (reminder == 0) {
                return N;
            }
        }
        return -1;
    }
}