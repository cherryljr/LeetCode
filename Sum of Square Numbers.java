/*
Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.

Example 1:
Input: 5
Output: True
Explanation: 1 * 1 + 2 * 2 = 5

Example 2:
Input: 3
Output: False
 */

/**
 * Approach: Mathematics + Two Pointers
 * 是一道 Brute Force 的题目，需要注意判断范围从 0~sqrt(c) 即可。
 *
 *  时间复杂度：O(sqrt(c))
 * 空间复杂度：O(1)
 */
class Solution {
    public boolean judgeSquareSum(int c) {
        int left = 0, right = (int) Math.sqrt(c);
        while (left <= right) {
            if (left * left + right * right == c) {
                return true;
            } else if (left * left + right * right < c) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }
}