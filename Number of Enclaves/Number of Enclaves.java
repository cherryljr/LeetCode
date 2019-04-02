/*
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
 */

/**
 * Approach: DFS
 * DFS的模板题，没什么好说的...不熟悉DFS的可以拿来练练手...
 *
 * 时间复杂度：O(mn)
 * 空间复杂度：O(1)
 *
 * 类似的问题：
 * Number of Islands:
 *  https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Islands.java
 * Number of Big Islands:
 *  https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Big%20Islands.java
 * Max Area of Island:
 *  https://github.com/cherryljr/LeetCode/blob/master/Max%20Area%20of%20Island.java
 */
class Solution {
    private static int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int numEnclaves(int[][] A) {
        int rows = A.length, cols = A[0].length;
        
        // 从四个边缘开始向内进行 infect 操作，
        // 从 1 开始，将所有能走到的 1 全部转换成 0
        for (int i = 0; i < cols; i++) {
            if (A[0][i] == 1) {
                dfs(A, 0, i);
            }
            if (A[rows - 1][i] == 1) {
                dfs(A, rows - 1, i);
            }
        }
        for (int i = 1; i < rows - 1; i++) {
            if (A[i][cols - 1] == 1) {
                dfs(A, i, cols - 1);
            }
            if (A[i][0] == 1) {
                dfs(A, i, 0);
            }
        }
        
        int ans = 0;
        // 统计整个地图中还有多少个 1 没被走到，就是我们需要的答案
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ans += A[i][j];
            }
        }
        return ans;
    }

    private void dfs(int[][] A, int x, int y) {
        if (x < 0 || x >= A.length || y < 0 || y >= A[0].length || A[x][y] != 1) {
            return;
        }

        // turn 1 into 0
        A[x][y] = 0;
        for (int[] dir : DIRS) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            dfs(A, nextX, nextY);
        }
    }
}