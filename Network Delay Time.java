/*
There are N network nodes, labelled 1 to N.
Given times, a list of travel times as directed edges times[i] = (u, v, w),
where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal?
If it is impossible, return -1.

Note:
N will be in the range [1, 100].
K will be in the range [1, N].
The length of times will be in the range [1, 6000].
All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 1 <= w <= 100.
 */

/**
 * 这里将会以本题为例分析几个 Graph Search 的做法已经相关模板。
 * 其内容包括：BFS, DFS, Dijkstra, Bellman-Ford, Floyd
 */

/**
 * Approach 1: BFS
 * 使用 BFS 暴力搜索耗时最低的路径。
 * 因为我们要求的是最低耗时的路径，所以我们需要遍历该点的所有路径，并取最小值。
 * （因此 BFS 遍历时用来求 最短步数(step) level by level 的特性，这里就用不上了）
 *
 * BFS 时，只有当前花费的时间更短时，我们才会将该点加入到队列中。并更新 time[]
 * 这相当于进行了剪枝操作。(这里是不使用visited的，因为要枚举所有路径，所以一个点可能被访问多次)
 * 用到相同做法的题目还有 The Maze II:
 *  https://github.com/cherryljr/LeetCode/blob/master/The%20Maze%20II.java
 *
 * 关于 BFS 模板的详细分析可以参考：
 * Matrix Water Injection：
 *  https://github.com/cherryljr/LintCode/blob/master/Matrix%20Water%20Injection.java
 */
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        // Build the graph
        // 这里选择了使用 Map 进行建图（当然使用 邻接矩阵 也可以）
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], x -> new ArrayList<>()).add(new int[]{time[1], time[2]});
        }

        // 该数组用于记录 K 到每个点的最短花费时间
        int[] time = new int[N + 1];
        Arrays.fill(time, Integer.MAX_VALUE);
        time[K] = 0;
        // 数组第一位为 位置，第二位为花费的 时间
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{K, 0});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            if (graph.containsKey(curr[0])) {
                for (int[] next : graph.get(curr[0])) {
                    // 当前花费的时间更少时，更新time[],并将当前值加入到队列中
                    if (time[next[0]] > curr[1] + next[1]) {
                        time[next[0]] = curr[1] + next[1];
                        queue.offer(new int[]{next[0], time[next[0]]});
                    }
                }
            }
        }

        int rst = 0;
        for (int i = 1; i <= N; i++) {
            // 如果有节点未能到达，返回 -1
            if (time[i] == Integer.MAX_VALUE) {
                return -1;
            }
            rst = Math.max(rst, time[i]);
        }
        return rst;
    }
}


/**
 * Approach 2: DFS
 * 太懒了...并不想写...
 * 可以参考该题 Fellow Up 的一个写法：
 *  https://github.com/cherryljr/LeetCode/blob/master/Cheapest%20Flights%20Within%20K%20Stops.java
 */


/**
 * Approach 3: Dijkstra's Algorithm
 * 本题属于 One Source All nodes shortest path 的经典问题.
 * 因此最优解法为 Dijkstra's Algorithm
 * 它可以求出单点出发到所有点的最短路径是多少。非常适合这道题目。
 * 其实质是一个贪心算法，有向图和无向图中均可使用，但是权值不能够是负数。
 * （如果权值为负数可以使用 Bellman-Ford 算法）
 * 
 * 时间复杂度：O(ElogV)
 * 
 * 关于该算法的介绍可以参考：
 *  https://www.youtube.com/watch?v=lAXZGERcDf4
 */
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        if (times == null || times.length == 0) {
            return -1;
        }

        // Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.computeIfAbsent(time[0], x -> new ArrayList<>()).add(new int[]{time[1], time[2]});
        }

        // Dijkstra
        // Use PriorityQueue to get the node with shortest absolute distance
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{K, 0});
        // Use the hashmap to store visited node and the distance
        Map<Integer, Integer> distances = new HashMap<>();

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int pos = curr[0], dis = curr[1];
            // Ignore processed nodes
            if (distances.containsKey(pos)) {
                continue;
            }
            distances.put(pos, dis);

            if (graph.containsKey(pos)) {
                for (int[] neigh : graph.get(pos)) {
                    if (!distances.containsKey(neigh[0])) {
                        pq.offer(new int[]{neigh[0], neigh[1] + dis});
                    }
                }
            }
        }

        if (distances.size() < N) {
            return -1;
        }
        int rst = 0;
        for (int distance : distances.values()) {
            rst = Math.max(rst, distance);
        }
        return rst;
    }
}

