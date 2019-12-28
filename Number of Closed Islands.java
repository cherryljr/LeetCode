/*
Given a 2D grid consists of 0s (land) and 1s (water).
An island is a maximal 4-directionally connected group of 0s and a closed island is an island totally
(all left, top, right, bottom) surrounded by 1s.

Return the number of closed islands.

https://leetcode.com/problems/number-of-closed-islands/
Example 1:
Input: grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
Output: 2
Explanation: 
Islands in gray are closed because they are completely surrounded by water (group of 1s).

Example 2:
Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
Output: 1

Example 3:
Input: grid = [[1,1,1,1,1,1,1],
               [1,0,0,0,0,0,1],
               [1,0,1,1,1,0,1],
               [1,0,1,0,1,0,1],
               [1,0,1,1,1,0,1],
               [1,0,0,0,0,0,1],
               [1,1,1,1,1,1,1]]
Output: 2

Constraints:
    1. 1 <= grid.length, grid[0].length <= 100
    2. 0 <= grid[i][j] <=1
*/

/**
 * Approach 1: DFS
 * island的定义为周围一圈全是 1，并且不能是边界。
 * 所以很明显，想要有 island，grid的大小必须大于 [2][2]。
 * 做法：
 *  1. 因为周围不能是边界，所以从四条边为 0 的地方开始 flood (fill)
 *  2. 遍历内部区域，利用 dfs 看有几个 island 即可
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */
class Solution {
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
     
    public int closedIsland(int[][] grid) {
        if (grid.length <= 2 || grid[0].length <= 2) {
            return 0;
        }
        
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            dfs(grid, i, 0);
            dfs(grid, i, grid[0].length - 1);
        }
        for (int i = 1; i < grid[0].length - 1; i++) {
            dfs(grid, 0, i);
            dfs(grid, grid.length - 1, i);
        }
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0) {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }
    
    private void dfs(int[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 0) {
            return;
        }
        
        grid[r][c] = 2;
        for (int[] dir : DIRS) {
            dfs(grid, r + dir[0], c + dir[1]);
        }
    }
}

/**
 * Approach 2: DFS
 * 依旧是 DFS，不过可以利用 DFS 的返回值来简化下代码。
 * 思路有一些些区别。这里我不再对四条边进行 flood 来进行初始化。
 * 而是直接遍历各个为 0 的点，看能不能通过 dfs 到达边界
 * （因此递归的结束条件就是到达边界，或者遇到1）
 * 只要有一条路径能到达边界，那么结果就是 false，无法形成 island，
 * 反之，所有路径均以 1 为结束的，那么就说明可以形成一个 island。
 *
 * 时间复杂度: O(n)
 * 空间复杂度: O(n)
 */
class Solution {
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
     
    public int closedIsland(int[][] grid) {
        if (grid.length <= 2 || grid[0].length <= 2) {
            return 0;
        }
        
        int ans = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0 && dfs(grid, i, j)) {
                    ans += 1;
                }
            }
        }
        return ans;
    }
    
    private boolean dfs(int[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
            return false;
        }
        if (grid[r][c] == 1) {
            return true;
        }
        
        grid[r][c] = 1;
        boolean ans = true;
        // 要求所有路径均以 1 作为结束
        for (int[] dir : DIRS) {
            ans &= dfs(grid, r + dir[0], c + dir[1]);
        }
        return ans;
    }
}