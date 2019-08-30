/*
Given two arrays of integers with equal lengths, return the maximum value of:
    |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| + |i - j|
where the maximum is taken over all 0 <= i, j < arr1.length.

Example 1:
Input: arr1 = [1,2,3,4], arr2 = [-1,4,5,6]
Output: 13

Example 2:
Input: arr1 = [1,-2,-5,0,10], arr2 = [0,-2,-1,-7,-4]
Output: 20

Constraints:
    1. 2 <= arr1.length == arr2.length <= 40000
    2. -10^6 <= arr1[i], arr2[i] <= 10^6
 */

/**
 * Approach 1: Deal with Absolute Value Expression
 * 对于包含绝对值的题目，因为其大小不确定的原因，使得我们不便直接看清真正表达式的情况。
 * 对此我们不妨对其进行 去绝对值 操作，这会使得我们更容易看清问题的本质。（千万别傻傻地以为用 Math.abs() 去处理）
 * （LeetCode上还有类似的题目，需要我们对绝对值表达式进行展开操作，然后对各个情况进行枚举分析。不过题目名字我忘了...233）
 * 需要 去绝对值 这类问题的表达式通常不长，即我们能够很容易对其各个情况进行枚举分析。
 *
 * 本题中，我们首先将 |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| + |i - j| 记作 |a1 - b1| + |a2 - b2| + |i - j| 以便书写。
 * 涉及到绝对值的值总共只有 3 个，并且在结果计算时，因为 i,j 为index，我们可以直接令 i > j，这样我们实际上只需要拆两个绝对值表达式而已。
 * 因此求 |a1 - b1| + |a2 - b2| + (i - j) 的最大值，就是求以下4个算式的最大值：
 *      (a1-b1) + (a2-b2) + (i-j)
 *      (a1-b1) + (b2-a2) + (i-j)
 *      (b1-a1) + (a2-b2) + (i-j)
 *      (b1-a1) + (b2-a2) + (i-j)
 * 进行转换后为:
 *      (+a1+a2+i) - (+b1+b2+j)
 *      (+a1-a2+i) - (+b1-b2+j)
 *      (-a1+a2+i) - (-b1+b2+j)
 *      (-a1-a2+i) - (-b1-b2+j)
 * a1,a2 / b1,b2对应的符号分别是
 *      +,+
 *      +,-
 *      -,+
 *      -,-
 * 因此，我们只需要遍历以上 4 种符号组合的情况，并综合对比就可以得到最终的结果。
 *
 * 时间复杂度：O(4 * n)
 * 空间复杂度：O(1)
 */
class Solution {
   public int maxAbsValExpr(int[] arr1, int[] arr2) {
       // 4种符合的组合情况
       int[][] OPS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
       int ans = Integer.MIN_VALUE;
       for (int[] op : OPS) {
           int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
           // 求出当前符号组合情况下的最大值和最小值
           for (int i = 0; i < arr1.length; i++) {
               max = Math.max(max, arr1[i] * op[0] + arr2[i] * op[1] + i);
               min = Math.min(min, arr1[i] * op[0] + arr2[i] * op[1] + i);
           }
           // 将最终 ans 与 当前符号组合表达式所能得到的最大值 进行比较
           ans = Math.max(ans, max - min);
       }
       return ans;
   }
}

/**
 * Approach 2: Manhattan Distance
 * 如果说 Approach 1 是从数学的 代数 方面去解决这个问题，
 * 那么 Approach 2 就是从数学的 几何 方面来解决这个问题。
 * 链接中有个图文解释非常形象生动，这里就不再赘述了。
 * 
 * 时间复杂度：O(4 * n)
 * 空间复杂度：O(1)
 * 
 * Reference:
 *  https://leetcode.com/problems/maximum-of-absolute-value-expression/discuss/339968/JavaC%2B%2BPython-Maximum-Manhattan-Distance
 */
class Solution {
    public int maxAbsValExpr(int[] arr1, int[] arr2) {
        int[][] OPS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int ans = Integer.MIN_VALUE;
        for (int[] op : OPS) {
            int closest = arr1[0] * op[0] + arr2[0] * op[1];
            for (int i = 1; i < arr1.length; i++) {
                int curr = arr1[i] * op[0] + arr2[i] * op[1] + i;
                ans = Math.max(ans, curr - closest);
                closest = Math.min(closest, curr);
            }
        }
        return ans;
    }
}