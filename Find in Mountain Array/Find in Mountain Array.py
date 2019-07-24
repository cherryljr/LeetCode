'''
(This problem is an interactive problem.)
You may recall that an array A is a mountain array if and only if:
    · A.length >= 3
    · There exists some i with 0 < i < A.length - 1 such that:
        · A[0] < A[1] < ... A[i-1] < A[i]
        · A[i] > A[i+1] > ... > A[A.length - 1]
Given a mountain array mountainArr, return the minimum index such that mountainArr.get(index) == target.
If such an index doesn't exist, return -1.

You can't access the mountain array directly.  You may only access the array using a MountainArray interface:
    ·MountainArray.get(k) returns the element of the array at index k (0-indexed).
    ·MountainArray.length() returns the length of the array.
Submissions making more than 100 calls to MountainArray.get will be judged Wrong Answer.
Also, any solutions that attempt to circumvent the judge will result in disqualification.

Example 1:
Input: array = [1,2,3,4,5,3,1], target = 3
Output: 2
Explanation: 3 exists in the array, at index=2 and index=5. Return the minimum index, which is 2.

Example 2:
Input: array = [0,1,2,4,2,1], target = 3
Output: -1
Explanation: 3 does not exist in the array, so we return -1.

Constraints:
    1. 3 <= mountain_arr.length() <= 10000
    2. 0 <= target <= 10^9
    3. 0 <= mountain_arr.get(index) <= 10^9
'''

# Approach: Binary Search
# 时间复杂度：O(logn)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件

# """
# This is MountainArray's API interface.
# You should not implement it, or speculate about its implementation
# """
#class MountainArray:
#    def get(self, index: int) -> int:
#    def length(self) -> int:
class Solution:
    def findInMountainArray(self, target: int, mountain_arr: 'MountainArray') -> int:
        peakIndex, right = 0, mountain_arr.length() - 1
        while peakIndex < right:
            mid = peakIndex + (right - peakIndex >> 1)
            if mountain_arr.get(mid) < mountain_arr.get(mid + 1):
                peakIndex = mid + 1
            else:
                right = mid

        # left-subArray (Increasing)
        left, right = 0, peakIndex
        while left < right:
            mid = left + (right - left >> 1)
            if mountain_arr.get(mid) >= target:
                right = mid
            else:
                left = mid + 1
        if (mountain_arr.get(left) == target): return left
        
        # right-subArray (Decreasing)
        left, right = peakIndex, mountain_arr.length()
        while left < right:
            mid = left + (right - left >> 1)
            if mountain_arr.get(mid) <= target:
                right = mid
            else:
                left = mid + 1
        return left if left < mountain_arr.length() and mountain_arr.get(left) == target else -1