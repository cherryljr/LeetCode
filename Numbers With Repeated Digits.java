/*
Given a positive integer N, return the number of positive integers less than or equal to N that have at least 1 repeated digit.

Example 1:
Input: 20
Output: 1
Explanation: The only positive number (<= 20) with at least 1 repeated digit is 11.

Example 2:
Input: 100
Output: 10
Explanation: The positive numbers (<= 100) with atleast 1 repeated digit are 11, 22, 33, 44, 55, 66, 77, 88, 99, and 100.

Example 3:
Input: 1000
Output: 262

Note:
    1. 1 <= N <= 10^9
 */

/**
 * Approach: Mathematics Permutation (Similar to Numbers At Most N Given Digit Set)
 * 根据题目给出的数据规模可以明确时间复杂度必定在 O(logn), O(sqrt(n)) 级别。
 * 因此考虑 Mathematics 的相关解法（二分在这里明显行不通）。
 *
 * 该题是一道计数类的问题，与 Numbers At Most N Given Digit Set 非常类似。
 * 分析过程基本是相同的，只不过这里条件有一点点变化，所以需要进行一些改动。（整体而言，该题难度更大一些）
 * 因为题目要求的是：数字的各个位上的数，至少有一次重复，对于这种范围性的问题，使得计数上要考虑的情况直接复杂了不少。
 * 因此第一反应就是：“求补集”。这样题目就变成了：不大于N，且各个位上的数字均不重复的元素个数了。
 *
 * 之后，按照 Numbers At Most N Given Digit Set 中的思路进行分析：
 *  1. 首先把所有 除去最高位数（必定小于N） 的不重复的数的个数进行相加
 *  这里值得注意的是：除了开头的第一位（leading number），后面各个位上的数都可以是0.
 *  因此这里的方案数可以用组合的方式表示为：9 * P(9, n-i-1)
 *  第一个 9 代表第一位上只能选择 1~9 中元素；
 *  P(9, n-i-1) 代表后面位上的数可以在除去第一位上数外，剩余9个元素中，选择 n-i-1 个数进行全排列。
 *  2. 计算完第一部分之后，我们需要对最高位上的数进行分析
 *  因为这里我们可以随意对 0~9 的元素进行选择，所以必定会出现 该位上的数可以被选择的情况。
 *  与 Numbers At Most N Given Digit Set 相同，我们从最高位开始对 N 进行遍历。
 *  每次对于 小于N当前位上的数，并且还未出现过的值，我们都可以进行一次 ans += A(9 - i, n - i - 1)
 *  直到 N 当前位置上的元素自身发生了重复，那么直接 return，因为后面的值必定是会出现重复位的。
 *
 * 时间复杂度：O(lgN)
 * 空间复杂度：O(lgN)
 *
 * Reference:
 * Numbers At Most N Given Digit Set:
 *  https://github.com/cherryljr/LeetCode/blob/master/Numbers%20At%20Most%20N%20Given%20Digit%20Set.java
 *
 * PS.这里对问题进行转换角度，使得更加容易解决是一个经常被使用到的技巧。
 * 但并不是代表要取补集啥的...有时候也会通过将“确切值的问题”转换成“范围性的问题”
 * ( 比如把 ans(x==k) 转换成 ans(x>=k) - ans(x>=k-1) ) 使用到类似思想的问题有：
 * Subarrays with K Different Integers:
 *  https://github.com/cherryljr/LeetCode/blob/master/Subarrays%20with%20K%20Different%20Integers.java
 */
class Solution {
    public int numDupDigitsAtMostN(int N) {
        // 注意这里对 N 进行了一次 +1 操作
        // 因为以上算法计算的是 <N 的所有不重复的元素个数
        // 如果 N 本身就不是重复的，那么漏掉它本身，使得结果少一位数，这样样就会使得最终结果多了一位
        // 因此这里对 N 进行了一次 +1，使得 ans(<=N) == ans(<N+1)
        // 和 Numbers At Most N Given Digit Set 的处理是一样的
        char[] s = String.valueOf(N + 1).toCharArray();
        int n = s.length, ans = 0;
        // 加上所有小于 ?XXX... 的不重复的元素个数。即不考虑最高位。
        for (int i = 1; i < n; i++) {
            ans += 9 * A(9, i - 1); // 务必注意该行代码的写法，解释中有详细说明
        }

        Set<Character> seen = new HashSet<>();
        for (int i = 0; i < n; i++) {
            // 注意这里的 j 是 char 类型的...
            for (char j = i > 0 ? '0' : '1'; j < s[i]; j++) {
                if (!seen.contains(j)) {
                    // 剩余 9-i 个数字可以选，选出 n-i-1 个进行全排列
                    ans += A(9 - i, n - i - 1);
                }
            }
            if (seen.contains(s[i])) {
                break;
            }
            seen.add(s[i]);
        }

        return N - ans;
    }

    // 求全排列 P(m, n) 的递归写法，直接用 while 写也行，不过这样好看~
    private int A(int m, int n) {
        return n == 0 ? 1 : A(m, n - 1) * (m - n + 1);
    }

}