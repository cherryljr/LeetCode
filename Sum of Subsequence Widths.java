/*
Given an array of integers A, consider all non-empty subsequences of A.
For any sequence S, let the width of S be the difference between the maximum and minimum element of S.
Return the sum of the widths of all subsequences of A.
As the answer may be very large, return the answer modulo 10^9 + 7.

Example 1:
Input: [2,1,3]
Output: 6

Explanation:
Subsequences are [1], [2], [3], [2,1], [2,3], [1,3], [2,1,3].
The corresponding widths are 0, 0, 0, 1, 1, 2, 2.
The sum of these widths is 6.

Note:
1 <= A.length <= 20000
1 <= A[i] <= 20000
 */

/**
 * Approach: Mathematics
 * 看到需要 mod 1e9+7 反应过来大概率是一个 DP 的问题。
 * 但是数据规模为 20000,意味着时间复杂度在 O(nlogn) 或者以下。
 * 并没有想出使用 DP 的做法，因此不妨依据 O(nlogn) 这个信息先进行排序看看。(子序列是顺序无关的)
 * 我们可以发现，对于一个 subsequence，影响到 width 的只有 最小 和 最大 这两个元素（width = max - min）
 * 因此我们不换转换下视角，从一个数A[i]来出发，以它作为 最大值的 subsequence，
 * 即此该子序列排序后 A[i] 是最右边的一个元素，这样的子序列有 2^i 个。
 * 在这些序列中，A[i] 对于结果的影响为：rst += A[i] * 2^i
 * 而以它作为最大值的子序列有 2^(N -i-1) 个。
 * 在这些序列中，A[i] 对于结果的影响为：rst -= A[i] * 2^(N -i-1)
 * 因此数字 A[i] 对于结果的贡献为 A[i] * (2^i - 2^(N -i-1))
 * 故最后结果为：
 *  Sum(A[i] * (2^i - 2^(N -i-1))) == Sum(A[i] * 2^i - A[N-i-1] * 2^i)
 * 上述利用 A[N-i-1] * 2^i 替代 A[i] * 2^(N -i-1) 可以进一步节省计算量。
 * 之所以能够进行替换的原因在于：
 *  (1) i:= 0 to n-1 sum Ai * 2^(n - 1 - i) = (A0 * 2^n-1) + (A1 * 2^n-2) + (A2 * 2^n-3) + ... (An-1 * 2^0)
 *  (2) i:= 0 to n-1 sum An-i-1 * 2^i = (An-1 * 2^0) + (An-2 * 2^1) + ... (A1 * 2^n-2) + (A0 * 2^n-1)
 *  Eq. (1) == (2) in reverse order.
 *
 * 时间复杂度：O(nlogn)
 *
 * References:
 *  https://www.youtube.com/watch?v=lfF7XqwXokw
 *  https://leetcode.com/problems/sum-of-subsequence-widths/discuss/161267/C++Java1-line-Python-Sort-and-One-Pass
 *
 * 应用到类似思想的问题还有：
 * Unique Letter String:
 * 	https://github.com/cherryljr/LeetCode/blob/master/Unique%20Letter%20String.java
 */
class Solution {
    public int sumSubseqWidths(int[] A) {
        final long MOD = 1000000007;
        int N = A.length;
        long pow = 1L;
        long rst = 0;
        Arrays.sort(A);

        for (int i = 0; i < N; i++) {
            rst = (rst + A[i] * pow - A[N - i - 1] * pow) % MOD;
            pow = (pow << 1) % MOD; // pow = 2^i,因为本题中 i 远大于 32，所以需要进行 %MOD 操作。
        }
        // 最后结果 +MOD 再取余，保证结果为正数
        return (int)((rst + MOD) % MOD);
    }
}