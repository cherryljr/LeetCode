/*
There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections 
forming a network where connections[i] = [a, b] represents a connection between servers a and b.
Any server can reach any other server directly or indirectly through the network.

A critical connection is a connection that, if removed, will make some server unable to reach some other server.

Return all critical connections in the network in any order.

Example 1:
https://leetcode.com/problems/critical-connections-in-a-network/
Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
Output: [[1,3]]
Explanation: [[3,1]] is also accepted.

Constraints:
    1. 1 <= n <= 10^5
    2. n-1 <= connections.length <= 10^5
    3. connections[i][0] != connections[i][1]
    4. There are no repeated connections.
*/

/**
 * Approach: Tarjon Algorithm
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 *
 * References:
 *  https://www.cnblogs.com/nullzx/p/7968110.html
 *  https://kirainmoe.com/blog/post/tarjan-algorithm-learning-note/
 */
class Solution {
    int index = 0;
    
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (List<Integer> connection : connections) {
            graph[connection.get(0)].add(connection.get(1));
            graph[connection.get(1)].add(connection.get(0));
        }
        
        List<List<Integer>> ans = new ArrayList<>();
        int[] dfn = new int[n], low = new int[n];
        // use dfn to track if visited (dfn[i] == -1)
        Arrays.fill(dfn, -1);
        for (int i = 0; i < n; i++) {
            if (dfn[i] == -1) {
                tarjan(i, graph, dfn, low, ans, i);
            }
        }
        return ans;
    }
    
    private void tarjan(int u, List<Integer>[] graph, int[] dfn, int[] low, List<List<Integer>> ans, int preU) {
        // discover u
        dfn[u] = low[u] = ++index;
        for (int v : graph[u]) {
            // if v is the father node of u then skip it
            if (v == preU) continue;
            if (dfn[v] == -1) {
                // if not discovered
                tarjan(v, graph, dfn, low, ans, u);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > dfn[u]) {
                    // if there is no path for v to reach back to previous vertices of u, then u - v is critical
                    ans.add(Arrays.asList(u, v));
                }
            } else {
                // if v has been discovered and not parent of u, update low[u]
                // cannot use low[v] because u is not subtree of v
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
    }
}