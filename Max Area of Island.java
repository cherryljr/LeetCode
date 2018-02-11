/*
Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) 
You may assume all four edges of the grid are surrounded by water.
Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

Example 1:
[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.

Example 2:
[[0,0,0,0,0,0,0,0]]
Given the above grid, return 0.
*/
    
/**
 * Approach: DFS
 * The idea is to count the area of each island using DFS.
 * During the dfs, we set the value of each point in the island to 2.
 * The time complexity is O(mn).
 * 
 * This question is the same as Number of Big Islands almost.
 * You can get detail explanations here:
 * https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Big%20Islands.java
 */
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0 ||
                grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rst = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    int area = infect(grid, i, j, rows, cols, 0);
                    rst = Math.max(rst, area);
                }
            }
        }

        return rst;
    }

    // DFS
    private int infect(int[][] grid, int i, int j, int rows, int cols, int area) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != 1) {
            return area;
        }

        // Mark the explored island cells with 2.
        grid[i][j] = 2;
        area++;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            area = infect(grid, i + dir[0], j + dir[1], rows, cols, area);
        }

        return area;
    }
}