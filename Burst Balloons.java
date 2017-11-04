This Problem is very similar to Strange Printer in LeetCode.

The nature way to divide the problem is burst one balloon and separate the balloons into 2 sub sections 
one on the left and one one the right. 
However, in this problem the left and right become adjacent and have effects on the maxCoins in the future.

Then another interesting idea come up. Which is quite often seen in dp problem analysis. 
That is reverse thinking. Like I said the coins you get for a balloon does not depend on the balloons already burst. 
Therefore instead of divide the problem by the first balloon to burst, we divide the problem by the last balloon to burst.

Why is that? Because only the first and last balloons we are sure of their adjacent balloons before hand!
For the first we have nums[i-1]*nums[i]*nums[i+1] for the last we have nums[-1]*nums[i]*nums[n].
OK. Think about n balloons if i is the last one to burst, what now?
We can see that the balloons is again separated into 2 sections. 
But this time since the balloon i is the last balloon of all to burst, 
the left and right section now has well defined boundary and do not affect each other! 
Therefore we can do either recursive method with memoization or dp.

Here comes the final solutions. Note that we put 2 balloons with 1 as boundaries 
and also burst all the zero balloons in the first round since they won't give any coins.
The algorithm runs in O(n^3) which can be easily seen from the 3 loops in dp solution.

/*
Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. 
You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. 
Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.
Find the maximum coins you can collect by bursting the balloons wisely.

Note: 
(1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
(2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100

Example:
Given [3, 1, 5, 8]
Return 167
    nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
   coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
*/

class Solution {
    public int maxCoins(int[] iNums) {
        if (iNums == null || iNums.length == 0) {
            return 0;
        }
        
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[n++] = x;
            }
        }
        nums[0] = nums[n++] = 1;
        int[][] dp = new int[n][n];
        // this operation could be canceled 
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        
        for (int l = 2; l <= n; l++) {
            for (int left = 0; left + l <= n; left++) {
                int right = left + l - 1;
                for (int k = left + 1; k < right; k++) {
                    dp[left][right] = Math.max(dp[left][right], 
                                              nums[left] * nums[k] * nums[right] + dp[left][k] + dp[k][right]);
                }
            }
        }
        
        return dp[0][n - 1];
    }
}