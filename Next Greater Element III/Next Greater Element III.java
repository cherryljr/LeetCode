/*
Given a positive 32-bit integer n, you need to find the smallest 32-bit integer
which has exactly the same digits existing in the integer n and is greater in value than n.
If no such positive 32-bit integer exists, you need to return -1.

Example 1:
Input: 12
Output: 21

Example 2:
Input: 21
Output: -1
 */

/**
 * Approach: Swap and Reverse (Similar to Next Permutation)
 * 这道题目与 Next Permutation 基本相同。
 * 唯一的区别就是当不存在更大的组合时，直接 return -1. (而在Next Permutation中是一个 loop)
 * 注意这道题目有个坑：要求 greater element 不能大于 Integer.MAX_VALUE...
 * 因此对于 1999999999 应该返回 -1 才对，而不是 9199999999.
 * 其他如果还有不清楚的可以参考 Next Permutation 中的解释
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Next Permutation:
 *  https://github.com/cherryljr/LeetCode/blob/master/Next%20Permutation.java
 */
class Solution {
    public int nextGreaterElement(int n) {
        char[] nums = (n + "").toCharArray();
        int index = nums.length - 2;
        // find the first pair of two successive numbers nums[index] and nums[index + 1]
        // from the right, which satisfy nums[index] < [index + 1]
        while (index >= 0 && nums[index] >= nums[index + 1]) {
            index--;
        }
        if (index < 0) {
            return -1;
        }

        // swap the number nums[index] and the number a[larger] which is just larger than itself 
        int larger = nums.length - 1;
        while (larger > 0 && nums[larger] <= nums[index]) {
            larger--;
        }
        swap(nums, index, larger);
        // reverse the numbers following a[index] to get the next smallest lexicographic permutation
        return reverse(nums, index + 1);
    }

    private void swap(char[] nums, int i, int j) {
        char temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private int reverse(char[] nums, int start) {
        int end = nums.length - 1;
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }

        long num = Long.valueOf(String.valueOf(nums));
        return num > Integer.MAX_VALUE ? -1 : (int)num;
    }

}