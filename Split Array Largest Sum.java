/*
Given an array which consists of non-negative integers and an integer m,
you can split the array into m non-empty continuous subarrays.
Write an algorithm to minimize the largest sum among these m subarrays.

Note:
If n is the length of array, assume the following constraints are satisfied:
1 ≤ n ≤ 1000
1 ≤ m ≤ min(50, n)

Examples:
Input:
nums = [7,2,5,10,8]
m = 2
Output:
18

Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.
 */

/**
 * Approach 1: Recursion + Memory Search
 * 这道题目与 Largest Sum of Averages 非常类似。
 * 其都是存在 最优子问题解 的，因此最直接的想法就是通过 DP 来解决。
 * 主要做法就是枚举 分割点。
 * 这里只写了 记忆化搜索 的做法，DP就不写了（因为本题有更好的做法哈...）
 *
 * 时间复杂度：O(n^2 * m)
 * 空间复杂度：O(mn)
 *
 * Largest Sum of Averages:
 *  https://github.com/cherryljr/LeetCode/blob/master/Largest%20Sum%20of%20Averages.java
 */
class Solution {
    int[][] mem;
    int[] preSum;

    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        mem = new int[n + 1][m + 1];
        for (int[] arr : mem) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        preSum = new int[n + 1];
        preSum[0] = 0;
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        return splitArray(nums, n, m);
    }

    private int splitArray(int[] nums, int n, int m) {
        if (m == 1) {
            return preSum[n];
        }
        if (mem[n][m] != Integer.MAX_VALUE) {
            return mem[n][m];
        }

        for (int i = m - 1; i < n; i++) {
            mem[n][m] = Math.min(mem[n][m], Math.max(splitArray(nums, i, m - 1), preSum[n] - preSum[i]));
        }
        return mem[n][m];
    }
}

/**
 * Approach 2: Greedy Partition + Binary Search
 * 这道题目实际上加点贪心的策略之后，就能有更好的解决方案。
 * 我们可以 二分查找 最小化的最大值。
 * 其范围必定在 max(nums) ~ sum(nums) 之间。
 * 然后以 mid 作为 limit 进行 Greedy Partition.
 * 这里的分组策略就是贪心的：
 *  从左往右进行分组，不断地将当前元素加入组中，直 subArray 的和已经超过 limit.
 *  那么重新开辟一个分组，继续操作下去。
 * 如果依此计算出来的分组数 > m,说明 limit 太小了，我们需要增大它，因此移动左边界。
 * 否则移动右边界。与传统二分相同，整个过程左闭右开。
 * 
 * 时间复杂度：O(log(sum(nums)) * n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int splitArray(int[] nums, int m) {
        long left = 0, right = 0;
        for (int num : nums) {
            left = Math.max(left, num);
            right += num;
        }
        // 保证左闭右开
        right++;

        while (left < right) {
            long mid = left + ((right - left) >> 1);
            if (minGroups(nums, mid) > m) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return (int)left;
    }

    // 以 limit 为上限，最少可以将 nums 分成多少组
    private int minGroups(int[] nums, long limit) {
        int count = 1;
        long sum = 0;
        for (int num : nums) {
            if (sum + num > limit) {
                sum = num;
                count++;
            } else {
                sum += num;
            }
        }
        return count;
    }
}