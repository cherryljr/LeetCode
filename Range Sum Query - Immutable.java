/*
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

Example:
Given nums = [-2, 0, 3, -5, 2, -1]

sumRange(0, 2) -> 1
sumRange(2, 5) -> -1
sumRange(0, 5) -> -3

Note:
You may assume that the array does not change.
There are many calls to sumRange function.
*/

/**
 * Approach: Prefix Sum
 */
class NumArray {
    private int[] preSum;

    public NumArray(int[] nums) {
        if (nums.length != 0) {
            preSum = new int[nums.length];
            
            preSum[0] = nums[0];
            for(int i = 1; i < nums.length; i++){
                preSum[i] = preSum[i - 1] + nums[i];
            }
        }
    }

    public int sumRange(int i, int j) {
        return i == 0 ? preSum[j] : preSum[j] - preSum[i - 1];
    }
}