/**
 * Approach 4: Bellman Ford
 * 该算法实质上是一个 动态规划 的一个过程。
 * 其思想是按照 走的步数 来进行递推过程。
 * dp[k][v] 表示从 起点 走到 v 最多走 k 步的话，最短耗时是多少。
 * 走到 v 的过程中，我们可以经过 u 也可以不经过 u。
 * 因此动态规划方程为： dp[k][v] = Math.min(dp[k][v] + dp[k - 1][u] + cost[u][v])
 * 因为当前状态仅仅依赖与上一层的状态，所以可以将其空间压缩成为 一维数组。
 * 即： dp[v] = Math.min(dp[v], dp[u] + cost[u][v]);
 *
 * 相比与 Dijkstra 其可以被应用在 权值为 负数 的图中，以及检测 负环(有负权边形成了环)。
 * 如 "1 -> -6 -> 3 -> 1", 那么我们应该如何检测负环呢？
 * 我们只需要进行 N-1(V-1) 次DP之后，再进行一次 DP，然后查看结果即可，
 * 如果结果不变说明其中不存在负环，如果值减小了，说明存在负环。
 *
 * 为什么我们需要重复 V-1 次 DP 过程呢？
 * 这是为了保证我们能够 reach 到途中所有点的最短路径。
 * 举个例子来说：
 *      Graph: 1 -> 2 -> 3 -> 4
 *      Edges: 3-4; 2->3; 1->2
 * 1为起始点。那么当我们按照 Edges 进行 DP 的话。
 * 因为初始化时，只有 dp[1] = 0,其他值均为 MAX,
 * 因此 3->4; 2->3 这两条路径在第一次 DP 时可以说是没派上用场的（结果仍然为 MAX）
 * 但是 dp[2] 可以通过 1->2 被计算出其 minDistance.也就不再是 MAX 了。
 * 然后继续下去，我们发现只有再经过 2 次上述过程，dp[4] 的值才能被正确计算出来。
 * 因此我们必须进行 V-1 次 DP 过程，以保证在最坏情况下起始点到每一个点的 minDistance 都能被计算出来。
 * 然而实际效果上，其移动次数通常 大于 V-1 次，这是为什么呢？
 * 有兴趣的可以参见：
 * Cheapest Flights Within K Stops
 *  https://github.com/cherryljr/LeetCode/blob/master/Cheapest%20Flights%20Within%20K%20Stops.java
 *
 * 时间复杂度为：O(V*E)
 * 空间复杂度为：O(V)
 *
 * 详细解析：
 *  https://www.youtube.com/watch?v=-mOEd_3gTK0&t=196s
 */
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        // Initialize the dp array
        int[] dp = new int[N + 1];
        final int MAX = 0x3f3f3f3f;
        Arrays.fill(dp, MAX);
        dp[K] = 0;

        // 总共有 N 个点，即我们总共需要进行 N-1 次 process
        for (int i = 1; i < N; i++) {
            // 尝试不同的路径，进行DP
            for (int[] time : times) {
                int u = time[0], v = time[1], t = time[2];
                dp[v] = Math.min(dp[v], dp[u] + t);
            }
        }

        int rst = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            if (dp[i] == MAX) {
                return -1;
            }
            rst = Math.max(rst, dp[i]);
        }
        return rst;
    }
}

/**
 * Approach 5: Floyd-Warshall
 * 可被用于计算图中 所有节点对 之间的最小距离。
 * 图可以是 无向图 或者是 有向图；
 * 边的权值可以为 正数 或者是 负数。
 * 该算法基于 动态规划。
 * 核心为：节点i 到 节点j 是否要经过节点k.这里有 经过 和 不经过两种方案，取两种方案中的最小值即可。
 * 因此动态规划方程为： dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
 * 对于 k, i, j 的位置均为 1~N 之间，因此使用 3 个for循环即可。
 *
 * 本题使用该算法确实是杀鸡用牛刀了，并且其时间复杂度也高于 Dijkstra 和 Bellman
 * 但是本题测试数比较小，还是能过的。而且该算法写起来非常的简洁明了哈。
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2)
 *
 * 算法具体讲解：
 *  https://www.youtube.com/watch?v=LwJdNfdLF9s
 */
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        // 这里使用了 邻接矩阵 来储存图的信息
        int[][] dp = new int[N + 1][N + 1];
        final int MAX = 0x3f3f3f3f;
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], MAX);
            dp[i][i] = 0;
        }
        for (int[] time : times) {
            dp[time[0]][time[1]] = time[2];
        }

        // Floyd-Warshall
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    // 这里涉及到了 dp[i][k] + dp[k][j] 操作
                    // 如果这两条路径是无效的话，我们将无效路径初始化成 Integer.MAX_VALUE 就会发生数值溢出的情况
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);

                    // 如果要初始化成 Integer.MAX_VALUE 的话，注意判断路径的合法性
                    // 只有当 i~k 与 k~j 之间能够相通时，才能取到 dp[i][k] 和 dp[k][j] 的值
                    // if (dp[i][k] != Integer.MAX_VALUE && dp[k][j] != Integer.MAX_VALUE) {
                    //     dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                    // }
                }
            }
        }

        int rst = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            if (dp[K][i] == MAX) {
                return -1;
            }
            rst = Math.max(rst, dp[K][i]);
        }
        return rst;
    }
}