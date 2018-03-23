/*
Given an array of integers and an integer k, you need to find
the total number of continuous subarrays whose sum equals to k.

Example 1:
Input:nums = [1,1,1], k = 2
Output: 2

Note:
The length of the array is in range [1, 20,000].
The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 */

/**
 * Approach: PreSum + HashMap
 * 这道题目与 累加和为K的最长子数组 十分类似。
 * 我们依旧引入 preSum 来优化我们的时间复杂度，并将其储存在 map 中。
 * 根据题目要求，在这里 key 为 preSum, value 为 preSum 出现的次数（因为我们要求的是次数，而不再是长度）。
 * 当 map.containsKey(preSum-k) 时，说明在 i 之前已经累加出现过 map.get(preSum-k) 次 k 的值。
 * 因此 count += map.get(preSum-k).
 * 同时将 preSum 与其对应的 index 加入到 map 中。
 * 注意：
 *  这里对于 map 的初始化为 (0, 1) 这意味着，当一个数都没有添加的时候，preSum 为 0，并且已经出现了 1 次。
 *  （对于 map 等参数的初始化，都必须根据题目要求）
 *
 * 时间复杂度为：O(n)
 * 空间辅助度为：O(n)
 *
 * More Methods: https://leetcode.com/problems/subarray-sum-equals-k/solution/
 */
class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        Map<Integer, Integer> map = new HashMap<>();
        // Initailize the values
        map.put(0, 1);  // very important
        int preSum = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            if (map.containsKey(preSum - k)) {
                count += map.get(preSum - k);
            }
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }
}