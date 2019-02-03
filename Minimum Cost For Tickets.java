/*
In a country popular for train travel, you have planned some train travelling one year in advance.
The days of the year that you will travel is given as an array days.  Each day is an integer from 1 to 365.

Train tickets are sold in 3 different ways:
    a 1-day pass is sold for costs[0] dollars;
    a 7-day pass is sold for costs[1] dollars;
    a 30-day pass is sold for costs[2] dollars.
The passes allow that many days of consecutive travel.
For example, if we get a 7-day pass on day 2, then we can travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.

Return the minimum number of dollars you need to travel every day in the given list of days.

Example 1:
Input: days = [1,4,6,7,8,20], costs = [2,7,15]
Output: 11
Explanation:
For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
In total you spent $11 and covered all the days of your travel.

Example 2:
Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
Output: 17
Explanation:
For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
In total you spent $17 and covered all the days of your travel.

Note:
    1. 1 <= days.length <= 365
    2. 1 <= days[i] <= 365
    3. days is in strictly increasing order.
    4. costs.length == 3
    5. 1 <= costs[i] <= 1000
 */

/**
 * Approach: DP
 * 属于比较经典的 DP 问题。
 * 定义状态 dp[i] 表示：前 i 天旅行需要使用的最少的花费
 * 则根据各种情况，有如下转移方程：
 *  dp[i] = dp[i-1]              // 第 i 天 不进行旅行
 *  dp[i] = dp[i-1] + costs[0]   // 买一天的票
 *  dp[i] = dp[i-7] + costs[1]   // 买七天的票
 *  dp[i] = dp[i-30] + costs[2]  // 买三十天的票
 * 以上结果中取最小值即可。
 *
 * PS.这里需要建立一个数组 willTravel[] 来记录 第ith天是否要进行旅游。
 * 数组大小为需要旅游的最后一天的天数。 willTravel[days[days.length-1] + 1]
 *
 * 时间复杂度：O(N)
 * 空间复杂度：O(N)
 */
class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        int n = days[days.length - 1];
        boolean[] willTravel = new boolean[n + 1];
        int[] dp = new int[n + 1];
        for (int day : days) {
            willTravel[day] = true;
        }
        dp[0] = 0;	// Initialize the dp array (Could be skip)

        for (int i = 1; i < dp.length; i++) {
            if (willTravel[i]) {
                dp[i] = dp[i - 1] + costs[0];
                dp[i] = Math.min(dp[i], dp[Math.max(0, i - 7)] + costs[1]);
                dp[i] = Math.min(dp[i], dp[Math.max(0, i - 30)] + costs[2]);
            } else {
                dp[i] = dp[i - 1];
            }
        }

        return dp[n];
    }
}