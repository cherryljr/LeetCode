Solution 1: DP Method 1
记得要理解： 为什么 第i-1天 的卖了又买，可以和 第i天 的卖合成一次交易？    
   因为每天交易的price是定的。所以卖了又买，等于没卖！这就是可以合并的原因。

给定一个数组,不能够进行 Sort, 求最大值。当前状态是由小状态(前i天的profit)推出 => Sequence DP

进行 DP 之前判断 k >= len/2 ? 如果是的话，这就意味着我们可以随时买入和卖出。
这样问题就直接转换成了 Best Time to Buy and Sell Stock II

状态： State
局部最优解 VS 全局最优解
	local[i][j]: 第i天，当天一定进行第 j 次交易的 profit     
	global[i][j]: 第i天，总共进行了 j 次交易的 profit.    
	
初始化： Initialize
	loacal[i][0] = global[i][0] = 0
	local[0][i] = gloabl[0][i] = 0
	这一步在开辟数组空间时 Java 已经帮我们完成了，故代码中并没有写出该步骤

方法： Function
	local[i][j] = Math.max(global[i – 1][j – 1] + diff, local[i – 1][j] + diff)    
  global[i][j] = Math.max(global[i – 1][j], local[i][j])     

答案： Answer
	global[prices.length - 1][k]

local[i][j]和global[i][j]的区别是：local[i][j]意味着在第i天一定有交易（卖出）发生。    
   当 第i天 的价格高于 第i-1天（即diff > 0）时，那么可以把这次交易 (第i-1天买入第i天卖出) 跟 第i-1天的交易(卖出) 合并为一次交易，即 local[i][j] = local[i-1][j] + diff；    
   当 第i天 的价格不高于 第i-1天（即diff <= 0）时，那么 local[i][j] = global[i-1][j-1] + diff，而由于 diff <= 0，所以可写成 local[i][j] = global[i-1][j-1]。    
   (Note:在我下面这个solution里面没有省去 +diff）   

global[i][j]就是我们所求的前i天最多进行k次交易的最大收益，可分为两种情况：    
   如果第i天没有交易（卖出），那么 global[i][j] = global[i-1][j]；     
   如果第i天有交易（卖出），那么 global[i][j] = local[i][j]。    
   
Solution 2: DP Method 2
	Learn form: https://discuss.leetcode.com/topic/8984/a-concise-dp-solution-in-java
	Very Smart Way !!!
    
Good Conclusions about Best Time to Buy and Sell Stock serial quesionts:
https://discuss.leetcode.com/topic/107998/most-consistent-ways-of-dealing-with-the-series-of-stock-problems
	
/*
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most k transactions.

Example
Given prices = [4,4,6,1,1,4,2,5], and k = 2, return 6.

Note
You may not engage in multiple transactions at the same time 
(i.e., you must sell the stock before you buy again).

Challenge
O(nk) time.

Tags Expand 
Dynamic Programming
*/

// Solution 1
class Solution {
    /**
     * @param k: An integer
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int k, int[] prices) {
        if (k == 0) {
            return 0;
        }
        if (k >= prices.length / 2) {
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }
        // State
        int n = prices.length;
        int[][] local = new int[n + 1][k + 1];   // local[i][j] 表示前i天，至多进行j次交易，第i天必须sell的最大获益
        int[][] global = new int[n + 1][k + 1];  // global[i][j] 表示前i天，至多进行j次交易，第i天可以不sell的最大获益
        // Initialize & Function
        for (int i = 1; i < n; i++) {
            int diff = prices[i] - prices[i - 1];
            local[i][0] = 0;
            for (int j = 1; j <= k; j++) {
                local[i][j] = Math.max(global[i - 1][j - 1] + diff,
                                            local[i - 1][j] + diff);
                global[i][j] = Math.max(global[i - 1][j], local[i][j]);
            }
        }
        // Answer
        return global[n - 1][k];
    }
};

// Solution 2
class Solution {
    /**
     * @param k: An integer
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int k, int[] prices) {
    	if (k == 0) {
    	    return 0;
    	}
        if (k >= prices.length / 2) {
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }
        
        int len = prices.length;
        int[][] dp = new int[k + 1][len];
        for (int i = 1; i <= k; i++) {
            int tmpMax =  -prices[0];
            for (int j = 1; j < len; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + tmpMax);
                tmpMax =  Math.max(tmpMax, dp[i - 1][j - 1] - prices[j]);
            }
        }
        
        return dp[k][len - 1];
    }
};
