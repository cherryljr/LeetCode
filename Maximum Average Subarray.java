看到 Subarray 的题目，首先需要考虑的就是要使用到 前缀和 这个参数。
思路：
	首先计算出前 k 个subarray的和作为初始的max值。
	然后将该大小为 k 的窗口向后滑动，保证其 sum 为最大值即可。

/*
Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value. And you need to output the maximum average value.

Example 1:
Input: [1,12,-5,-6,50,3], k = 4
Output: 12.75
Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75

Note:
1 <= k <= n <= 30,000.
Elements of the given array will be in the range [-10,000, 10,000].
*/

class Solution {
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        // Sliding Window
        int max = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            max = Math.max(sum, max);
        }
        // Change it to double
        return max / 1.0 / k;
    }
}