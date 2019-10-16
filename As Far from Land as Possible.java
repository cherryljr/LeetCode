/*
Given an N x N grid containing only values 0 and 1, where 0 represents water and 1 represents land,
find a water cell such that its distance to the nearest land cell is maximized and return the distance.

The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.
If no land or water exists in the grid, return -1.

Example 1:
Input: [[1,0,1],[0,0,0],[1,0,1]]
Output: 2
Explanation:
The cell (1, 1) is as far as possible from all the land with distance 2.

Example 2:
Input: [[1,0,0],[0,0,0],[0,0,0]]
Output: 4
Explanation:
The cell (2, 2) is as far as possible from all the land with distance 4.

Note:
    1. 1 <= grid.length == grid[0].length <= 100
    2. grid[i][j] is 0 or 1
 */

/**
 * Approach: BFS
 * 看到题目是一个 graph 且与最短路径有关，首先想到使用 BFS 来解决问题。
 * 我们可以先找出所有的陆地，然后从陆地开始向外进行 BFS 扩散，然后每扩散一层就将计数器加一，
 * 不断“填海造陆”直到整个地图再也不存在海洋为止。这个时候的计数器就是最远的海洋距离陆地的距离。
 *
 * 时间复杂度：O(M*N)
 * 空间复杂度：O(M*N)
 *
 * Reference:
 *  https://leetcode.com/problems/as-far-from-land-as-possible/discuss/360963/C%2B%2B-with-picture-DFS-and-BFS
 */
class Solution {
    public int maxDistance(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        if (queue.isEmpty() || queue.size() == m * n) {
            return -1;
        }
        int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int step = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(), i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] dir : DIRS) {
                    int nextRow = curr[0] + dir[0], nextCol = curr[1] + dir[1];
                    if (nextRow < 0 || nextRow >= m || nextCol < 0 || nextCol >= n || visited[nextRow][nextCol] || grid[nextRow][nextCol] == 1) {
                        continue;
                    }
                    queue.offer(new int[]{nextRow, nextCol});
                    visited[nextRow][nextCol] = true;
                }
            }
            step++;
        }
        return step;
    }
}