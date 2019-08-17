/*
There are two sorted arrays nums1 and nums2 of size m and n respectively.
Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:
nums1 = [1, 3]
nums2 = [2]
The median is 2.0

Example 2:
nums1 = [1, 2]
nums2 = [3, 4]
The median is (2 + 3)/2 = 2.5
 */

/**
 * Approach: Binary Search
 * 二分查询的一道经典好题，也是难题。
 * 时间复杂度：O(log(min(m, n))) m, n代表两个数组的长度
 * 空间复杂度：O(1)
 *
 * 目前网上有非常多很好的解析，这边推荐两个连接给大家。
 * 文章解析：
 *  https://mp.weixin.qq.com/s/iAFjmYiLMTGwa2ixs_hw8A
 * 视频讲解：
 *  https://youtu.be/LPFhl65R7ww
 */
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        // 选择长度较短的数组来进行二分（节省时间）
        if (m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int start = 0, end = m, mid = m + n + 1 >> 1;
        while (start <= end) {
            int i = start + (end - start >> 1);
            int j = mid - i;
            if (i < end && nums1[i] < nums2[j - 1]) {
                // i偏小了，需要右移
                start = i + 1;
            } else if (i > start && nums1[i - 1] > nums2[j]) {
                // i偏大了，需要左移
                end = i - 1;
            } else {
                // i刚好合适，或 i 已达到数组边界
                int maxLeft;
                // 防止越界处理
                if (i == 0) {
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m + n & 1) == 1) {
                    // 如果两个数组长度之和是奇数，中位数就是左半部分的最大值
                    return maxLeft;
                }

                int minRight;
                // 防止越界处理
                if (i == m) {
                    minRight = nums2[j];
                } else if (j == n) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums1[i], nums2[j]);
                }
                // 如果两个数组长度之和是偶数，取左侧最大值和右侧最小值的平均
                return (maxLeft + minRight) / 2.0;
            }
        }

        return 0.0;
    }
}