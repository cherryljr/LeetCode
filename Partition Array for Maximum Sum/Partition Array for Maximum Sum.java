/*
Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.
After partitioning, each subarray has their values changed to become the maximum value of that subarray.

Return the largest sum of the given array after partitioning.

Example 1:
Input: A = [1,15,7,9,2,5,10], K = 3
Output: 84
Explanation: A becomes [15,15,15,9,10,10,10]

Note:
    1. 1 <= K <= A.length <= 500
    2. 0 <= A[i] <= 10^6
 */

/**
 * Approach: DP
 * 刚刚看到这个问题的时候没有什么好的思路，因此从数据规模入手推测大概的时间复杂度为 O(n^2)
 * 因为是优化类的问题，想到使用 Greedy，从左向右进行修改，但是很容易就发现该策略是错的，
 * 而且时间复杂度是 O(n)，同时该题无法排序解决。因此排除 Greedy 和 PriorityQueue 的解法。
 * 最后只剩下 DP 的解法（老实说一开始有考虑到 DP，但是没静下心去想 递推方程，就把 DP 给 Pass了，naive...）
 *
 * 确定从 DP 入手后，我们需要想好如何确定状态，已经状态之间的递推关系。
 * 当数组范围确定之后，题目的结果也已经确定了。因此可以从这里进行入手：
 *  dp[i] 表示前 i 个元素的 maxSum 结果。
 *  则 dp[i] 的结果依赖于 [i-K, i] 这段数组的一个划分情况。（划分的子数组长度不得超过 K）
 *  [i-K, i]区间中可以任意选取一段 [x, i] 来作为当前分割的最后一段，因此状态转移方程为：
 *      dp[i] = max{dp[i - k] + currMax * k} (1 ≤k≤K)
 * 同时，在此过程中我们需要维护一个 currMax ([i-k, i] 的区间最大值)
 * 
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 * 
 * Reference:
 *  https://youtu.be/3M8q-wB2tmw
 */
class Solution {
    public int maxSumAfterPartitioning(int[] A, int K) {
        int n = A.length, dp[] = new int[n + 1];
        // 关于数组元素下标的问题，如果感觉不确定可以直接举个例子即可
        for (int i = 1; i <= n; i++) {
            int currMax = Integer.MIN_VALUE;
            for (int k = 1; k <= Math.min(i, K); k++) {
                currMax = Math.max(currMax, A[i - k]);
                dp[i] = Math.max(dp[i], dp[i - k] + currMax * k);
            }
        }
        return dp[n];
    }
}