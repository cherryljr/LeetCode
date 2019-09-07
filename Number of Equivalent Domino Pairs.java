/*
Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d]
if and only if either (a==c and b==d), or (a==d and b==c) - that is, one domino can be rotated to be equal to another domino.

Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is equivalent to dominoes[j].

Example 1:
Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
Output: 1

Constraints:
    1. 1 <= dominoes.length <= 40000
    2. 1 <= dominoes[i][j] <= 9
 */

/**
 * Approach 1: Decode value and Count Occurrence
 * 题目要求：一样的多米诺骨牌有多少对，每个骨牌是可以进行翻转的，因此只要 a,b 与 c,d 相等即可。
 *
 * 因为题目明确给出 1 <= dominoes[i][j] <= 9，因此我们可以利用 乘10 的方法进行编码来存储。（方式为：较小值*10+较大值）
 * 并且其编码后的最大值不会超过 99，因此没必要使用 HashMap 来存储编码后元素的出现次数，直接用数组即可。
 * 最后统计一样的多米诺骨牌的对数即可。
 * 统计方式有两种：
 *  1. 记各个相同骨牌的个数为 x, 则结果为：ans = sum(x*(x-1)/2)
 *  2. 直接在统计过程中进行累加，比如骨牌 [1,2] 的出现次数为 3，当改该骨牌再次出现时，说明已当前骨牌能够组成 3 对相同的，同时 count[12]++
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
        int[] count = new int[100];
        int ans = 0;
        for (int[] dominoe : dominoes) {
            int num = dominoe[0] > dominoe[1] ? 10 * dominoe[1] + dominoe[0] : dominoe[0] * 10 + dominoe[1];
            count[num]++;
        }
        for (int x : count) {
            ans += x * (x - 1) / 2;
        }
        return ans;
    }
}

/**
 * Approach 2: Decode value and Count Occurrence
 */
class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
        int[] count = new int[100];
        int ans = 0;
        for (int[] dominoe : dominoes) {
            int num = dominoe[0] > dominoe[1] ? 10 * dominoe[1] + dominoe[0] : dominoe[0] * 10 + dominoe[1];
            ans += count[num]++;
        }
        return ans;
    }
}