/*
Given an array nums of integers, a move consists of choosing any element and decreasing it by 1.
An array A is a zigzag array if either:
    ·Every even-indexed element is greater than adjacent elements, ie. A[0] > A[1] < A[2] > A[3] < A[4] > ...
    ·OR, every odd-indexed element is greater than adjacent elements, ie. A[0] < A[1] > A[2] < A[3] > A[4] < ...
Return the minimum number of moves to transform the given array nums into a zigzag array.

Example 1:
Input: nums = [1,2,3]
Output: 2
Explanation: We can decrease 2 to 0 or 3 to 1.

Example 2:
Input: nums = [9,6,1,6,2]
Output: 4

Constraints:
    1. 1 <= nums.length <= 1000
    2. 1 <= nums[i] <= 1000
 */

/**
 * Approach: Two Pointers
 * Two options, either make A[even] smaller or make A[odd] smaller.
 * Loop on the whole array A, find the min(A[i - 1],A[i + 1]),
 * calculate that the moves need to make smaller than both side.
 * If it's negative, it means it's already smaller than both side, no moved needed.
 * Add the moves need to odd or even. In the end return the smaller option.
 *
 * Complexity
 *  Time Complexity: O(N) for one pass
 *  Space Complexity: O(2) for two options
 *
 * Similar Question:
 *  https://github.com/cherryljr/LintCode/blob/master/Wiggle%20Sort.java
 */
class Solution {
    public int movesToMakeZigzag(int[] nums) {
        // odd is the ans of odd index's value is smaller than neighbors
        // even is the ans of even index's value is smaller than neighbors
        int odd = 0, even = 0;
        int left = 0, right = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            left = i == 0 ? Integer.MAX_VALUE : nums[i - 1];
            right = i == n - 1 ? Integer.MAX_VALUE : nums[i + 1];
            if (i % 2 == 0) {
                even += Math.max(0, nums[i] - Math.min(left, right) + 1);
            } else {
                odd += Math.max(0, nums[i] - Math.min(left, right) + 1);
            }
        }
        return Math.min(odd, even);
    }
}