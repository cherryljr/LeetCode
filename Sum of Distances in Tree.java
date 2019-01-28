/*
An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
The ith edge connects nodes edges[i][0] and edges[i][1] together.
Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.

Example 1:
Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
Output: [8,12,6,10,10,10]
Explanation:
Here is a diagram of the given tree:
  0
 / \
1   2
   /|\
  3 4 5
We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.

Note: 1 <= N <= 10000
 */

/**
 * Approach: PreOrder and PostOrder DFS
 * Intuition:
 *  What if given a tree, with a certain root 0?
 *  In O(N) we can find sum of distances in tree from root and all other nodes.
 *  Now for all N nodes?
 *
 * When we move our root from one node to its connected node, one part of nodes get closer, one the other part get further.
 * If we know exactly hom many nodes in both parts, we can solve this problem.
 *
 * With one single traversal in tree, we should get enough information for it and don't need to do it again and again.
 * Let's solve it with node 0 as root.
 *  Initial an List of hashset graph, graph[i] contains all connected nodes to i.
 *  Initial an array count, count[i] counts all nodes in the subtree i. (value is 1)
 *  Initial an array of res, res[i] counts sum of distance in subtree i.
 *
 *  Post order dfs traversal, update count and res:
 *      count[root] = sum(count[i])
 *      res[root] = sum(res[i]) + sum(count[i])
 *
 *  Pre order dfs traversal, update res:
 *  When we move our root from parent to its child i,
 *  count[i] points get 1 closer to root, n - count[i] nodes get 1 further to root.
 *      res[i] = res[root] - count[i] + N - count[i]
 *
 * Time Complexity: O(N)
 * Space Complexity: O(N)
 *
 * Reference:
 *  https://leetcode.com/problems/sum-of-distances-in-tree/solution/
 */
class Solution {
    int[] res, count;
    List<Set<Integer>> graph;
    int N;

    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        this.N = N;
        res = new int[N];
        count = new int[N];
        graph = new ArrayList<>();

        // Initialize the value of count array. (Cuz it contains itself)
        Arrays.fill(count, 1);
        // Build the tree graph
        for (int i = 0; i < N; i++) {
            graph.add(new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        // PreOrder Process
        dfs(0, -1);
        // PostOrder Process
        dfs2(0, -1);

        return res;
    }

    private void dfs(int node, int parent) {
        for (int child : graph.get(node)) {
            if (child != parent) {
                dfs(child, node);
                count[node] += count[child];
                res[node] += res[child] + count[child];
            }
        }
    }

    private void dfs2(int node, int parent) {
        for (int child : graph.get(node)) {
            if (child != parent) {
                res[child] = res[node] - count[child] + N - count[child];
                dfs2(child, node);
            }
        }
    }
}
