/*
Given a number N, return a string consisting of "0"s and "1"s that 
represents its value in base -2 (negative two).

The returned string must have no leading zeroes, unless the string is "0".

Example 1:
Input: 2
Output: "110"
Explantion: (-2) ^ 2 + (-2) ^ 1 = 2

Example 2:
Input: 3
Output: "111"
Explantion: (-2) ^ 2 + (-2) ^ 1 + (-2) ^ 0 = 3

Example 3:
Input: 4
Output: "100"
Explantion: (-2) ^ 2 = 4

Note:
    1. 0 <= N <= 10^9
 */

/**
 * Approach: Mathematics (Negative Base)
 * 为了使得这道题目有更广的适用性，这里将对所有 负整数 作为 base 的情况进行讨论，而不仅仅局限与 -2.
 *
 * 首先，我们来考虑 正整数作为base 的情况。
 * 求以 n 作为 base 的表达，我们可以有以下步骤：
 *  a % n == r,  a /= n,  ans = str(r) + ans
 * 然后不断依次循环下去，将所有的 reminder 拼接起来即可。
 * 因为 n 为正整数，所以得到的 余数 必定是 非负数，同时余数的范围在 [0, n-1] 之内。
 * （不考虑被除数为 负数 的情况，在 n为正数 的情况下，根本无法以 n 为 base 表示一个负数）
 *
 * 然后，我们再来考虑下 负整数作为base 的情况。
 * 设：a = n * b + r, 此时因为 n 为负数，当 a 也为负数的话，就可能出现 r 为负数的情况。
 * 这是我们转换过程中所不允许的，所以此时我们对于 r 的要求为：尽可能小(接近0)的非负整数
 * （这个要求可能与我们平时进行的取模运算有点不同）
 * eg. 我们要求 146 以 -3 进制的表达
 *      146 / -3 = -48 …… 2
 *      -48 / -3 = 16  …… 0
 *       16 / -3 = -5  …… 1
 *       -5 / -3 =  2  …… 1
 *        2 / -3 =  0  …… 2
 *  注意这里的 -5 / -3 本来结果应该是 1 …… -2，但是为了使得 reminder 为非负数，
 *  我们对结果进行了调整 -2 -(-3) = 1, 使得  -5 / -3 =  2  …… 1
 *  结果为： (((2*(–3)+1)*(–3)+1)*(–3)+0)*(–3)+2 = 146
 *
 * 因此为了使得求得的 余数 满足条件，我们需要进行如下处理：
 *  if r < 0 then r = r + abs(n), a = a / n + 1
 *  (因为下一步操作会将 b 赋值给 a, 即相当于 b += 1，商的值+1)
 * 证明如下：
 *  这里为了理解和表示方便，我们令 n 为正整数，并且我们知道如果r为负数的话，那么 r 的范围必定在 [-n+1, 0] 上面
 *  a = (-n) * b + r  ==>  a = (-n) * b + (r + n) - n  ==>  a = (-n)*(b + 1) + (r + n)
 * 因此介于 r 的范围，我们可以保证通过 r+n 操作使得余数 r 为 非负整数，
 * 同时下一轮的被除数 b = b + 1
 *（注意：题目这里没有提到的一点是，当 base n 为负整数时，负整数和正整数 的 n 进制 均可被表示）
 *
 * 时间复杂度：O(lgn)
 * 空间复杂度：O(1)
 *
 * References:
 *  https://www.geeksforgeeks.org/convert-number-negative-base-representation/
 *  https://en.wikipedia.org/wiki/Negative_base#Calculation
 */
class Solution {
    public String baseNeg2(int N) {
        StringBuilder ans = new StringBuilder();
        int negBase = -2;
        while (N != 0) {
            int reminder = N % negBase;
            N /= negBase;
            // 当 reminder < 0 时，将其通过 -negative 调整成 正整数
            // 同时对 商的值+1 (下一轮的被除数)
            if (reminder < 0) {
                reminder -= negBase;
                N += 1;
            }
            ans.append(reminder);
        }

        return ans.length() == 0 ? "0" : ans.reverse().toString();
    }
}