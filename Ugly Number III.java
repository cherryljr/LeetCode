/*
Write a program to find the n-th ugly number.
Ugly numbers are positive integers which are divisible by a or b or c.

Example 1:
Input: n = 3, a = 2, b = 3, c = 5
Output: 4
Explanation: The ugly numbers are 2, 3, 4, 5, 6, 8, 9, 10... The 3rd is 4.

Example 2:
Input: n = 4, a = 2, b = 3, c = 4
Output: 6
Explanation: The ugly numbers are 2, 3, 4, 6, 8, 9, 10, 12... The 4th is 6.

Example 3:
Input: n = 5, a = 2, b = 11, c = 13
Output: 10
Explanation: The ugly numbers are 2, 4, 6, 8, 10, 11, 12, 13... The 5th is 10.

Example 4:
Input: n = 1000000000, a = 2, b = 217983653, c = 336916467
Output: 1999999984

Constraints:
    1. 1 <= n, a, b, c <= 10^9
    2. 1 <= a * b * c <= 10^18
    3. It's guaranteed that the result will be in range [1, 2 * 10^9]
 */

/**
 * Approach: LCM + Binary Search
 * Calculate how many numbers from 1 to num are divisible by either a, b or c by using the formula:
 *  (num / a) + (num / b) + (num / c) – (num / lcm(a, b)) – (num / lcm(b, c)) – (num / lcm(a, c)) + (num / lcm(a, b, c))
 *
 * Time Complexity: O(log(2^31)) ==> O(1)
 * Space Complexity: O(1)
 *
 * Reference: https://www.bilibili.com/video/av68819716
 */
class Solution {
    public int nthUglyNumber(int n, int a, int b, int c) {
        int left = 1, right = Integer.MAX_VALUE;
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (getCount(mid, a, b, c) >= n) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private long getCount(int n, int a, int b, int c) {
        return n / a + n / b + n / c - n / LCM(a, b) - n / LCM(b, c) - n / LCM(a, c) + n / LCM(a, LCM(b, c));
    }

    private long LCM(long a, long b) {
        return a * b / GCD(a, b);
    }

    private long GCD(long a, long b) {
        return b == 0 ? a : GCD(b, a % b);
    }
}