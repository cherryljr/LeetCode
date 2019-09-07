/*
Alex and Lee continue their games with piles of stones.
There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].
The objective of the game is to end with the most stones.

Alex and Lee take turns, with Alex starting first.  Initially, M = 1.
On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M. Then, we set M = max(M, X).

The game continues until all the stones have been taken.
Assuming Alex and Lee play optimally, return the maximum number of stones Alex can get.

Example 1:
Input: piles = [2,7,9,4,4]
Output: 10
Explanation:  If Alex takes one pile at the beginning, Lee takes two piles, then Alex takes 2 piles again. Alex can get 2 + 4 + 4 = 10 piles in total. If Alex takes two piles at the beginning, then Lee can take all three piles left. In this case, Alex get 2 + 7 = 9 piles in total. So we return 10 since it's larger.

Constraints:
    1. 1 <= piles.length <= 100
    2. 1 <= piles[i] <= 10 ^ 4
 */

/**
 * Approach: DFS + Memory Search
 * 与 Coins in a Line 系列是同一类型的问题（和 Coins in a Line II 非常类似）
 * 只是取的方式不同了而已，即每次可以取 1~2*M 个石头。对此我们只需要利用一个 for 循环枚举所有的取法即可。
 * （原来可能取法比较单一，要么一个或两个，要么从左边或者右边取，不需要用到 for 循环来枚举）
 * 除此之外，就没有其他区别了。核心思想依然只有一个：把最差的留给别人，自己的利益就能够最大化。
 * 实现上利用 DFS + Memory Search 实现即可。
 * mem[i][j]代表：在前i个石头中，取j个石头的情况下，先手所能够获取到的最大价值。
 * 同时为了优化计算过程，我们需要维护一个 后缀和数组。（前缀和也行，不过用起来比较麻烦）
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(n^2)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Coins%20in%20a%20Line%20II.java
 *  https://github.com/cherryljr/LintCode/blob/master/Coins%20in%20a%20Line%20III.java
 */
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length, sum = 0;
        int[][] mem = new int[n][n];
        int[] suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        return dfs(piles, 0, 1, suffixSum, mem);
    }

    private int dfs(int[] piles, int index, int M, int[] suffixSum, int[][] mem) {
        // 如果剩下的石头在一次内能够全被取完，那么必定要全部取走（贪心）
        if (index + 2 * M >= piles.length) {
            return suffixSum[index];
        }
        if (mem[index][M] != 0) {
            return mem[index][M];
        }

        // 只要对手下一次所能取到的价值最少，则当前做法就是利益最大化的
        int min = Integer.MAX_VALUE;
        for (int count = 1; count <= 2 * M && index + count <= piles.length; count++) {
            min = Math.min(min, dfs(piles, index + count, Math.max(count, M), suffixSum, mem));
        }
        mem[index][M] = suffixSum[index] - min;
        return mem[index][M];
    }
}