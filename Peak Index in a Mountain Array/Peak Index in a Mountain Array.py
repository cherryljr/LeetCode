'''
Let's call an array A a mountain if the following properties hold:
    ·A.length >= 3
    ·There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
Given an array that is definitely a mountain, return any i such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].

Example 1:
Input: [0,1,0]
Output: 1

Example 2:
Input: [0,2,1,0]
Output: 1

Note:
    1. 3 <= A.length <= 10000
    2. 0 <= A[i] <= 10^6
    3. A is a mountain, as defined above.
'''

# Approach: Binary Search
# 时间复杂度：O(logn)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def peakIndexInMountainArray(self, A: List[int]) -> int:
        left, right = 0, len(A)
        while left < right:
            mid = left + (right - left >> 1)
            if A[mid] < A[mid + 1]:
                left = mid + 1
            else:
                right = mid
        return left
        # Approach 2: Using Python's API
        # return A.index(max(A))