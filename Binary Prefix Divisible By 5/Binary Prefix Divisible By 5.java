/*
Given an array A of 0s and 1s, consider N_i: the i-th subarray from A[0] to A[i] interpreted as a binary number
(from most-significant-bit to least-significant-bit.)

Return a list of booleans answer, where answer[i] is true if and only if N_i is divisible by 5.

Example 1:
Input: [0,1,1]
Output: [true,false,false]
Explanation:
The input numbers in binary are 0, 01, 011; which are 0, 1, and 3 in base-10.
Only the first number is divisible by 5, so answer[0] is true.

Example 2:
Input: [1,1,1]
Output: [false,false,false]

Example 3:
Input: [0,1,1,1,1,1]
Output: [true,false,false,false,true,false]

Example 4:
Input: [1,1,1,0,1]
Output: [false,false,false,false,false]

Note:
    1. 1 <= A.length <= 30000
    2. A[i] is 0 or 1
 */

/**
 * Approach: Mathematics (MOD Detail Explanation)
 * 对于可能出现 数值Overflow 情况的问题，我们通常都会想到使用 MOD 运算。
 * 这里使用到了关于 取余运算 方面的一个数学知识。
 *
 * 设 n2 = n1*a + c 则我们可以推出: n2 % k = (n1%k*a + c) % k  (a !=0，k为正整数，c为常数)
 * 证明：
 *  首先根据定义可知：n1 = ⌊n1/k⌋*k + n1%k
 *  将上式代入 n2 = n1*a + c 可得：
 *  n2 % k = (⌊n1/k⌋*k*a + n1%k*a + c) % k
 *         = (0 + n1%k*a + c) % k
 *         = (n1%k*a + c) % k
 * 则结论得证
 *
 * 在本题中 n2 = n1 * 2 + A[i], k = 5，要求的是 n % k == n % 5 的结果。
 * 因此正好适用上述公式，直接按照该方法计算即可。
 * n2 % 5 = (n1%5*2 + A[i]) % 5 
 * 同时介于二进制的特殊性，上述计算可以利用 位运算 进行优化。
 * （如果不采用取模的方法，30000长度的二进制字符串肯定要爆的....这点相信大家很容易看出来）
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 考察到了 取余数学知识 的类似问题有：
 * Smallest Integer Divisible by K:
 *  https://github.com/cherryljr/LeetCode/blob/master/Smallest%20Integer%20Divisible%20by%20K.java
 * Continuous Subarray Sum:
 *  https://github.com/cherryljr/LeetCode/blob/master/Continuous%20Subarray%20Sum.java
 * Pairs of Songs With Total Durations Divisible by 60:
 *  https://github.com/cherryljr/LeetCode/blob/master/Pairs%20of%20Songs%20With%20Total%20Durations%20Divisible%20by%2060.java
 *  
 * PS.遇到结果与 取模 相关，并且数值会出现溢出情况的问题，
 * 务必要到使用：取模操作的相关知识。
 * eg. n2 = n1*a + c  ==>  n2 % k = (n1%k*a + c) % k 
 *     (a + b) % k = (a%k + b%k) % k
 */
class Solution {
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> ans = new ArrayList<>();
        int reminder = 0;
        for (int i = 0; i < A.length; i++) {
            reminder = (reminder << 1 | A[i]) % 5;
            ans.add(reminder == 0);
        }
        return ans;
    }
}
