'''
Given a matrix, and a target, return the number of non-empty submatrices that sum to target.
A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.
Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different
if they have some coordinate that is different: for example, if x1 != x1'.

Example 1:
Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
Output: 4
Explanation: The four 1x1 submatrices that only contain 0.

Example 2:
Input: matrix = [[1,-1],[-1,1]], target = 0
Output: 5
Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.

Note:
    1. 1 <= matrix.length <= 300
    2. 1 <= matrix[0].length <= 300
    3. -1000 <= matrix[i] <= 1000
    4. -10^8 <= target <= 10^8
'''

# Approach: Turn to Subarray Sum Equals Target
# 时间复杂度：O(n^3)
# 空间复杂度：O(n^2)
# 解法详解参考同名 java 文件
class Solution:
    def numSubmatrixSumTarget(self, matrix: List[List[int]], target: int) -> int:
        rows, cols = len(matrix) + 1, len(matrix[0]) + 1
        preSum = [[0] * cols for _ in range(rows)]
        for i in range(1, rows):
            for j in range(1, cols):
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i - 1][j - 1]
        
        count = 0
        for top in range(rows):
            for down in range(top + 1, rows):
                c = collections.Counter({0 : 1})
                for k in range(1, cols):
                    s = preSum[down][k] - preSum[top][k]
                    count += c[s - target]
                    c[s] += 1
        return count