/*
Write a program to check whether a given number is an ugly number.
Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.

Example 1:
Input: 6
Output: true
Explanation: 6 = 2 × 3

Example 2:
Input: 8
Output: true
Explanation: 8 = 2 × 2 × 2

Example 3:
Input: 14
Output: false
Explanation: 14 is not ugly since it includes another prime factor 7.

Note:
    1. 1 is typically treated as an ugly number.
    2. Input is within the 32-bit signed integer range: [−231,  231 − 1].
 */

/**
 * Approach: Mathematics
 * 丑数的定义为 只包含质因子 2, 3, 5 的正整数。
 * 因此我们只需要将判断的数不断不断地除以 2， 3， 5
 * （如果余数为 0 的话），然后判断结果是否能被整除即可。
 *
 * 时间复杂度：O(log2(N) + logn3(N) + log5(N))
 * 空间复杂度：O(1)
 *
 * Reference: https://www.bilibili.com/video/av31569881
 */
class Solution {
    private static final int[] factors = {2, 3, 5};

    public boolean isUgly(int num) {
        if (num == 0) {
            return false;
        }
        for (int factor : factors) {
            while (num % factor == 0)  {
                num /= factor;
            }
        }
        return num == 1;
    }
}