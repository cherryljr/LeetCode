/*
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent,
the "Pacific ocean" touches the left and top edges of the matrix
and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:
    1. The order of returned grid coordinates does not matter.
    2. Both m and n are less than 150.

Example:
Given the following 5x5 matrix:
  Pacific ~   ~   ~   ~   ~
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * Atlantic
Return:
[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 */

/**
 * Approach 1: BFS Reverse
 * Graph类型的题目，很明显可以使用 BFS/DFS 来解决。
 * 题目要求的是 均能够到达 Pacific ocean 和 Atlantic ocean 的点。
 * 因为水流的要求是：下一个位置的水位必须 <= 当前水位。
 * 最后判断是否能到达四条边即可。
 * 因此我们可以从四个边缘开始，逆着进行 BFS/DFS 看从边缘点出发能够到达的点即可。
 * 相应的，水流的规则就变成了爬楼梯一样，下一个位置的水位必须 >= 当前位置的水位。
 * 具体代码就是 Graph 类的模板了...因为本题需要区分 左上区域 和 右下区域，所以需要使用两个队列和 visited 数组来记录状态。
 * 最后整合两个 visited[][] 求出均能够到达 Pacific ocean 和 Atlantic ocean 的点即可。
 * 
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 */
class Solution {
    public List<int[]> pacificAtlantic(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }

        int m = matrix.length, n = matrix[0].length;
        Queue<int[]> pacific = new LinkedList<>();
        boolean[][] pacificVisited = new boolean[m][n];
        Queue<int[]> atlantic = new LinkedList<>();
        boolean[][] atlanticVisited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            pacific.offer(new int[]{i, 0});
            pacificVisited[i][0] = true;
            atlantic.offer(new int[]{i, n - 1});
            atlanticVisited[i][n - 1] = true;
        }
        for (int i = 0; i < n; i++) {
            pacific.offer(new int[]{0, i});
            pacificVisited[0][i] = true;
            atlantic.offer(new int[]{m - 1, i});
            atlanticVisited[m - 1][i] = true;
        }

        bfs(matrix, pacific, pacificVisited);
        bfs(matrix, atlantic, atlanticVisited);

        List<int[]> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificVisited[i][j] && atlanticVisited[i][j]) {
                    ans.add(new int[]{i, j});
                }
            }
        }
        return ans;
    }

    private void bfs(int[][] matrix, Queue<int[]> queue, boolean[][] visited) {
        int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : DIRS) {
                int nextX = curr[0] + dir[0], nextY = curr[1] + dir[1];
                if (nextX < 0 || nextY < 0 || nextX >= matrix.length || nextY >= matrix[0].length || visited[nextX][nextY]) {
                    continue;
                }
                if (matrix[curr[0]][curr[1]] > matrix[nextX][nextY]) {
                    continue;
                }
                queue.offer(new int[]{nextX, nextY});
                visited[nextX][nextY] = true;
            }
        }
    }
}

/**
 * Approach 2: DFS Reverse
 * 与 BFS 的做法大同小异...大家可以发现，在代码上相似度还是很高的（懒得改，直接copy...233...）
 * 由于不需要维护队列了，并且这道题目使用 BFS 并不能帮我们优化掉某些路径。
 * 因此这道题目使用 DFS 很更快一些，写法跟更加简洁，推荐使用 DFS 来写哈。
 * 
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 */
class Solution {
    public List<int[]> pacificAtlantic(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new ArrayList<>();
        }

        int m = matrix.length, n = matrix[0].length;
        boolean[][] pacificVisited = new boolean[m][n];
        boolean[][] atlanticVisited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            dfs(matrix, pacificVisited, Integer.MIN_VALUE, i, 0);
            dfs(matrix, atlanticVisited, Integer.MIN_VALUE, i, n - 1);
        }
        for (int i = 0; i < n; i++) {
            dfs(matrix, pacificVisited, Integer.MIN_VALUE, 0, i);
            dfs(matrix, atlanticVisited, Integer.MIN_VALUE, m - 1, i);
        }

        List<int[]> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacificVisited[i][j] && atlanticVisited[i][j]) {
                    ans.add(new int[]{i, j});
                }
            }
        }
        return ans;
    }

    private void dfs(int[][] matrix, boolean[][] visited, int preHeight, int x, int y) {
        if (x < 0 || y < 0 || x >= matrix.length || y >= matrix[0].length || visited[x][y] || matrix[x][y] < preHeight) {
            return;
        }

        int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        visited[x][y] = true;
        for (int[] dir : DIRS) {
            dfs(matrix, visited, matrix[x][y], x + dir[0], y + dir[1]);
        }
    }
}