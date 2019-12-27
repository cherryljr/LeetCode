/*
Given an array of integers nums and an integer k. A subarray is called nice if there are k odd numbers on it.
Return the number of nice sub-arrays.

Example 1:
Input: nums = [1,1,2,1,1], k = 3
Output: 2
Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

Example 2:
Input: nums = [2,4,6], k = 1
Output: 0
Explanation: There is no odd numbers in the array.

Example 3:
Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
Output: 16

Constraints:
    1. 1 <= nums.length <= 50000
    2. 1 <= nums[i] <= 10^5
    3. 1 <= k <= nums.length
*/

/**
 * Approach: Sliding Window with Reduction
 * 与问题 Subarrays with K Different Integers 非常类似。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Subarrays%20with%20K%20Different%20Integers.java
 *
 * PS. 这道问题还可以通过将 偶数看作0，奇数看作1。从而将问题转换成：求SubArray Sum等于 K 的子数组个数。
 * 对此使用 PreSum + HashMap 即可解决。但是明显这不是本题想要考察的，且空间复杂度也非最优，这里大家只需要知道即可。
 */
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        return slidingWindow(nums, k) - slidingWindow(nums, k - 1);
    }
    
    private int slidingWindow(int[] nums, int k) {
        int count = 0, ans = 0;
        for (int begin = 0, end = 0; end < nums.length; end++) {
            if ((nums[end] & 1) == 1) {
                count++;
            }
            while (count > k) {
                if ((nums[begin++] & 1) == 1) {
                    count--;
                }
            }
            ans += end - begin + 1;
        }
        return ans;
    }
}