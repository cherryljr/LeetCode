/*
Given a sorted array consisting of only integers where every element appears twice except for one element which appears once.
Find this single element that appears only once.

Example 1:
Input: [1,1,2,3,3,4,4,8,8]
Output: 2
Example 2:
Input: [3,3,7,7,10,11,11]
Output: 10

Note: Your solution should run in O(log n) time and O(1) space.
 */

/**
 * Approach: Binary Search
 * LintCode 上的 Single Number IV 换了个问法罢了。
 * 实质就在于：
 *  1. 所有相同的元素都是相邻的
 *  2. 重复元素出现次数必定为 2 次
 *
 * 具体解析可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Single%20Number%20IV.java
 *  https://www.youtube.com/watch?v=uJa9Q-05JxY
 */
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            int index = mid ^ 1;
            if (nums[mid] == nums[index]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
}