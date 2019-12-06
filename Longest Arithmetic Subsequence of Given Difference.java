/*
Given an integer array arr and an integer difference,
return the length of the longest subsequence in arr which is an arithmetic sequence
such that the difference between adjacent elements in the subsequence equals difference.

Example 1:
Input: arr = [1,2,3,4], difference = 1
Output: 4
Explanation: The longest arithmetic subsequence is [1,2,3,4].

Example 2:
Input: arr = [1,3,5,7], difference = 1
Output: 1
Explanation: The longest arithmetic subsequence is any single element.

Example 3:
Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
Output: 4
Explanation: The longest arithmetic subsequence is [7,5,3,1].

Constraints:
    1. 1 <= arr.length <= 10^5
    2. -10^4 <= arr[i], difference <= 10^4
*/

/**
 * Approach: Pre HashMap
 * SubSequence问题，解决方法不外乎 DP, Two Pointers, HashMap, Stack。
 * 这里很明显就是 PreSum + HashMap 考点的一个简化版本。（因为不要求连续了，所以不用使用 PreSum）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Remove%20Zero%20Sum%20Consecutive%20Nodes%20from%20Linked%20List.java
 */
class Solution {
    public int longestSubsequence(int[] arr, int difference) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 1;
        for (int i = 0; i < arr.length; i++) {
            int len = map.getOrDefault(arr[i] - difference, 0) + 1;
            map.put(arr[i], len);
            ans = Math.max(ans, len);
        }
        return ans;
    }
}