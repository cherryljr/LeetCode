/*
Today, the bookstore owner has a store open for customers.length minutes.
Every minute, some number of customers (customers[i]) enter the store, and all those customers leave after the end of that minute.

On some minutes, the bookstore owner is grumpy.
If the bookstore owner is grumpy on the i-th minute, grumpy[i] = 1, otherwise grumpy[i] = 0.
When the bookstore owner is grumpy, the customers of that minute are not satisfied, otherwise they are satisfied.

The bookstore owner knows a secret technique to keep themselves not grumpy for X minutes straight, but can only use it once.
Return the maximum number of customers that can be satisfied throughout the day.

Example 1:
Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
Output: 16
Explanation: The bookstore owner keeps themselves not grumpy for the last 3 minutes.
The maximum number of customers that can be satisfied = 1 + 1 + 1 + 1 + 7 + 5 = 16.

Note:
    1. 1 <= X <= customers.length == grumpy.length <= 20000
    2. 0 <= customers[i] <= 1000
    3. 0 <= grumpy[i] <= 1
 */

/**
 * Approach: Sliding Window (Fixed Length)
 * 该问题属于 定长的滑动窗口 类问题。
 * 使用滑动窗口 useTechnique 来记录如果店家使用了持续时长为 X 的技巧的话，可以额外获得的满意值。
 * 如果当前窗口的大小大于 X，则需要对 useTechnique 值进行更新。
 * 然后使用 satisfy 这个值来记录 不使用技巧 能够获得的满意值（基础值）。
 * 则问题最终的结果就是 satisfy + max(win)
 * 
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 非定长的滑动窗口类问题可以参考：
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int satisfy = 0, max = 0;
        for (int i = 0, useTechnique = 0; i < grumpy.length; ++i) {
            if (grumpy[i] == 0) {
                satisfy += customers[i];
            } else {
                useTechnique += customers[i];
            }
            if (i >= X) {
                // grumpy[i] 为1代表生气无法获得满意值，0代表不生气可以获得满意值，
                // 所以这里使用乘法操作来进行解决
                useTechnique -= grumpy[i - X] * customers[i - X];
            }
            max = Math.max(useTechnique, max);
        }
        return satisfy + max;
    }
}