'''
Given an array A of positive integers (not necessarily distinct), 
return the lexicographically largest permutation that is smaller than A, 
that can be made with one swap (A swap exchanges the positions of two numbers A[i] and A[j]).  
If it cannot be done, then return the same array.

Example 1:
Input: [3,2,1]
Output: [3,1,2]
Explanation: Swapping 2 and 1.

Example 2:
Input: [1,1,5]
Output: [1,1,5]
Explanation: This is already the smallest permutation.

Example 3:
Input: [1,9,4,6,7]
Output: [1,7,4,6,9]
Explanation: Swapping 9 and 7.

Example 4:
Input: [3,1,1,3]
Output: [1,3,1,3]
Explanation: Swapping 1 and 3.

Note:
    1. 1 <= A.length <= 10000
    2. 1 <= A[i] <= 10000
'''

# Approach: Traverse and Swap (Similar to Next Permutation)
# 时间复杂度：O(n)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def prevPermOpt1(self, A: List[int]) -> List[int]:
        if not A or len(A) <= 1:
            return A

        n, index = len(A), len(A) - 2
        while index >= 0 and A[index] <= A[index + 1]:
            index -= 1
        if index >= 0:
            smaller = n - 1
            while smaller >= 0 and A[smaller] >= A[index]:
                smaller -= 1
                while smaller >= 1 and A[smaller] == A[smaller - 1]:
                    smaller -= 1
            A[index], A[smaller] = A[smaller], A[index]
        return A