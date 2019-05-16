'''
Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise order.
Suppose you triangulate the polygon into N-2 triangles.
For each triangle, the value of that triangle is the product of the labels of the vertices,
and the total score of the triangulation is the sum of these values over all N-2 triangles in the triangulation.

Return the smallest possible total score that you can achieve with some triangulation of the polygon.

Example 1:
Input: [1,2,3]
Output: 6
Explanation: The polygon is already triangulated, and the score of the only triangle is 6.

Example 2:
Input: [3,7,4,5]
Output: 144
Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The minimum score is 144.

Example 3:
Input: [1,3,1,4,1,5]
Output: 13
Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.

Note:
    1. 3 <= A.length <= 50
    2. 1 <= A[i] <= 100
'''

# Approach: Interval DP
# 时间复杂度：O(n^3)
# 空间复杂度：O(n^2)
# 解法详解参考同名 java 文件
class Solution:
    def minScoreTriangulation(self, A: List[int]) -> int:
        n = len(A)
        dp = [[0] * n for _ in range(n)]
        for l in range(3, n + 1):
            for start in range(n - l + 1):
                end = start + l - 1
                dp[start][end] = min(dp[start][pivot] + dp[pivot][end] + A[start] * A[pivot] * A[end] for pivot in range(start + 1, end))
        return dp[0][n - 1]