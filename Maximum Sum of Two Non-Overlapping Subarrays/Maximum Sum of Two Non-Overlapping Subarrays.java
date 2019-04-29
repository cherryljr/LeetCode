/*
Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping (contiguous) subarrays,
which have lengths L and M.  (For clarification, the L-length subarray could occur before or after the M-length subarray.)
Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1]) and either:
    0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
    0 <= j < j + M - 1 < i < i + L - 1 < A.length.

Example 1:
Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
Output: 20
Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.

Example 2:
Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
Output: 29
Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.

Example 3:
Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
Output: 31
Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.

Note:
    1. L >= 1
    2. M >= 1
    3. L + M <= A.length <= 1000
    4. 0 <= A[i] <= 1000
 */

/**
 * Approach: PreSum (Sliding Window)
 * 这道题目根据题目所给的数据规模可以推测出时间复杂度在 O(n^2) 级别。
 * 但是这个解法较为简单...只需要提前计算出一个 preSum[]，
 * 然后以遍历位置 index 作为分界，依据 M 和 L 的长度枚举所有的可能组合即可。
 * 思路非常直白粗暴，就不再提及了。
 *
 * 这里分享一个较优的时间复杂度 O(n) 级别算法。
 * 依旧先对数组进行一次计算 preSum[] 的预处理，以节省后面的计算量。
 * 然后，我们对结果进行分析（这里先抛开 L, M 子数组的先后顺序问题，默认 L 在前）：
 *  结果必定是：前 L 个连续元素组成的最大子数组 和 后 M 个连续元素组成的最大子数组。
 *  因此，我们可以枚举 后 M 个连续元素所组成的最大子数组的最后一个位置。
 *  即对于 M 子数组，我们每次都取最后 M 个元素来构成它（遍历所有的位置之后，最优解必定在这里面）
 *  而对于前面长度为 L 的最大子数组，因为要求是 non-overlap 的，所以我们只需要保证维护一个区间在
 *  [0, i-M]上的一个长度为 L 的最大连续子数组和的值 Lmax 即可。
 *  则最终结果就是： ans = max(ans, Lmax + preSum[i] - preSum[i - M])
 * 最后，考虑 L, M 子数组的先后问题，无非就是增加了一种情况罢了，多维护一个变量 Mmax 即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int[] preSum = new int[A.length + 1];
        for (int i = 1; i <= A.length; i++) {
            preSum[i] = preSum[i - 1] + A[i - 1];
        }

        // Lmax: 最后 M 个元素之前，长度为 L 的最大连续子数组之和
        // Mmax: 最后 L 个元素之前，长度为 M 的最大连续子数组之和
        int Lmax = preSum[L], Mmax = preSum[M], ans = preSum[L + M];
        for (int i = L + M + 1; i < preSum.length; i++) {
            // get the Lmax/Mmax by Sliding Window
            Lmax = Math.max(Lmax, preSum[i - M] - preSum[i - M - L]);
            Mmax = Math.max(Mmax, preSum[i - L] - preSum[i - L - M]);
            ans = Math.max(ans, Math.max(Lmax + preSum[i] - preSum[i - M], Mmax + preSum[i] - preSum[i - L]));
        }
        return ans;
    }
}