/*
We are given hours, a list of the number of hours worked per day for a given employee.
A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.
A well-performing interval is an interval of days for which the number of tiring days is strictly larger than the number of non-tiring days.

Return the length of the longest well-performing interval.

Example 1:
Input: hours = [9,9,6,0,6,6,9]
Output: 3
Explanation: The longest well-performing interval is [9,9,6].

Constraints:
    1. 1 <= hours.length <= 10000
    2. 0 <= hours[i] <= 16
 */

/**
 * Approach: PreSum + HashMap
 * 题意：一天工作 大于8个小时 算是工作量饱和。
 * 如果在连续若干天内，工作量饱和的天数 大于 不饱和天数，则这个天数成为工作良好天数。
 * 求最大的工作良好天数。
 *
 * 思路：问题可以转化一下，工作饱和标记为1，不饱和标记为-1，问题就变成了 求区间和大于 0 的最长区间。
 * 对此我们可以参考LintCode上面的一道问题：Maximum Size Subarray Sum Equals k。
 *
 * 我们可以分析数据，假设当前位置i的前缀和是preSum[i]，如果preSum[i]>0，那显然整个前缀都是答案。
 * 如果preSum[i]<=0，那我们需要找到一个区间preSum[j,i]，使得preSum[j,i]>0。
 * 因为 preSum[0,j-1] + preSum[j,i] = preSum[i]，带入到preSum[j,i]中，即可得到preSum[i] - preSum[0,j-1] > 0。
 * 公式转化一下，即可得到0 >= preSum[i] > preSum[0,j-1]。
 * 由此，就可以发现，我们的目标是找到最前面的比preSum[i]小的位置，从而就可以计算出i的最优答案。
 *
 * 由于整个序列是有1和-1组成，所以前缀和组成的序列是严格连续的。
 * 严格连续的定义指的是相邻的两个前缀和之差肯定是1。
 * 假设最优答案是k，即 preSum[k] < preSum[i] <= 0。
 * 那么在preSum[k]肯定是preSum[i]-1。
 *
 * 反证法：假设最优答案preSum[k] < preSum[i]-1，不妨假设位置是L，值preSum[L] < preSum[i]-1。
 * 由于前缀和的严格连续性，从起始位置到位置L之间肯定存在-1, -2, ..., preSum[i]-1, ..., preSum[L]的前缀和。
 * 这样，在位置L之前肯定存在另一个位置，值是preSum[i]-1，比位置L更优。
 * 假设不成立。所以，最优答案是preSum[i]-1。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * Reference:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 */
class Solution {
    public int longestWPI(int[] hours) {
        Map<Integer, Integer> map = new HashMap<>();
        // 由于本题将 >0 的情况进行了单独的考虑（即从起始位置到当前位置区间为解的情况）
        // 因此，这里不需要对 map 进行初始化来处理这类 corner case.
        // map.put(0, -1);
        int preSum = 0, maxLen = 0;
        for (int i = 0; i < hours.length; i++) {
            preSum += hours[i] > 8 ? 1 : -1;
            if (preSum > 0) {
                maxLen = Math.max(maxLen, i + 1);
            } else {
                if (map.containsKey(preSum - 1)) {
                    maxLen = Math.max(maxLen, i - map.get(preSum - 1));
                }
                map.putIfAbsent(preSum, i);
            }
        }
        return maxLen;
    }
}