/*
Given an array nums of integers, you can perform operations on the array.
In each operation, you pick any nums[i] and delete it to earn nums[i] points.
After, you must delete every element equal to nums[i] - 1 or nums[i] + 1.

You start with 0 points. Return the maximum number of points you can earn by applying such operations.

Example 1:
Input: nums = [3, 4, 2]
Output: 6
Explanation:
Delete 4 to earn 4 points, consequently 3 is also deleted.
Then, delete 2 to earn 2 points. 6 total points are earned.

Example 2:
Input: nums = [2, 2, 3, 3, 3, 4]
Output: 9
Explanation:
Delete 3 to earn 3 points, deleting both 2's and the 4.
Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
9 total points are earned.

Note:
The length of nums is at most 20000.
Each element nums[i] is an integer in the range [1, 10000].
 */

/**
 * Approach: Sequence DP (Turn into House Robber)
 * 题目中说明，当我们选择了一个元素 num,那么 num-1, num+1 都会被删除掉。
 * 也就意味着我们无法取到它们了。但是同时也意味着我们可以将值为 num 的元素全部取走。
 * 此时，我们其实可以发现这和 House Robber 这道题目非常相似了。
 * 我们只需要求第 num 个房子有多少的价值即可，即 m * num.
 * 一旦我们抢了第 num 个房子，我们就必须放弃第 num-1 和 num+1 个房子。
 *
 * 时间复杂度：O(n + m) (m代表数组中的最大值)
 * 空间复杂度：O(m)
 *
 * House Robber:
 *  https://github.com/cherryljr/LintCode/blob/master/House%20Robber.java
 */
class Solution {
    public int deleteAndEarn(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }

        // 建立 points 数组，代表每个元素 num 可以被取的总价值是多少
        int[] points = new int[max + 1];
        for (int num : nums) {
            points[num] += num;
        }
        // 使用 House Robber 的做法即可
        return rob(points);
    }

    // House Robber Code
    private int rob(int[] points) {
        long rob = 0, notRob = 0;
        for (int i = 0; i < points.length; i++) {
            long currRob = notRob + points[i];
            notRob = Math.max(notRob, rob);
            rob = currRob;
        }
        return (int)Math.max(rob, notRob);
    }
}