/*
You are standing at position 0 on an infinite number line. There is a goal at position target.
On each move, you can either go left or right. During the n-th move (starting from 1), you take n steps.

Return the minimum number of steps required to reach the destination.

Example 1:
Input: target = 3
Output: 2
Explanation:
On the first move we step from 0 to 1.
On the second step we step from 1 to 3.

Example 2:
Input: target = 2
Output: 3
Explanation:
On the first move we step from 0 to 1.
On the second move we step  from 1 to -1.
On the third move we step from -1 to 2.

Note:
target will be a non-zero integer in the range [-10^9, 10^9].
 */

/**
 * Approach: Mathematics + Greedy
 * 根据题目的数据量，我们可以确定其时间复杂度在 O(log) 级别或者以下。
 * 因此可以直接排除掉 DP 或者 DFS 的做法。
 *
 * 首先想到使用 二分，发现用不上，因此觉得这道题目应该是一道数学题。
 * 那么我们不妨将其转换成数学表达。即我们的目标是：
 *  0  +/-1  +/-2  +/-3  +/-4 ... +/-k = target
 * 我们需要在这 k 个数之前放上正负号，使得 k 最小的情况下，等式成立。
 * 同时我们也可以得知：steps(target) == steps(target).
 * 因此对于 target 为负数的情况，我们直接求相应的正数情况即可。
 *
 * 首先，这里使用了一个 贪心 的策略。要想使得 k 最小，那么就说明我们应该尽量放置 + 号。
 * 则有如下结果：
 *  0  +/-1  +/-2  +/-3  +/-4 ... +/-k = target + d. (0 <= d <= k)
 * 即我们找到一个 k 使得 1~k 的和刚刚 >= target. d 表示 sum 和 target 之间的差值。
 * 此时我们可以对 d 进行分析：
 *  如果 d % 2 == 0，说明我们可以找到一个数 i = d / 2 (0 <= i <= k/2)，然后对其进行翻转，就能够得到结果：
 *  0  +/-1  +/-2  +/-3  +/-4 ...-i ... +/-k = target
 * 如果 d % 2 == 1，此时需要考虑 k 的值：
 *  如果 k % 2 == 0，此时我们只需要多走一步，就能够找到符合条件的 i.
 *  （因为 k 为偶数，则 k+1 为奇数，奇数+奇数=偶数，因此 k+1+d == 偶数）
 *  0 +1 +2 +3 +4 ...-i ... +k + k+1 = target (i = (d + k + 1) / 2 <= k)
 *  如果 k % 2 == 1，此时我们需要多走两步，才能找到符合条件的 i.
 *  但是注意此时 k+1 步需要反着走，不然距离就超了，d 就不满足条件了。
 *  0 +1 +2 +3 +4 ...-i ... +k -k+1 +k+2 = target (i = (d + k+2 - k-1) / 2 == (d+1) / 2 <= k)
 *
 * 时间复杂度：O(sqrt(n))
 * 空间复杂度：O(1)
 *
 * 视频解析：
 *  http://zxi.mytechroad.com/blog/math/leetcode-754-reach-a-number/
 */
class Solution {
    public int reachNumber(int target) {
        // 取 target 的绝对值（正负数结果相同）
        target = target < 0 ? -target : target;
        int sum = 0, k = 0;
        while (sum < target) {
            sum += ++k;
        }
        // d 为 偶数，只需要走 k 步
        if (((sum - target) & 1) == 0) {
            return k;
        }
        // d 为 奇数，此时如果 k 为偶数多走一步，否则多走两步
        return k + 1 + (k & 1);
    }
}