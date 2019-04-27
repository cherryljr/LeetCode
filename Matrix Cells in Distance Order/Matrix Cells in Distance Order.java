/*
We are given a matrix with R rows and C columns has cells with integer coordinates (r, c), where 0 <= r < R and 0 <= c < C.
Additionally, we are given a cell in that matrix with coordinates (r0, c0).
Return the coordinates of all cells in the matrix, sorted by their distance from (r0, c0) from smallest distance to largest distance.
Here, the distance between two cells (r1, c1) and (r2, c2) is the Manhattan distance, |r1 - r2| + |c1 - c2|.
(You may return the answer in any order that satisfies this condition.)

Example 1:
Input: R = 1, C = 2, r0 = 0, c0 = 0
Output: [[0,0],[0,1]]
Explanation: The distances from (r0, c0) to other cells are: [0,1]

Example 2:
Input: R = 2, C = 2, r0 = 0, c0 = 1
Output: [[0,1],[0,0],[1,1],[1,0]]
Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2]
The answer [[0,1],[1,1],[0,0],[1,0]] would also be accepted as correct.

Example 3:
Input: R = 2, C = 3, r0 = 1, c0 = 2
Output: [[1,2],[0,2],[1,1],[0,1],[1,0],[0,0]]
Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2,2,3]
There are other answers that would also be accepted as correct, such as [[1,2],[1,1],[0,2],[1,0],[0,1],[0,0]].

Note:
    1. 1 <= R <= 100
    2. 1 <= C <= 100
    3. 0 <= r0 < R
    4. 0 <= c0 < C
 */

/**
 * Approach: BFS
 * 简单的模板类问题...没什么好说的。
 * 以 [r0, c0] 为起始位置进行 BFS 即可。
 *
 * 时间复杂度：O(R * C)
 * 空间复杂度：O(R * C)
 */
class Solution {
    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        int[][] ans = new int[R * C][2];
        int index = 0;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[R][C];
        queue.offer(new int[]{r0, c0});
        visited[r0][c0] = true;

        int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            ans[index][0] = curr[0];
            ans[index++][1] = curr[1];

            for (int[] dir : DIRS) {
                int nextX = curr[0] + dir[0], nextY = curr[1] + dir[1];
                if (nextX < 0 || nextX >= R || nextY < 0 || nextY >= C || visited[nextX][nextY]) {
                    continue;
                }
                queue.offer(new int[]{nextX, nextY});
                visited[nextX][nextY] = true;
            }
        }

        return ans;
    }
}