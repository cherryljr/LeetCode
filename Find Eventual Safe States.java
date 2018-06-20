/*
In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.
If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.

Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.
More specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a terminal node in less than K steps.

Which nodes are eventually safe?  Return them as an array in sorted order.
The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.
The graph is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.

Example:
Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
Output: [2,4,5,6]
Here is a diagram of the above graph.

Illustration of graph
https://leetcode.com/problems/find-eventual-safe-states/description/

Note:
graph will have length at most 10000.
The number of edges in the graph will not exceed 32000.
Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
 */

/**
 * Approach: Topological Sort
 * 本题实质上就是要寻找有哪些点不在 环 中。
 * 因为在环中的点会无限制地走下去，因此不是 safe point.
 *
 * 而本题是一个 有向图，那么要判断环自然就会想到使用 拓扑排序 来解决。
 * 只要当前节点能够被成功 拓扑排序 那就说明该节点不在 环 中，则在 rst 中 add 当前节点。
 * 这里使用了基于 DFS 的做法，同样也有 BFS 的做法。
 * 详细解析可以参考模板：
 *  https://github.com/cherryljr/LintCode/blob/master/Course%20Schedule%20II.java
 *
 * Note:
 *  本题输入就是一个邻接矩阵，因此无需建表，直接拿过来用即可；
 *  遍历的时候是按照顺序 0~graph.length-1, 因此结果也是按照顺序添加的，无需进行排序
 *
 * 时间复杂度：O(V + E)
 */
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> rst = new ArrayList<>();
        byte[] visited = new byte[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (topoSort(graph, i, visited)) {
                rst.add(i);
            }
        }
        return rst;
    }

    private boolean topoSort(int[][] graph, int index, byte[] visited) {
        if (visited[index] == -1) {
            return false;
        }
        if (visited[index] == 1) {
            return true;
        }

        visited[index] = -1;
        for (int neigh : graph[index]) {
            if (!topoSort(graph, neigh, visited)) {
                return false;
            }
        }
        visited[index] = 1;
        return true;
    }
}