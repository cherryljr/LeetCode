/*
Given a directed, acyclic graph of N nodes.
Find all possible paths from node 0 to node N-1, and return them in any order.
The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.
graph[i] is a list of all nodes j for which the edge (i, j) exists.

Example:
Input: [[1,2], [3], [3], []]
Output: [[0,1,3],[0,2,3]]
Explanation: The graph looks like this:
0--->1
|    |
v    v
2--->3
There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.

Note:
The number of nodes in the graph will be in the range [2, 15].
You can print different paths in any order, but you should keep the order of nodes inside one path.
 */

/**
 * Approach: Graph DFS (Backtracking)
 * 需要求出所有从 0 到 n-1 的具体路径，因此需要使用 DFS 进行遍历，并记录路径。
 * 这里我们枚举了所有的方案数，找出结尾通向 n-1 的即可。
 *
 * 关于 Backtracking 的模板和详细解释可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Subset.java
 */
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> rst = new LinkedList<>();
        List<Integer> path = new LinkedList<>();
        path.add(0);
        dfs(graph, 0, path, rst);
        return rst;
    }

    private void dfs(int[][] graph, int index, List<Integer> path, List<List<Integer>> rst) {
        if (index == graph.length - 1) {
            rst.add(new LinkedList<>(path));
            return;
        }

        for (int neigh : graph[index]) {
            path.add(neigh);
            dfs(graph, neigh, path, rst);
            // Backtracking
            path.remove(path.size() - 1);
        }
    }
}