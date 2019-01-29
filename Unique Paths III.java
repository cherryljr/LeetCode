/*
On a 2-dimensional grid, there are 4 types of squares:
1 represents the starting square.  There is exactly one starting square.
2 represents the ending square.  There is exactly one ending square.
0 represents empty squares we can walk over.
-1 represents obstacles that we cannot walk over.
Return the number of 4-directional walks from the starting square to the ending square,
that walk over every non-obstacle square exactly once.

Example 1:
Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
Output: 2
Explanation: We have the following two paths:
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)

Example 2:
Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
Output: 4
Explanation: We have the following four paths:
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)

Example 3:
Input: [[0,1],[2,0]]
Output: 0
Explanation:
There is no path that walks over every empty square exactly once.
Note that the starting and ending square can be anywhere in the grid.

Note:
    1 <= grid.length * grid[0].length <= 20
 */

/**
 * Approach: DFS (Backtracking)
 * 根据数据规模，可以推测出本题的算法应该在指数级别，所以可以使用 DFS 来解决。
 * 因为要求图中的每个空位都必须走过，且只能走一次。
 * 所以我们需要记录还剩余多少个空位没走。
 * 然后利用这个数值就能够判断是否走完全部空位。
 * 这边注意：因为起始位置也是可以走的，所以 empty 在初始化的时候值应该为 1。
 *
 * 时间复杂度：O(4^(rows*cols))
 * 空间复杂度：O(rows*cols)
 */
class Solution {
    int res = 0;

    public int uniquePathsIII(int[][] grid) {
        int startX = 0, startY = 0, empty = 1;
        int rows = grid.length, cols = grid[0].length;
        // 找到图的中起点和终点，并统计空位数量
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    startX = i;
                    startY = j;
                } else if (grid[i][j] == 0) {
                    empty++;
                }
            }
        }

        dfs(grid, startX, startY, empty);
        return res;
    }

    private void dfs(int[][] grid, int x, int y, int empty) {
        // 检查合理性（递归的终止条件）
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] < 0) {
            return;
        }
        // 当到达终点，并且每个空位都走过一遍的话，结果方法数+1
        if (grid[x][y] == 2 && empty == 0) {
            res++;
            return;
        }

        // 标记该位置已经走过（工程上修改input并不是一个很好的做法，大家也可以使用一个visited数组来做）
        int temp = grid[x][y];
        grid[x][y] = -2;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            // 往四个方向进行dfs，同时空位数目-1
            dfs(grid, x + dir[0], y + dir[1], empty - 1);
        }
        // Backtracking
        grid[x][y] = temp;
    }
}