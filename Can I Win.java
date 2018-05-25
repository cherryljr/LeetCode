/*
In the "100 game," two players take turns adding, to a running total, any integer from 1..10. 
The player who first causes the running total to reach or exceed 100 wins.

What if we change the game so that players cannot re-use integers?
For example, two players might take turns drawing from a common pool of numbers of 1..15 
without replacement until they reach a total >= 100.
Given an integer maxChoosableInteger and another integer desiredTotal, 
determine if the first player to move can force a win, assuming both players play optimally.
You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not be larger than 300.

Example
Input:
maxChoosableInteger = 10
desiredTotal = 11

Output:
false

Explanation:
No matter which integer the first player choose, the first player will lose.
The first player can choose an integer from 1 up to 10.
If the first player choose 1, the second player can only choose integers from 2 up to 10.
The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
Same with other integers chosen by the first player, the second player will always win.
*/

/**
 * Approach: Game Theory (Using Recursion + Memory Search)
 * 这道题目属于博弈论的类型，其实质是一个求 Permutations 的过程。
 * 在取数的过程中，安装取的顺序，我们总共有 maxChoosableInteger! 种方案，即 1~maxChoosableInteger 的全排列。
 * 但是枚举 maxChoosableInteger! 种方案明显超过了能承受的时间复杂度。
 * 原因在于我们进行了大量重复的不必要计算。
 *
 * 分析题目，我们发现，只要当前状态已经确定，那么结果也就确定了。
 * 即只要游戏还没结束，我们并不关心如何来到当前状态。如：玩家取了 1,3 和 3,1 结果是相同的。
 * 因此我们可以采用记忆化搜索的方法将已经计算过的状态保存下来，从而达到优化时间复杂度的目的。
 *
 * 递归过程中，我们关心的状态有：当前所能取到的最大值（这个是不变的）；当前所取到值之和；以及那些数已经被取过了
 * 这里我们稍微进行了 两点 空间复杂度上的优化：
 *  1. 使用 dp[] 来记录计算出来的结果。因为其只有 3 中状态，所以我们使用 byte 就够了，开 int[] 浪费了。
 *  2. 使用一个 int 来记录哪些数被取过。因为 maxChoosableInteger 不会超过 20，而 int 有 32 位，完全够用了。
 *  这个做法非常常见，包括大家所熟悉的 BitMap 也使用到了这个做法。
 *
 * 时间复杂度：O(2^n)
 *
 * 参考资料：https://www.youtube.com/watch?v=GNZIAbf0gT0
 */
class Solution {
    byte[] dp;  // 0: unknow, 1: can win, -1: can't win

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) >> 1;
        // 取出所有的数都凑不出 desiredTotal，肯定输
        if (desiredTotal > sum) {
            return false;
        }
        // 可取的最大值直接 >= 期望值，先手必赢
        if (desiredTotal <= maxChoosableInteger) {
            return true;
        }

        // dp[]的 index 就表示了其中一种方案.（思想与利用 二进制 计算subset相同）
        dp = new byte[1 << maxChoosableInteger];
        return canIWinHelper(maxChoosableInteger, desiredTotal, 0);
    }

    private boolean canIWinHelper(int maxChoosableInteger, int desiredTotal, int state) {
        // if the previous player make desiredTotal <= 0, it means he can win, then the current player lose.
        if (desiredTotal <= 0) {
            return false;
        }
        // if the current state has been calculated before, return the result directly
        if (dp[state] != 0) {
            return dp[state] == 1;
        }

        for (int i = 0; i < maxChoosableInteger; i++) {
            // if number i is used, skip it
            if (((state >> i) & 1) > 0) {
                continue;
            }
            // the next player can't win, them the current player can win
            if (!canIWinHelper(maxChoosableInteger, desiredTotal - (i + 1), state | (1 << i))) {
                dp[state] = 1;
                return true;
            }
        }

        dp[state] = -1;
        return false;
    }
}