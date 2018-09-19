/*
Given an array of integers A, find the sum of min(B),
where B ranges over every (contiguous) subarray of A.
Since the answer may be large, return the answer modulo 10^9 + 7.

Example 1:
Input: [3,1,2,4]
Output: 17
Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.

Note:
1 <= A.length <= 30000
1 <= A[i] <= 30000
 */

/**
 * Approach: Monotonic Stack
 * 这道题目与 最小数字乘以区间和的最大值 这道题目非常的类似。说是变形题也不为过。
 * 根据题目的数据量可知算法的时间复杂度应该在 O(nlogn) 级别以下。
 * 这里我们需要确定以当前数 A[i] 作为最小值的 subarray 的范围。
 * 然后计算这些 subarray 有多少个，如果是 n 个的话，
 * 那么 A[i] 对于答案的贡献就是 A[i] * n.
 *
 * 那么对于寻找 A[i] 左边/右边 小于 A[i] 的第一个元素，
 * 毫无疑问就是使用到了我们 单调栈 了。
 * 同样的栈中存储数组的下标即可。
 * 然后我们就可以依次来划分以 A[i] 作为最小值的 subarray 的左右边界了。
 * 然后将 左边界可能的划分位置 * 右边界可能的划分位置 * A[i] 就是结果了。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 最小数字乘以区间和的最大值：
 *  https://github.com/cherryljr/NowCoder/blob/master/%E6%9C%80%E5%B0%8F%E6%95%B0%E5%AD%97%E4%B9%98%E4%BB%A5%E5%8C%BA%E9%97%B4%E5%92%8C%E7%9A%84%E6%9C%80%E5%A4%A7%E5%80%BC.java
 */
class Solution {
    private static final int MOD = 1000000007;

    public int sumSubarrayMins(int[] A) {
        long rst = 0L;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= A.length; i++) {
            int curr = i == A.length ? -1 : A[i];
            while (!stack.isEmpty() && A[stack.peek()] >= curr) {
                // 需要被 pop() 出来元素的下标
                int index = stack.pop();
                // 以 A[index] 为最小值的 subarray 左边界为 A[stack.peek()] 右边界为 A[i]
                if (stack.isEmpty()) {
                    rst = (rst + A[index] * (index + 1) * (i - index)) % MOD;
                } else {
                    rst = (rst + A[index] * (index - stack.peek()) * (i - index)) % MOD;
                }
            }
            stack.push(i);
        }
        return (int)rst;
    }
}