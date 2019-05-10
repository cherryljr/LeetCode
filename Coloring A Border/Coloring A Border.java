/*
Given a 2-dimensional grid of integers, each value in the grid represents the color of the grid square at that location.
Two squares belong to the same connected component if and only if they have the same color and are next to each other in any of the 4 directions.
The border of a connected component is all the squares in the connected component that are either 4-directionally
adjacent to a square not in the component, or on the boundary of the grid (the first or last row or column).

Given a square at location (r0, c0) in the grid and a color,
color the border of the connected component of that square with the given color, and return the final grid.

Example 1:
Input: grid = [[1,1],[1,2]], r0 = 0, c0 = 0, color = 3
Output: [[3, 3], [3, 2]]

Example 2:
Input: grid = [[1,2,2],[2,3,2]], r0 = 0, c0 = 1, color = 3
Output: [[1, 3, 3], [2, 3, 3]]

Example 3:
Input: grid = [[1,1,1],[1,1,1],[1,1,1]], r0 = 1, c0 = 1, color = 2
Output: [[2, 2, 2], [2, 1, 2], [2, 2, 2]]

Note:
    1. 1 <= grid.length <= 50
    2. 1 <= grid[0].length <= 50
    3. 1 <= grid[i][j] <= 1000
    4. 0 <= r0 < grid.length
    5. 0 <= c0 < grid[0].length
    6. 1 <= color <= 1000
 */

/**
 * Approach 1: DFS (Similar to Flood Fill)
 * 这是一道 泛洪染色问题 的变形题，区别在于这里要求染色的部分是区域的边缘部分，而不是整个区域。
 * 因此我们在 flood fill 的时候，需要判断是否为边缘区域，只有是边缘区域才需要进行染色。
 * 对此我们依然可以使用 DFS 来解决这个问题，只不过加入了边缘区域的判断操作。
 * PS.值得注意的是，我们不能在遍历的过程中直接对边界区域进行染色，否则会因为修改原始数据从而导致遍历出错。
 * 比如：[[1,2,1,2,1,2],[2,2,2,2,1,2],[1,2,2,2,1,2]]
 * r0 = 1, c0 = 3, color = 1
 * 如果直接染色的话，对于 grid[1][1] 会因为 grid[1][2] 被染色成 1，从而导致判断错误将 gird[1][1] 错认为是边界区域。
 * 因此在遍历过程中，我们应该相对边界区域进行 标记/记录 然后再统一对其进行染色。
 *
 * 时间复杂度：O(M*N)
 * 空间复杂度：O(M+N)
 *
 * 类似的问题：
 *  https://github.com/cherryljr/LeetCode/blob/master/Flood%20Fill.java
 */
class Solution {
    public int[][] colorBorder(int[][] grid, int r0, int c0, int color) {
        // 利用 DFS 对边缘区域进行标记
        floodFill(grid, r0, c0, grid[r0][c0]);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] < 0) {
                    grid[i][j] = color;
                }
            }
        }
        return grid;
    }

    // 这里利用了 1 <= grid[i][j] <= 1000 这个条件，将边缘区域标记成对应的负数
    private void floodFill(int[][] grid, int r, int c, int color) {
        int m = grid.length, n = grid[0].length;
        // 递归结束条件
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] != color) {
            return;
        }

        grid[r][c] = -color;
        int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] dir : DIRS) {
            floodFill(grid, r + dir[0], c + dir[1], color);
        }
        // 如果当前位置不位于版图区域的边缘，并且当前位置的值与相邻四个点的颜色均相同，
        // 那么它就不应该被染色（不属于边缘区域），因此将其重新置为原来的值 color （正数）
        if (r > 0 && r < m - 1 && c > 0 && c < n - 1) {
            if (Math.abs(grid[r][c - 1]) == color && Math.abs(grid[r][c + 1]) == color
                    && Math.abs(grid[r - 1][c]) == color && Math.abs(grid[r + 1][c]) == color) {
                grid[r][c] = color;
            }
        }
    }
}

/**
 * Approach 2: BFS
 * 对于图的遍历问题同样可以使用 BFS 来解决。操作过程依旧需要加入对 边缘条件 的判断。
 * 并且注意不能在 BFS 的过程中对边缘区域进行染色。
 *
 * 时间复杂度：O(M*N)
 * 空间复杂度：O(M*N)
 */
class Solution {
    public int[][] colorBorder(int[][] grid, int r0, int c0, int color) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r0, c0});
        visited[r0][c0] = true;
        int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        // 用于记录边缘区域的点
        List<int[]> borders = new ArrayList<>();

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : DIRS) {
                int nextRow = curr[0] + dir[0], nextCol = curr[1] + dir[1];
                if (nextRow < 0 || nextRow >= m || nextCol < 0 || nextCol >= n || grid[nextRow][nextCol] != grid[r0][c0]) {
                    borders.add(new int[]{curr[0], curr[1]});
                    continue;
                }
                if (!visited[nextRow][nextCol]) {
                    queue.offer(new int[]{nextRow, nextCol});
                    visited[nextRow][nextCol] = true;
                }
            }
        }

        // 对边缘区域进行染色
        for (int[] border : borders) {
            grid[border[0]][border[1]] = color;
        }
        return grid;
    }
}