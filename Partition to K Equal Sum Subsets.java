/*
Given an array of integers nums and a positive integer k,
find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

Example 1:
Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
Note:

1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.
 */

/**
 * Approach: Backtracking (DFS)
 * 这道题目算是 Partition Equal Subset Sum 的一个 Fellow Up 吧。
 * 从原本分成 2 个和相等的子数组，变成了分成 k 个和相等的子数组。
 * 刚刚开始依旧打算从 DP 方向入手，发现有点麻烦，怒放弃之...
 * 因为是求 SubSet 的问题，所以想能不能直接 DFS 遍历所有的可能性组合。
 * 然后看能不能凑出答案就可以了...
 *
 * 具体做法为：
 * 每次 DFS 寻找一个 subset 其子数组和为 target = sum / k.
 * 如果找到一个，那么继续递归调用 canPartition, 同时 k--.直到 k == 1.
 * 说明能够找到 k 个相等的 subset,返回 true.
 * 在此过程中，我们需要使用 visited[] 来标记各个元素是否已经被使用过
 * （防止递归调用时重复使用某个元素）
 * 并且因为求 SubSet 时是存在 空集 这个情况的，所以我们需要加入 currCount 参数来跟踪，以防止空集的情况。
 *
 * 说实话，这么暴力的方法能 AC 我也是有点意外的...
 */
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return false;
        }

        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (sum % k == 0) {
            boolean[] visited = new boolean[nums.length];
            return canPartition(nums, 0, 0, 0, sum / k, k, visited);
        } else {
            return false;
        }
    }

    /**
     * @param nums 数据源数组
     * @param index 当前遍历的下标
     * @param currSum 当前 subset 之和
     * @param currCount 当前 subset 中所含的元素个数
     * @param target 目标和
     * @param k 需要凑出的 subset 个数
     * @param visited 标记元素是否被使用过
     * @return
     */
    private boolean canPartition(int[] nums, int index, int currSum, int currCount, long target, int k, boolean[] visited) {
        if (k == 1) {
            return true;
        }
        // 若满足以下条件，说明可以凑出一个和为 target 的 subset (不要忘记加上 currCount>0 的限制)
        if (currSum == target && currCount > 0) {
            return canPartition(nums, 0, 0, 0, target, k - 1, visited);
        }

        for (int i = index; i < nums.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                if (canPartition(nums, i + 1, currSum + nums[i], currCount + 1, target, k, visited)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        return false;
    }
}