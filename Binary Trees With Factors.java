/*
Given an array of unique integers, each integer is strictly greater than 1.
We make a binary tree using these integers and each number may be used for any number of times.
Each non-leaf node's value should be equal to the product of the values of it's children.
How many binary trees can we make?  Return the answer modulo 10 ** 9 + 7.

Example 1:
Input: A = [2, 4]
Output: 3
Explanation: We can make these trees: [2], [4], [4, 2, 2]

Example 2:
Input: A = [2, 4, 5, 10]
Output: 7
Explanation: We can make these trees: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2].

Note:
1 <= A.length <= 1000.
2 <= A[i] <= 10 ^ 9.
 */

/**
 * Approach: DP
 * 求方法数，如果采用 DFS 枚举的方法无疑会超时。
 * 分析发现，该问题为 无后效性 问题，因此使用 DP 解决该问题。
 * dp[i] 代表：以 A[i] 作为父亲节点，构成符合要求的二叉树的可能性方案个数。
 * 因为建立 dp[] 的过程中，存在明显的大小性。（父亲节点必定比子节点要大）
 * 因此我们可以首先对整个数据进行 从小到大 的排序来降低我们所需要遍历的可能性来节省时间。
 * 注释已经写得很详细，这里就不再重复了。
 */
class Solution {
    private static final long MOD = 1000000007L;

    public int numFactoredBinaryTrees(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        // 首先对 A[] 进行从小到大排序，然后进行 DP 的时候，就可以节省一些时间。
        Arrays.sort(A);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            map.put(A[i], i);
        }
        long[] dp = new long[A.length];
        // 对 dp[] 进行初始化，每个元素都可以单独成为一棵符合要求的树
        Arrays.fill(dp, 1L);
        // A[i] 为父亲节点
        for (int i = 0; i < A.length; i++) {
            // A[j] 为左孩子 (也是左子树的父亲节点)
            for (int j = 0; j < i; j++) {
                // 如果 map 中包含 A[i] / A[j]，说明这三个部分就能够组成一棵符合要求的二叉树
                // 所有的可能性为：dp[i] + 左子树可能性 * 右子树可能性 (这里的思想与 Get All Possible Binary Trees 相同)
                if (A[i] % A[j] == 0 && map.containsKey(A[i] / A[j])) {
                    dp[i] = (dp[i] + dp[j] * dp[map.get(A[i] / A[j])]) % MOD;
                }
            }
        }

        long rst = 0L;
        for (long num : dp) {
            rst += num;
        }
        return (int) (rst % MOD);
    }
}