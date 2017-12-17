/*
Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].

Note:
You must do this in-place without making a copy of the array.
Minimize the total number of operations.
 */

/**
 * Approach 1: traverse all of the array. (sub-optimal)
 * After the index i reaches the end of array,
 * we now know that all the non-0 elements have been moved to beginning of array in their original order.
 * Now comes the time to fulfil other requirement, "Move all 0's to the end".
 * We now simply need to fill all the indexes after the "lastNonZeroFoundAt" index with 0.
 *
 * Complexity Analysis
 * Space Complexity : O(1). Only constant space is used.
 * Time Complexity: O(n).
 * However, the total number of operations are still sub-optimal.
 * The total operations (array writes) that code does is n (Total number of elements).
 */
class Solution {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        // If the current element is not 0, then we need to
        // append it just in front of last non 0 element we found.
        int lastNonZeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[lastNonZeroIndex++] = nums[i];
            }
        }

        // After we have finished processing new elements,
        // all the non-zero elements are already at beginning of array.
        // We just need to fill remaining array with 0's.
        for (int i = lastNonZeroIndex; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}

/**
 * Approach 2: Optimal
 * the code maintain the following invariant:
 *  1. All elements before the slow pointer (lastNonZeroIndex) are non-zeroes.
 *  2. All elements between the current and slow pointer are zeroes.
 *  Therefore, when we encounter a non-zero element, we need to swap elements pointed by current and slow pointer,
 *  then advance both pointers. If it's zero element, we just advance current pointer.
 *
 *  Complexity Analysis
 *  Space Complexity : O(1). Only constant space is used.
 *  Time Complexity: O(n).
 *  However, the total number of operations are optimal.
 *  The total operations (array writes) that code does is Number of non-0 elements.
 *  This gives us a much better best-case (when most of the elements are 0) complexity than last solution.
 *  However, the worst-case (when all elements are non-0) complexity for both the algorithms is same.
 */
class Solution {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int lastNonZeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                swap(nums, lastNonZeroIndex++, i);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}