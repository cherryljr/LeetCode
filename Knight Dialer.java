/*
A chess knight can move as indicated in the chess diagram below:
https://leetcode.com/problems/knight-dialer/
This time, we place our chess knight on any numbered key of a phone pad (indicated above), and the knight makes N-1 hops.
Each hop must be from one key to another numbered key.

Each time it lands on a key (including the initial placement of the knight),
it presses the number of that key, pressing N digits total.

How many distinct numbers can you dial in this manner?
Since the answer may be large, output the answer modulo 10^9 + 7.

Example 1:
Input: 1
Output: 10

Example 2:
Input: 2
Output: 20

Example 3:
Input: 3
Output: 46

Note:
1 <= N <= 5000
 */

/**
 * Approach: Recursion + Memory Search
 * 这道题目与 Knight Probability in Chessboard 非常类似。
 * 区别就是在于将国际象棋的棋盘换成了电话拨号盘。
 * 因此就出现了数字0旁边两个位置无法使用情况。即存在两个特例。
 * 但是可以理解成为 迷宫中的障碍物，只需要对此稍微处理一下即可。
 * 做法仍然是通过 递归+记忆化搜索 来实现的。
 * 比较简单直接，直接参考代码注释即可。
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(12 * N) = O(N)
 *
 * Knight Probability in Chessboard:
 *  https://github.com/cherryljr/LeetCode/blob/master/Knight%20Probability%20in%20Chessboard.java
 */
class Solution {
    final int MOD = 1000000007;
    final int[][] DIRS = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};
    int[][][] mem = null;

    public int knightDialer(int N) {
        mem = new int[N][4][3];
        int rst = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                rst = (rst + dfs(i, j, N - 1)) % MOD;
            }
        }
        return rst;
    }

    /**
     * 该函数的作用为：计算出骑士在坐标(row, col)上，还剩下 n 步没走的情况下
     * 可以走出多少种不同的结果
     */
    public int dfs(int row, int col, int n) {
        // 越界或者该位置无法通行（数字0两边的位置），则直接返回0
        if (row < 0 || row > 3 || col < 0 || col > 2 || (row == 3 && col != 1)) {
            return 0;
        }

        // 走完所有步数，且骑士所处的位置仍然合法，则返回1，代表找到一种方案。
        if (n == 0) {
            return 1;
        }
        // 当前情况已经计算过，则直接返回
        if (mem[n][row][col] != 0) {
            return mem[n][row][col];
        }

        // 初始化当前值
        mem[n][row][col] = 0;
        // 递归调用计算当前位置的结果
        for (int[] dir : DIRS) {
            mem[n][row][col] = (mem[n][row][col] + dfs(row + dir[0], col + dir[1], n - 1)) % MOD;
        }
        return mem[n][row][col];
    }
}