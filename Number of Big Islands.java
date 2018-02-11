/*
Description
Given a boolean 2D matrix, 0 is represented as the sea, 1 is represented as the island. 
If two 1 is adjacent, we consider them in the same island. 
We only consider up/down/left/right adjacent.

Find the number of islands that size bigger or equal than K.

Example
Given graph:
[
  [1, 1, 0, 0, 0],
  [0, 1, 0, 0, 1],
  [0, 0, 0, 1, 1],
  [0, 0, 0, 0, 0],
  [0, 0, 0, 0, 1]
]
Given the K = 2

return 2
*/

/**
 * Approach 1: DFS
 * 与最基础的 Numbers of Island 相比，我们只需要多维护一个 面积area 的参数即可。
 * 做法 Max Area of Island 基本相同
 * https://github.com/cherryljr/LeetCode/blob/master/Max%20Area%20of%20Island.java
 * 主要是使用了 DFS 算法进行 infection.
 * 一点小小的区别就是 本题使用了 visited 数组来记录已经遍历过的岛屿。
 * 但是在 Max Area of Island 中，直接对 grid 进行了修改来区分是否已经遍历过该岛屿。
 * 两种方式均可，本题也可以直接通过将遍历过的 grid 修改成 false 来达到目的 (代码见 Approach2).
 * 虽然节省了 O(mn) 的空间，但是修改了原数据。大家还是根据实际情况取舍吧。
 */
public class Solution {
    // 用于标记已经遍历过的岛屿
    boolean[][] visited;

    /*
     * @param : a 2d boolean array
     * @param : an integer
     * @return: the number of Islands
     */
    public int numsofIsland(boolean[][] grid, int k) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        visited = new boolean[grid.length][grid[0].length];
        int rst = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // 当岛屿面积大于等于 k 时，结果加1
                if (grid[i][j] == true && dfs(grid, i, j, 0) >= k) {
                    rst++;
                }
            }
        }

        return rst;
    }

    private int dfs(boolean[][] grid, int i, int j, int area) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length
                || grid[i][j] == false || visited[i][j] == true) {
            return area;
        }

        visited[i][j] = true;
        // 一旦遇到一个 grid[i][j] 为 true 的单位，则岛屿面积加1
        area++;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        // 向四周 infect 看岛屿面积能否继续增加
        for (int[] dir : dirs) {
            area = dfs(grid, i + dir[0], j + dir[1], area);
        }

        return area;
    }
}

/**
 * Approach 2: DFS (Space Optimized)
 * 直接对原数据进行修改以区分岛屿是否已经被遍历过。
 */
public class Solution {
    /*
     * @param : a 2d boolean array
     * @param : an integer
     * @return: the number of Islands
     */
    public int numsofIsland(boolean[][] grid, int k) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rst = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // 当岛屿面积大于等于 k 时，结果加1
                if (grid[i][j] == true && dfs(grid, i, j, 0) >= k) {
                    rst++;
                }
            }
        }

        return rst;
    }

    private int dfs(boolean[][] grid, int i, int j, int area) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length
                || grid[i][j] != true) {
            return area;
        }

        // 修改 grid[i][j] 的值，表示其已经被遍历过了
        grid[i][j] = false;
        // 一旦遇到一个 grid[i][j] 为 true 的单位，则岛屿面积加1
        area++;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        // 向四周 infect 看岛屿面积能否继续增加
        for (int[] dir : dirs) {
            area = dfs(grid, i + dir[0], j + dir[1], area);
        }

        return area;
    }
}