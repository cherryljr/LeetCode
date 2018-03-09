/*
Given an integer, write a function to determine if it is a power of three.

Follow up:
Could you do it without using any loop / recursion?
 */

/**
 * Approach 1: Loop Iteration
 * 采用 while 循环一次次地将 n 除以 3，并判断其余数是否为 0.
 * 该方法适用于所有该类型题目，时间复杂度为：O(log3(n))
 */
class Solution {
    public boolean isPowerOfThree(int n) {
        if (n < 1) {
            return false;
        }

        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1 ? true : false;
    }
}

/**
 * Approach 2: Integer Limitations
 * 该解法是从网上看来的...利用了 Integer 类型的最大值去计算...
 * 反正我是没想出来啦...
 * 参考资料：
 * https://leetcode.com/problems/power-of-three/solution/
 */
class Solution {
    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }
}