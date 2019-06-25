/*
In an N by N square grid, each cell is either empty (0) or blocked (1).
A clear path from top-left to bottom-right has length k if and only if it is composed of cells C_1, C_2, ..., C_k such that:
    Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they are different and share an edge or corner)
    C_1 is at location (0, 0) (ie. has value grid[0][0])
    C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
    If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
Return the length of the shortest such clear path from top-left to bottom-right.  If such a path does not exist, return -1.

Example 1:
Input: [[0,1],[1,0]]
Output: 2

Example 2:
Input: [[0,0,0],[1,1,0],[1,1,0]]
Output: 4

Note:
    1. 1 <= grid.length == grid[0].length <= 100
    2. grid[r][c] is 0 or 1
 */

/**
 * Approach: BFS
 * 题意：给一个 N*N 的矩阵，值为 0 代表可以走，路径的行走方向可为相邻的任意一个位置，即总共 八个 方向。
 * 问是否存在一条路径可以从左上角到达右下角，如果存在输出最短的路径长度。
 *
 * 可以明显看出，这是一道BFS求最短路径的模板题。可以聊的不多，注意起点位置也需要算一步。（总共经过了几个点）
 * 然后注意一下路径不存在的情况即可。
 *
 * 时间复杂度：O(N^2)
 * 空间复杂度：O(N^2)
 */
class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0][0] == 1) {
            return -1;
        }

        int n = grid.length;
        Queue<int[]> queue = new LinkedList<>();
        int[][] DIRS = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        queue.offer(new int[]{0, 0});
        int step = 1;   // 起点也算一步，所以初始化为 1
        int[] curr = {0, 0};

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                curr = queue.poll();
                if (curr[0] == n - 1 && curr[1] == n - 1) {
                    return step;
                }

                for (int[] dir : DIRS) {
                    int nextR = curr[0] + dir[0], nextC = curr[1] + dir[1];
                    if (nextR < 0 || nextR >= n || nextC < 0 || nextC >= n || grid[nextR][nextC] != 0) {
                        continue;
                    }
                    queue.offer(new int[]{nextR, nextC});
                    grid[nextR][nextC] = 2;
                }
            }
            step++;
        }
        // if reach here, it means there's no path exists
        return -1;
    }
}