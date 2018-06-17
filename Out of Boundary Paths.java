/*
There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball,
you can move the ball to adjacent cell or cross the grid boundary in four directions (up, down, left, right).
However, you can at most move N times. Find out the number of paths to move the ball out of grid boundary.
The answer may be very large, return it after mod 109 + 7.

Example 1:
Input:m = 2, n = 2, N = 2, i = 0, j = 0
Output: 6
Explanation:
https://leetcode.com/static/images/problemset/out_of_boundary_paths_1.png

Example 2:
Input:m = 1, n = 3, N = 3, i = 0, j = 1
Output: 12
Explanation:
https://leetcode.com/static/images/problemset/out_of_boundary_paths_2.png

Note:
Once you move the ball out of boundary, you cannot move it back.
The length and height of the grid is in range [1,50].
N is in range [0,50].
 */

/**
 * Approach 1: DFS + Memory Search
 * 这道题目与 Knight Probability in Chessboard 基本一样。
 * 一个求在 棋盘上，一个求在 棋盘外 罢了。
 * 只需要对 递归 的终止条件进行修改即可（当然移动方式也有点不同就是了...）
 * 
 * Knight Probability in Chessboard:
 *  https://github.com/cherryljr/LeetCode/blob/master/Knight%20Probability%20in%20Chessboard.java
 */
class Solution {
    public static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    public static final int MOD = 1000000007;
    long[][][] dp;

    public int findPaths(int m, int n, int N, int i, int j) {
        dp = new long[m][n][N + 1];
        return (int)dfs(m, n, N, i, j);
    }

    private long dfs(int m, int n, int step, int row, int col) {
        if (step >= 0 && (row < 0 || row >= m || col < 0 || col >= n)) {
            return 1;
        } else if (step < 0) {
            return 0;
        }
        // 如果当前位置的结果已经计算过了，则直接返回记录的结果即可。
        if (dp[row][col][step] != 0) {
            return dp[row][col][step];
        }

        long outOfBoard = 0;
        for (int[] dir : DIRS) {
            outOfBoard = (outOfBoard + dfs(m, n, step - 1, row + dir[0], col + dir[1])) % MOD;
        }
        dp[row][col][step] = outOfBoard;
        return outOfBoard;
    }
}

/**
 * Approach 2: DP
 * 有了 Approach 1 的基础，我们只需要将其改成 DP 即可。
 * 建立空间递推过程，实际上就是填写一个 三维矩阵 的过程。
 * 顺序为从上往下一层层递推下去，即：
 *  dp[i][j][k] = dp[i + dir[0]][j + dir[1]][k - 1]
 * 但是因为 每一层矩阵 仅仅依赖于 上一层矩阵。（dp[i][j][k] 仅依赖于 dp[i+dir[0][j+dir[1]][k-1]）
 * 所以可将 三维矩阵 优化成 两个二位矩阵。
 *
 * 自下而上的 DP 解法可以参考：
 * https://leetcode.com/problems/out-of-boundary-paths/solution/
 */
class Solution {
    public int findPaths(int m, int n, int N, int i, int j) {
        if (N <= 0) {
            return 0;
        }

        final int MOD = 1000000007;
        int[][] dp = new int[m][n];
        // 当 k == 1 时,矩阵最外围的值需要进行一次初始化
        // 边上为 1,四个角为 2
        for (int row = 0; row < m; row++) {
            dp[row][0]++;
            dp[row][n - 1]++;
        }
        for (int col = 0; col < n; col++) {
            dp[0][col]++;
            dp[m - 1][col]++;
        }

        int[][] dirs = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int k = 2; k <= N; k++) {
            int[][] dp2 = new int[m][n];
            for (int row = 0; row < m; row++) {
                for (int col = 0; col < n; col++) {
                    for (int[] dir : dirs) {
                        int nextRow = row + dir[0];
                        int nextCol = col + dir[1];
                        if (outOfBounds(m, n, nextRow, nextCol)) {
                            dp2[row][col] = (dp2[row][col] + 1) % MOD;
                        } else {
                            dp2[row][col] = (dp2[row][col] + dp[nextRow][nextCol]) % MOD;
                        }
                    }
                }
            }
            dp = dp2;
        }

        return dp[i][j];
    }

    private boolean outOfBounds(int m, int n, int row, int col) {
        return row < 0 || row >= m || col < 0 || col >= n;
    }
}