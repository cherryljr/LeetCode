和Stock I 的区别：可以买卖多次，求总和的最大盈利。

思路：
本题中买卖次数无限制，所以为了利润最大化我们要找到所有的 波峰 和 波谷。
故遍历整个数组，只要前后两个数之间的差值为正数，则说明可以获利。
将 profit 加上二者的差值即可。

/*
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like 
(ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple transactions 
at the same time (ie, you must sell the stock before you buy again).

Example
Given an example [2,1,2,0,1], return 2

Tags Expand 
Greedy Enumeration Array
*/

public class Solution {
    /*
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int profit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            if (diff > 0) {
                profit += diff;
            }
        }
        
        return profit;
    }
}