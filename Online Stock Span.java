/*
Write a class StockSpanner which collects daily price quotes for some stock,
and returns the span of that stock's price for the current day.

The span of the stock's price today is defined as the maximum number of consecutive days (starting from today and going backwards)
for which the price of the stock was less than or equal to today's price.

For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85],
then the stock spans would be [1, 1, 1, 2, 1, 4, 6].

Example 1:
Input: ["StockSpanner","next","next","next","next","next","next","next"], [[],[100],[80],[60],[70],[60],[75],[85]]
Output: [null,1,1,1,2,1,4,6]
Explanation:
First, S = StockSpanner() is initialized.  Then:
S.next(100) is called and returns 1,
S.next(80) is called and returns 1,
S.next(60) is called and returns 1,
S.next(70) is called and returns 2,
S.next(60) is called and returns 1,
S.next(75) is called and returns 4,
S.next(85) is called and returns 6.

Note that (for example) S.next(75) returned 4, because the last 4 prices
(including today's price of 75) were less than or equal to today's price.

Note:
Calls to StockSpanner.next(int price) will have 1 <= price <= 10^5.
There will be at most 10000 calls to StockSpanner.next per test case.
There will be at most 150000 calls to StockSpanner.next across all test cases.
The total time limit for this problem has been reduced by 75% for C++, and 50% for all other languages.
 */

/**
 * Approach 1: Similar to Sequence DP
 * 根据数据规模，并且题目告知了运行时间要求比较被缩短了 50%，因此可以推断出来需要使用 O(n) 级别的算法。
 * 因为该做法是较为明显的序列DP的做法。
 * dp[i] 表示以 ith 作为结尾的连续的低于当前价格的天数。
 * 因此可以推断出递归方程为：
 *  如果 dp[i-1] > dp[i] 那么值就是 1
 *  否则我们需要不断地向前跳，即当 prices[i] >= prices[j]
 *  则 j -= prices[j]. 知道跳到头部或者条件不成立为止。
 *
 * 时间复杂度：均摊 O(n)
 *  关于时间复杂度的证明可以看 Approach 2.
 * 空间复杂度：O(n)
 */
class StockSpanner {
    private List<Integer> prices;
    private List<Integer> dp;

    public StockSpanner() {
        prices = new ArrayList<>();
        dp = new ArrayList<>();
    }

    public int next(int price) {
        if (prices.isEmpty() || price < prices.get(prices.size() - 1)) {
            dp.add(1);
        } else {
            int i = prices.size() - 1;
            while (i >= 0 && prices.get(i) <= price) {
                i -= dp.get(i);
            }
            dp.add(prices.size() - i);
        }
        prices.add(price);
        return dp.get(dp.size() - 1);
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */


/**
 * Approach 2: Monotonic Stack
 * 问题的实际上也可以转换成为：距离当前价格最近的价格大于当前价格的时间t。
 * 那么则有 span = curr - t + 1.
 * 因此这道题目可以转换成使用 单调栈 来解决。
 *
 * 时间复杂度：O(n)
 *  每个元素进出栈各一次，因此总的时间复杂度为 O(2n) => 0(n)
 * 空间复杂度：O(n)
 *
 * 类似的问题：
 *  https://github.com/cherryljr/LintCode/blob/master/Largest%20Rectangle%20in%20Histogram.java
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%9C%80%E5%B0%8F%E6%95%B0%E5%AD%97%E4%B9%98%E4%BB%A5%E5%8C%BA%E9%97%B4%E5%92%8C%E7%9A%84%E6%9C%80%E5%A4%A7%E5%80%BC.java
 */
class StockSpanner {
    private Stack<int[]> stack;

    public StockSpanner() {
        stack = new Stack<>();
    }

    public int next(int price) {
        int span = 1;
        while (!stack.isEmpty() && price >= stack.peek()[0]) {
            span += stack.pop()[1];
        }
        stack.push(new int[]{price, span});
        return span;
    }
}
