/*
Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A good
if the number of different integers in that subarray is exactly K.

(For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
Return the number of good subarrays of A.

Example 1:
Input: A = [1,2,1,2,3], K = 2
Output: 7
Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].

Example 2:
Input: A = [1,2,1,3,4], K = 3
Output: 3
Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].

Note:
    1. 1 <= A.length <= 20000
    2. 1 <= A[i] <= A.length
    3. 1 <= K <= A.length
 */

/**
 * Approach: Sliding Window Reduction
 * 这个问题可以很明显地看出考察的是 Sliding Window.
 * 并且与 LeetCode 上另一道问题 Longest Substring with At Most Two Distinct Characters 很类似。
 * 因此我们可以想着把这道问题转换成：
 *  求最多含有 K 个 Distinct Number 的 SubArray 的个数。
 * 则最后的答案就可以表示为：Ans = Func(K) - Func(K-1)
 *
 * 那么如何求解 最多含有 K 个 Distinct Number 的 SubArray 的个数 呢？
 * 我们可以按照以 A[end] 为结尾的 SubArray 个数来分析。
 * 则，其值为 end - begin. 然后将所有的值累计起来就是我们需要的结果了。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LeetCode/blob/master/Longest%20Substring%20with%20At%20Most%20Two%20Distinct%20Characters.java
 */

class Solution {
    public int subarraysWithKDistinct(int[] A, int K) {
        return countsOfSubarraysWithKDistinct(A, K) - countsOfSubarraysWithKDistinct(A, K - 1);
    }

    // 计算所有 Distinct Number 个数小于等于 K 的 SubArray 个数之和
    // 代码基本就是采用 Sliding Window Template
    // 与 Longest Substring with At Most K Distinct Characters 基本相同
    private int countsOfSubarraysWithKDistinct(int[] A, int K) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0, ans = 0;

        for (int left = 0, right = 0; right < A.length; right++) {
            map.put(A[right], map.getOrDefault(A[right], 0) + 1);
            if (map.get(A[right]) == 1) {
                count++;
            }

            while (count > K) {
                map.put(A[left], map.get(A[left]) - 1);
                if (map.get(A[left]) == 0) {
                    count--;
                }
                left++;
            }
            ans += right - left + 1;
        }
        return ans;
    }
}
