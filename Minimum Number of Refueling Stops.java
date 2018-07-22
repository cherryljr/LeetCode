/*
A car travels from a starting position to a destination which is target miles east of the starting position.
Along the way, there are gas stations.  Each station[i] represents a gas station that
is station[i][0] miles east of the starting position, and has station[i][1] liters of gas.

The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it.
It uses 1 liter of gas per 1 mile that it drives.

When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.

What is the least number of refueling stops the car must make in order to reach its destination?
If it cannot reach the destination, return -1.

Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there.
If the car reaches the destination with 0 fuel left, it is still considered to have arrived.

Example 1:
Input: target = 1, startFuel = 1, stations = []
Output: 0
Explanation: We can reach the target without refueling.

Example 2:
Input: target = 100, startFuel = 1, stations = [[10,100]]
Output: -1
Explanation: We can't reach the target (or even the first gas station).

Example 3:
Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
Output: 2
Explanation:
We start with 10 liters of fuel.
We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
We made 2 refueling stops along the way, so we return 2.

Note:
1 <= target, startFuel, stations[i][1] <= 10^9
0 <= stations.length <= 500
0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] < target
 */

/**
 * Approach 1: Greedy + PriorityQueue(maxHeap)
 * 第一眼看到本题感觉是 01背包问题 的变形，但是发现数据规模 target 达到了 10^9.
 * 因此排除了这个做法（然而实际上是可以的...这打脸打的...）
 * 因为考虑到 DP 走不通了，又没有其他的想法，那么干脆就使用 贪心 的做法来试试。
 * 思路就是平时生活中的思路罢了，因为一旦加油就会全加，并且油箱无限大。
 * 因此我们肯定是先考虑在能够到达的加油站中选择油量最多的加。
 * 对此我们需要使用到 maxHeap 这个数据结构来帮助我们实现。
 * 具体步骤解析参见代码注释。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // 一开始能够到达的最远距离
        int curr = startFuel;
        int index = 0;
        // maxHeap
        // 你问我为什么不用 lambda 表达式？那玩意跑出来要 56ms 啊！！！ O(n^2) 的方法跑出来都比他快！
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b.compareTo(a);
            }
        });

        int count = 0;
        while (true) {
            // 如果能够到达 target 直接返回加油次数
            if (curr >= target) {
                return count;
            }
            // 还有加油站，并且该加油站是可以到达的，则将其加入到最大堆中
            while (index < stations.length && stations[index][0] <= curr) {
                maxHeap.offer(stations[index++][1]);
            }
            // 可到达的范围内没有加油站可以加油了，说明无法到达，结束循环
            if (maxHeap.isEmpty()) {
                break;
            }
            // 取还没加过的油量最大的加油站进行加油，并更新加油后能够到达的距离，同时加油次数 +1.
            curr += maxHeap.poll();
            count++;
        }

        return -1;
    }
}

/**
 * Approach 2: DP (01 Backpack)
 * 这道问题实际上属于 01背包问题 的改编题。
 * 在 Approach 1 我提到因为空间复杂度的原因我没有使用这个解法。
 * 但是将状态稍微转换一下，我们依然能够使用这个做法来解决这道问题。
 * 即 dp[i] 表示使用前 i 个加油站最远能够到达的距离。这样数组的大小不会超过 500。
 * 接下来的做法与 01背包问题 相同。并且同样进行了空间复杂度的优化。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=vWTPA5zw24M
 */
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        // State: 利用前i个加油站，最远能够到大的距离
        int[] dp = new int[n + 1];
        // Initialize: 一开始最远能到达的距离
        dp[0] = startFuel;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j > 0; j--) {
                // 如果利用前 j-1 个加油站能够到达第 i 个加油站，则说明我们能在这加油
                if (dp[j - 1] >= stations[i][0]) {
                    // 取最远能到达的距离（在 ith 加油 / 不在 ith 加油）
                    dp[j] = Math.max(dp[j], dp[j - 1] + stations[i][1]);
                }
            }
        }

        // 遍历一遍 dp,求最少需要多少个加油站
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }
}