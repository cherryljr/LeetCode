/*
有一堆共 M 个鹰蛋，一位教授想研究这些鹰蛋的坚硬度 E。
*/

class Solution {
    /**
     * @param m 蛋的数目
     * @param n 楼层的高度
     * @return 最坏情况下的最小代价(扔多少次)
     */
    public int dropEggs(int m, int n) {
        if (n == 0) {
            return 0;
        }
        
        int ans = 0;
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i <= n; ++i) {
            dp[1][i] = i;
        }

        for(int i = 2; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j-1] + 1;
                for(int k = 1; k < j; ++k) {
                    dp[i][j] = Math.min(dp[i][j], 
                                        Math.max(dp[i-1][k-1], dp[i][j-k]) + 1);
                }
            }
        }
        
        return dp[m][n];
    }
}
