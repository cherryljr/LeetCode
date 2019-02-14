/*
Given an integer array nums, find the contiguous subarray within an array
(containing at least one number) which has the largest product.

Example 1:
Input: [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.

Example 2:
Input: [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 */

/**
 * Approach 1: Sequence DP
 * State:
 *  max[i]:表示以nums[i]作为结尾的前i个数的最大连续子序列之积
 *  min[i]:表示以nums[i]作为结尾的前i个数的最小连续子序列之积
 *
 * Initialize:
 *  max[0] = min[0] = 1;
 *  rst = nums[0];
 *
 * Function:
 *  对于每一个位置上的 nums[i] 我们都有两种处理方式：
 *      1. 将 nums[i] 加入到前面的 subarray 中
 *      2. 去除前面的 subarray, 以 nums[i] 为起点重新开始一个 subarray
 *  并且因为涉及到负数的问题。所以需要分开讨论：
 *  当 nums[i] 是正数时：
 *      max[i] = Math.max(max[i], max[i - 1] * nums[i]);
 *      min[i] = Math.min(min[i], min[i - 1] * nums[i]);
 * 当 nums[i] 是负数时：
 *      max[i] = Math.max(max[i], min[i - 1] * nums[i - 1]);
 *      min[i] = Math.min(min[i], max[i - 1] * nums[i - 1]);
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Note：
 * 负数是在求 Max 和 Min 时非常值得重视的一个问题！！！
 * 特别是在涉及到 乘法 和 绝对值 运算时。负数常常扮演着十分重要的角色！！！
 * 与该题相同的还有： 合唱团 这道题目，也是一道输入中含有 负数 的 DP 问题。
 *  https://github.com/cherryljr/NowCoder/blob/master/%E5%90%88%E5%94%B1%E5%9B%A2.java
 */
class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] max = new int[n + 1];
        int[] min = new int[n + 1];
        int ans = nums[0];
        max[0] = min[0] = 1;

        for (int i = 1; i <= n; i++) {
            // 将 max[i], min[i] 初始化为 nums[i-1] 代表不使用之前的subarray的情况
            // （即以 nums[i-1] 作为 start 开始一个 subarray）
            max[i] = min[i] = nums[i - 1];
            if (nums[i - 1] > 0) {
                max[i] = Math.max(max[i], nums[i - 1] * max[i - 1]);
                min[i] = Math.min(min[i], nums[i - 1] * min[i - 1]);
            } else {
                max[i] = Math.max(max[i], nums[i - 1] * min[i - 1]);
                min[i] = Math.min(min[i], nums[i - 1] * max[i - 1]);
            }
            ans = Math.max(ans, max[i]);
        }

        return ans;
    }
}

/**
 * Approach 2: Sequence DP (Space Optimized)
 * 通过观察我们发现，该题的 DP 中，当前状态仅仅只与上一个状态有关，与其他状态没有关系。
 * 所以我们只需要用一个变量来保持上一个状态即可。
 * 这样我们便可以把 O(N) 的额外空间优化到 O(1) 的额外空间。
 * 同时也可以把 Approach 1 中的分情况讨论的做法换成一种更加简洁的写法。
 * （不过在计算量上面就稍微多了一点点，大家可以自行选择）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // State & Initialize
        int minPre = nums[0], maxPre = nums[0];
        int max = nums[0], min = nums[0];
        int ans = nums[0];

        // Function
        for (int i = 1; i < nums.length; i ++) {
            max = Math.max(nums[i], Math.max(maxPre * nums[i], minPre * nums[i]));
            min = Math.min(nums[i], Math.min(maxPre * nums[i], minPre * nums[i]));
            ans = Math.max(ans, max);
            maxPre = max;
            minPre = min;
        }

        // Answer
        return ans;
    }
}