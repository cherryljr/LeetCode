/*
Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?

Example:
Input: 3
Output: 5
Explanation:
Given n = 3, there are a total of 5 unique BST's:
       1         3     3      2      1
        \       /     /      / \      \
         3     2     1      1   3      2
        /     /       \                 \
       2     1         2                 3
 */

/**
 * Approach: Catalan Number
 * 典型卡特兰数的应用问题...
 * 
 * Reference:
 *  卡特兰数推理过程介绍：https://youtu.be/YDf982Lb84o
 *  卡特兰数的应用：http://www-math.mit.edu/~rstan/ec/catalan.pdf
 *  类似的问题：https://github.com/cherryljr/NowCoder/blob/master/Game%20of%20Connections.java
 */
class Solution {
    public int numTrees(int n) {
        // int[] ans = new int[n + 1];
        // ans[0] = 1;
        // ans[1] = 1;
        // for (int i = 2; i <= n; i++) {
        //     for (int j = 0; j < i; j++) {
        //         ans[i] += ans[j] * ans[i - j - 1];
        //     }
        // }
        // return ans[n];

        // 使用以下递推式时，考虑到精度问题，最好使用BigInteger来进行计算
        // 这里能过是因为题目的 test case 比较小，范围只有int
        double[] ans = new double[n + 1];
        ans[0] = 1;
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i - 1] * (4 * i - 2) / (i + 1);
        }
        return (int)ans[n];
    }
}