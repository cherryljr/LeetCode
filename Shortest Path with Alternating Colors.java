/*
Consider a directed graph, with nodes labelled 0, 1, ..., n-1.  In this graph, each edge is either red or blue, and there could be self-edges or parallel edges.
Each [i, j] in red_edges denotes a red directed edge from node i to node j.  Similarly, each [i, j] in blue_edges denotes a blue directed edge from node i to node j.
Return an array answer of length n, where each answer[X] is the length of the shortest path from node 0 to node X such
that the edge colors alternate along the path (or -1 if such a path doesn't exist).

Example 1:
Input: n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
Output: [0,1,-1]

Example 2:
Input: n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
Output: [0,1,-1]

Example 3:
Input: n = 3, red_edges = [[1,0]], blue_edges = [[2,1]]
Output: [0,-1,-1]

Example 4:
Input: n = 3, red_edges = [[0,1]], blue_edges = [[1,2]]
Output: [0,1,2]

Example 5:
Input: n = 3, red_edges = [[0,1],[0,2]], blue_edges = [[1,0]]
Output: [0,1,1]

Constraints:
    1. 1 <= n <= 100
    2. red_edges.length <= 400
    3. blue_edges.length <= 400
    4. red_edges[i].length == blue_edges[i].length == 2
    5. 0 <= red_edges[i][j], blue_edges[i][j] < n
 */

/**
 * Approach: BFS
 * 看到无权图求最短路径，第一反应就是 BFS。
 * 因为题目中所给出边的信息除了包含所连接的节点信息以外，还有一个颜色信息。
 * 并且在遍历的过程中要求：所走的路径必须是红蓝顺序相间。
 * 因此，我们可以分别建立 redMap 和 blueMap 来维护红色和蓝色的路径，以便于我们BFS进行搜索。
 *
 * 值得注意的是：因为在遍历的时候，使用了两个 Map 的信息，所以对于 visited 的信息也要区分进行维护。
 * 即，我们不仅需要记录这个点被 visited 过，还需要记录它是通过 红色 还是 蓝色 的路径来到当前点的。
 * 这样才能遍历完所有的方案。与 Shortest Path Visiting All Nodes 类似。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 * 
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Path%20Visiting%20All%20Nodes.java
 */
class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        Map<Integer, List<Integer>> redMap = constructMap(red_edges);
        Map<Integer, List<Integer>> blueMap = constructMap(blue_edges);
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][2];
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        // 0代表红色路径，1代表蓝色路径
        // 起始位置在0，代表两种方案均可到达 0 位置，从 0 出发走红色还是蓝色路径均可。
        // 即初始化时要把 0 的两种状态均进行初始化。
        queue.offer(new int[]{0, 0});
        queue.offer(new int[]{0, 1});
        visited[0][0] = visited[0][1] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                ans[curr[0]] = ans[curr[0]] == -1 ? step : Math.min(ans[curr[0]], step);
                if (curr[1] == 0) {
                    // 下一步需要走蓝色
                    if (blueMap.containsKey(curr[0])) {
                        for (int neig : blueMap.get(curr[0])) {
                            if (visited[neig][1]) continue;
                            queue.offer(new int[]{neig, 1});
                            visited[neig][1] = true;
                        }
                    }
                } else {
                    // 下一步需要走红色
                    if (redMap.containsKey(curr[0])) {
                        for (int neig : redMap.get(curr[0])) {
                            if (visited[neig][0]) continue;
                            queue.offer(new int[]{neig, 0});
                            visited[neig][0] = true;
                        }
                    }
                }
            }
            step++;
        }
        return ans;
    }

    private Map<Integer, List<Integer>> constructMap(int[][] edges) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(edge[1]);
        }
        return map;
    }
}

