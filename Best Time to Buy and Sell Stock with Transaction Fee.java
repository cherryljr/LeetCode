This Question is similar to Best Time to Buy and Sell Stock II.
But the solution is different. In this question the current status is depend on the last status.
So it should be solved by DP.

Define dp array:
hold[i] : The maximum profit of holding stock until day i;
notHold[i] : The maximum profit of not hold stock until day i;

dp transition function:
For day i, we have two situations:
    Hold stock:
    (1) We do nothing on day i: hold[i - 1];
    (2) We buy stock on day i: notHold[i - 1] - prices[i]; 

    Not hold stock:
    (1) We do nothing on day i: notHold[i - 1];
    (2) We sell stock on day i: hold[i - 1] + prices[i] - fee;

Note:
    I use a fantastic number: 0x3f3f3f3f here. Using it represents INF can avoid overflow.
    Because INF + INF = INF
    
/*
Your are given an array of integers prices, for which the i-th element is the price of a given stock on day i; 
and a non-negative integer fee representing a transaction fee.

You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction. 
You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share before you buy again.)

Return the maximum profit you can make.

Example 1:
Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
Output: 8
Explanation: The maximum profit can be achieved by:
Buying at prices[0] = 1
Selling at prices[3] = 8
Buying at prices[4] = 4
Selling at prices[5] = 9
The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.

Note:
0 < prices.length <= 50000.
0 < prices[i] < 50000.
0 <= fee < 50000.

*/

class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int len = prices.length;
        int[] hold = new int[len + 1];
        int[] nothold = new int[len + 1];
        // Because we don't have money, so when we buy a stock first time, the money is negative
        // the hold[0] must be initialize as MIN_VALUE.
        // Think about why I use -0x3f3f3f3f but not Integer.MIN_VALUE
        // Cuz if I use MIN_VALUE, it will cause overflow due to the operation: hold[0] + prices[0] - fee
        // If you want to know more about 0x3f3f3f3f, plz google it. It's really a fantastic number
        hold[0] = -0x3f3f3f3f;   
        
        for (int i = 1; i <= len; i++) {
            hold[i] = Math.max(hold[i - 1], nothold[i - 1] - prices[i - 1]);  // prices[i - 1] represents the prices of day ith.
            nothold[i] = Math.max(nothold[i - 1], hold[i - 1] + prices[i - 1] - fee);
        }
        
        return nothold[len];
    }
}

// Solution 2: DP (Optimized)
// The current status is only depend on the last status, so the space can be optimized.
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int notHold = 0, hold = -0x3f3f3f3f;
        
        for (int i = 0; i < prices.length; i++) {
            hold = Math.max(hold, notHold - prices[i]);
            notHold = Math.max(notHold, hold + prices[i] - fee);
        }
        
        return notHold;
    }
}