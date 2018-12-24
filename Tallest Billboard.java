/*
You are installing a billboard and want it to have the largest height.
The billboard will have two steel supports, one on each side.
Each steel support must be an equal height.

You have a collection of rods which can be welded together.
For example, if you have rods of lengths 1, 2, and 3, you can weld them together to make a support of length 6.

Return the largest possible height of your billboard installation.
If you cannot support the billboard, return 0.

Example 1:
Input: [1,2,3,6]
Output: 6
Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.

Example 2:
Input: [1,2,3,4,5,6]
Output: 10
Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.

Example 3:
Input: [1,2]
Output: 0
Explanation: The billboard cannot be supported, so we return 0.

Note:
    1. 0 <= rods.length <= 20
    2. 1 <= rods[i] <= 1000
    3. The sum of rods is at most 5000.
 */

/**
 * Approach: DP (01-Backpack Problem)
 * 根据题目所给的数据量，如果我们使用 DFS 来暴力求解的话，
 * 时间复杂度在 3^n 级别（每个 棍棒 有 3 中选择情况：不用，放在左边，放在右边）
 * 因此肯定是会超时的。
 * 然后我们发现题目给出了一个非常重要的提示条件：
 *  所有 棍棒的高度 之和不超过 5000.
 * 因此我们可以考虑使用 DP 来进行状态压缩，从而解决这道问题。
 *
 * 明确了解法所需要使用的算法之后，我们需要找出 DP 优化的点在哪呢。
 * 经过分析，我们可以发现：
 *  因为结果要求的是两边高度相等，所以我们只关心使用了一些棍棒后，左右两边之间的高度差是多少。
 *  并不在意其具体组成情况是怎么样的。
 *  比如：左边为3，右边高度为5。我们并不在意这个 3 是由 1 + 1 + 1 组成，还是 1 + 2 组成的。
 *  我们只需要知道其差值为 2，然后在后续的过程中看能否将这个高度差消除掉即可。
 *  这就是我们 DP 过程所优化的点所在，而此时大家应该也感觉出来这道问题与 01背包问题 之间的相似性了。
 *
 * 依据上述分析，我们可以建立一个二维数组 dp[][] 来存储我们需要的答案.
 * dp[i][j]：表示利用前 i 个 rods,在左右两边高度差为 j 的情况下，两边说组成棍棒的最高公共高度
 *  这里只存 最高公共高度 做法的原因为：
 *  假设我们存在两组数组：(h1, h2), (h3, h4)
 *  其中 h1 <= h2, h3 <= h4, h1 < h3, h2 – h1 = h4 – h3 即 高度差 相同
 *  如果 min(h1, h2) < min(h3, h4) 那么(h1, h2) 不可能产生最优解，直接舍弃。
 *  因为如果后面的柱子可以填补 h4–h3 / h2–h1 这个高度差，使得两根柱子一样高，那么答案就是 h2 和 h4。
 *  但 h2 < h4，所以最优解只能来自后者。
 *
 * 而对于状态转移方程可以按照：
 *  1.不使用当前 rod; 2.将当前rod放在较高一侧; 3.将当前rod放在较低一侧
 * 这三种情况进行分析即可。
 *
 * 时间复杂度：O(N*S) S代表所有棍棒高度之和
 * 空间复杂度：O(N*S) S代表所有棍棒高度之和
 *
 * 参考资料：
 *  https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-956-tallest-billboard/
 */
class Solution {
    public int tallestBillboard(int[] rods) {
        int n = rods.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += rods[i];
        }

        // Initialization
        int[][] dp = new int[n + 1][sum + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;

        // 01 Backpack Problem
        for (int i = 1; i <= n; i++) {
            int height = rods[i - 1];
            // Without the current height, the difference must be in [0, sum-height]
            for (int j = 0; j <= sum - height; j++) {
                if (dp[i - 1][j] < 0) {
                    continue;
                }
                // Not used
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                // Put on the taller one
                dp[i][j + height] = Math.max(dp[i][j + height], dp[i - 1][j]);
                // Put on the shorter one
                dp[i][Math.abs(j - height)] = Math.max(dp[i][Math.abs(j - height)], dp[i - 1][j] + Math.min(j, height));
            }
        }

        return dp[n][0];
    }
}