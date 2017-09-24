There is no doubt that it's a very basic DP question. O(mn)
But it also has a very smart method -- using formula. O(n)

First of all you should understand that we need to do n + m - 2 movements :
m - 1 down, n - 1 right, because we start from cell (1, 1).

Secondly, the path it is the sequence of movements( go down / go right),
therefore we can say that two paths are different
when there is i-th (1 .. m + n - 2) movement in path1 differ i-th movement in path2.

So, how we can build paths.
Let's choose (n - 1) movements(number of steps to the right) from (m + n - 2),
and rest (m - 1) is (number of steps down).

I think now it is obvious that count of different paths are all combinations (n - 1) movements from (m + n-2).

/*
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
The robot can only move either down or right at any point in time. 
The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
How many possible unique paths are there?

The picture: https://leetcode.com/problems/unique-paths/description/
Above is a 3 x 7 grid. How many possible unique paths are there?

Note: m and n will be at most 100.
*/

// Solution 1: DP O(mn)
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            dp[i][1] = 1;
        }
        for (int i = 1; i <= n; i++) {
            dp[1][i] = 1;
        }
        
        for (int i = 2; i <= m; i++) {
            for (int j = 2; j <= n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        
        return dp[m][n];
    }
}

// Solution 2: formula O(n)
class Solution {
    public int uniquePaths(int m, int n) {
        int N = n + m - 2; // how much steps we need to do
        int k = m - 1; // number of steps that need to go down
        double res = 1;
        // here we calculate the total possible path number 
        // Combination(N, k) = n! / (k!(n - k)!)
        // reduce the numerator and denominator and get
        // C = ( (n - k + 1) * (n - k + 2) * ... * n ) / k!
        for (int i = 1; i <= k; i++) {
            res *= (double)(N - k + i) / (double)i;            
        }

        return (int)Math.round(res);
    }
}