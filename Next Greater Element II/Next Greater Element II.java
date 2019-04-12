/*
Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element.
The Next Greater Number of a number x is the first greater number to its traversing-order next in the array,
which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.

Example 1:
Input: [1,2,1]
Output: [2,-1,2]
Explanation:
The first 1's next greater number is 2;
The number 2 can't find next greater number;
The second 1's next greater number needs to search circularly, which is also 2.

Note:
    1. The length of given array won't exceed 10000.
 */

/**
 * Approach: Monotonic Stack (Break the Circle)
 * 这道题目算是 Next Greater Element I 的一个 Follow Up.
 * 把原来的一维数组首尾相连组成了一个环形。
 * 但是对于 环形数组 的处理方法，其实特别简单，我们只需要将其进行 断开（展开），即 将其连续遍历两遍。
 * （在原本数组的后面添上一个原本数组的拷贝，这样对新数组进行遍历的时候可以保证所有环内的区间都被遍历过一遍）
 * 其他方面与 Next Greater Element I 一模一样，没啥好说的，单调栈问题。
 *
 * 时间复杂度：O(2n) => O(n)
 * 空间复杂度：O(n)
 *
 * References:
 *  https://github.com/cherryljr/LeetCode/tree/master/Next%20Greater%20Element%20I
 */
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < 2 * n; i++) {
            while (!stack.isEmpty() && nums[i % n] > nums[stack.peek()]) {
                ans[stack.pop()] = nums[i % n];
            }
            stack.push(i % n);
        }
        return ans;
    }
}