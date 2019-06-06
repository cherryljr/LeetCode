/*
We have a collection of rocks, each rock has a positive integer weight.
Each turn, we choose any two rocks and smash them together.
Suppose the stones have weights x and y with x <= y.  The result of this smash is:

If x == y, both stones are totally destroyed;
If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
At the end, there is at most 1 stone left.
Return the smallest possible weight of this stone (the weight is 0 if there are no stones left.)

Example 1:
Input: [2,7,4,1,8,1]
Output: 1
Explanation:
We can combine 2 and 4 to get 2 so the array converts to [2,7,1,8,1] then,
we can combine 7 and 8 to get 1 so the array converts to [2,1,1,1] then,
we can combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we can combine 1 and 1 to get 0 so the array converts to [1] then that's the optimal value.

Note:
    1. 1 <= stones.length <= 30
    2. 1 <= stones[i] <= 100
 */

/**
 * Approach: 01 Backpack
 * 该问题实质上可以转换成：将一个数组分成两个子数组，使得这两个子数组之和相差最小。
 * 之所以可以这么转换的原因证明如下：
 *   假设我们有 4 个石头，其重量分别为 a,b,c,d.
 *   此时我们可以进行按照题目要求进行如下操作：
 *     取出 c,d 两块石头进行 smash 操作，则剩下：a, b, d-c
 *     然后将 d-c 与 b 进行 smash，则剩下：a, d-c-b
 *     最后将剩下的两块石头进行 smash，最终结果为：d-c-b-a == d - (a+b+c)  （是两个子数组的差值）
 *   当然我们可以变换上述的顺序
 *     分别取出 a,b 和 c,d 进行 smash 操作，则剩下：(a-b), (c-d)
 *     然后将剩下的两块石头进行 smash，最终结果为：(a-b)-(c-d) == (a+d) - (b+c)  （同样是两个子数组的差值）
 * 因此，通过上述说明，我们可以发现，通过不同的操作顺序，可以获得不同 subset array 之间的差值。
 *
 * 现在我们已经明白了这道题目的本质要求，那么这就是一个简单的 01背包问题 了。
 * 设两个 subset array 之和分别为 S1, S2.
 * 则有：S1 + S2 = sum; S1 - S2 = -diff (S1 ≤ S2)
 * 将上述两式相加可得：2S1 = sum - diff  ==>  diff = sum - 2S1
 * sum 值是固定的，因此为了使得 diff 值最小，需要使得 S1 值尽可能的大。（S1的范围在 0~sum/2 之间）
 * 即：求一个数组的 subset 使得其子数组之和尽可能的大，但是不能超过 sum/2.
 *
 * 解法如下：
 * dp[i] 表示能否组成一个数组和为 i 的一个 subset array.
 * 则状态转移方程为：dp[i] |= ∑（dp[i - stones[j]]）
 * 然后求当前所能够获得的最大的子数组和 max，
 * 则最后结果为 sum - max - max
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 类似的问题：
 * Target Sum:
 *  https://github.com/cherryljr/LeetCode/blob/master/Target%20Sum.java
 */
class Solution {
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }

        boolean[] dp = new boolean[sum / 2 + 1];
        dp[0] = true;
        for (int i = 0; i < stones.length; i++) {
            for (int j = sum >> 1; j >= stones[i]; j--) {
                dp[j] |= dp[j - stones[i]];
            }
        }
        for (int i = sum >> 1; i >= 0; i--) {
            if (dp[i]) {
                return sum - i - i;
            }
        }
        return 0;
    }
}