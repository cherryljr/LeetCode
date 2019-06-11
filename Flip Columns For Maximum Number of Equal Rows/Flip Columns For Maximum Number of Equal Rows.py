'''
Given a matrix consisting of 0s and 1s, we may choose any number of columns in the matrix and flip every cell in that column.
Flipping a cell changes the value of that cell from 0 to 1 or from 1 to 0.

Return the maximum number of rows that have all values equal after some number of flips.

Example 1:
Input: [[0,1],[1,1]]
Output: 1
Explanation: After flipping no values, 1 row has all values equal.

Example 2:
Input: [[0,1],[1,0]]
Output: 2
Explanation: After flipping values in the first column, both rows have equal values.

Example 3:
Input: [[0,0,0],[0,0,1],[1,1,0]]
Output: 2
Explanation: After flipping values in the first two columns, the last two rows have equal values.

Note:
    1. 1 <= matrix.length <= 300
    2. 1 <= matrix[i].length <= 300
    3. All matrix[i].length's are equal
    4. matrix[i][j] is 0 or 1
'''

# Approach 1: Find the Most Frequent Pattern and Opposite Pattern
# 时间复杂度：O(N*M)
# 空间复杂度：O(N*M)
# 解法详解参考同名 java 文件
class Solution:
    def maxEqualRowsAfterFlips(self, matrix: List[List[int]]) -> int:
        d = collections.defaultdict(int)
        for r in matrix:
            d[tuple(r)] += 1
            d[tuple([1 ^ i for i in r])] += 1
        return max(d.values())

# Approach 2: Python in One Line
class Solution:
    def maxEqualRowsAfterFlips(self, matrix: List[List[int]]) -> int:
        return max(collections.Counter(tuple(x ^ r[0] for x in r) for r in matrix).values())