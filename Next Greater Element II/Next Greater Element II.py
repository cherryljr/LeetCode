'''
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
'''

# Approach: Monotonic Stack
# 解法详解参考同名 java 文件
class Solution:
    def nextGreaterElements(self, nums: List[int]) -> List[int]:
        n = len(nums)
        ans, stack = [-1] * n, []
        for i in range(2 * n):
            while stack and nums[i % n] > nums[stack[-1]]:
                ans[stack.pop()] = nums[i % n]
            stack.append(i % n)
        return ans