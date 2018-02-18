/*
Given an array of n positive integers and a positive integer s,
find the minimal length of a contiguous subarray of which the sum ≥ s.
If there isn't one, return 0 instead.

For example, given the array [2,3,1,2,4,3] and s = 7,
the subarray [4,3] has the minimal length under the problem constraint.
 */

/**
 * Approach: Two Pointers (Sliding Window)
 * This question is about Sliding Window, You can learn something about it here:
 * Sliding Window Template:
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 * Of course, this question could be solved by more concise code, so we don't need to use the template.
 * But the main idea is the same.
 *
 * Algorithm
 * Initialize left pointer to 0 and sum to 0
 * Iterate over the nums:
 *  Add nums[right] to sum
 *  While sum is greater than or equal to s:
 *      1. Update rst=min(rst,right−left+1), where right−left+1 is the size of current subarray
 *      2. It means that the first index can safely be incremented,
 *      since, the minimum subarray starting with this index with sum≥s has been achieved
 *      3. nums[left] from sum and increment left
 *
 * Complexity analysis
 *  Time complexity: O(n). Single iteration of O(n).
 *  Each element can be visited at most twice, once by the right pointer(i) and (at most)once by the left pointer.
 *  Space complexity: O(1) extra space.
 *  Only constant space required for left, right, sum and rst.
 *
 * Reference:
 * https://leetcode.com/articles/minimum-size-subarray-sum/#
 */
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        int rst = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= s) {
                rst = Math.min(rst, right - left + 1);
                sum -= nums[left++];
            }
        }

        return rst == Integer.MAX_VALUE ? 0 : rst;
    }
}