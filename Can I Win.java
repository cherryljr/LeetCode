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
 * 这题似乎是一题经典的博弈问题，但是有一个很重要的限制条件 —— 每个数只能取一次。
 * 这也是该题解题的核心点所在。
 * 有了这个条件，我们很容易就能想到所有的取数方案，最多有 maxChoosableInteger! 种，即 1~maxChoosableInteger 的全排列。
 * 但是一一枚举这 maxChoosableInteger! 种方案明显超过了能承受的时间复杂度。
 * 因为方案有很多的部分重复计算了，所以我们可以采用记忆化搜索的方法来优化该算法。
 *
 * 在记忆化搜索中，使用了两个条件判断胜利：
 * 一是当前剩下的desiredTotal(减去了取走的数)小于等于0，
 * 二是对于剩下的还未取的数，已经搜索过且是必胜的状态，
 * 假如这个状态没有搜索过，就进行 dfs 判断这个状态，直到遇到判断过的状态或desiredTotal小于等于0。
 *
 * 对于记录状态的数组，
 * 我们使用 01 表示，0代表这个数没有使用过，1代表已经使用过。
 * 把 1~maxChoosableInteger 的使用情况定义为一个只有0,1的向量，
 * 比如：maxChoosableInteger=3，他们的使用情况有8种，
 * 分别是000(没有元素被使用),001,010,100,011,110,101,111(所有元素都被使用)。
 * 再把这个向量转化成十进制数作为下标。
 *
 * 复杂度分析：
 * 除了特殊情况外我们需要计算所有的状态，且每个状态只需要计算一次，则时间和空间复杂度均为O(2^n)。
 * n 为 maxChoosableInteger.
 */
class Solution {
    int[] dp;
    boolean[] used;
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
        // 取完所有数，也达不到desiredTotal，无法赢得游戏
        if (sum < desiredTotal) {
            return false;
        }
        // 第一步就可以获得胜利
        if (desiredTotal <= maxChoosableInteger) {
            return true;
        }

        dp = new int[1 << maxChoosableInteger];
        Arrays.fill(dp , -1);
        used = new boolean[maxChoosableInteger + 1];

        return helper(desiredTotal);
    }

    public boolean helper(int desiredTotal) {
        if (desiredTotal <= 0) {
            return false;
        }
        // 把used数组转为十进制表示
        int key = format(used);
        if (dp[key] == -1) {
            // 枚举未选择的数
            for (int i = 1; i < used.length; i++) {
                if (!used[i]) {
                    used[i] = true;

                    if (!helper(desiredTotal - i)) {
                        dp[key] = 1;
                        used[i] = false;
                        return true;
                    }
                    used[i] = false;
                }
            }
            dp[key] = 0;
        }
        return dp[key] == 1;
    }


    public int format(boolean[] used) {
        int num = 0;
        for (boolean b: used) {
            num <<= 1;
            if(b) {
                num |= 1;
            }
        }
        return num;
    }
}