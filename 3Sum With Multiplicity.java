/*
Given an integer array A, and an integer target,
return the number of tuples i, j, k such that i < j < k and A[i] + A[j] + A[k] == target.

As the answer can be very large, return it modulo 10^9 + 7.

Example 1:
Input: A = [1,1,2,2,3,3,4,4,5,5], target = 8
Output: 20
Explanation:
Enumerating by the values (A[i], A[j], A[k]):
(1, 2, 5) occurs 8 times;
(1, 3, 4) occurs 8 times;
(2, 2, 4) occurs 2 times;
(2, 3, 3) occurs 2 times.

Example 2:
Input: A = [1,1,2,2,2,2], target = 5
Output: 12
Explanation:
A[i] = 1, A[j] = A[k] = 2 occurs 12 times:
We choose one 1 from [1,1] in 2 ways,
and two 2s from [2,2,2,2] in 6 ways.

Note:
3 <= A.length <= 3000
0 <= A[i] <= 100
0 <= target <= 300
 */

/**
 * Approach: Counting with Cases (Combination)
 * 与 3 Sum 问题非常类似，但是因为计算重复结果。
 * 并且题目给定数据大小范围，所以可以利用 组合 这个数学方法来进行优化。
 * 需要注意的是，虽然 count[] 用于计算各个数的出现次数，
 * 但是在进行 combination 计算的时候会涉及到 乘法，
 * 所以很容易溢出，因此需要使用 long 类型来存储。
 *
 * 时间复杂度：O(N^2) (N为 target)
 * 空间复杂度：O(100) = O(1)
 *
 * 参考资料：
 *  https://www.youtube.com/watch?v=GYIrV9yxgeU
 *  https://leetcode.com/problems/3sum-with-multiplicity/solution/
 *  3 Sum： 
 *  https://github.com/cherryljr/LintCode/blob/master/3%20Sum.java
 */
class Solution {
    private static final int MOD = 1000000007;
    private static final int MAX = 100;

    public int threeSumMulti(int[] A, int target) {
        if (A == null || A.length < 3) {
            return 0;
        }

        // Count the element frequency
        long[] count = new long[MAX + 1];
        for (int num : A) {
            count[num]++;
        }

        long rst = 0L;
        // i <= j <= k
        for (int i = 0; i <= target; i++) {
            for (int j = i; j <= target; j++) {
                int k = target - i - j;
                if (k < 0 || k > MAX || k < j) {
                    continue;
                }
                if (count[i] == 0 || count[j] == 0 || count[k] == 0) {
                    continue;
                }

                if (i == j && j == k) {
                    rst += count[i] * (count[i] - 1) * (count[i] - 2) / 6;
                } else if (i == j && j != k) {
                    rst += count[i] * (count[i] - 1) / 2 * count[k];
                } else if (i != j && j == k) {
                    rst += count[i] * count[j] * (count[j] - 1) / 2;
                } else {
                    rst += count[i] * count[j] * count[k];
                }
            }
        }

        return (int)(rst % MOD);
    }
}