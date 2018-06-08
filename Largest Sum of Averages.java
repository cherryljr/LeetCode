/*
We partition a row of numbers A into at most K adjacent (non-empty) groups,
then our score is the sum of the average of each group.
What is the largest score we can achieve?

Note that our partition must use every number in A, and that scores are not necessarily integers.

Example:
Input:
A = [9,1,2,3,9]
K = 3
Output: 20
Explanation:
The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
We could have also partitioned A into [9, 1], [2], [3, 9], for example.
That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.


Note:
1 <= A.length <= 100.
1 <= A[i] <= 10000.
1 <= K <= A.length.
Answers within 10^-6 of the correct answer will be accepted as correct.
 */

/**
 * Approach 1: Recursion + Memory Search
 * 这道题目实际上是一道 区间DP 的问题。
 * 如果对 DP 不够熟练，直接去推递推方程确实会有点困难。
 * 
 * 对此，我们可以从 递归 的角度去分析题目，并加上 记忆化搜索 即可。（这也是动态规划的本质所在）
 * 决定结果的信息有：数组A，需要被划分的段数K。
 * dp[n][k] 表示：前 n 个元素，划分成 k 段的最大平均值之和是多少。
 * 而我们需要做的就是去枚举每一个划分点的位置 i.
 * 因此 dp[n][k] = Math.max(dp[n][k], dp[i][k - 1] + avg(i, n));
 * 有了以上过程，我们只需要使用一个二维数组 dp[][] 将其存储起来，从而实现记忆化搜索即可。
 * （这里为了方便直接将 dp 和 preSum 定义为成员变量，以便递归函数直接访问，避免了引用传递。但是在实际工程中，还是要认真考虑下的）
 * 
 * 为了优化对数组A i~n 段的求平均值过程，我们可以实现计算一个 preSum[] 
 * 从而在 O(1) 的时间内获得 i~n 段的平均值。
 * 
 * 类似的问题：
 * Word Break:
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Break.java
 * Word Break II:
 *  https://github.com/cherryljr/LintCode/blob/master/Word%20Break%20II.java
 */
class Solution {
    double[][] dp;
    double[] preSum;

    public double largestSumOfAverages(int[] A, int K) {
        int N = A.length;
        dp = new double[N + 1][K + 1];
        // 计算前项和，以在 O(1) 的时间内计算平均值
        preSum = new double[N + 1];
        for (int i = 1; i <= N; i++) {
            preSum[i] = preSum[i - 1] + A[i - 1];
        }
        return LSA(A, N, K);
    }

    private double LSA(int[] A, int n, int k) {
        // 当只剩下 1 段时，说明将 1~n 划分为 1 段。
        // 此时直接返回 avg(1, n) 即可
        if (k == 1) {
            return preSum[n] / n;
        }

        for (int i = k - 1; i < n; i++) {
            if (dp[i][k - 1] == 0) {
                // 若需要的状态还未被计算过，则递归调用子过程进行计算
                dp[i][k - 1] = LSA(A, i, k - 1);
            }
            // 当前状态为 dp[n][k] 和 切分点在 i，左半段的 LSA + avg(i, n] 的值 中的最大值
            dp[n][k] = Math.max(dp[n][k], dp[i][k - 1] + (preSum[n] - preSum[i]) / (n - i));
        }
        return dp[n][k];
    }
}

/**
 * Approach 2: DP
 * 有了以上的基础，我们就可以将其改写为DP的做法了。
 * 主要思想都是相同的，这里就不多加解释了
 *
 * 详细解释：
 *  http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-813-largest-sum-of-averages/
 */
class Solution {
    public double largestSumOfAverages(int[] A, int K) {
        int n = A.length;
        double[] preSum = new double[n + 1];
        double[] dp = new double[n + 1];
        for (int i = 1; i <= n; ++i) {
            preSum[i] = preSum[i - 1] + A[i - 1];
            // Initialize the dp array
            dp[i] = preSum[i] / i;
        }

        for (int k = 2; k <= K; k++) {
            double[] dp2 = new double[n + 1];
            System.arraycopy(dp, 0, dp2, 0, n + 1);
            for (int j = k; j <= n; j++) {
                for (int i = k - 1; i < j; i++) {
                    dp2[j] = Math.max(dp2[j], dp[i] + (preSum[j] - preSum[i]) / (j - i));
                }
            }
            dp = dp2;
        }

        return dp[n];
    }
}