/*
We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall, "@" is the starting point,
("a", "b", ...) are keys, and ("A", "B", ...) are locks.

We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.
We cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.
We can't walk over a lock unless we have the corresponding key.

For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the English alphabet in the grid.
This means that there is exactly one key for each lock, and one lock for each key;
and also that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.

Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.

Example 1:
Input: ["@.a.#","###.#","b.A.B"]
Output: 8

Example 2:
Input: ["@..aA","..B#.","....b"]
Output: 6

Note:
1 <= grid.length <= 30
1 <= grid[0].length <= 30
grid[i][j] contains only '.', '#', '@', 'a'-'f' and 'A'-'F'
The number of keys is in [1, 6].  Each key has a different letter and opens exactly one lock.
 */

/**
 * Approach: BFS
 * 与 Shortest Path Visiting All Nodes 具有一定的类似之处。
 * 都是通过 位置信息 + 该位置上的状态信息 来进行 BFS 操作。
 * 本题的状态信息就是当前位置上拥有多少把钥匙。
 * 因为本题明确说明最多只有 6 把钥匙，加上行，列数均不超过30。
 * 因此我们可以只使用一个 int 就能够表示这三个状态信息，从而达到节省空间的目的。
 * 由高位向低位分别为：
 *  横坐标信息x(8位) -> 纵坐标信息y(8位) -> 持有的钥匙信息(8位)
 * 即：state = 000xxxxx | 000yyyyy | 000kkkkk
 * 之后根据 BFS 模板处理各个情况即可。
 *
 * 时间复杂度：O(m * n * 2^keys)
 * 空间复杂度：O(m * n * 2^keys)
 *
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/searching/leetcode-865-shortest-path-to-get-all-keys/
 */
class Solution {
    public int shortestPathAllKeys(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();
        int target = 0;

        Queue<Integer> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][64];
        // Initialize
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = grid[i].charAt(j);
                // 起点位置
                if (c == '@') {
                    queue.offer((i << 16) | (j << 8));
                    visited[i][j][0] = true;
                } else if (c >= 'a' && c <= 'f') {
                    // 记录总共有哪些钥匙（最终状态）
                    target |= 1 << c - 'a';
                }
            }
        }

        int step = 0;
        int[][] dirs = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                int x = curr >>> 16;
                int y = curr >> 8 & 0xFF;
                int state = curr & 0xFF;
                if (state == target) {
                    return step;
                }

                for (int[] dir : dirs) {
                    int nextX = x + dir[0];
                    int nextY = y + dir[1];
                    int nextState = state;
                    // 超出边界
                    if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols) {
                        continue;
                    }
                    char c = grid[nextX].charAt(nextY);
                    // 有障碍物或者不具有当前锁的钥匙
                    if (c == '#' ||(c >= 'A' && c <= 'F' && (nextState >> c - 'A' & 1) == 0))  {
                        continue;
                    }
                    // 获得一把钥匙
                    if (c >= 'a' && c <= 'f') {
                        nextState |= 1 << c - 'a';
                    }
                    if (!visited[nextX][nextY][nextState]) {
                        queue.offer((nextX << 16) | (nextY << 8) | nextState);
                        visited[nextX][nextY][nextState] = true;
                    }
                }
            }
            step++;
        }

        return -1;
    }
}