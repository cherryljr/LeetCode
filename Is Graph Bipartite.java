/*
Given an undirected graph, return true if and only if it is bipartite.
Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B
such that every edge in the graph has one node in A and another node in B.

The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j exists.
Each node is an integer between 0 and graph.length - 1.
There are no self edges or parallel edges: graph[i] does not contain i, and it doesn't contain any element twice.

Example 1:
Input: [[1,3], [0,2], [1,3], [0,2]]
Output: true
Explanation:
The graph looks like this:
0----1
|    |
|    |
3----2
We can divide the vertices into two groups: {0, 2} and {1, 3}.

Example 2:
Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
Output: false
Explanation:
The graph looks like this:
0----1
| \  |
|  \ |
3----2
We cannot find a way to divide the set of nodes into two independent subsets.

Note:
graph will have length in range [1, 100].
graph[i] will contain integers in range [0, graph.length - 1].
graph[i] will not contain i or duplicate values.
The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
 */

/**
 * Approach: Graph Coloring Based on DFS
 * 与 LeetCode 886: Possible Bipartition 基本相同。
 * 详细解析和 BFS 的做法可以参考：
 *
 *
 * 时间复杂度：O(V+E)
 */
class Solution {
    public boolean isBipartite(int[][] graph) {
        int node = graph.length, edge = graph[0].length;
        int[] colors = new int[node];

        // Dye and Check all nodes
        for (int i = 0; i < node; i++) {
            if (colors[i] == 0 && !dfs(graph, colors, i, 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int[] colors, int curr, int color) {
        colors[curr] = color;
        for (int neigh : graph[curr]) {
            // if the neigh node has the same color, then return false
            if (colors[neigh] == colors[curr]) {
                return false;
            }
            // if the neigh node hasn't been dyed, check it can be dyed successfully
            if (colors[neigh] == 0 && !dfs(graph, colors, neigh, -color)) {
                return false;
            }
        }
        return true;
    }
}