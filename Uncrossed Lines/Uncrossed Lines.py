'''
We write the integers of A and B (in the order they are given) on two separate horizontal lines.
Now, we may draw a straight line connecting two numbers A[i] and B[j] as long as A[i] == B[j],
and the line we draw does not intersect any other connecting (non-horizontal) line.

Return the maximum number of connecting lines we can draw in this way.

Example 1:
https://leetcode.com/problems/uncrossed-lines/
Input: A = [1,4,2], B = [1,2,4]
Output: 2
Explanation: We can draw 2 uncrossed lines as in the diagram.
We cannot draw 3 uncrossed lines, because the line from A[1]=4 to B[2]=4 will intersect the line from A[2]=2 to B[1]=2.

Example 2:
Input: A = [2,5,1,2,5], B = [10,5,2,1,5,2]
Output: 3

Example 3:
Input: A = [1,3,7,1,7,5], B = [1,9,2,5,1]
Output: 2

Note:
    1. 1 <= A.length <= 500
    2. 1 <= B.length <= 500
    3. 1 <= A[i], B[i] <= 2000
'''

# Approach: Sequence DP (Same as Longest Common SubSequence)
# 时间复杂度：O(M * N)
# 空间复杂度：O(M * N)
# 解法详解参考同名 java 文件
class Solution:
    def maxUncrossedLines(self, A: List[int], B: List[int]) -> int:
        m, n = len(A) + 1, len(B) + 1
        dp = [[0] * n for _ in range(m)]
        for i in range(1, m):
            for j in range(1, n):
                # dp[i][j] = max(dp[i - 1][j - 1] + (A[i - 1] == B[j - 1]), dp[i - 1][j], dp[i][j - 1])
                if A[i - 1] == B[j - 1]:
                    dp[i][j] = dp[i - 1][j - 1] + 1
                else:
                    dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
        return dp[m - 1][n - 1]
        