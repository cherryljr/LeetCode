/*
Starting with an undirected graph (the "original graph") with nodes from 0 to N-1, subdivisions are made to some of the edges.
The graph is given as follows: edges[k] is a list of integer pairs (i, j, n) such that (i, j) is an edge of the original graph,
and n is the total number of new nodes on that edge.
Then, the edge (i, j) is deleted from the original graph, n new nodes (x_1, x_2, ..., x_n) are added to the original graph,
and n+1 new edges (i, x_1), (x_1, x_2), (x_2, x_3), ..., (x_{n-1}, x_n), (x_n, j) are added to the original graph.
Now, you start at node 0 from the original graph, and in each move, you travel along one edge.
Return how many nodes you can reach in at most M moves.

Example 1:
    https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/description/
Input: edges = [[0,1,10],[0,2,1],[1,2,2]], M = 6, N = 3
Output: 13
Explanation:
The nodes that are reachable in the final graph after M = 6 moves are indicated below.

Example 2:
Input: edges = [[0,1,4],[1,2,6],[0,2,8],[1,3,1]], M = 10, N = 4
Output: 23

Note:
1. 0 <= edges.length <= 10000
2. 0 <= edges[i][0] < edges[i][1] < N
3. There does not exist any i != j for which edges[i][0] == edges[j][0] and edges[i][1] == edges[j][1].
4. The original graph has no parallel edges.
5. 0 <= edges[i][2] <= 10000
6. 0 <= M <= 10^9
7. 1 <= N <= 3000
8. A reachable node is a node that can be travelled to using at most M moves starting from node 0.
 */

/**
 * Approach: Dijkstra
 * 本题的主要两个考点：
 *  1.需要将两个定点中间的那些小顶点转换成 权值 来进行表示。
 *  这样不仅可以节省建图所需的空间，在后续的处理过程中还能节省大量时间。
 *  因为由题意可得：edges[i][2] 最大值为 10000 还是一个非常大的数值，
 *  同时根据 M 的数据范围（1e9），我们可以清楚地知道，之间复杂度不应该和 M 有关系，
 *  所以如果将中间顶点也进行建图的话，肯定会超时。
 *  2. 利用 Dijkstra 算法对时间复杂度进行一个优化，
 *  题目问的是最多可以 reach 到的点数，而 Dijkstra 求的是单源最短路径问题，如何将二者联系起来呢？
 *  这里是利用了 剩余步数(hp) 这个信息。当一个点所剩余的步数越多（hp值越大）这就意味着：
 *  从该点出发可以到达的点数也就越多。因此我们可以利用 Dijkstra 来求最短路径信息（即使得到达时剩余步数最多）
 *  因此此处用的 PriorityQueue 是一个最大堆。（根据 hp 排序）
 *
 * 时间复杂度：O(ElogE)
 * 空间复杂度：O(E)
 *
 * 参考资料：
 *  https://zxi.mytechroad.com/blog/graph/leetcode-886-reachable-nodes-in-subdivided-graph/
 */
class Solution {
    public int reachableNodes(int[][] edges, int M, int N) {
        // Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(new int[]{edge[1], edge[2] + 1});
            graph.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(new int[]{edge[0], edge[2] + 1});
        }

        // {node, hp}, sort by HP desc
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        // node -> max HP(step) left
        Map<Integer, Integer> hpMap = new HashMap<>();
        maxHeap.offer(new int[]{0, M});
        while (!maxHeap.isEmpty()) {
            int[] curr = maxHeap.poll();
            int pos = curr[0], hp = curr[1];
            if (hpMap.containsKey(pos)) {
                continue;
            }

            hpMap.put(pos, hp);
            if (graph.containsKey(pos)) {
                for (int[] neigh : graph.get(pos)) {
                    int nextPos = neigh[0], nextHp = hp - neigh[1];
                    // skip the visited node or the node that can't reach
                    if (hpMap.containsKey(nextPos) || nextHp < 0) {
                        continue;
                    }
                    maxHeap.offer(new int[]{nextPos, nextHp});
                }
            }
        }

        int rst = hpMap.size(); // Original nodes covered
        for (int[] edge : edges) {
            int u = hpMap.getOrDefault(edge[0], 0);
            int v = hpMap.getOrDefault(edge[1], 0);
            rst += Math.min(edge[2], u + v);
        }
        return rst;
    }
}