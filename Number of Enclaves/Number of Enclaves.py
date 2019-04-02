'''
Given a 2D array A, each cell is 0 (representing sea) or 1 (representing land)
A move consists of walking from one land square 4-directionally to another land square, or off the boundary of the grid.

Return the number of land squares in the grid for which we cannot walk off the boundary of the grid in any number of moves.

Example 1:
Input: [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
Output: 3
Explanation: 
There are three 1s that are enclosed by 0s, and one 1 that isn't enclosed because its on the boundary.

Example 2:
Input: [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
Output: 0
Explanation: 
All 1s are either on the boundary or can reach the boundary.

Note:
    1. 1 <= A.length <= 500
    2. 1 <= A[i].length <= 500
    3. 0 <= A[i][j] <= 1
    4. All rows have the same size.
'''

#
class Solution:
    def numEnclaves(self, A: List[List[int]]) -> int:
        rows, cols = len(A), len(A[0])

        def dfs(x, y):
            if x < 0 or x >= rows or y < 0 or y >= cols or A[x][y] != 1:
                return

            A[x][y] = 0
            dfs(x, y + 1), dfs(x, y - 1), dfs(x - 1, y), dfs(x + 1, y)

        for i in range(rows):
            dfs(i, 0), dfs(i, cols - 1)
        for j in range(1, cols - 1):
            dfs(0, j), dfs(rows - 1, j)

        return sum([sum(A[i]) for i in range(rows)])