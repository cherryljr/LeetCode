/*
Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value. 
And you need to output the maximum average value.

Example 1:
Input: [1,12,-5,-6,50,3], k = 4
Output: 12.75
Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75

Note:
1 <= k <= n <= 30,000.
Elements of the given array will be in the range [-10,000, 10,000].
*/

This question is about subarray. 
We often use Prefix Sum(Cumulative Sum) / Forward-Backward Traverse / DP to solve problems about subarray.
So we consider that: Is that could be solve by Prefix Sum?

// Approach 1: Prefix Sum (Cumulative Sum)
Algorithm
	We know that in order to obtain the averages of subarrays with length k, we need to obtain the sum of these k length subarrays. 
	One of the methods of obtaining this sum is to make use of a prefix sum array. 
	Here, sum[i] is used to store the sum of the elements of the given nums array from the first element upto the element at the ith index.

	Once the sumsum array has been filled up, in order to find the sum of elements from the index i to i+k, 
	all we need to do is to use: sum[i] - sum[i-k]. 
	Thus, now, by doing one more iteration over the sum array, we can determine the maximum average possible from the subarrays of length k.
	A vividly Algorithm show here: https://leetcode.com/articles/maximum-average-subarray/
Complexity Analysis
	Time complexity  : O(n). We iterate over the numsnums array of length nn once to fill the sumsum array. 
					   Then, we iterate over n−k elements of sumsum to determine the required result.
	Space complexity : O(n). We make use of a sumsum array of length nn to store the cumulative sum.
	
// Code Below
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0d;
        }
        
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        
        double rst = sum[k - 1] * 1.0 / k;
        for (int i = k; i < nums.length; i++) {
            rst = Math.max(rst, (sum[i] - sum[i - k]) * 1.0 / k);
        }
        return rst;
    }
}

// Approach 2: Sliding Window
Algorithm
	Instead of creating a cumulative sum array first, and then traversing over it to determine the required sum, 
	we can simply traverse over numsnums just once, and on the go keep on determining the sums possible for the subarrays of length k. 
	To understand the idea, assume that we already know the sum of elements from index i to index i+k, say it is x.

	Now, to determine the sum of elements from the index i+1 to the index i+k+1, 
	all we need to do is to subtract the element nums[i] from x and to add the element nums[i+k+1] to x. 
	We can carry out our process based on this idea and determine the maximum possible average.
Complexity Analysis
	Time complexity  : O(n). We iterate over the given numsnums array of length n once only.
	Space complexity : O(1). Constant extra space is used.

// Code Below
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0d;
        }
        
        int sum = 0;
		// Sliding Window
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        
        double rst = sum * 1.0 / k;
        for (int i = k; i < nums.length; i++) {
            sum = sum + nums[i] - nums[i - k];
            rst = Math.max(rst, sum * 1.0 / k);
        }
        return rst;
    }
}