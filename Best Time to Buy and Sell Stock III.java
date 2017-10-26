Solution 1: DP -- Change to Stock I
相比于 Best Time to Buy and Sell Stock I
	该题变成了有2次卖出机会。于是受到 Single Number I 与 III 的启发。
	我们不禁开始考虑能否将该问题转化为两个 Stock I 问题呢？
做法：
	从两边同时开始找 maxProfit. (这实质就是一个 DP 问题)
  leftProfit是从左往右，每个i点上的最大Profit。
  rightProfit是从i点开始到结尾，每个点上的最大profit.
  因此 i 点就是 leftProfit 和 rightProfit 的分割点。
  故我们只需要在i点，将 leftProfit + rightProfit ，找到最大值，那么这个值就是 Maximum Profit。
 
Solution 2: Best Way
	来自 LeetCode 上的一个优秀解法，在这里跟大家分享一下。
	这里需要有四个变量 buy1, sell1, buy2, sell2.
	int sell1 = 0, sell2 = 0, buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;

	首先，刚刚开始可以看作我们身上是没有任何钱的，即 0.因此
	buy1: 意味着我们需要从他人处借钱来购买Stock, 即 buy1 = 0 - prices[i]
	这个时候我们希望借来的钱越少越好。故 buy1 = Math.max(buy1, -prices[i])
	这里使用了 Math.max 运算，因为是借钱，所以这里的 buy1 是负数。

	sell1: 意味着我们想要卖出手里的Stock,所以我们可以收获到 prices[i] 的金钱，但是同时，我们需要还掉之前借来的钱 buy1.
	即 sell1 = prices[i] - |buy1| = prices[i] + buy1			(注意 buy1 是负数)
	同样的，我们希望赚到的钱尽量多。故 sell1 = Math.max(sell1, prices[i] + buy1)

	buy2: 意味着我们想要购买另外一只Stock,此时我们手里已经有了 sell1 的钱，因此购买之后我们手里还剩下 sell1 - prices[i].
	同样的，我们希望花更少的钱买入，即留在手里的钱尽量的多。故 buy2 = Math.max(buy2, sell1 - prices[i])

	sell2: 意味着我们将卖掉第二只购买的Stock. 此时我们手里的钱为 buy2, 故卖出之后，我们能够拥有 buy2 + prices[i] 的钱
	同样的，我们希望赚到的钱尽量多。故 sell2 = Math.max(sell2, buy2 + prices[i])
  
/*
Describe:
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most two transactions.

Example
Given an example [4,4,6,1,1,4,2,5], return 6.

Note
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

Tags Expand 
Enumeration Forward-Backward Traversal Array

*/

// Solution 1: Change to Stock I
class Solution {
    /**
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int[] leftProfit = new int[prices.length];
        int[] rightProfit = new int[prices.length];
        
        // DP from left to right
        // Default:leftProfit[0] = 0;
        int min = prices[0];    
        for (int i = 1; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            leftProfit[i] = Math.max(leftProfit[i - 1], prices[i] - min);
        }
        
        // DP from right to left
        // Default:rightProfit[prices.length - 1] = 0;
        int max = prices[prices.length - 1];    
        for (int i = prices.length - 2; i >= 0; i--) {
            max = Math.max(max, prices[i]);
            rightProfit[i] = Math.max(rightProfit[i + 1], max - prices[i]);
        }
        
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            profit = Math.max(profit, leftProfit[i] + rightProfit[i]);
        }
        
        return profit;
    }
};

// Solution 2
public class Solution {
    /*
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
        int sell1 = 0, sell2 = 0;
        for (int i : prices) {
            buy1 = Math.max(buy1, -i);
            sell1 = Math.max(sell1, buy1 + i);
            buy2 = Math.max(buy2, sell1 - i);
            sell2 = Math.max(sell2, buy2 + i);
        }
        
        return sell2;
    }
}
