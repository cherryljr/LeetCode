'''
Given a 2-dimensional grid of integers, each value in the grid represents the color of the grid square at that location.
Two squares belong to the same connected component if and only if they have the same color and are next to each other in any of the 4 directions.
The border of a connected component is all the squares in the connected component that are either 4-directionally
adjacent to a square not in the component, or on the boundary of the grid (the first or last row or column).

Given a square at location (r0, c0) in the grid and a color,
color the border of the connected component of that square with the given color, and return the final grid.

Example 1:
Input: grid = [[1,1],[1,2]], r0 = 0, c0 = 0, color = 3
Output: [[3, 3], [3, 2]]

Example 2:
Input: grid = [[1,2,2],[2,3,2]], r0 = 0, c0 = 1, color = 3
Output: [[1, 3, 3], [2, 3, 3]]

Example 3:
Input: grid = [[1,1,1],[1,1,1],[1,1,1]], r0 = 1, c0 = 1, color = 2
Output: [[2, 2, 2], [2, 1, 2], [2, 2, 2]]

Note:
    1. 1 <= grid.length <= 50
    2. 1 <= grid[0].length <= 50
    3. 1 <= grid[i][j] <= 1000
    4. 0 <= r0 < grid.length
    5. 0 <= c0 < grid[0].length
    6. 1 <= color <= 1000
'''

# Approach 1: DFS (Similar to Flood Fill)
# 时间复杂度：O(M*N)
# 空间复杂度：O(M+N)
# 解法详解参考同名 java 文件
class Solution:
    def colorBorder(self, grid: List[List[int]], r0: int, c0: int, color: int) -> List[List[int]]:
        self.floodFill(grid, r0, c0, grid[r0][c0])
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] < 0:
                    grid[i][j] = color
        return grid
        
    def floodFill(self, grid: List[List[int]], r: int, c: int, color: int):
        m, n = len(grid), len(grid[0])
        if r < 0 or r >= m or c < 0 or c >= n or grid[r][c] != color:
            return
        grid[r][c] = -color

        self.floodFill(grid, r, c - 1, color)
        self.floodFill(grid, r, c + 1, color)
        self.floodFill(grid, r - 1, c, color)
        self.floodFill(grid, r + 1, c, color)

        if 0 < r < m - 1 and 0 < c < n - 1:
            if abs(grid[r][c - 1]) == color and abs(grid[r][c + 1]) == color and abs(grid[r - 1][c]) == color and abs(grid[r + 1][c]) == color:
                grid[r][c] = color


# Approach 2: BFS
# 时间复杂度：O(M*N)
# 空间复杂度：O(M*N)
# 解法详解参考同名 java 文件
class Solution:
    def colorBorder(self, grid: List[List[int]], r0: int, c0: int, color: int) -> List[List[int]]:
        m, n = len(grid), len(grid[0])
        queue, visited, border = [[r0, c0]], set([(r0, c0)]), set()
        for r, c in queue:
            for i, j in [[0, -1], [0, 1], [-1, 0], [1, 0]]:
                nextR, nextC = r + i, c + j
                if nextR < 0 or nextR >= m or nextC < 0 or nextC >= n or grid[nextR][nextC] != grid[r][c]:
                    border.add((r, c))
                    continue
                if (nextR, nextC) not in visited:
                    queue.append([nextR, nextC])
                    visited.add((nextR, nextC))
        
        for r, c in border: grid[r][c] = color
        return grid