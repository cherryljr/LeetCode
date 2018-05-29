/*
Given an integer matrix, find the length of the longest increasing path.
From each cell, you can either move to four directions: left, right, up or down.
You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

Example 1:
nums = [
  [9,9,4],
  [6,6,8],
  [2,1,1]
]
Return 4
The longest increasing path is [1, 2, 6, 9].

Example 2:
nums = [
  [3,4,5],
  [3,2,6],
  [2,2,1]
]
Return 4
The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 */
 
/**
 * Approach: DFS + Memory Search
 * 本题最暴力的做法是直接从每个点开始进行 DFS，然后取最长的路径即可。
 * 虽然没有给出数据规模，但是怎么做肯定是会超时的。
 * 这是因为对每个点进行DFS的过程中存在着大量重复计算。
 * 这边举一个极端的例子：
 *      [1, 2, 3, 4]
 *      [2, 3, 4, 5]
 *      [3, 4, 5, 6]
 * 上述矩阵就存在着大量的重复计算，时间复杂度为：O(2^n)
 *
 * 对此我们可以利用 Memory Search 进行时间复杂度的优化，避免重复计算。
 * 而这实际上就是一个 DP 的过程。
 * 当前状态依赖于其周围 4个 邻居节点的状态。
 * 当 matrix[nextRow][nextCol] > matrix[row][col] 时，则说明这是一条递增路径。
 * 因此如果该条路径成立，则 dp[row][col] = Math.max(dp[row][col], dp[nextRow][nextCol] + 1)
 * 因为我们使用了 dp[][] 来记录各个节点的 longest Increasing Path,
 * 所以当再次需要这个值，且该值已经被计算过时，可以直接返回。
 *
 * 时间复杂度：O(MN)
 * 对于每个结点，其值依赖于周围四个节点，计算时间复杂度为O(1)
 * 总共有 M*N 个节点，因此总体时间复杂度为 O(MN)
 */
class Solution {
    private static final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length, cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        int maxPath = 1;
        // DFS from each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Update the maxLen
                maxPath = Math.max(maxPath, dfs(dp, matrix, i, j));
            }
        }

        return maxPath;
    }

    private int dfs(int[][] dp, int[][] matrix, int row, int col) {
        // 如果当前值不为空（已经计算过了）则直接返回
        if (dp[row][col] != 0) {
            return dp[row][col];
        }

        dp[row][col] = 1;   // 初始化当前值
        for (int[] dir : DIRS) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if (nextRow < 0 || nextRow >= matrix.length || nextCol < 0 || nextCol >= matrix[0].length
                    || matrix[nextRow][nextCol] <= matrix[row][col]) {
                continue;
            }
            dp[row][col] = Math.max(dp[row][col], dfs(dp, matrix, nextRow, nextCol) + 1);
        }

        return dp[row][col];
    }
}