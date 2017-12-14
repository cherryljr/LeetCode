/*
Given a non-empty array of non-negative integers nums, 
the degree of this array is defined as the maximum frequency of any one of its elements.
Your task is to find the smallest possible length of a (contiguous) subarray of nums, 
that has the same degree as nums.

Example 1:
Input: [1, 2, 2, 3, 1]
Output: 2
Explanation: 
The input array has a degree of 2 because both elements 1 and 2 appear twice.
Of the subarrays that have the same degree:
[1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
The shortest length is 2. So return 2.

Example 2:
Input: [1,2,2,3,1,4,2]
Output: 6

Note:
nums.length will be between 1 and 50,000.
nums[i] will be an integer between 0 and 49,999. 
*/

/**
 * 这是一题考察 HashMap 的简单题目
 * 对题目稍微分析，我们便可以发现：
 * 结果要求的最长子串的首元素一定是nums中出现最多的元素的第一个，而尾元素也一定是该元素的最后一个。
 * 这样问题就转换了为：
 * 求 nums 中出现次数最多的元素第一次出现，到最后一次出现的子串的最小长度。
 *
 * 那么我们只需要扫描一次 nums,并在扫描过程中用一个 map(命名为count) 记录各个元素目前出现的次数，
 * 在用一个 map(命名为startIndex) 记录各个元素第一次出现的位置，用于计算当前元素的子串长度。
 * 同时在循环中不断地判断是否需要更新当前的 最大出现次数frequency.
 * 如果最大次数变大了，那么相应的我们也需要更新 子串长度；
 * 如果当前元素的出现次数和最大出现次数相等，我们应该选择 子串长度更短的那个元素，
 * 即这里也可能需要更新 子串长度。
 *
 * 该算法仅仅需要扫描一次 nums 数组，而 map 的各个操作时间复杂度为 O(1).
 * 因此算法的时间复杂度为：O(n)； 空间复杂度为：O(n).
 */
class Solution {
    public int findShortestSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        // record the frequency of elements
        Map<Integer, Integer> count = new HashMap<>();
        // record the first occurrence of elements
        Map<Integer, Integer> startIndex = new HashMap<>();
        // the maximum frequency of any one of its elements
        int frequency = 0;
        // the smallest possible length of a contiguous subarray
        int len = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (!startIndex.containsKey(nums[i])) {
                startIndex.put(nums[i], i);
            }
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
            // if the current element's frequency is equals to the maximum frequency,
            // the length should be the shorter subarray's.
            if (count.get(nums[i]) == frequency) {
                len = Math.min( i - startIndex.get(nums[i]) + 1, len);
            // if the maximum frequency is changed,
            // the longest length must be updated, too.
            } else if (count.get(nums[i]) > frequency) {
                frequency = count.get(nums[i]);
                len = i - startIndex.get(nums[i]) + 1;
            }
        }

        return len;
    }
}
