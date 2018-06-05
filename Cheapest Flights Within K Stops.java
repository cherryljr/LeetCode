/*
There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
Now given all the cities and fights, together with starting city src and the destination dst,
your task is to find the cheapest price from src to dst with up to k stops.
If there is no such route, output -1.

Example 1:
Input:
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 1
Output: 200
Explanation:
The graph looks like this:
https://leetcode.com/problems/cheapest-flights-within-k-stops/description/
The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.

Example 2:
Input:
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 0
Output: 500
Explanation:
The graph looks like this:
https://leetcode.com/problems/cheapest-flights-within-k-stops/description/
The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.

Note:
The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
The size of flights will be in range [0, n * (n - 1) / 2].
The format of each flight will be (src, dst, price).
The price of each flight will be in the range [1, 10000].
k is in the range of [0, n - 1].
There will not be any duplicated flights or self cycles.
 */

/**
 * Approach 1: BFS
 * 使用 BFS 暴力搜索最便宜的路径。
 * 因为我们要求的是最便宜的路径，所以我们在遇到 终点(dst) 时不能直接退出，
 * 而是需要求所有通向终点的路径中最便宜的那条。
 * （因此 BFS 遍历时用来求 最短步数(step) level by level 的特性，这里就用不上了）
 * 我们在遇到 dst 后无法提前结束循环，所以使用了 出队列 时进行判断的 BFS 模板。
 * 同时这里进行了两个地方的优化：
 *  1. 只有当前的花费信息 < 可能的结果 rst 时，我们才会将该点加入到队列中
 *  这相当于进行了剪枝操作。(这里是不使用visited的，因为要枚举所有路径，所以一个点可能被访问多次)
 *  用到相同做法的题目还有 The Maze II:
 *  https://github.com/cherryljr/LeetCode/blob/master/The%20Maze%20II.java
 *  2. 当 Step 超过要求的 K 时，直接结束循环。
 *
 * 关于 BFS 模板的详细分析可以参考：
 * Matrix Water Injection：
 *  https://github.com/cherryljr/LintCode/blob/master/Matrix%20Water%20Injection.java
 *
 * 时间复杂度为：O(n^(k+1)) 因为进行了优化，所以实际运行时间远低于此
 * 空间复杂度为：O(n^(k+1))
 */
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // Build the graph
        // 使用 Map 或者 邻接矩阵 建图均可
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.computeIfAbsent(flight[0], x -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }

        // 数组第一位为节点位置信息，第二位为当前的花费
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{src, 0});
        int step = 0, rst = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                if (curr[0] == dst) {
                    rst = Math.min(curr[1], rst);
                }

                List<int[]> nextList = graph.get(curr[0]);
                if (nextList == null) {
                    continue;
                }
                for (int[] next : nextList) {
                    // 当前的花费信息 < 可能结果 rst 时，才加入队列（剪枝）
                    if (curr[1] + next[1] < rst) {
                        queue.offer(new int[]{next[0], curr[1] + next[1]});
                    }
                }
            }
            // 步数超过 K 时，直接跳出
            if (step++ > K) {
                break;
            }
        }

        return rst == Integer.MAX_VALUE ? -1 : rst;
    }
}

/**
 * Approach 2: DFS (Backtracking)
 * 经过 Approach 1 中的分析，我们知道本题中 BFS 并不具备什么优势。
 * 因此该题也可以使用 DFS 进行暴力搜索求解。
 * 同样,DFS 的过程中我们也需要对其进行 剪枝 操作。
 *
 * 时间复杂度为：O(n^(k+1)) 因为进行了优化，所以实际运行时间远低于此
 * 空间复杂度为：O(k+1)     递归的深度只有 k+1 
 *
 * 关于 DF模板 的详细解析可以参考：
 * SubSet:
 *  https://github.com/cherryljr/LintCode/blob/master/Subset.java
 * Word Search (When should we use backtracking):
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Search.java
 */
class Solution {
    private int rst = Integer.MAX_VALUE;

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        if (src == dst) {
            return 0;
        }

        // Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.computeIfAbsent(flight[0], x -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }
        // Marked the visited node
        boolean[] visited = new boolean[n];
        visited[src] = true;
        dfs(graph, src, dst, K, 0, visited);

        return rst == Integer.MAX_VALUE ? -1 : rst;
    }

    private void dfs(Map<Integer, List<int[]>> graph, int src, int dst, int K, int cost, boolean[] visited) {
        if (src == dst) {
            rst = Math.min(rst, cost);
            return;
        }
        if (K < 0) {
            return;
        }

        List<int[]> list = graph.get(src);
        // the next stop should be existed
        if (list != null) {
            for (int[] next : graph.get(src)) {
                // 只有当该点 未被访问过 且 花费小于 可能的结果 时才遍历它
                if (!visited[next[0]] && cost + next[1] < rst) {
                    visited[next[0]] = true;
                    dfs(graph, next[0], dst, K - 1, cost + next[1], visited);
                    // Backtracking
                    visited[next[0]] = false;
                }
            }
        }
    }
}

/**
 * Approach 3: Bellman Ford
 * 因为本题对 移动次数 进行了限制，并且需要求最短路径。
 * 自然想到可以使用 Bellman Ford 来进行解决，因为这个算法就是针对 移动次数 来进行 DP 的。
 * 这里我们只需要对移动次数进行限制即可。
 * 限制为 K 个 stop,因此意味着我们最多可以移动 K+1 次。
 *
 * 同时我们可以利用 滚动数组 对其空间复杂度进行优化。
 * 对比于次数限制为 V-1 的写法 Network Delay Time.
 * 我们发现写法有点区别，这是因为在 Network Delay Time 中我们虽然说是限制 V-1 次。
 * 但实际上其每次 第二层循环process(DP) 时，移动次数可能并不仅仅只是 1 次而已，举个例子来说：
 *      Graph: 1 -> 2 -> 3 -> 4
 *      Edges: 1 -> 2; 2 -> 3; 3 -> 4
 * 实际上经过一次 process 就完全足够了，这是因为其实际的移动为 3.
 * 因此 准确地说 Network Delay Time 我们是为了 保证至少有 V-1 次 process，并不是只能有 V-1 次。
 * 而本题中则需要避免这种情况的发生。
 * 因此一次移动之后的效果只能在下一次移动才能够生效。
 * 对此我们使用了 滚动数组 来保存前一个状态，使得每次 process 的都是根据前一个状态信息。
 * 这样就保证了每次只移动 1 步。
 *
 * 视频讲解：
 *  http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-787-cheapest-flights-within-k-stops/
 * Network Delay Time：
 *
 */
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[] dp = new int[n + 1];
        final int MAX = 0x3f3f3f3f;
        Arrays.fill(dp, MAX);
        dp[src] = 0;

        for (int i = 0; i <= K; i++) {
            int[] temp = new int[n + 1];
            System.arraycopy(dp, 0, temp, 0, n + 1);
            for (int[] flight : flights) {
                int u = flight[0], v = flight[1], cost = flight[2];
                // 只进行一次移动
                temp[v] = Math.min(temp[v], dp[u] + cost);
            }
            dp = temp;
        }

        return dp[dst] == MAX ? -1 : dp[dst];
    }
}