'''
Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.
After partitioning, each subarray has their values changed to become the maximum value of that subarray.

Return the largest sum of the given array after partitioning.

Example 1:
Input: A = [1,15,7,9,2,5,10], K = 3
Output: 84
Explanation: A becomes [15,15,15,9,10,10,10]

Note:
    1. 1 <= K <= A.length <= 500
    2. 0 <= A[i] <= 10^6
'''

# Approach: DP
# 时间复杂度：O(n^2)
# 空间复杂度：O(n)
# 解法详解参考同名 java 文件
class Solution:
    def maxSumAfterPartitioning(self, A: List[int], K: int) -> int:
        n = len(A)
        dp =[0] * (n + 1)
        for i in range(1, n + 1):
            currMax = 0
            for k in range(1, min(i, K) + 1):
                currMax = max(currMax, A[i - k])
                dp[i] = max(dp[i], dp[i - k] + currMax * k)
        return dp[n]