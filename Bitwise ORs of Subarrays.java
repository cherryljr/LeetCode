/*
We have an array A of non-negative integers.
For every (contiguous) subarray B = [A[i], A[i+1], ..., A[j]] (with i <= j),
we take the bitwise OR of all the elements in B, obtaining a result A[i] | A[i+1] | ... | A[j].

Return the number of possible results.  (Results that occur more than once are only counted once in the final answer.)

Example 1:
Input: [0]
Output: 1
Explanation:
There is only one possible result: 0.

Example 2:
Input: [1,1,2]
Output: 3
Explanation:
The possible subarrays are [1], [1], [2], [1, 1], [1, 2], [1, 1, 2].
These yield the results 1, 1, 2, 1, 3, 3.
There are 3 unique values, so the answer is 3.

Example 3:
Input: [1,2,4]
Output: 6
Explanation:
The possible results are 1, 2, 3, 4, 6, and 7.

Note:
1 <= A.length <= 50000
0 <= A[i] <= 10^9
 */

/**
 * Approach: DP (Using the Attributes of OR Operation )
 * 这道题目只想出了利用 DP 实现空间换时间的做法。
 * 时间复杂度为：O(n^2)，显然根据题目的数据量这是不满足要求的。
 * 下面这个解法是赛后在讨论区看到的，这边做个记录和大家一起分享一下。
 *
 * 同样是使用到了 DP 的做法。dp[i]表示以 A[i] 作为结尾的所有 subarray 的或起来的值的集合。
 * 因此我们应该使用 Set<Integer> 来表示 dp[i]。
 * 即：dp[i] = {A[i], A[i]|A[i – 1], A[i]|A[i – 1]|A[i – 2], … , A[i]|A[i – 1]| … |A[0]}
 * 这个序列是单调递增的，通过把A[i]中的0变成1。A[i]最多有32个0。所以这个集合的大小 <= 32。
 *
 * e.g. 举例：Worst Case 最坏情况 A = [8, 4, 2, 1, 0] A[i] = 2^(n-i)。
 * A[5] = 0，dp[5] = {0, 0|1, 0|1|2, 0|1|2|4, 0|1|2|4|8} = {0, 1, 3, 7, 15}.
 *
 * 时间复杂度：O(n*log(max(A))) < O(32n)
 * 空间复杂度：O(n*log(max(A)) < O(32n)
 *
 * Reference:https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-898-bitwise-ors-of-subarrays/
 */
class Solution {
    public int subarrayBitwiseORs(int[] A) {
        Set<Integer> curr = new HashSet<>();
        Set<Integer> rst = new HashSet<>();
        for (int num : A) {
            Set<Integer> next = new HashSet<>();
            // 每次我们先将 num(A[i]) 这个数字加入到 nextSet 中
            next.add(num);
            // 然后遍历上一个状态中所有的或起来的数值，并与当前数值进行或运算
            for (int x : curr) {
                next.add(x | num);
            }
            // 将 nextSet 中的所有元素加入到结果集当中
            rst.addAll(next);
            // 滚动替换
            curr = next;
        }
        return rst.size();
    }
}