/*
We have a collection of rocks, each rock has a positive integer weight.
Each turn, we choose the two heaviest rocks and smash them together.
Suppose the stones have weights x and y with x <= y.  The result of this smash is:
    If x == y, both stones are totally destroyed;
    If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
At the end, there is at most 1 stone left.  Return the weight of this stone (or 0 if there are no stones left.)

Example 1:
Input: [2,7,4,1,8,1]
Output: 1
Explanation:
We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of last stone.

Note:
    1. 1 <= stones.length <= 30
    2. 1 <= stones[i] <= 1000
 */

/**
 * Approach: PriorityQueue
 * 题目要求：每次取出最大的两个数，用较大值-较小值，然后将差值重新放入集合中进行排序。
 * （如果当前最大的两个数相等，则放入0，这是因为 0 必定是最小的，同时也符合题目要求的返回结果）
 * 每次操作之后，都需要对集合进行一次重新排序。
 * 毫无疑问，上述情况非常使用于 最大堆（Heap） 这个数据结构。
 * 在 Java 中直接使用 PriorityQueue 即可解决。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
class Solution {
    public int lastStoneWeight(int[] stones) {
        // 构建最大堆（从大到小的顺序）
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int stone : stones) {
            pq.offer(stone);
        }
        // N个石头，会发生 N-1 次Collisions
        for (int i = 0; i < stones.length - 1; i++) {
            pq.offer(pq.poll() - pq.poll());
        }
        return pq.poll();
    }
}