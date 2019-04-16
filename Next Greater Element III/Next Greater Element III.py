'''
Given a positive 32-bit integer n, you need to find the smallest 32-bit integer
which has exactly the same digits existing in the integer n and is greater in value than n.
If no such positive 32-bit integer exists, you need to return -1.

Example 1:
Input: 12
Output: 21

Example 2:
Input: 21
Output: -1
'''

# Approach: Swap and Reverse
# 解法详解参考同名 java 文件
class Solution:
    def nextGreaterElement(self, n: int) -> int:
        nums = [c for c in str(n)]
        index = len(nums) - 2
        while index >= 0 and nums[index] >= nums[index + 1]:
            index -= 1
        
        if index < 0:
            return -1
        larger = len(nums) - 1
        while larger > 0 and nums[larger] <= nums[index]:
            larger -= 1
        # Swap 
        nums[index], nums[larger] = nums[larger], nums[index]
        index += 1
        # Reverse
        nums[index:] = reversed(nums[index:])
        
        ans = int(''.join(nums))
        return ans if ans <= ((1 << 31) - 1) else -1