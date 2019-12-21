/*
Given an integer n, your task is to count how many strings of length n
can be formed under the following rules:
    Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
    Each vowel 'a' may only be followed by an 'e'.
    Each vowel 'e' may only be followed by an 'a' or an 'i'.
    Each vowel 'i' may not be followed by another 'i'.
    Each vowel 'o' may only be followed by an 'i' or a 'u'.
    Each vowel 'u' may only be followed by an 'a'.
Since the answer may be too large, return it modulo 10^9 + 7.

Example 1:
Input: n = 1
Output: 5
Explanation: All possible strings are: "a", "e", "i" , "o" and "u".

Example 2:
Input: n = 2
Output: 10
Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".

Example 3: 
Input: n = 5
Output: 68

Constraints:
    1. 1 <= n <= 2 * 10^4
*/

/**
 * Approach 1: Graph + DP
 * 根据数据规模和数据之间的关系可以很容易推测出是一道DP的问题。
 * 画个联系图，就可以轻松推出 DP 的状态转移方程了。
 * 并且每个元素的当前状态结果只取决于其他元素的当前状态。
 * 所以可以使用滚动数组进行一下优化。（这里用到 10 个变量即可）
 * 
 * Time Complexity: O(n)
 * Sapce Complexity: O(n) 可优化为 O(1)
 * 
 * Reference:
 *  https://leetcode.com/problems/count-vowels-permutation/discuss/398222/Detailed-Explanation-using-Graphs-With-Pictures-O(n)
 */

class Solution {
    private static final int MOD = 1000000007;
    
    public int countVowelPermutation(int n) {
        long[][] dp = new long[n + 1][5];
        Arrays.fill(dp[1], 1);

        // a->0  ,  e->1,  i->2,  o->3,  u->4        
        for (int i = 2; i <= n; i++) {
            // a前面可能为e,i,u
            dp[i][0] = (dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][4]) % MOD;
            // e前面可能为a,i
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            // i前面可能为e,o
            dp[i][2] = (dp[i - 1][1] + dp[i - 1][3]) % MOD;
            //o前面可能为i,
            dp[i][3] = dp[i - 1][2];
            //u前面可能为i,o
            dp[i][4] = (dp[i - 1][2] + dp[i - 1][3]) % MOD;
        }
        long ans = 0L;
        for (int i = 0; i < 5; i++) {
            ans = (ans + dp[n][i]) % MOD;
        }
        return (int)ans;
    }
}

/**
 * Approach 2: Matrix Quick Pow
 * 矩阵快速幂可以加快对递推式的求解过程，优化时间复杂度。
 * 而对于 DP 问题，其核心不外乎就是递推方程的求解。
 * 因此本题可以根据递推方程，构造出对应的矩阵，然后利用矩阵快速幂的解法来进行优化。
 * 如何构造出对应的矩阵可以参考：av66312293
 *
 * 时间复杂度：O(logn)
 * 空间复杂度：O(1)
 */
class Solution {
    public int countVowelPermutation(int n) {
        final int MOD = 1000000007;
        long[][] matrix = {{0, 1, 1, 0, 1}, {1, 0, 1, 0, 0}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 1, 1, 0}};
        matrix = quickPow(matrix, n - 1, MOD);
        // 最后需要的结果是一个列向量的和，所以这里先构造一个列向量，然后利用矩阵乘法操作，即可得到结果。
        long[][] ans = {{1}, {1}, {1}, {1}, {1}};
        // A^n * ANS == ANS，注意这里是左乘
        ans = multiply(matrix, ans, MOD);
        for (int i = 1; i < 5; i++) {
            ans[0][0] = (ans[0][0] + ans[i][0]) % MOD;
        }
        return (int)ans[0][0];
    }

    // 矩阵乘法 A[m][p] * B[p][n] = ans[m][n]
    private long[][] multiply(long[][] A, long[][] B, int MOD) {
        int m = A.length, p = A[0].length, n = B[0].length;
        long[][] ans = new long[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < p; k++) {
                    ans[i][j] = (ans[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return ans;
    }

    // 矩阵快速幂（这里必定是一个方阵）
    private long[][] quickPow(long[][] A, int n, int MOD) {
        int m = A.length;
        // 构造一个单元矩阵，作为初始化
        long[][] ans = new long[m][m];
        for (int i = 0; i < m; i++) {
            ans[i][i] = 1;
        }

        while (n > 0) {
            if ((n & 1) == 1) {
                ans = multiply(ans, A, MOD);
            }
            A = multiply(A, A, MOD);
            n >>= 1;
        }
        return ans;
    }
}

