Solution 1: Enumeration   
  n天只让买卖一次，那就找个最低价买进，找个最高价卖出。
  即我们需要寻找所谓的 波峰 和 波谷，然后在最低的波谷买进，最高的波峰卖出。
	每天都算和当下的 Min 买卖, 能够赚取的 profit 最大多少。 O(N)
 
Note：
	由于第一天的价格前面没有数据，所以我们无法判断为 波峰 还是 波谷。
	故我们在初始化的时候设定第一天前一天的数据为 Integer.MAX_VALUE

Solution 2: Kadane’s Algorithm
	因为只能买卖一次，所以问题可以转换成：
	1. 由原来的 价格数组 得到 每天前后价格差的 数组。
	比如：{1, 7, 4, 11} => {0, 6, -3, 7}
	2. 这样问题就变成了求转换后数组的 最大子序列 的问题。
	而该问题大家是非常熟悉的，也是著名的 Kadane’s Algorithm
	核心便是：当 maxCur 的值小于 0 时，将其置为0.
	这点稍作思考便能够理解，该算法具体的解析和经典应用可以参见：（数据结构与算法分析该书上也有哦~）
	https://en.wikipedia.org/wiki/Maximum_subarray_problem
	
/*
Description
Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Have you met this question in a real interview? Yes
Example
Given array [3,2,3,1,2], return 1.

Tags 
Greedy Enumeration Array Uber Facebook
*/

// Solution 1: Enumeration
public class Solution {
    /*
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int min = Integer.MAX_VALUE;
        int profit = 0;
        for (int i : prices) {
            min = i > min ? min : i;
            profit = (i - min) > profit ? i - min : profit;
        }
        
        return profit;
    }
}

// Solution 2: Kadane's Algorithm
public class Solution {
    /*
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        
        for(int i = 1; i < prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        
        return maxSoFar;
    }
}