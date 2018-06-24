/*
An undirected, connected graph of N nodes (labeled 0, 1, 2, ..., N-1) is given as graph.
graph.length = N, and j != i is in the list graph[i] exactly once, if and only if nodes i and j are connected.
Return the length of the shortest path that visits every node. You may start and stop at any node,
you may revisit nodes multiple times, and you may reuse edges.

Example 1:
Input: [[1,2,3],[0],[0],[0]]
Output: 4
Explanation: One possible path is [1,0,2,0,3]

Example 2:
Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
Output: 4
Explanation: One possible path is [0,1,4,2,3]

Note:
1 <= graph.length <= 12
0 <= graph[i].length < graph.length
 */

/**
 * Approach: BFS
 * 题目要求走遍所有点的最短路径(最少步数)。而该图是一个 权值为1的无向图。
 * 并且数据规模为 node <= 12, 所以首先可以考虑使用 BFS 来做。
 *
 * 该题与平时遇到的 BFS 不同点在于，在同一条路径(同一轮遍历)中，一个点是可以被重复遍历的。
 * 而我们平时都是会记录一个 visited 数组来避免遍历重复的点，或者是限制加入队列的条件
 * 来减小问题的规模。否则就会出现死循环。而这也是本题的难点所在。
 *
 * 对此我们仍然可以通过记录 visited 状态来解决。
 * 只不过这里需要记录的状态为：当前的位置 以及 对应的遍历过的节点状态
 * 当我们从一个节点出发，遍历后没有新的节点增加的话，那就说明我们走的路是无用的。
 * 同时因为节点个数最多只有 12 个，所以我们可以通过 二进制 来表示状态从而达到优化的效果。
 *
 * 注：这里使用了 进队列 时进行判断的做法，代码上看上去可能没那么好看(for循环遍历邻居时)
 * 但是在时间上可以优化不少。
 * 关于这点的分析可以详细参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Matrix%20Water%20Injection.java
 *
 * 解法参考：
 *  http://zxi.mytechroad.com/blog/graph/leetcode-847-shortest-path-visiting-all-nodes/
 */
class Solution {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        // 采用 入队列时判断 的BFS做法，因此需要处理一下起始情况。
        if (n <= 1) {
            return 0;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][1 << n];
        // 可以选择任意点作为起点，因此开始时需要将所有的节点都 add 到队列中
        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{i, 1 << i});
            visited[i][1 << i] = true;
        }
        // 采用二进制记录状态信息
        int target = (1 << n) - 1;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int pos = curr[0], state = curr[1];

                for (int neigh : graph[pos]) {
                    int nextState = state | (1 << neigh);
                    if (nextState == target) {
                        return step + 1;
                    }
                    if (visited[neigh][nextState]) {
                        continue;
                    }
                    visited[neigh][nextState] = true;
                    queue.offer(new int[]{neigh, state | (1 << neigh)});
                }
            }
            step++;
        }

        return -1;
    }
}