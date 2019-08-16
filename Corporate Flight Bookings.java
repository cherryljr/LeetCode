/*
There are n flights, and they are labeled from 1 to n.
We have a list of flight bookings.
The i-th booking bookings[i] = [i, j, k] means that we booked k seats from flights labeled i to j inclusive.

Return an array answer of length n, representing the number of seats booked on each flight in order of their label.

Example 1:
Input: bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
Output: [10,55,45,25,25]

Constraints:
    1. 1 <= bookings.length <= 20000
    2. 1 <= bookings[i][0] <= bookings[i][1] <= n <= 20000
    3. 1 <= bookings[i][2] <= 10000
 */

/**
 * Approach: Record the change and Accumulate
 * 这道题目在评论区出现了 线段树 和 Sweep Line 解法的解读。
 * 这两种方法确实可以很轻松地解决这个问题，但是未免小题大作了。
 * 我们不妨简单一点，因为要求的是最终每个站上有多少人。
 * 而题目又把每个站的人数变化告知我们了，因此只需要提前统计一下每个站的人数变化。
 * 然后最后依次累加下去就是我们最后需要的答案了。就是这么简单的逻辑...
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] ans = new int[n];
        for (int[] booking : bookings) {
            ans[booking[0] - 1] += booking[2];
            // 注意别越界就行
            if (booking[1] < n) {
                ans[booking[1]] -= booking[2];
            }
        }
        for (int i = 1; i < ans.length; i++) {
            ans[i] += ans[i - 1];
        }
        return ans;
    }
}