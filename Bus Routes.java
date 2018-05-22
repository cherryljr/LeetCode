/*
We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever.
For example if routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the sequence 
1->5->7->1->5->7->1->... forever.
We start at bus stop S (initially not on a bus), and we want to go to bus stop T. 
Travelling by buses only, what is the least number of buses we must take to reach our destination? 
Return -1 if it is not possible.

Example:
Input: 
routes = [[1, 2, 7], [3, 6, 7]]
S = 1
T = 6
Output: 2

Explanation: 
The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.

Note:
1 <= routes.length <= 500.
1 <= routes[i].length <= 500.
0 <= routes[i][j] < 10 ^ 6.
 */

/**
 * Approach: BFS
 * 这就是我们日常生活中的 公交车换乘 问题。
 * 本题的目的在于求：从起点站到目的地站，最少需要 乘坐的车辆数（路线数）。
 * 对此我们可以使用 BFS 来解决问题，但是难点在于：
 *  每个站点都会有几辆车经过，如果是求最少的站数，那么我们可以通过构建 交通图，
 *  然后直接计算 BFS 的步数即可。但是本题所要求的是实现最少的 换乘。
 *
 * 对此我们可以对应到日常生活中，看平时生活中我们是如何解决问题的呢？
 * 假如有如下交通图：
 *  0号线： 1->3->4->5
 *  1号线： 3->8->12
 *  2号线： 4->7->8->10
 * 我们从 1号站 出发，想到到大 12号站。想要实现最少换乘的话，我们肯定会先找
 * 有没有同时经过 1，12号站的路线，如果有只需要 1 次即可，否则我们就会去找
 * 有没有哪个站同时在 1号站所属的线路 与 12号站所属的线路 上面。
 * 如果在的话，那么它就可以作为 换乘点 来实现 1->12，所需路线数：2.
 *
 * 鉴于上述思想，我们可以把目光放在：每个站点分别在哪几条路线上 而不是 每条路线分别经过了那几个站点。
 * 首先，我们需要统计每个站点上有哪几条路线经过。
 * 然后以起始站点 S 开始进行 BFS，依次对 S 站点上的路线进行遍历。
 * 如果当前路线还未被 visited,则遍历当前路线上所有的站点，并将其加入到队列中。
 * 如果遇到终点 T，则直接返回 times.
 * times 为当每次我们进行换乘路线的时候，次数+1.
 * 因此，通过 BFS 我们就能够找到最少的换乘次数，即我们所需要的答案。
 */
class Solution {
    public int numBusesToDestination(int[][] routes, int S, int T) {
        if (S == T) {
            return 0;
        }

        Map<Integer, List<Integer>> stopToRoute = new HashMap<>();
        // 统计每个站点上有哪几条路线经过
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToRoute.computeIfAbsent(stop, x -> new LinkedList<>()).add(i);
            }
        }

        // 记录已经遍历过的线路
        Set<Integer> visitedRoute = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(S);
        int times = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int currStop = queue.poll();

                // 遍历当前站点上所有的线路
                for (int routeId : stopToRoute.get(currStop)) {
                    // 如果当前线路还未被遍历过，则遍历该线路上所有能够经过的站点，将其加入到队列中。
                    if (!visitedRoute.contains(routeId)) {
                        visitedRoute.add(routeId);
                        for (int stop : routes[routeId]) {
                            // 当遇到终点，直接返回 times 即可
                            // 我们也可以等到 poll() 的时候再做判断，但是那将浪费大量无用的计算
                            // 因此 BFS 还是尽早退出的好
                            if (stop == T) {
                                return times + 1;
                            }
                            queue.offer(stop);
                        }
                    }
                }
            }
            times++;
        }

        // 无法到达
        return -1;
    }
}