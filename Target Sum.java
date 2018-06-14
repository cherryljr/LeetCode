/*
You are given a list of non-negative integers, a1, a2, ..., an, and a target, S.
Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.

Example 1:
Input: nums is [1, 1, 1, 1, 1], S is 3.
Output: 5

Explanation:
-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.

Note:
The length of the given array is positive and will not exceed 20.
The sum of elements in the given array will not exceed 1000.
Your output answer is guaranteed to be fitted in a 32-bit integer.
 */

/**
 * Approach 1: DFS (Brute Force)
 * 根据题目的数据规模来看，最多只有 20 个数字，因此可以直接使用 DFS 暴力穷举即可。
 * 时间不会超过 2^20. O(2^n)
 *
 * 对于如何穷举，可以有两种做法：
 *  1. 对每个数字穷举所有可能性，本题中只有 + 和 - 两种情况。
 *  类似的，如果是图的话，可能性就是其邻居的情况。
 *  比如：Knight Probability in Chessboard
 *  2. 对于每个数字，不外乎 + / - 两种情况，所以可以转成二进制表示。
 *  即使用 二进制 来进行 DFS。可以利用 0 代表 -， 1代表 + 的方式。
 *  比如：SubSet (取或者不取)
 *  
 * 这里给出的是第一种的实现方案。
 *
 * 该题的 Fellow Up 可以参见：
 * 
 */
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return dfs(nums, 0, S);
    }

    private int dfs(int[] nums, int index, int target) {
        if (index == nums.length) {
            if (target == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        int count = 0;
        // 正号
        count += dfs(nums, index + 1, target + nums[index]);
        // 负号
        count += dfs(nums, index + 1, target - nums[index]);
        return count;
    }
}

/**
 * Approach 2: DP (Top-Down)
 * 虽然题目的数据量很小，但是分析后我们发现这是一道无后效性的题目。
 * 因此我们可是使用 DP 来解决。
 * 涉及到的信息有：位置信息 和 当前值。
 *
 * 本题因为存在负数的情况，而数组下边不能为负。所以我们需要对此进行一个处理。
 * 使用的是 偏移量offset 来进行处理。
 * offset = sum(nums),表示的是 0 值。这样我们就能够正常进行 DP 了。
 * （使用 递归 + 记忆化搜索 也是同理，需要考虑如何使用数组存储已经计算过的结果）
 * dp[i][j] 表示的是：前 i 个元素，组成和为 j 的方案数个数。
 * 由此我们便可以推出DP方程，值得注意的是：
 *  这里的思路与 Approach 1 中的思路是相反的。
 *  大家可以选择更加符合自己思路的方法来理解。
 */
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < S) {
            return 0;
        }

        int offset = sum;
        sum = 2 * sum;
        int[][] dp = new int[len + 1][sum + 1];
        dp[0][offset] = 1;
        for (int i = 0; i < len; i++) {
            for (int j = nums[i]; j <= sum - nums[i]; j++) {
                dp[i + 1][j + nums[i]] += dp[i][j];
                dp[i + 1][j - nums[i]] += dp[i][j];
            }
        }

        return dp[len][offset + S];
    }
}

/**
 * Approach 3: Subset Sum -> Turn to 01 Backpack
 * 我们知道一种方案中必定是用到了所有的数字，那么每个数要么前面是 +, 要么是 -.
 * 因此可以将前面符号为 + 的数字集合记为 P; 将前面符号为 - 的数字集合记为 N.
 * 则有：P ∪ N = nums[], P ∩ N = ∅
 *
 * 则我们想要求的就是 P 或者是 N 的方案个数，使得其满足：
 *  sum(P) - sum(N) = target (sum指的是求和，里面的数全为非负数)
 * 将上式两边同时加上 sum(P) + sum(N) 后可得：
 *  sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
 * 因为 sum(P) + sum(N) = sum(nums)，则可得：
 *  2sum(P) = target + sum(nums)
 *  sum(P) = (target + sum(nums)) / 2
 *
 * 因此问题就转换成了：求一个集合P(subset),使得其和为 (target + sum(nums)) / 2
 * 问符合条件的集合方案个数总共有几个？
 * 而这就是一个 01背包问题。
 *
 * 01背包模板分析——数组和为sum的方法数：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-494-target-sum/
 */
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // 根据上述分析进行一个判断能否找到集合 P
        if (sum < S || ((S + sum) & 1) == 1) {
            return 0;
        }

        int target = (S + sum) >> 1;
        int[] dp = new int[target + 1];
        // Initialize
        dp[0] = 1;
        // Function
        for (int i = 0; i < nums.length; i++) {
            for (int j = target; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}