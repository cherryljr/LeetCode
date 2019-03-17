/*
Given a binary array, find the maximum number of consecutive 1s in this array.

Example 1:
Input: [1,1,0,1,1,1]
Output: 3
Explanation: 
The first two digits or the last three digits are consecutive 1s.
The maximum number of consecutive 1s is 3.

Note:
    1. The input array will only contain 0 and 1.
    2. The length of input array is a positive integer and will not exceed 10,000
 */

/**
 * Approach: Traverse (Easy)
 * 直接遍历就好了...难度很低...不知道说啥好...
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int ans = 0, count = 0;
        for (int num : nums) {
            if (num == 1) {
                ans = Math.max(ans, ++count);
            } else {
                count = 0;
            }
        }
        return ans;
    }
}