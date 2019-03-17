/*
Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.

Example 1:
Input: [1,0,1,1,0]
Output: 4
Explanation:
Flip the first zero will get the the maximum number of consecutive 1s.
After flipping, the maximum number of consecutive 1s is 4.

Note:
    1. The input array will only contain 0 and 1.
    2. The length of input array is a positive integer and will not exceed 10,000

Follow up:
What if the input numbers come in one by one as an infinite stream?
In other words, you can't store all numbers coming from the stream as it's too large to hold in memory.
Could you solve it efficiently?
 */

/**
 * Approach: Traversal
 * 这道题属于 Max Consecutive Ones 的 Follow Up，
 * 添加了一个条件：有一次将0翻转成1，求此时最大连续1的个数。
 * 再看看follow up中的说明，很明显是让我们只遍历一次数组，
 * 因此可以想到：需要用一个变量count来记录连续1的个数，那么当遇到了0的时候怎么处理呢?
 * 因为我们有一次0变1的机会，所以我们遇到0了还是要累加count，
 * 但是此时要利用另外一个变量 curr 来存储这个值，然后将 count 重置为0 （使得 count 表示真正的连续'1'的个数），
 * 最后使用 curr + count 来更新我们的结果：ans = max(ans, curr + count)
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        // curr:表示经过一次反转之后的最长连续'1'的个数
        // count:表示未经过反转最长的连续'1'的个数
        int ans = 0, curr = 0, count = 0;
        for (int num : nums) {
            count++;
            if (num == 0) {
                curr = count;
                count = 0;
            }
            ans = Math.max(ans, curr + count);
        }
        return ans;
    }
}