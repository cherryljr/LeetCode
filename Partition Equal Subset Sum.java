/*
Given a non-empty array containing only positive integers,
find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

Note:
Each of the array element will not exceed 100.
The array size will not exceed 200.

Example 1:
Input: [1, 5, 11, 5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:
Input: [1, 2, 3, 5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
 */

/**
 * Approach: 01 Backpack
 * Subset 问题，因为可以无序，所以直接不考虑 Sliding Window / PreSum 等这些方法。
 * 因为要求能否将整个数组分成两个 sum 相等的 subArray.
 * 因此首先将整个数组的 sum 求出来，若不能被 2 整除直接返回 false 即可。
 * 如果可以，那么问题就变成了能否从数组中取出 几个数 使其和为 sum / 2.
 * 也就是典型的 01背包问题。
 * 因为过于典型...这里直接给出优化后的解法了...
 *
 * 不了解 01背包问题 的可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 */
class Solution {
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if ((sum & 1) == 0) {
            return backpack(nums, sum >> 1);
        } else {
            return false;
        }
    }

    private boolean backpack(int[] nums, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        if (nums[0] <= target) {
            dp[nums[0]] = true;
        }

        for (int i = 1; i < nums.length; i++) {
        	// Guarantee the size is big enough to put num[i] into the backpack.
            for (int j = target; j >= nums[i]; j--) {
                dp[j] |= dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}