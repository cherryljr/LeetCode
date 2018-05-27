/*
Given an integer array, return the k-th smallest distance among all the pairs.
The distance of a pair (A, B) is defined as the absolute difference between A and B.

Example 1:
Input:
nums = [1,3,1]
k = 1
Output: 0

Explanation:
Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.

Note:
2 <= len(nums) <= 10000.
0 <= nums[i] < 1000000.
1 <= k <= len(nums) * (len(nums) - 1) / 2.
 */

/**
 * Approach: Sorting Matrix + Binary Search
 * 这道题目与 K-th Smallest Prime Fraction 十分类似。
 * 我们只需要对 nums[] 进行一次排序，然后就能轻松地构造出 Sorted Matrix 了。
 * 本题的 Sorted Matrix 为：行方向上：从下往上递增；列方向上：从左往右递增。
 * 因此最大值为矩阵的右上角。
 * 而题目要求的是 Kth Smallest.因此我们最后计算的结果是除去右上三角形一部分的结果。
 * 根据题意距离不可能存在负数，所以我们可以定义最小值为 0.
 * 当然也可以遍历一遍数组来求最小差值，但是这里就不这么做了。
 * 然后我们就可以开始二分求 kth smallest distance 了。
 *
 * 因为这两道题目太像了，这里就不再赘述了，详细分析可以参考：
 *
 */
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);

        // Minimum absolute difference
        int low = 0;
        // Maximum absolute difference
        int high = nums[n - 1] - nums[0];

        // Do binary search for k-th absolute difference
        while (low < high) {
            int mid = low + ((high - low) >> 1);
            // 统计出来的 >= mid 的pairs 大于等于 k
            // 说明取得 mid(distance) 太大了，应该缩小一些
            if (countPairs(nums, mid) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    // With the help of Sorted Matrix, we can calculate
    // the number of pairs with absolute difference less than or equal to mid in O(n).
    private int countPairs(int[] nums, int target) {
        int n = nums.length, count = 0;
        // col pointer move from left to right (smaller -> bigger)
        for (int row = 0, col = 1; row < n - 1; row++) {
            while (col < n && nums[col] - nums[row] <= target) {
                col++;
            }
            count += col - row - 1;
        }
        return count;
    }
}