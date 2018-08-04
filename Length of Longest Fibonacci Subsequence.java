/*
A sequence X_1, X_2, ..., X_n is fibonacci-like if:
n >= 3
X_i + X_{i+1} = X_{i+2} for all i + 2 <= n
Given a strictly increasing array A of positive integers forming a sequence,
find the length of the longest fibonacci-like subsequence of A.  If one does not exist, return 0.
(Recall that a subsequence is derived from another sequence A by deleting any number of elements (including none) from A,
without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].)

Example 1:
Input: [1,2,3,4,5,6,7,8]
Output: 5
Explanation:
The longest subsequence that is fibonacci-like: [1,2,3,5,8].

Example 2:
Input: [1,3,7,11,12,14,18]
Output: 3
Explanation:
The longest subsequence that is fibonacci-like:
[1,11,12], [3,11,14] or [7,11,18].

Note:
3 <= A.length <= 1000
1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
(The time limit has been reduced by 50% for submissions in Java, C, and C++.)
 */

/**
 * Approach 1: Sequence DP
 * 由题目的数据量可得，该题解法的算法时间复杂度应该在 O(n^2) 级别。
 * 同时对于这种 subsequence 求最大长度的问题，我们通常可以使用 DP 来解决。
 *
 * 对于本题，首先我们需要确定状态的定义。
 * 这里 dp[i][j] 表示：i~j 最长能够找到的斐波那契子序列的长度。
 * 我们继续往后看，这时候我们需要推导出 状态转移方程。
 * 斐波那契数列成立的条件为：A[i] + A[j] = A[k].
 * 因此我们需要判断之前数列中是否存在 A[i].对此我们需要使用到 HashMap 来保存它的信息。
 * 如果存在的话： dp[j][k] = dp[i][j] + 1.
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=Py3Jj0M1McY
 */
class Solution {
    public int lenLongestFibSubseq(int[] A) {
        int n = A.length;
        // 记录 A[i] 以及对于的位置信息，以便在 O(1) 时间内进行查询
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(A[i], i);
        }
        int[][] dp = new int[n][n];
        // Initialize
        // 我们只会使用到矩阵的 右上三角的区域。初始值为 2.（斐波那契数列长度必须 >= 3）
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = 2;
            }
        }

        int maxLen = 0;
        for (int j = 0; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                int num = A[k] - A[j];  // A[i]
                // Pruning
                // 因为 A[i] < A[j],并且数列数严格单点递增的
                // 所以当 A[i] >= A[j] 时可以直接跳出循环（后面的数只会更大）
                if (num >= A[j]) {
                    break;
                }
                if (map.containsKey(num)) {
                    dp[j][k] = dp[map.get(num)][j] + 1;
                    maxLen = Math.max(maxLen, dp[j][k]);
                }
            }
        }

        return maxLen;
    }
}