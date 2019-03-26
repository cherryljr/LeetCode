/*
There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1).
You begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.

Note:
If there exists a solution, it is guaranteed to be unique.
Both input arrays are non-empty and have the same length.
Each element in the input arrays is a non-negative integer.

Example 1:
Input:
gas  = [1,2,3,4,5]
cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.

Example 2:
Input:
gas  = [2,3,4]
cost = [3,4,3]
Output: -1
Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.
 */

/**
 * Approach: Traversal (Greedy)
 * 如果要走完一整圈，那么必定存在：sum(gas[i] - cost[i]) >= 0.
 * 并且题目明确说明：如果存在解的话，那么这个解是 unique 的。
 * 因此，我们可以放心地直接遍历...详见代码注释。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // tank表示当前油箱内汽油的量
        // leftGas表示消耗之后剩余的汽油量
        int index = 0, tank = 0, leftGas = 0;
        for (int i = 0; i < gas.length; i++) {
            // 当前站点提供的汽油量扣除去消耗后剩余的量
            int left = gas[i] - cost[i];
            // 如果汽油量不足以支撑到下一个站点，则将起点移动到下一个站点，同时油箱重置为初始状态
            if (tank + left < 0) {
                tank = 0;
                index = i + 1;
            } else {
                tank += left;
            }
            leftGas += left;
        }
        // 如果走完全程剩余的汽油量 < 0，说明无解
        return leftGas >= 0 ? index : -1;
    }
}