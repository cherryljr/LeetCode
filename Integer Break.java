/*
Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those integers.
Return the maximum product you can get.

Example 1:
Input: 2
Output: 1
Explanation: 2 = 1 + 1, 1 × 1 = 1.

Example 2:
Input: 10
Output: 36
Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.

Note: You may assume that n is not less than 2 and not larger than 58.
 */

/**
 * Approach 1: Recursion + Memory Search
 * 看到这个第一反应一个类似完全背包问题的题目。
 * 因为使用 Memory Search 写起来非常简单并且易于理解，所以并没有采用 DP 的写法。
 * 记忆化搜索的做法很简单：
 *  使用 mem 来记录每个 n 所对应的最优解以避免重复计算。（这里可以使用数组代替）
 *  然后每次枚举所有的分割方案。（从 1~2/n，对于乘积的分割到 n/2 即可，再后面就重复了）
 *  在所有的方案中取最大值即可。
 * 注意：处理初始的几个数值即可（1，2，3）
 *
 * 事后看了下，DP写法基本没差，这里就不写了 O(∩_∩)O
 */
class Solution {
    private Map<Integer, Integer> mem = new HashMap<>();

    public int integerBreak(int n) {
        if (n == 2 || n == 3) {
            return n - 1;
        } else {
            return dfs(n);
        }
    }

    private int dfs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        if (mem.containsKey(n)) {
            return mem.get(n);
        }

        int max = n;
        // Do DFS (1~n/2)
        for (int i = 1; i <= n / 2; i++) {
            max = Math.max(max, i * dfs(n - i));
        }
        // Make a record
        mem.put(n, max);
        return max;
    }
}

/**
 * Approach 2: Mathematics
 * 该解法是网上看到的，跟大家分享一下。数学证明题。
 *
 * 根据题目设定的条件整数n的取值范围为： 2 <= n <= 58
 * 分析一下：
 *  当 n = 2 时： n=1+1; result = 1*1=1
 *  当 n = 3 时：可以拆分为: 1+2 或者 1+1+1，但是显然拆分为 1+2，所获得的乘积最大
 *  当 n = 4 时：可以拆分为： 1+3 或者 2+2，但是显然拆分为 2+2，所获得的乘积最大
 *  当 n = 5 时：可以拆分为：2+3，所获得乘积最大
 *  当 n = 6 时：可以拆分为：3+3，所获得乘积最大
 *  当 n = 7 时：可以拆分为：3+4，所获得乘积最大
 *  当 n = 8 时：可以拆分为：3+3+2，所获得乘积最大
 *  当 n = 9 时：可以拆分为：3+3+3，所获得乘积最大
 *  当 n = 10 时：可以拆分为：3+3+4，所获得乘积最大
 * 通过观察上述内容，我们可以发现从n=5开始，拆分的结果都有数字3。
 * 之后的数字，例如11，可以先拆出来1个3，然后再看余下的8如何拆分。
 *
 * 以上是纯粹的通过样例去寻找规律，当找到规律之后我们就可以去证明它了。
 * 证明使用到了 求导 证明。详细过程可以参考：
 *  https://leetcode.com/problems/integer-break/discuss/80721/Why-factor-2-or-3-The-math-behind-this-problem.
 */
class Solution {
    public int integerBreak(int n) {
        if (n == 2 || n == 3) {
            return n - 1;
        } else if (n == 4) {
            return 4;
        }
        int rst = 1;
        while (n > 4) {
            rst *= 3;
            n -= 3;
        }
        return rst * n;
    }
}