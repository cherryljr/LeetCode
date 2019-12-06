/*
In a gold mine grid of size m * n, each cell in this mine has an integer representing 
the amount of gold in that cell, 0 if it is empty.

Return the maximum amount of gold you can collect under the conditions:
    Every time you are located in a cell you will collect all the gold in that cell.
    From your position you can walk one step to the left, right, up or down.
    You can't visit the same cell more than once.
    Never visit a cell with 0 gold.
    You can start and stop collecting gold from any position in the grid that has some gold.

Example 1:
Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
Output: 24
Explanation:
[[0,6,0],
 [5,8,7],
 [0,9,0]]
Path to get the maximum gold, 9 -> 8 -> 7.

Example 2:
Input: grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
Output: 28
Explanation:
[[1,0,7],
 [2,0,6],
 [3,4,5],
 [0,3,0],
 [9,0,20]]
Path to get the maximum gold, 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7.

Constraints:
    1. 1 <= grid.length, grid[i].length <= 15
    2. 0 <= grid[i][j] <= 100
    3. There are at most 25 cells containing gold.
*/

/**
 * Approach: BFS
 * 与References中的两道题目类似，虽然这里是不能重复走，而那两题是可以重复走。
 * 但是本质都是一样的：我们都需要对每个点的位置单独维护一个其对应的 visited 集合。
 *
 * References:
 *  https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Path%20Visiting%20All%20Nodes.java
 *  https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Path%20to%20Get%20All%20Keys.java
 */
class Solution {
    public int getMaximumGold(int[][] grid) {
        int m = grid.length, n = grid[0].length, goldCellId = 0;
        int[][] state = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    state[i][j] = 1 << goldCellId++;
                    queue.offer(new int[]{i, j, grid[i][j], state[i][j]});
                }
            }
        }
        
        int ans = 0;
        int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            ans = Math.max(ans, curr[2]);
            for (int[] dir : DIRS) {
                int nextRow = curr[0] + dir[0], nextCol = curr[1] + dir[1];
                if (nextRow < 0 || nextRow >= m || nextCol < 0 || nextCol >= n || grid[nextRow][nextCol] == 0 || (curr[3] & state[nextRow][nextCol]) != 0) {
                    continue;
                }
                queue.offer(new int[]{nextRow, nextCol, curr[2] + grid[nextRow][nextCol], curr[3] | state[nextRow][nextCol]});
            }
        }
        return ans;
    }
}
