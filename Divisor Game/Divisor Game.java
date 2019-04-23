/*
Alice and Bob take turns playing a game, with Alice starting first.
Initially, there is a number N on the chalkboard.  On each player's turn, that player makes a move consisting of:

Choosing any x with 0 < x < N and N % x == 0.
Replacing the number N on the chalkboard with N - x.
Also, if a player cannot make a move, they lose the game.

Return True if and only if Alice wins the game, assuming both players play optimally.

Example 1:
Input: 2
Output: true
Explanation: Alice chooses 1, and Bob has no more moves.

Example 2:
Input: 3
Output: false
Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.

Note:
    1. 1 <= N <= 1000
*/

/**
 * Approach 1: Recursion with Memory Search
 * 看到题目反应是 Nim 问题，因此想到可以使用 DP 的做法进行解决。
 * （这里没有想着从数学方面去证明是因为比赛时没考虑这么多...这个数据规模用 DP 肯定能过，更优的解法可以赛后再想）
 * 因为对于每个元素，一旦值确定，那么先手成败的结果就已经确定了，故使用 Recursion + Memory Search 进行解决。
 * 转移方程即：
 *  只要存在一种做法使得下一位玩家输掉比赛，那么当前玩家就能够赢得比赛。
 *  win |= !canWin(mem, n - i)  （i为 n 的一个约数)
 * 
 * 时间复杂度：O(n * sqrt(n))
 * 空间复杂度：O(n)
 */
class Solution {
    public boolean divisorGame(int N) {\
        // 记录数值为 i 时，先手的成败结果。
        // 0代表状态未知，1代表成功，2代表失败。
        int[] mem = new int[N + 1];
        return canWin(mem, N);
    }

    private boolean canWin(int[] mem, int n) {
        if (n == 1) {
            return false;
        }
        if (mem[n] != 0) {
            return mem[n] == 1;
        }

        boolean win = false;
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                win |= !canWin(mem, n - i);
            }
        }
        mem[n] = win ? 1 : 2;

        return win;
    }
}

/**
 * Approach 2: Mathematics
 * 这道题目实际上可以通过递推发现结论：
 *  当 N 为偶数时，先手必赢。
 * 这一过程我们可以通过递推证明：
 * 因为 1 是任意数的一个约数，所以我们每次都能够进行一次 N-1 的变换操作。
 *  当 N==1 时，先手必输
 *  当 N==2 时，可以进行一次 -1 操作，reduce 到 1，因此先手必赢
 *  当 N==3 时，可以进行一次 -1 操作，reduce 到 2，因此先手必败
 *  当 N==4 时，可以进行一次 -1 操作，reduce 到 3，因此先手必赢
 *  ...
 *  依次类推下去即可
 * 对于 奇数而言，其约数都是 奇数，因此进行变换之后结果必定是 偶数。
 * 所以 偶数先手必赢，奇数先手必败
 *
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 */
class Solution {
    public boolean divisorGame(int N) {
        return (N & 1) == 0;
    }
}