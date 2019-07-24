/*
(This problem is an interactive problem.)
You may recall that an array A is a mountain array if and only if:
    · A.length >= 3
    · There exists some i with 0 < i < A.length - 1 such that:
        · A[0] < A[1] < ... A[i-1] < A[i]
        · A[i] > A[i+1] > ... > A[A.length - 1]
Given a mountain array mountainArr, return the minimum index such that mountainArr.get(index) == target.
If such an index doesn't exist, return -1.

You can't access the mountain array directly.  You may only access the array using a MountainArray interface:
    ·MountainArray.get(k) returns the element of the array at index k (0-indexed).
    ·MountainArray.length() returns the length of the array.
Submissions making more than 100 calls to MountainArray.get will be judged Wrong Answer.
Also, any solutions that attempt to circumvent the judge will result in disqualification.

Example 1:
Input: array = [1,2,3,4,5,3,1], target = 3
Output: 2
Explanation: 3 exists in the array, at index=2 and index=5. Return the minimum index, which is 2.

Example 2:
Input: array = [0,1,2,4,2,1], target = 3
Output: -1
Explanation: 3 does not exist in the array, so we return -1.

Constraints:
    1. 3 <= mountain_arr.length() <= 10000
    2. 0 <= target <= 10^9
    3. 0 <= mountain_arr.get(index) <= 10^9
 */

/**
 * Approach: Binary Search
 * 这道题目属于 Peak Index in a Mountain Array 的一个 Follow Up。
 * 主要是针对 二分查找 的一个考察，看大家能够想到先找出 peakPoint，
 * 然后利用它将数组划分成两个有序数组再次利用二分法进行查询。
 * 算是 二分法 问题中比较考察大家情况分析能力的，有一定的难度。
 * 但是代码实现上还是比较简单的，毕竟这个算法都被写烂了。。。
 * 对于求 Peak 的算法上，还可以使用 Golden-section Search，有兴趣的可以参见下面给出的链接。
 *  https://github.com/cherryljr/LeetCode/tree/master/Peak%20Index%20in%20a%20Mountain%20Array
 * 
 * 时间复杂度：O(logn)
 * 空间复杂度：O(1)
 */

/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int peakIndex = peakIndexInMountainArray(mountainArr);
        // 左半部分（升序排列）
        int left = 0, right = peakIndex;
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (mountainArr.get(mid) >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        if (mountainArr.get(left) == target) {
            return left;
        }

        // 右半部分（降序排列）
        left = peakIndex;
        right = mountainArr.length();
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (mountainArr.get(mid) <= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left < mountainArr.length() && mountainArr.get(left) == target ? left : -1;
    }

    private int peakIndexInMountainArray(MountainArray arr) {
        int left = 0, right = arr.length() - 1;
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (arr.get(mid) < arr.get(mid + 1)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}