'''
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent,
the "Pacific ocean" touches the left and top edges of the matrix
and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:
    1. The order of returned grid coordinates does not matter.
    2. Both m and n are less than 150.

Example:
Given the following 5x5 matrix:
  Pacific ~   ~   ~   ~   ~
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic
Return:
[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
'''

# Approach: DFS
# 解法详解参考同名 java 文件
# DFS比BFS写着简单一些，所以这里就只给出DFS的解法啦~
# BFS的解法以后随缘补上...
class Solution:
    def pacificAtlantic(self, matrix: List[List[int]]) -> List[List[int]]:
        if not matrix or not matrix[0]:
            return[]
        
        m, n = len(matrix), len(matrix[0])
        pacificVisited = [[False] * n for _ in range(m)]
        atlanticVisited = [[False] * n for _ in range(m)]
        
        for i in range(m):
            self.dfs(matrix, pacificVisited, -0x3f3f3f3f, i, 0)
            self.dfs(matrix, atlanticVisited, -0x3f3f3f3f, i, n - 1);
        for j in range(n):
            self.dfs(matrix, pacificVisited, -0x3f3f3f3f, 0, j)
            self.dfs(matrix, atlanticVisited, -0x3f3f3f3f, m - 1, j)
        
        return [[i, j] for i in range(m) for j in range(n) if pacificVisited[i][j] and atlanticVisited[i][j]]
        
        
    def dfs(self, matrix, visited, preHeight, x, y):
        if x < 0 or y < 0 or x >= len(matrix) or y >= len(matrix[0]) or visited[x][y] or matrix[x][y] < preHeight:
            return
        
        visited[x][y] = True
        DIRS = [(1, 0), (-1, 0), (0, 1), (0, -1)]
        for d in DIRS:
            self.dfs(matrix, visited, matrix[x][y], x + d[0], y + d[1]) 