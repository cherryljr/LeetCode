/*
Alex and Lee play a game with piles of stones.  There are an even number of piles arranged in a row,
and each pile has a positive integer number of stones piles[i].

The objective of the game is to end with the most stones.
The total number of stones is odd, so there are no ties.

Alex and Lee take turns, with Alex starting first.
Each turn, a player takes the entire pile of stones from either the beginning or the end of the row.
This continues until there are no more piles left, at which point the person with the most stones wins.

Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.

Example 1:
Input: [5,3,4,5]
Output: true
Explanation:
Alex starts first, and can only take the first 5 or the last 5.
Say he takes the first 5, so that the row becomes [3, 4, 5].
If Lee takes 3, then the board is [4, 5], and Alex takes 5 to win with 10 points.
If Lee takes the last 5, then the board is [3, 4], and Alex takes 4 to win with 9 points.
This demonstrated that taking the first 5 was a winning move for Alex, so we return true.

Note:
2 <= piles.length <= 500
piles.length is even.
1 <= piles[i] <= 500
sum(piles) is odd.
 */

/**
 * Approach 1: DP (Game Theory)
 * 解法与 Coins in a Line III 完全一样。
 *
 * Coins in a Line III:
 *  https://github.com/cherryljr/LintCode/blob/master/Coins%20in%20a%20Line%20III.java
 */
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + piles[i - 1];
        }

        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = piles[i];
                } else {
                    int sum = preSum[j + 1] - preSum[i];
                    dp[i][j] = sum - Math.min(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1] > preSum[n] / 2;
    }
}

/**
 * Approach: Mathematics
 * Alex is first to pick pile.
 * piles.length is even, and this lead to an interesting fact:
 * Alex can always pick odd piles or always pick even piles!
 * 
 * For example,
 * If Alex wants to pick even indexed piles piles[0], piles[2], ....., piles[n-2],
 * he picks first piles[0], then Lee can pick either piles[1] or piles[n - 1].
 * Every turn, Alex can always pick even indexed piles and Lee can only pick odd indexed piles.
 * 
 * In the description, we know that sum(piles) is odd.
 * If sum(piles[even]) > sum(piles[odd]), Alex just picks all evens and wins.
 * If sum(piles[even]) < sum(piles[odd]), Alex just picks all odds and wins.
 * 
 * So, Alex always defeats Lee in this game.
 */
class Solution {
    public boolean stoneGame(int[] piles) {
        return true;
    }
}
