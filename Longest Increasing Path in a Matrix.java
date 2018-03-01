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
 * Approach: DFS
 * This Method is similar to The Maze II (DFS Method) and Portal (Although it's BFS, but main idea is the same).
 * https://github.com/cherryljr/LeetCode/blob/master/The%20Maze%20II.java
 * https://github.com/cherryljr/LintCode/blob/master/Portal.java
 *
 * We can do DFS from each cell and keep as value maxLen as the longest increasing path.
 * The Process:
 *  1. Do DFS from every cell
 *  2. Compare every 4 direction and skip cells that are out of boundary or smaller
 *  3. Get matrix max from every cell’s max
 */
class Solution {
    int maxLen = 1; // the longest increasing path

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        // Initialize the distance array.
        int[][] distance = new int[matrix.length][matrix[0].length];
        for (int[] arr : distance) {
            Arrays.fill(arr, 1);
        }

        // DFS from each cell
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                dfs(matrix, i, j, distance);
            }
        }

        return maxLen;
    }

    private void dfs(int[][] matrix, int i, int j, int[][] distance) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length
                    || matrix[x][y] <= matrix[i][j]) {
                continue;
            }
            // if we find a longer path, update distance[x][y]
            // then do DFS from here
            if (distance[i][j] + 1 > distance[x][y]) {
                distance[x][y] = distance[i][j] + 1;
                // update the maxLen
                maxLen = Math.max(maxLen, distance[x][y]);
                dfs(matrix, x, y, distance);
            }
        }
    }
}

/**
 * Approach 2: DFS + Memory Search
 * THe main process is the same as Approach 1.
 * But we can use Memory Search to optimize the Time Complexity.
 * 
 * Note:
 *  1. Use dp[i][j] so we don’t need a visited[m][n] array
 *  (if it's not equals to 0, it means that the point has been visited)
 *  2. The key is to cache the distance because it’s highly possible to revisit a cell
 *
 * Owing to Memory Search, the Time Complexity is O(mn), and the Space Complexity is O(mn).
 */
class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rst = 1;
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                rst = Math.max(rst, dfs(matrix, i, j, dp));
            }
        }

        return rst;
    }

    private int dfs(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int len = 1;

        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length
                    || matrix[x][y] <= matrix[i][j]) {
                continue;
            }
            len = Math.max(len, dfs(matrix, x, y, dp) + 1);
        }
        dp[i][j] = len;

        return len;
    }
}
