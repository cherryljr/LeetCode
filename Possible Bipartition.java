/*
Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
Each person may dislike some other people, and they should not go into the same group.
Formally, if dislikes[i] = [a, b], it means it is not allowed to put the people numbered a and b into the same group.
Return true if and only if it is possible to split everyone into two groups in this way.

Example 1:
Input: N = 4, dislikes = [[1,2],[1,3],[2,4]]
Output: true
Explanation: group1 [1,4], group2 [2,3]
Example 2:
Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
Output: false
Example 3:
Input: N = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
Output: false

Note:
1 <= N <= 2000
0 <= dislikes.length <= 10000
1 <= dislikes[i][j] <= N
dislikes[i][0] < dislikes[i][1]
There does not exist i != j for which dislikes[i] == dislikes[j].
 */

/**
 * Approach 1: Graph Coloring Based on DFS
 * 对于本题，需要将所有的划分成两个群体，那么我们可以用互相讨厌这个关系作为 边 来建立图。
 * 要求划分之后，两个群体中的人不存在互相讨厌的关系，即这两个子图的内部节点之间没有关联（没有边相连）。
 * 与 LeetCode 785: Is Graph Bipartite? 可以说是同一道题目了。
 * 
 * 因此本题考察的是将一个图进行划分的问题。对此我们可以使用 图的染色 方法来解决。
 * 具体实现就是：
 *  1. 初始情况下，所有节点都是无色的，记为状态 0。
 *  2. 然后将当前节点标记为一个颜色，这里假设为 红色，记为状态 1。
 *  3. 将所有与该节点相邻的节点编辑为 黑色。
 *  4. 依次进行遍历下去，如果发现有两个已经标记过的节点的颜色相同，
 *  这说明无法完成分类，否则可以进行分类。
 * 由上述做法可得，我们很容易就能想到可以使用 DFS 或者 BFS 的方法来实现这个做法。
 *
 * 时间复杂度：O(V+E)
 * 注意：这里为了泛用性直接使用了 Map 来进行建图，但是本题数据明确在 1~N，因此使用 List 即可。
 * 这样可以节省非常多的时间。（为了方便起见这里还使用了 Lamda 表达式，为了追求速度的话，请不要这么写）
 * 对于 数组的实现 方法，可以参考：
 * Is Graph Bipartite?：
 *  
 * 
 * 参考资料：
 *  https://www.youtube.com/watch?v=VlZiMD7Iby4
 */
class Solution {
    public boolean possibleBipartition(int N, int[][] dislikes) {
        // Build the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : dislikes) {
            graph.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(edge[0]);
        }

        int[] colors = new int[N + 1];
        // Color and Check all nodes
        for (int i = 1; i <= N; i++) {
            // 当前点颜色还未被标记过且无法完成对该点的正常标记
            if (colors[i] == 0 && !dfs(colors, i, 1, graph)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 表示将第 pos 个位置的元素 染成color 的颜色后，是否会违反规则
     * 即相邻的两个点颜色相同。
     */
    private boolean dfs(int[] colors, int curr, int color, Map<Integer, List<Integer>> graph) {
        colors[curr] = color;
        if (graph.containsKey(curr)) {
            for (int neigh : graph.get(curr)) {
                // 邻居节点已经被标记过，且颜色相同
                if (colors[neigh] == color) {
                    return false;
                }
                // 邻居节点未被标记过，但是无法正常完成标记
                if (colors[neigh] == 0 && !dfs(colors, neigh, -color, graph)) {
                    return false;
                }
            }
        }
        return true;
    }
}

/**
 * Approach 2: Graph Coloring Based on BFS
 * 相同的做法，不过使用 BFS 实现而已。
 * 
 * 时间复杂度：O(V+E)
 */
class Solution {
    public boolean possibleBipartition(int N, int[][] dislikes) {
        // Build the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : dislikes) {
            graph.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], x -> new ArrayList<>()).add(edge[0]);
        }

        int[] colors = new int[N + 1];
        Queue<Integer> queue = new LinkedList<>();
        // 有可能存在独立节点，所以对每个节点都要判断一次
        for (int i = 1; i <= N; i++) {
            if (colors[i] != 0) {
                continue;
            }
            // Color the current node
            queue.offer(i);
            colors[i] = 1;

            // BFS
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                if (graph.containsKey(curr)) {
                    for (int neigh : graph.get(curr)) {
                    	// 如果相邻节点已经染色过，并且颜色相同，返回false
                        if (colors[neigh] == colors[curr]) {
                            return false;
                        }
                        // 如果相邻节点还未染色过，将其染成相反颜色，然后push到队列中
                        if (colors[neigh] == 0) {
                            colors[neigh] = -colors[curr];
                            queue.offer(neigh);
                        }
                    }
                }
            }
        }

        return true;
    }
}