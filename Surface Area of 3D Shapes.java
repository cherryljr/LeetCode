/*
On a N * N grid, we place some 1 * 1 * 1 cubes.
Each value v = grid[i][j] represents a tower of v cubes placed on top of grid cell (i, j).
Return the total surface area of the resulting shapes.

Example 1:
Input: [[2]]
Output: 10

Example 2:
Input: [[1,2],[3,4]]
Output: 34

Example 3:
Input: [[1,0],[0,2]]
Output: 16

Example 4:
Input: [[1,1,1],[1,0,1],[1,1,1]]
Output: 32

Example 5:
Input: [[2,2,2],[2,1,2],[2,2,2]]
Output: 46

Note:
1 <= N <= 50
0 <= grid[i][j] <= 50
 */

/**
 * Approach: Mathematics
 * 计算岛屿面积（表面积）的 3D 版本。
 * 同样根据求表面积的方法进行数学分析即可。
 *
 * 当 grid[i][j] > 0 时，说明该位置上存在一个 tower,因此首先加上 上表面 和 下表面 的面积 2.
 * 然后在分析旁边四个面的面积。
 * 我们不妨先将它们加起来，当存在两个相邻 tower 的时候，相邻部分的面积因为重合的原因需要减去。
 * 那么减去的面积就是 2 * Math.min(grid[i][j], neigh)
 * 这里 乘以2 是因为这部分的面积我们计算了两次，但是结果中它是不存在的。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 *
 * 该问题的 2D 版本：
 * Island Perimeter:
 *  https://github.com/cherryljr/LeetCode/blob/master/Island%20Perimeter.java
 */
class Solution {
    public int surfaceArea(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 如果该位置存在 tower
                if (grid[i][j] > 0) {
                    result += 2 + 4 * grid[i][j];
                }
                // 减去重合部分的面积
                if (i > 0) {
                    result -= Math.min(grid[i][j], grid[i - 1][j]) * 2;
                }
                if (j > 0) {
                    result -= Math.min(grid[i][j], grid[i][j - 1]) * 2;
                }
            }
        }
        return result;
    }
}