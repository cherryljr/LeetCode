/*
Given an integer array with all positive numbers and no duplicates,
find the number of possible combinations that add up to a positive integer target.

Example:
nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.
Therefore the output is 7.

Follow up:
What if negative numbers are allowed in the given array?
How does it change the problem?
What limitation we need to add to the question to allow negative numbers?
 */

/**
 * 这里是归纳总结，对于 DP 基础并不好的朋友，建议先跳过本部分，按照 Approach 1 -> Approach 2 的顺序看题解。
 * 因为要求的是 方案的个数，而不是具体的方案。
 * 因此这道题目实际上考察的是 DP，而不是 DFS。
 *
 * 与这道题目十分类似的是 换零钱（求方案个数） 问题：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 * 但是这道题目与 换零钱 问题有着一定的区别：
 * 在 换零钱 问题中：
 * 相同元素组成的方法被视为一种方法（就算排列不同），其 dp[j] 的含义为 前i件 物品装满容量 j 的方法数，
 * 因此只和 i之前 的物品 相关，而和 i之后 的物品 无关。
 * 即我们只能先取前 i 件物品，然后再取之后的物品。
 * 在 本题 中：
 * 不同的顺序被认为是不同的方法，因此和元素位置无关，可以先取后面的元素再取前面的元素，
 * 即当 traget 确定后，我们需要从 整个数组 中选出一些元素来凑成 target.
 * 这是因为相同元素的不同排列被视为不同的方法，故 dp[j] 表示的是数组 nums 装满容量 j 的方法数，是和数组中所有元素有关。
 *
 * 之前几题能够用一维dp表示的本质其实是因为当前行的值只和上一行有关，因此用滚动数组进行了空间优化。
 * 如果直接在上一行更新当前行的状态则只需要一行即可，因此其本质就是还是二维dp。
 * 但是这道题真的是一维dp，即当前容量的填装数量只和之前容量填装的结果有关，
 * 只不过每次填装都要遍历 整个nums数组 来寻找相关的之前容量的状态，因此要用两重for循环。
 * 这里 dp[] 所做的事情就是将之前计算过的值事先存储起来罢了，实际上就一个 记忆化搜索。
 *
 * Good Explanations:
 * https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
 */

/**
 * Approach 1: Recursion (Time Limit Exceeded)
 * 这道题目与经典的 完全背包 问题有一定的类似，当并不相同。
 * 对于无法直接写出 DP 解法情况，我们不妨先尝试着写出 递归尝试 版本。
 * 因为 取出元素的顺序不同 被认为是不同的方案。所以我们每次为了完成 j 的目标，都需要考虑整个数组才行。
 * 当然这个方法肯定是会 超时 的。但是对于大家理解这道题目的解法而言，非常重要。
 */
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return recursion(nums, target);
    }

    private int recursion(int[] nums, int remainTarget) {
        // 当剩余容量为 0 时，说明找到了一个方案
        if (remainTarget == 0) {
            return 1;
        }
        int rst = 0;
        // 每次 Recursion 都需要遍历整个数组
        for (int i = 0; i < nums.length; i++) {
            if (remainTarget >= nums[i]) {
                rst += recursion(nums, remainTarget - nums[i]);
            }
        }
        return rst;
    }
}

/**
 * Approach 2: DP
 * 有了 Approach 1 的基础，我们可以发现这是一个 无后效性 的问题。
 * 即当 target == j 时，我们使用 整个数组 拼凑出 j 的方案数是固定的。
 * 因此我们可以使用 动态规划 来进行优化。
 * 在 Approach 1 中，我们发现 当前状态dp[j] 依赖于 sum(dp[j-nums[i]]) (j >= nums[i])
 * 因此我们可以得到动态规划的递推方程为：
 * dp[j] = sum( dp[j - nums[i]) (j >= nums[i])
 * 对于 Base Case 而言呢，当 target == 0 时，其状态是没有任何依赖的。
 * 因此 dp[0] = 1，然后依次 自左向右进行递推即可。
 */
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] dp = new int[target + 1];
        // Initialize
        dp[0] = 1;

        // Function
        for (int j = 1; j <= target; j++) {
            for (int i = 0; i < nums.length; i++) {
                if (j >= nums[i]) {
                    dp[j] += dp[j - nums[i]];
                }
            }
        }

        return dp[target];
    }
}