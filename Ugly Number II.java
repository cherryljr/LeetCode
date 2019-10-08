/*
Write a program to find the n-th ugly number.
Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.

Example:
Input: n = 10
Output: 12
Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.

Note:
    1. 1 is typically treated as an ugly number.
    2. n does not exceed 1690.
 */

/**
 * Approach 1: Brute Force
 * 直接计算出所有 Integer.MAX_VALUE 以内所有的丑数，然后保存下来，再排序一次即可。
 * 而由题意可知，丑数可被表示为： num = 2^k1 * 3^k2 * 5^k3 (k1, k2, k3 可为 0)
 * 这样我们枚举所有的情况即可。（根据唯一分解定理可得：任意一个数都可以被拆分成 n 个素数的乘积，且拆分方案是唯一的）
 *
 * 因为使用的是 static list,所有只会被建立一次，
 * 这样我们只需要全部查询一次即可，之后每次需要时直接取就行了。
 * 相当于建立了一个 Cache，而这也就是我们常说的 单例模式。
 *
 * 时间复杂度：O(nlogn + T) T指测试样例个数
 * 空间复杂度：O(n)
 */
class Solution {
    private static List<Integer> nums;

    public int nthUglyNumber(int n) {
        if (nums == null) {
            nums = new ArrayList<>();
            // a = 2^k1
            for (long a = 1; a < Integer.MAX_VALUE; a *= 2) {
                // b = 2^k1 * 3^k2
                for (long b = a; b < Integer.MAX_VALUE; b *= 3) {
                    // c = 2^k1 * 3^k2 * 5^k3 (k1, k2, k3 可为 0)
                    for (long c = b; c < Integer.MAX_VALUE; c *= 5) {
                        nums.add((int)c);
                    }
                }
            }
            Collections.sort(nums);
        }
        return nums.get(n - 1);
    }
}

/**
 * Approach 2: Mathematics
 * Approach 1 中属于暴力解法，其实我们可以按照需求来进行计算第 n 个丑数。
 * 因为一个丑数可以被因数分解为： num = 2^k1 * 3^k2 * 5^k3
 * 所以我们可以一步步枚举 k1, k2, k3，并选出当前最小的数加入到 nums 中
 * 依次进行下去。同样这里也使用到了 static 来避免重复计算。（对 T 个 case 进行优化）
 *
 * 时间复杂度：O(n + T)
 * 空间复杂度：O(n)
 *
 * Fellow Up:
 *  Super Ugly Number: https://github.com/cherryljr/LeetCode/blob/master/Super%20Ugly%20Number.java
 *  
 * 参考资料：
 *  http://zxi.mytechroad.com/blog/math/leetcode-264-ugly-number-ii/
 */
class Solution {
    private static List<Integer> nums;
    private static int i2 = 0, i3 = 0, i5 = 0;

    public int nthUglyNumber(int n) {
        if (nums == null) {
            nums = new ArrayList<>();
            nums.add(1);
        }

        // 只有当 n 大于当前计算出来的个数时，我们才需要进行计算，否则直接查询即可
        while (nums.size() < n) {
            int next2 = nums.get(i2) * 2;
            int next3 = nums.get(i3) * 3;
            int next5 = nums.get(i5) * 5;
            int next = Math.min(next2, Math.min(next3, next5));

            // 只要是乘积等于 min 的 index 都需要进行自增操作，从而达到去重的目的
            if (next == next2) {
                i2++;
            }
            if (next == next3) {
                i3++;
            }
            if (next == next5) {
                i5++;
            }
            nums.add(next);
        }
        return nums.get(n - 1);
    }
}
