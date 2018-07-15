/*
In a N x N grid representing a field of cherries, each cell is one of three possible integers.

0 means the cell is empty, so you can pass through;
1 means the cell contains a cherry, that you can pick up and pass through;
-1 means the cell contains a thorn that blocks your way.
Your task is to collect maximum number of cherries possible by following the rules below:

Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells (cells with value 0 or 1);
After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.

Example 1:
Input: grid =
[[0, 1, -1],
 [1, 0, -1],
 [1, 1,  1]]
Output: 5
Explanation:
The player started at (0, 0) and went down, down, right right to reach (2, 2).
4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
Then, the player went left, up, up, left to return home, picking up one more cherry.
The total number of cherries picked up is 5, and this is the maximum possible.

Note:
grid is an N by N 2D array, with 1 <= N <= 50.
Each grid[i][j] is an integer in the set {-1, 0, 1}.
It is guaranteed that grid[0][0] and grid[N-1][N-1] are not -1.
 */

/**
 * Approach: Recursion + Memory Search
 * 本题根据数据规模可以轻易分析出使用 DP 来进行解决。
 * 但是 Minimum Path 类的做法是行不通的，因为一次只走一条路径的话。
 * 其实是一个贪心的策略，而这个策略是错误的。（LeetCode的官方解答中有给出）
 *
 * 因此本题实际上可以转换成，两个人同时从 (n-1, n-1) 位置出发向 (0, 0) 前进。
 * 每次只能走一步，并且这两个人在前进过程中互相独立且同时进行。
 * 这样就能很好地解决这个问题了。只需要处理，两个人在同一位置时，樱桃只能被取一份即可。
 * 而对于这两个人的状态，我们只需要用坐标进行代表即可。
 * 比如 A 为(x1, y1), B 为(x2, y2)。因为两个人是同步前进的，所以 x1 + y1 = x2 + y2.
 * 因此两个人只需要用 3 个状态信息既能够表示。
 * （同理如果有 3 个人，用 4 个即可，即空间消耗将变成 4 维数组 (x1, y1, x2, x3)）
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^3)
 *
 * 参考资料：
 *  https://leetcode.com/problems/cherry-pickup/solution/
 *  https://www.youtube.com/watch?v=vvPSWORCKow
 */
class Solution {
    int[][][] mem;

    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        mem = new int[n][n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    mem[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        return Math.max(0, dfs(grid, n - 1, n - 1, n - 1));
    }

    private int dfs(int[][] grid, int x1, int y1, int x2) {
        int y2 = x1 + y1 - x2;
        // 当前方案行不通，这返回 -1.（包括处理边界条件）
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || grid[x1][y1] == -1 || grid[x2][y2] == -1) {
            return -1;
        }
        // 到达结束位置（左上角），递归结束
        if (x1 == 0 && y1 == 0) {
            return grid[0][0];
        }
        // 当前状态已经计算过，则直接返回
        if (mem[x1][y1][x2] != Integer.MIN_VALUE) {
            return mem[x1][y1][x2];
        }

        // 递归调用四个子过程（每个人有两种走法，有两个人，所以总共 2*2 = 4 个子状态）
        int rst = Math.max(Math.max(dfs(grid, x1 - 1, y1, x2), dfs(grid, x1 - 1, y1, x2 - 1)),
                Math.max(dfs(grid, x1, y1 - 1, x2), dfs(grid, x1, y1 - 1, x2 - 1)));
        // 所有的子过程都行不通，则返回 -1.
        if (rst < 0) {
            mem[x1][y1][x2] = -1;
            return rst;
        }
        // 拾取(x1, y1)当前位置的樱桃
        rst += grid[x1][y1];
        // 如果两个人的位置不同，则也需要拾取(x2, y2)位置的樱桃。
        if (x1 != x2) {
            rst += grid[x2][y2];
        }
        mem[x1][y1][x2] = rst;
        return mem[x1][y1][x2];
    }
}