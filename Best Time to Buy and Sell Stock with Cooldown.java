1. Define States
To represent the decision at index i:
	buy[i]: Max profit till index i. The series of transaction is ending with a buy.
	sell[i]: Max profit till index i. The series of transaction is ending with a sell.
To clarify:
Till index i, the buy / sell action must happen and must be the last action. It may not happen at index i. It may happen at i - 1, i - 2, ... 0.
In the end n - 1, return sell[n - 1]. Apparently we cannot finally end up with a buy. In that case, we would rather take a rest at n - 1.
For special case no transaction at all, classify it as sell[i], so that in the end, we can still return sell[n - 1]. 

2. Define Recursion (the analysis here is very similar to Best Time to Buy and Sell Stock with Transaction Fee)
buy[i]: To make a decision whether to buy at i, we either take a rest, by just using the old decision at i - 1, 
		or sell at/before i - 2, then buy at i, We cannot sell at i - 1, then buy at i, because of cooldown.
sell[i]: To make a decision whether to sell at i, we either take a rest, by just using the old decision at i - 1, 
		or buy at/before i - 1, then sell at i.
So we get the following formula:
	buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);   
	sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);

3. Optimize to O(1) Space
DP solution only depending on i - 1 and i - 2 can be optimized using O(1) space.
Let b2, b1, b0 represent buy[i - 2], buy[i - 1], buy[i]
Let s2, s1, s0 represent sell[i - 2], sell[i - 1], sell[i]
Then arrays turn into Fibonacci like recursion:
	b0 = Math.max(b1, s2 - prices[i]);
	s0 = Math.max(s1, b1 + prices[i]);
    
/*
Say you have an array for which the ith element is the price of a given stock on day i.
Design an algorithm to find the maximum profit. You may complete as many transactions as you like 
(ie, buy one and sell one share of the stock multiple times) with the following restrictions:
	1. You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
	2. After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)

Example:
prices = [1, 2, 3, 0, 2]
maxProfit = 3
transactions = [buy, sell, cooldown, buy, sell]
*/

// Solution 1: DP 
// O(n) extra space
class Solution {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length <= 1) {
            return 0;
        }
        int len = prices.length;
        int[] buy = new int[len + 1];
        int[] sell = new int[len + 1];
        buy[1] = -prices[0];  // hold[1] = -prices[0]
        
        for (int i = 2; i <= prices.length; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i - 1]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i - 1]);
        }
        
        return sell[len];
    }
}

// Solution 2: DP (Optimized)
// O(1) extra space
class Solution {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length <= 1) {
            return 0;
        }
        int b0 = -prices[0], b1 = b0;
        int s0 = 0, s1 = 0, s2 = 0;

        for (int i = 1; i < prices.length; i++) {
            b0 = Math.max(b1, s2 - prices[i]);
            s0 = Math.max(s1, b1 + prices[i]);
            b1 = b0;  // buy[i - 1] = buy[i]  
            s2 = s1;  // sell[i - 2] = sell[i - 1]
            s1 = s0;  // sell[i - 1] = sell[i]
        }
        
        return s0;
    }
}