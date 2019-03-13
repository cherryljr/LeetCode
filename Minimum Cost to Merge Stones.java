/*
There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.

A move consists of merging exactly K consecutive piles into one pile,
and the cost of this move is equal to the total number of stones in these K piles.

Find the minimum cost to merge all piles of stones into one pile.
If it is impossible, return -1.

Example 1:
Input: stones = [3,2,4,1], K = 2
Output: 20
Explanation:
We start with [3, 2, 4, 1].
We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
We merge [4, 1] for a cost of 5, and we are left with [5, 5].
We merge [5, 5] for a cost of 10, and we are left with [10].
The total cost was 20, and this is the minimum possible.

Example 2:
Input: stones = [3,2,4,1], K = 3
Output: -1
Explanation: After any merge operation, there are 2 piles left, and we can't merge anymore.  So the task is impossible.

Example 3:
Input: stones = [3,5,1,2,6], K = 3
Output: 25
Explanation:
We start with [3, 5, 1, 2, 6].
We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
We merge [3, 8, 6] for a cost of 17, and we are left with [17].
The total cost was 25, and this is the minimum possible.

Note:
    1. 1 <= stones.length <= 30
    2. 2 <= K <= 30
    3. 1 <= stones[i] <= 100
 */

/**
 * Approach 1: Interval DP
 * 石子归并问题属于经典的区间DP问题。可以使用区间DP的解题步骤模板解决。
 * 这道题目中加入了 每次只能合并连续K个石堆 作为限制。
 * 因此我们可以直接在DP维数上增加一维即可。
 *  dp[start][end][k]表示将区间 [start, end] 的石堆合并成 K 堆。
 * 最后要求的结果就是 dp[0][len-1][1]
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2 * k)
 *
 * 类似的问题：
 *  https://github.com/cherryljr/LintCode/blob/master/Segment%20Stones%20Merge.java
 * 区间DP问题的解题步骤模板：
 *  https://github.com/cherryljr/LeetCode/blob/master/Burst%20Balloons.java
 */
class Solution {
    private static final int INF = 0x3f3f3f3f;

    public int mergeStones(int[] stones, int K) {
        int len = stones.length;
        if ((len - 1) % (K - 1) != 0) {
            return -1;
        }
        
        int[] preSum = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            preSum[i] = preSum[i - 1] + stones[i - 1];
        }

        // Initialize
        int[][][] dp = new int[len][len][K + 1];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                for (int k = 0; k <= K; k++) {
                    dp[i][j][k] = (i == j && k == 1) ? 0 : INF;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            dp[i][i][1] = 0;
        }

        // 枚举区间长度
        for (int l = 2; l <= len; l++) {
        	// 枚举起始位置
            for (int start = 0; start + l <= len; start++) {
                int end = start + l - 1;
                for (int k = 2; k <= K; k++) {
                    // 注意这里与 Burst Balloons 里不一样的是 pivot 的起始位置为 start.(不然会漏掉 dp[i][i] 的情况)
                    // 并且 pivot 每次移动 K-1 步，从而保证 [start, pivot] 肯定可以被合并
                    for (int pivot = start; pivot < end; pivot += K - 1) {
                        dp[start][end][k] = Math.min(dp[start][end][k], dp[start][pivot][1] + dp[pivot + 1][end][k - 1]);
                    }
                }
                dp[start][end][1] = dp[start][end][K] + preSum[end + 1] - preSum[start];
            }
        }

        return dp[0][len - 1][1];
    }
}

/**
 * Approach 2: Interval DP (Optimized)
 * 实际上本题中的 K 是可以省略掉的。
 * 因为K是确定的，所以将 [start, end] 尽可能合并之后，是有固定最优解的。
 * 因此 K 这个维度实际上是可以被省略掉的。
 * 其他地方写法与 Approach 1 相同。
 *
 * 时间复杂度：O(n^3 / K)
 * 空间复杂度：O(n^2)
 *
 * Reference: https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-1000-minimum-cost-to-merge-stones/
 */
class Solution {
    private static final int INF = 0x3f3f3f3f;

    public int mergeStones(int[] stones, int K) {
        int len = stones.length;
        if ((len - 1) % (K - 1) != 0) {
            return -1;
        }
        
        int[] preSum = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            preSum[i] = preSum[i - 1] + stones[i - 1];
        }

        // Initialize
        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][i] = 0;
        }

        for (int l = 2; l <= len; l++) {
            for (int start = 0; start + l <= len; start++) {
                int end = start + l - 1;
                for (int pivot = start; pivot < end; pivot += K - 1) {
                    dp[start][end] = Math.min(dp[start][end], dp[start][pivot] + dp[pivot + 1][end]);
                }
                dp[start][end] += (l - 1) % (K - 1) == 0 ? preSum[end + 1] - preSum[start] : 0;
            }
        }

        return dp[0][len - 1];
    }
}
