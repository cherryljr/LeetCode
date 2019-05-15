'''
A boomerang is a set of 3 points that are all distinct and not in a straight line.
Given a list of three points in the plane, return whether these points are a boomerang.

Example 1:
Input: [[1,1],[2,3],[3,2]]
Output: true

Example 2:
Input: [[1,1],[2,2],[3,3]]
Output: false

Note:
    1. points.length == 3
    2. points[i].length == 2
    3. 0 <= points[i][j] <= 100
'''

# Approach: Mathematics (slope of 2 lines are not equal)
# 时间复杂度：O(1)
# 空间复杂度：O(1)
# 解法详解参考同名 java 文件
class Solution:
    def isBoomerang(self, p: List[List[int]]) -> bool:
        return (p[0][0] - p[2][0]) * (p[0][1] - p[1][1]) != (p[0][0] - p[1][0]) * (p[0][1] - p[2][1]) 