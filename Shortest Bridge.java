/*
In a given 2D binary array A, there are two islands.  
(An island is a 4-directionally connected group of 1s not connected to any other 1s.)

Now, we may change 0s to 1s so as to connect the two islands together to form 1 island.
Return the smallest number of 0s that must be flipped.  (It is guaranteed that the answer is at least 1.)

Example 1:
Input: [[0,1],[1,0]]
Output: 1

Example 2:
Input: [[0,1,0],[0,0,0],[0,0,1]]
Output: 2

Example 3:
Input: [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
Output: 1

Note:
    1. 1 <= A.length = A[0].length <= 100
    2. A[i][j] == 0 or A[i][j] == 1
*/

/**
 * Approach: DFS + BFS
 * Graph Search 类的模板问题。考察的是 DFS 和 BFS 的结合。
 * 本题中我们需要寻找从一个岛到另外一个岛的最短路径。
 * 因此就意味着本题中同时存在 多个起点 和 多个终点。
 * 故解决方案为：
 *  先利用 DFS 找出所有的起点（其中一个岛上所有的点）
 *  然后将这些点放入队列中作为 BFS 的起点，开始寻找到另外一个岛的最短路径
 *  根据 BFS 的特性，当我们第一次遇到另外一个岛上的点时，这就是我们需要走的最少步数。
 * 
 * 时间复杂度：O(mn)
 * 空间复杂度：O(mn)
 * 
 * Number of Islands: https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Islands.java
 */
class Solution {
    final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int shortestBridge(int[][] A) {
        Queue<int[]> queue = new LinkedList<>();
        // 找到其中一个岛屿上的一个点，然后利用 DFS 找出整个岛
        // 同时将该岛上所有的点加到到 queue 中作为 BFS 的起始点
        boolean found = false;
        for (int i = 0; i < A.length && !found; i++) {
            for (int j = 0; j < A[0].length && !found; j++) {
                if (A[i][j] == 1) {
                    dfs(A, i, j, queue);
                    found = true;
                }
            }
        }

        // 利用 BFS 找出所有起始点到另外一个岛（多个终点）的最短路径即可
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] currPos = queue.poll();
                for (int[] dir : DIRS) {
                    int nextRow = currPos[0] + dir[0];
                    int nextCol = currPos[1] + dir[1];
                    if (nextRow < 0 || nextRow >= A.length || nextCol < 0 || nextCol >= A[0].length || A[nextRow][nextCol] == 2) {
                        continue;
                    }
                    // 如果遇到了另外一个岛上的点，直接返回 step.
                    // 注意：题目要求的是需要 flip 的次数，不是真正所谓的步数，因此最后一步不需要算（即这里不需要 +1 操作）
                    if (A[nextRow][nextCol] == 1) {
                        return step;
                    }
                    // 将遍历过的地方标记成 2，这样后面就不需要再次遍历
                    A[nextRow][nextCol] = 2;
                    queue.offer(new int[]{nextRow, nextCol});
                }
            }
            step++;
        }

        return -1;
    }

    // DFS 标准模板，与 Number of Islands 的做法相同
    public void dfs(int[][] A, int row, int col, Queue<int[]> queue) {
        if (row < 0 || row >= A.length || col < 0 || col >= A[0].length || A[row][col] != 1) {
            return;
        }
        A[row][col] = 2;
        queue.offer(new int[]{row, col});

        for (int[] dir : DIRS) {
            dfs(A, row + dir[0], col + dir[1], queue);
        }
    }
}